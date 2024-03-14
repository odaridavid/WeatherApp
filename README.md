### Weather App

[![Build Status](https://app.bitrise.io/app/80f9b4627fc90757/status.svg?token=3KnRQl0WRfDT5UTzPDiRgA&branch=develop)](https://app.bitrise.io/app/80f9b4627fc90757)
[![codecov](https://codecov.io/gh/odaridavid/WeatherApp/branch/develop/graph/badge.svg?token=eZcGjGhF83)](https://codecov.io/gh/odaridavid/WeatherApp)

*Summary*

A simple weather app that gets your location and displays the forecast for the current day and a few
days after that.

*API :* [OpenWeatherMap](https://openweathermap.org/api)

Reason for choosing mentioned API :

- 1000 free api calls per day, good for a small project.
- Ability to specify different units in requests and receive a formatted response based on the unit
  i.e imperial/metric etc.
- Api response can also be modified to include the amount of needed data all with one call ,
  contains icons for different conditions and has multilingual support if adopting for a wider
  audience is ever needed.
- They have a large user base and handle millions of requests, if the app were ever to scale,
  there's confidence on the api providing high availability.
- Has capabilities for alerts for severe weather conditions

More info on how to make an api call [here](https://openweathermap.org/api/one-call-3#multi).

# Pre-requisite 📝

In your `local.properties` you will need to add your Open Weather API key and copy the urls in.

```properties
OPEN_WEATHER_API_KEY=YOUR KEY
OPEN_WEATHER_BASE_URL=https://api.openweathermap.org
OPEN_WEATHER_ICONS_URL=https://openweathermap.org/img/wn/
```

Check for one under  [`Api Keys`](https://home.openweathermap.org/api_keys)

*Environment*

- Built on A.S Hedgehog+
- JDK 17

# Design/Architectural decisions 📐

The project makes use of common android patterns in modern android codebases.

**Project Structure**

The folders are split into 4 boundaries:
<details>
  <summary>Core</summary> 
 Contains the models/data classes that are independent of any framework specific dependencies and represent the business logic. 
   In a Clean Arch world you can consider these as your domain classes and interfaces.
</details>

<details>
  <summary>Data</summary>
  Contains data sources , local or remote, this is where the implementation for such is kept. All
  data related actions and formatting happens in this layer as well.
  It may contain framework related dependencies to orchestrate and create instances of data stores
  like a database or shared preference etc.
  One common pattern used in this area is the repository pattern, which mediates data sources and
  acts as a source of truth to the consumer.
</details>

<details>
  <summary>DI</summary>
  This acts as the glue between the core ,data and UI.The UI relies on the core models and
  interfaces which are implemented in data.
</details>

<details>
  <summary>UI</summary>
  Contains the presentation layer of the app, the screen components and viewmodels. Framework
  specific dependencies are best suited for this layer.
  In this layer MVI is also used, it looks similar to MVVM but the difference is the actions from a
  screen a.k.a intents e.g ```HomeScreenIntent``` are predefined and are finite,making the
  the screen state a bit more predictable and it's easier to scan through what actions are possible
  from a given screen.

The screen state e.g ```HomeScreenViewState``` is also modelled as a class with immutable
properties and makes state management way easier by reducing the state whenever their is a new
update received.
Some design patterns that can be seen here are the Observer pattern when consuming the flow ->
state flows in the composables and provides a reactive app.
</details>

![Add flow diagram here](/docs/MVI.png)

<details>
  <summary>Testing</summary>

The data layer is unit tested by mocking out external dependencies and the ui layer on the
viewmodels, an integration test is written that makes use of fake,so as to mimic the real scenario
as much as possible over using mocks, which would also turn it to a unit test.
</details>

# Other Stuff 📦

*Code style*

For now there is no strict adherence to a code style, but the project is formatted using the default
android studio formatter.
You can run `./gradlew detekt` to check for any code smells and `./gradlew ktlint` to check for any
linting issues.
Alternatively for ktlint
the [IDE plugin](https://pinterest.github.io/ktlint/latest/install/setup/#recommended-setup) might
be a much better option :)
Or setting up
a [pre-commit/pre-push hook](https://pinterest.github.io/ktlint/latest/install/cli/#git-hooks) to
run the checks before a commit is made or pushed.

*CI/CD*

The project is built on Bitrise and the workflow is maintained from its dashboard.Github actions are
responsible for
dependency updates and running code quality checks ,defined in the `.github/workflows` folder.

*Design System*

Under the `designsystem` package ,it follows a tiered approach to styling the app i.e
<details>
<summary>Atoms (Smallest Components)</summary>
Typography:
Define font styles, sizes, and weights for headers, paragraphs, and other text elements.

Color Palette:
Establish a color palette with primary, secondary, and accent colors. Specify their usage in
different contexts.

Icons:
Design a set of basic icons that represent common actions or concepts. Ensure consistency in style
and sizing.

Buttons:
Create button styles with variations for primary, secondary, and tertiary actions. Include states
like hover and disabled.

Input Fields:
Design consistent styles for text inputs, checkboxes, radio buttons, and other form elements.
</details>
<details>
<summary>Molecules (Simple Components)</summary>
Form Elements:
Combine atoms to create complete form components. Ensure consistency in spacing and alignment.

Cards:
Combine text, images, and buttons to create card components. Define variations for different use
cases.

Badges:
Assemble icons and text to create badge components for notifications or status indicators.

Avatars:
Design avatar components for user profiles, incorporating images or initials.
</details>
<details>
<summary>Organisms (Complex Components)</summary>
Navigation Bars:
Create a consistent navigation bar design that includes menus, icons, and navigation elements.

Headers and Footers:
Define headers and footers with appropriate spacing, logos, and navigation links.

Lists:
Assemble atoms and molecules to create list components, incorporating variations like simple lists,
detailed lists, and nested lists.

Modals:
Design modal components for overlays or pop-ups, ensuring consistency in styles and behavior.
</details>
<details>
<summary>Templates (Page-Level Structures)</summary>
Page Layouts:
Establish consistent layouts for different types of pages (e.g., home page, product page, settings 
page).

Grid Systems:
Define grid systems that ensure alignment and consistency across various screen sizes.
</details>

*Performance*

The app is monitored using Firebase Performance and Crashlytics,for performance it's using the
default
traces but can be extended as the app grows to monitor specific parts of the app that might be slow.
LeakCanary is also used to monitor for any memory leaks that might occur in debug mode.

*Build Times*

The current CI build time , factoring in the project size, the number of tests etc.

| Task                                    | Avg Time |
|-----------------------------------------|----------|
| Build -  Bitrise                        | 4m 30s   |
| Code Analysis    - Github Actions       | 6m 30s   |
| Update Dependencies    - Github Actions | 8m 30s   |

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

|                               Light Theme                                |                               Dark Theme                                |
|:------------------------------------------------------------------------:|:-----------------------------------------------------------------------:|
|       <img src="/docs/screenshots/(Light)Main.png" width="250px">        |       <img src="/docs/screenshots/(Dark)Main.png" width="250px">        |
|     <img src="/docs/screenshots/(Light)Settings.png" width="250px">      |     <img src="/docs/screenshots/(Dark)Settings.png" width="250px">      |
| <img src="/docs/screenshots/(Light)Settings-Exclude.png" width="250px">  | <img src="/docs/screenshots/(Dark)Settings-Exclude.png" width="250px">  |
|       <img src="/docs/screenshots/(Light)About.png" width="250px">       |       <img src="/docs/screenshots/(Dark)About.png" width="250px">       |
|       <img src="/docs/screenshots/(Light)Error.png" width="250px">       |       <img src="/docs/screenshots/(Dark)Error.png" width="250px">       |
| <img src="/docs/screenshots/(Light)Settings-Language.png" width="250px"> | <img src="/docs/screenshots/(Dark)Settings-Language.png" width="250px"> |
|   <img src="/docs/screenshots/(Light)Settings-Time.png" width="250px">   |   <img src="/docs/screenshots/(Dark)Settings-Time.png" width="250px">   |
|         <img src="/docs/screenshots/Excluded.png" width="250px">         |                                    -                                    |

![](https://media.giphy.com/media/hWvk9iUU4uBBeyBq0k/giphy.gif)


