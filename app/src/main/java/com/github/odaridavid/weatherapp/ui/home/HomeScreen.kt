package com.github.odaridavid.weatherapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.HourlyWeather

// TODO Improve UI,add loading and error UI and move hardcoded strings
@Composable
fun HomeScreen(state: HomeScreenViewState, onSettingClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = "Setting Icon",
            modifier = Modifier
                .clickable { onSettingClicked() }
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            state.weather?.current?.let { currentWeather ->
                CurrentWeatherWidget(currentWeather = currentWeather)
            }
            state.weather?.hourly?.let { hourlyWeather ->
                HourlyWeatherWidget(hourlyWeatherList = hourlyWeather)
            }
            state.weather?.daily?.let { dailyWeather ->
                DailyWeatherWidget(dailyWeatherList = dailyWeather)
            }
        }
    }
}

@Composable
private fun CurrentWeatherWidget(currentWeather: CurrentWeather) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Now",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = "City Name, Address",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.h5
        )
        Text(
            text = "${currentWeather.temperature}",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.h2
        )
        Text(
            text = "Feels like ${currentWeather.feelsLike}",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
private fun HourlyWeatherWidget(hourlyWeatherList: List<HourlyWeather>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Today's Forecast",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(4.dp),
        )
        for (hourlyWeather in hourlyWeatherList) {
            HourlyWeatherRow(hourlyWeather = hourlyWeather)
        }
    }
}

@Composable
private fun HourlyWeatherRow(hourlyWeather: HourlyWeather) {
    Row {
        AsyncImage(
            model = hourlyWeather.weather.first().icon,
            contentDescription = hourlyWeather.weather.first().description,
            modifier = Modifier.padding(4.dp),
        )
        Text(
            text = "${hourlyWeather.forecastedTime}",
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "${hourlyWeather.temperature}",
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun DailyWeatherWidget(dailyWeatherList: List<DailyWeather>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Weekly Forecast",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(4.dp),
        )
        for (dailyWeather in dailyWeatherList) {
            DailyWeatherRow(dailyWeather = dailyWeather)
        }
    }
}

@Composable
private fun DailyWeatherRow(dailyWeather: DailyWeather) {
    Row {
        // TODO Flatten the data
        AsyncImage(
            model = dailyWeather.weather.first().icon,
            contentDescription = dailyWeather.weather.first().description,
            modifier = Modifier.padding(4.dp),
        )
        Text(
            text = "${dailyWeather.forecastedTime}",
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "${dailyWeather.temperature}",
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.body2
        )
    }
}
