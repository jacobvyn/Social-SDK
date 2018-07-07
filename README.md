# Social SDK
 Social SDK allows for hassle-free integration of different popular social network in your application—often in few lines of code!
 <br/>
 <br/>
     <img src="https://github.com/jacobwyn/ECommerceDemo/blob/master/screenshots/reddit_feed.jpg" width=250/>
     <img src="https://github.com/jacobwyn/ECommerceDemo/blob/master/screenshots/reddit_detail.jpg" width=250/>

## Requirements ##
Requires `Android 5.0+` and above
## Integration ##
1. Download latest version of SDK (`social-sdk-[version].aar` file) and put it in folder `libs`
2. Add dependency to `social-sdk` in your project (`build.gradle` file):
```

repositories {
 flatDir {
    dirs 'libs'
    }
 }

 dependencies {
    api(name:'social-sdk-[version]', ext:'aar')
  }
  
```
3. Launch `SocialDemoActivity.class` with preferable social type from your activity.
<br/>

```

     Intent intent = new Intent(this, SocialDemoActivity.class);
     intent.putExtra(SocialDemoActivity.SOCIAL_TYPE, Social.Type.REDDIT);
     startActivity(intent);
     
```

## Integration with fine-grain control ##
1. Get instance of `Social.class` providing preferable social type and container for view <br/>
```
        FrameLayout container = (FrameLayout) findViewById(R.id.root_container);
        Social  mReddit = SocialSdk.getInstance(YourActivity.this, container, Social.Type.REDDIT);
        
```
2. Bind lifecycle of `Social.class` instance to your activity
```

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
        
```
3. Provide search query
```

           mReddit.searchFor(query);

```
   That's all.
## Sample ##
Get test application [here](https://github.com/jacobwyn/ECommerceDemo/tree/master/app)
