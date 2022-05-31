# MoviesCompose
Simple app to consumer api movies with retrofit and save result in database with room using android jetpack

### Instruccions

1. Create or select your ptoject in https://console.cloud.google.com/
2. Enable *Maps SDK for Android* and generate your api key
3. Up your api key in *local.properties* in yout app file, remember name.In this case is *MAPS_API_KEY*

```kotlin
MAPS_API_KEY="your_api_key_xxxx"
```



4. Verify that has this in your *AndroidManifest*

 <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="${MAPS_API_KEY}" />
 
 This is possible for *id 'com.google.android.libraries.mapsplatform.secrets-gradle-plugin'* in your file *build.gradle*


### Note 

This project is not complete, as the map compositing library does not have the *snapshot* functions, to take a picture of the tracking.
the image will be replaced by the app logo in the main screen items



## Screenshots
### Splash
<p>
  <img src="https://i.imgur.com/fYfSFJV.png" alt="splash" width="200"/>
</p>

### MainScreen
<p>
  <img src="https://i.imgur.com/dqG8SOW.png" alt="main" width="200"/>
  <img src="https://i.imgur.com/yTfs9pw.png" alt="details" width="200"/>
</p>

### SearchScreen
<p>
  <img src="https://i.imgur.com/aqqsc4K.png" alt="start-search" width="200"/>
  <img src="https://i.imgur.com/w8VL3aC.png" alt="search" width="200"/>
  <img src="https://i.imgur.com/2GVcwHE.png" alt="results" width="200"/>
  <img src="https://i.imgur.com/BKel5j7.png" alt="details-search" width="200"/>
  <img src="https://i.imgur.com/SuvEQan.png" alt="no_found" width="200"/>
</p>
