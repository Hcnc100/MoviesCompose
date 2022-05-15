# MoviesCompose
Simple app to consumer api movies with retrofit and save result in database with room using android jetpack

### Instruccions

1. Log Up in https://www.themoviedb.org/signup
2. Get api key in https://www.themoviedb.org/settings/api
3. Up your api key in *local.properties* in yout app file, remember name.In this case is *keyApiMovies*

```kotlin
keyApiMovies="your_api_key_xxxx"
```



4. In the *build.gradle* in *defaultConfig* put:

```kotlin
def localProperties = new Properties()
localProperties.load(new FileInputStream(rootProject.file("local.properties")))
buildConfigField "String", "API_KEY_MOVIES",localProperties['keyApiMovies']
```
where *keyApiMovies* is the name that yout put in the step 3, and *API_KEY_MOVIES* is the name for your api key, for use in the inner code.

With that caun you user *BuildConfig.API_KEY_MOVIES* in *MoviesApiServices*



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
