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
   *Data*
   *Testing*

# Open Questions / Things to Note ❓

```TODO```

# Things I would have loved to do 💙

1. Time formatting i.e current time

# Screenshots 📱

<img src="" width="300px"> <img src="" width="300px">


![](https://media.giphy.com/media/hWvk9iUU4uBBeyBq0k/giphy.gif)


