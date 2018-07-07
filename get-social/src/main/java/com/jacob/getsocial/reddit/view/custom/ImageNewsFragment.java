package com.jacob.getsocial.reddit.view.custom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacob.getsocial.R;
import com.jacob.getsocial.reddit.data.model.News;
import com.squareup.picasso.Picasso;

public class ImageNewsFragment extends BaseFragment {

    public static ImageNewsFragment newInstance(News news) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEWS, news);
        ImageNewsFragment fragment = new ImageNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_image_news_image);
        TextView author = (TextView) view.findViewById(R.id.fragment_image_news_author);
        TextView desc = (TextView) view.findViewById(R.id.fragment_image_news_description);
        author.setText(mNews.getAuthor());
        desc.setText(mNews.getTitle());
        Picasso.get().load(mNews.getUrl()).into(imageView);
    }
}
