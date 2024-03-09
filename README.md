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
OPEN_WEATHER_ICONS_URL= https://openweathermap.org/img/wn/
```

Check for one under  [`Api Keys`](https://home.openweathermap.org/api_keys)


*Environment*
- Built on A.S Hedgehog+
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

# Other Stuff 📦

*Dependency Updates*
They are scheduled to run every week based on the update dependencies action in the workflow.

*Code style*
For now there is no strict adherence to a code style, but the project is formatted using the default android studio formatter.
You can run `./gradlew detekt` to check for any code smells and `./gradlew ktlint` to check for any linting issues.
Alternatively for ktlint the [IDE plugin](https://pinterest.github.io/ktlint/latest/install/setup/#recommended-setup) might be a much better option :)
Or setting up a [pre-commit/pre-push hook](https://pinterest.github.io/ktlint/latest/install/cli/#git-hooks) to run the checks before a commit is made or pushed.

# Technologies 🔨

**Language :** [Kotlin](https://github.com/JetBrains/kotlin)

**Libraries :**
<details>
  <summary>UI</summary> 
  <a href="https://developer.android.com/jetpack/compose">Compose</a><br>
  <a href="https://coil-kt.github.io/coil/compose/">Coil</a><br>
  <a href="https://developer.android.com/guide/playcore/in-app-updates">InAppUpdate</a>
</details>

<details>
  <summary>Data</summary> 
  <a href="https://square.github.io/retrofit/">Retrofit</a><br>
  <a href="https://square.github.io/okhttp/">OkHTTP</a><br>
  <a href="https://kotlinlang.org/docs/serialization.html">kotlinx.serialization</a><br>
  <a href="https://developer.android.com/topic/libraries/architecture/datastore">Preference Data Store</a>
</details>

<details>
  <summary>Testing</summary> 
  <a href="https://junit.org/junit4/">JUnit</a><br>
  <a href="https://mockk.io/">Mockk</a><br>
  <a href="https://truth.dev/">Truth</a><br>
  <a href="https://github.com/cashapp/turbine">Turbine</a>
</details>

<details>
  <summary>Tooling/Project setup</summary>
  <a href="https://github.com/google/secrets-gradle-plugin">Gradle secrets plugin</a><br>
  <a href="https://developer.android.com/training/dependency-injection/hilt-android">Hilt (DI)</a><br>
  <a href="https://firebase.google.com/docs">Firebase - Crashlytics, Performance</a><br>
  <a href="https://www.bitrise.io/">Bitrise</a><br>
  <a href="https://about.codecov.io/">Codecov</a><br>
  <a href="https://github.com/detekt/detekt">Detekt</a><br>
  <a href="https://ktlint.github.io/">Ktlint</a><br>
  <a href="https://square.github.io/leakcanary/">LeakCanary</a><br>
  <a href="https://github.com/mikepenz/AboutLibraries">About Libraries</a><br>
  <a href="https://kotlinlang.org/docs/multiplatform.html">KMM</a>
</details>



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


