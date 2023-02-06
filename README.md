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

The project makes use of common android patterns in modern android codebases.

**Project Structure**

The folders are split into 4 boundaries:
 - **Core**:

   Contains the models/data classes that are independent of any framework specific dependencies and represent the business logic. 
   In a Clean Arch world you can consider these as your domain classes and interfaces.

 - **Data**:

   Contains data sources , local or remote, this is where the implementation for such is kept. All data related actions and formatting happens in this layer as well.
   It may contain framework related dependencies to orchestrate and create instances of data stores like a database or shared preference etc.
   One common pattern used in this area is the repository pattern, which mediates data sources and acts as a source of truth to the consumer.

 - **DI**:

   This acts as the glue between the core ,data and UI.The UI relies on the core models and interfaces which are implemented in data.

 - **UI**:

   Contains the presentation layer of the app, the screen components and viewmodels. Framework specific dependencies are best suited for this layer.
   In this layer MVI is also used, it looks similar to MVVM but the difference is the actions from a screen a.k.a intents e.g ```HomeScreenIntent``` are predefined and are finite,making the
   the screen state a bit more predictable and it's easier to scan through what actions are possible from a given screen.

   The screen state e.g ```HomeScreenViewState``` is also modelled as a class with immutable properties and makes state management way easier by reducing the state whenever their is a new update received.
   Some design patterns that can be seen here are the Observer pattern when consuming the flow -> state flows in the composables and provides a reactive app.

![Add flow diagram here](/docs/MVI.png)

**Testing**

The data layer is unit tested by mocking out external dependencies and the ui layer on the viewmodels, an integration test
is written that makes use of fake,so as to mimic the real scenario as much as possible over using mocks, which would also turn it to a unit test.

# Technologies 🔨

**Language :** Kotlin
 - Has first class support and a tonne of modern language features to leverage on and what I use on a daily basis.

**Libraries :**

  *UI*
- [Compose](https://developer.android.com/jetpack/compose) : Preferred way to build modern android apps,this will save us from future tech debt and brings in code cohesion by using kotlin for everything.

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

# Things I would have loved to do/On the RoadMap 💙

1. Time formatting i.e current time, 12hr / 24hr system.
2. Split debug and release build i.e Better app icon for debug and release and other environment settings.
3. Define a better app theme.
4. Integrate CI Pipeline with lint checks,code formatting and code signing
5. Better issue observability i.e logging errors on a dashboard somewhere and following user session journeys.
6. Fine grained error handling for API errors.
7. Setup for performance monitoring i.e Baseline Profiles, Memory Check i.e leak canary etc.
8. Add a splash screen and smooth onboarding process.
9. Notification for weather alerts and current day forecast.
10. Support for a weather widget
11. Consume more API data once current feature set is polished i.e humidity,wind speed etc.

# Screenshots 📱

<img src="" width="300px"> <img src="" width="300px">


![](https://media.giphy.com/media/hWvk9iUU4uBBeyBq0k/giphy.gif)


