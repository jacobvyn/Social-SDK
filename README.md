# Social SDK
 Social SDK allows for hassle-free integration of different popular social network in your application—often in few lines of code!
<br/>
     <img src="https://raw.githubusercontent.com/wiki/loopme/loopme-android-sdk/assets/static.jpg"/><br/>
     <img src="https://raw.githubusercontent.com/wiki/loopme/loopme-android-sdk/assets/static.jpg"/><br/>

## Requirements ##
Requires `Android 5.0+` and above
## Integration ##
1. Download latest version of SDK (`social-sdk-[version].aar` file) and put it in folder `libs`
2. Add dependency to `social-sdk` in your project (`build.gradle` file):
<br/>
``` repositories {
 flatDir {
    dirs 'libs'
    }
 }

 dependencies {
    api(name:'social-sdk-[version]', ext:'aar')
  }
  ```
3. Launch `SocialDemoActivity.class` with preferable social type from your activity.
```
     Intent intent = new Intent(this, SocialDemoActivity.class);
     intent.putExtra(SocialDemoActivity.SOCIAL_TYPE, Social.Type.REDDIT);
     startActivity(intent);
```

## Integration with fine-grain control ##
1. Get instance of `Social.class` providing preferable social type and container for view <br/>

        FrameLayout container = (FrameLayout) findViewById(R.id.root_container);
        Social  mReddit = SocialSdk.getInstance(YourActivity.this, container, Social.Type.REDDIT);

2. Bind lifecycle of `Social.class` instance to your activity

        @Override
        protected void onResume() {
            super.onResume();
            mReddit.onResume();
        }

        @Override
        protected void onPause() {
            super.onPause();
            mReddit.onPause();
        }
3. Provide search query <br/>

           mReddit.searchFor(query);

      That's all.
## Example ##
Get test application [here](https://github.com/jacobwyn/ECommerceDemo/tree/master/app)