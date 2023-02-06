### Weather App

*Summary*

A simple weather app that displays the forecast for the current day and a few days after that.

*API :* [OpenWeatherMap](https://openweathermap.org/api)

Reason for choosing mentioned API :
- 1000 free api calls per day, good for a small project.
- Ability to specify different units in requests and receive a formatted response based on the unit i.e imperial/metric etc.
- Api response can also be modified to include the amount of needed data all with one call ,
contains icons for different conditions and has multilingual support if adopting for a wider audience is ever needed.
- They have a large user base and handle millions of requests, if the app were ever to scale, there's confidence on the api providing high availability.
- Has capabilities for alerts for severe weather conditions

More info on how to make an api call [here](https://openweathermap.org/api/one-call-3#multi).

# Pre-requisite 📝

In your `local.properties` you will need to add your Open Weather API key and copy the urls in.

```properties
OPEN_WEATHER_API_KEY = YOUR KEY
OPEN_WEATHER_BASE_URL=https://api.openweathermap.org
OPEN_WEATHER_ICONS_URL= http://openweathermap.org/img/wn/
```

Check for one under  [`Api Keys`](https://home.openweathermap.org/api_keys)

For now my key can be used.(TBD)

# Design/Architectural decisions 📐

```TODO```

# Technologies 🔨

*Language :* Kotlin
 - Has first class support and a tonne of modern language features to leverage on and what I use on a daily basis.

*Libraries :*
   *UI*
     - [Compose](https://developer.android.com/jetpack/compose) : Preferred way to build modern android apps,this will save us from 
future tech debt and brings in code cohesion by using kotlin for everything.
   *Data*
     - [Retrofit](https://square.github.io/retrofit/) : Familiarity and battle tested in many apps with RESTful APIS.
     - [OkHTTP](https://square.github.io/okhttp/)
     - [kotlinx.serialization](https://kotlinlang.org/docs/serialization.html) : Has jetbrains support hence blends in seamlessly with Kotlin.
     - [Preference Data Store](https://developer.android.com/topic/libraries/architecture/datastore) : For settings screen key - value pairs
   *Testing*
     - [Junit](https://junit.org/junit4/)
     - [Mockk](https://mockk.io/) : Mocking library that works well with Kotlin.
     - [Truth](https://truth.dev/) : Better assertions and better than vanilla junit assertions on objects.
     - [Turbine](https://github.com/cashapp/turbine) : For easier testing of flows.
   *Tooling/Project setup*
     - [Gradle secrets plugin](https://github.com/google/secrets-gradle-plugin) : Helps prevent checking secrets into vcs.
     - [Hilt(DI)](https://developer.android.com/training/dependency-injection/hilt-android) : Easier to use than Dagger and less stressing about manually wiring your modules.

# Open Questions / Things to Note ❓

```TODO```

# Things I would have loved to do 💙

1. Time formatting i.e current time.
2. Add better app icon for debug and release.
3. Define a better app theme.

# Screenshots 📱

<img src="" width="300px"> <img src="" width="300px">


![](https://media.giphy.com/media/hWvk9iUU4uBBeyBq0k/giphy.gif)


