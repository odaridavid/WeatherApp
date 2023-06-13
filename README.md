### Weather App

[![Build Status](https://app.bitrise.io/app/80f9b4627fc90757/status.svg?token=3KnRQl0WRfDT5UTzPDiRgA&branch=develop)](https://app.bitrise.io/app/80f9b4627fc90757)
[![codecov](https://codecov.io/gh/odaridavid/WeatherApp/branch/develop/graph/badge.svg?token=eZcGjGhF83)](https://codecov.io/gh/odaridavid/WeatherApp)

*Summary*

A simple weather app that gets your location and displays the forecast for the current day and a few days after that.

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


*Environment*
- Built on A.S Hedgehog
- JDK 17

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

**Language :** [Kotlin](https://github.com/JetBrains/kotlin)

**Libraries :**

  *UI*
- [Compose](https://developer.android.com/jetpack/compose)
- [Coil](https://coil-kt.github.io/coil/compose/https://coil-kt.github.io/coil/compose/) 

  *Data*
- [Retrofit](https://square.github.io/retrofit/)
- [OkHTTP](https://square.github.io/okhttp/)
- [kotlinx.serialization](https://kotlinlang.org/docs/serialization.html)
- [Preference Data Store](https://developer.android.com/topic/libraries/architecture/datastore) 

   *Testing*
- [Junit](https://junit.org/junit4/)
- [Mockk](https://mockk.io/) 
- [Truth](https://truth.dev/)
- [Turbine](https://github.com/cashapp/turbine)

   *Tooling/Project setup*
- [Gradle secrets plugin](https://github.com/google/secrets-gradle-plugin)
- [Hilt(DI)](https://developer.android.com/training/dependency-injection/hilt-android)
- [Firebase - Crashlytics](https://firebase.google.com/docs/crashlytics)


# LICENSE

```
   Copyright 2023 David Odari

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
```


# Screenshots 📱

|               Home       (Light Theme)                |                  Home  (Dark Theme)                   |
|:-----------------------------------------------------:|:-----------------------------------------------------:|
| <img src="/docs/screenshots/white.png" width="300px"> | <img src="/docs/screenshots/black.png" width="300px"> |

|                         Settings                          |
|:---------------------------------------------------------:|
| <img src="/docs/screenshots/settings.jpeg" width="300px"> |

|                        Error                        |
|:---------------------------------------------------:|
| <img src="/docs/screenshots/drk.png" width="300px"> |


![](https://media.giphy.com/media/hWvk9iUU4uBBeyBq0k/giphy.gif)


