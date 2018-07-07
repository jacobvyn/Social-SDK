package com.testapp.ecommerce;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jacob.getsocial.SocialDemoActivity;
import com.jacob.getsocial.base.Social;
import com.testapp.ecommerce.fragments.ActionBarFragment;
import com.testapp.ecommerce.fragments.ProductsListFragment;
import com.testapp.ecommerce.fragments.ProductsTypesListFragment;
import com.testapp.ecommerce.fragments.SearchFragment;
import com.testapp.ecommerce.model.DataSource;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnSearchFragmentListener,
        ProductsTypesListFragment.OnProductsTypeListFragmentListener, ActionBarFragment.Listener {

    private static final int PERMISSION_REQUEST_CODE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.action_bar_root_fragment, ActionBarFragment.newInstance(true));
        fragmentTransaction.add(R.id.search_root, SearchFragment.newInstance());
        fragmentTransaction.add(R.id.recycler_view_root, ProductsTypesListFragment.newInstance());
        fragmentTransaction.commit();

        checkPermissions();
    }

    @Override
    public void onSearch(String expression) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.recycler_view_root);
        if (fragment != null && fragment instanceof ProductsListFragment) {
            ((ProductsListFragment) fragment).setProductsList(expression);
        } else {
            setProductsListFragment(expression);
        }
    }

    @Override
    public void onDelete() {
        setProductTypesListFragment();
    }

    @Override
    public void onItemClicked(String type) {
        setProductsListFragment(type);
    }


    private void setProductsListFragment(String type) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.recycler_view_root, ProductsListFragment.newInstance(type));
        fragmentTransaction.commit();
    }

    private void setProductTypesListFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.recycler_view_root, ProductsTypesListFragment.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                DataSource.init();
            } else {
                Toast.makeText(getApplicationContext(), R.string.please_change_permissions, Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.recycler_view_root);
        if (fragment != null && fragment instanceof ProductsListFragment) {
            setProductTypesListFragment();
            return;
        }
        super.onBackPressed();
    }


    private void checkPermissions() {
        if (hasReadPermission() && hasWritePermission()) {
            DataSource.init();
        } else {
            askPermissions();
        }
    }

    private boolean hasReadPermission() {
        int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return readPermission == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasWritePermission() {
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return writePermission == PackageManager.PERMISSION_GRANTED;
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onGetSocialClicked() {
        Intent intent = new Intent(this, SocialDemoActivity.class);
        intent.putExtra(SocialDemoActivity.SOCIAL_TYPE, Social.Type.REDDIT);
        startActivity(intent);
    }
}
