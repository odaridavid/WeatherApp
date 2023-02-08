package com.github.odaridavid.weatherapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.HourlyWeather

// TODO Improve UI,add loading and error UI and move hardcoded strings
@Composable
fun HomeScreen(state: HomeScreenViewState, onSettingClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        HomeTopBar(cityName = state.locationName, onSettingClicked)

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

@Composable
private fun HomeTopBar(cityName: String, onSettingClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Image(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = stringResource(R.string.home_content_description_setting_icon),
            modifier = Modifier
                .defaultMinSize(40.dp)
                .clickable { onSettingClicked() }
                .padding(8.dp)
        )
    }
}

@Composable
private fun CurrentWeatherWidget(currentWeather: CurrentWeather) {
    Column {
        Text(
            text = stringResource(R.string.home_title_currently),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = currentWeather.temperature,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.h2
        )
        Text(
            text = stringResource(
                id = R.string.home_feels_like_description,
                currentWeather.feelsLike
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp),
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
            text = stringResource(R.string.home_today_forecast_title),
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
            text = hourlyWeather.forecastedTime,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.body2
        )
        Text(
            text = hourlyWeather.temperature,
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
            text = stringResource(R.string.home_weekly_forecast_title),
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
    Row(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        // TODO Flatten the data, ui model
        AsyncImage(
            model = dailyWeather.weather.first().icon,
            contentDescription = dailyWeather.weather.first().description,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically),
        )
        Text(
            text = "${dailyWeather.forecastedTime}",
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = stringResource(R.string.home_max_temp, dailyWeather.temperature.max),
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = stringResource(R.string.home_min_temp, dailyWeather.temperature.min),
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.body2
            )
        }
    }
}
