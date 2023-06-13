package com.github.odaridavid.weatherapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.common.getCityName
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.HourlyWeather
import com.github.odaridavid.weatherapp.designsystem.Body
import com.github.odaridavid.weatherapp.designsystem.ErrorTextWithAction
import com.github.odaridavid.weatherapp.designsystem.LoadingScreen
import com.github.odaridavid.weatherapp.designsystem.Subtitle
import com.github.odaridavid.weatherapp.designsystem.TemperatureText
import com.github.odaridavid.weatherapp.designsystem.TopBar
import com.github.odaridavid.weatherapp.designsystem.WeatherIcon

@Composable
fun HomeScreen(
    state: HomeScreenViewState,
    onSettingClicked: () -> Unit,
    onTryAgainClicked: () -> Unit,
    onCityNameReceived: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        LocalContext.current.getCityName(
            latitude = state.defaultLocation.latitude,
            longitude = state.defaultLocation.longitude
        ) { address ->
            val cityName = address.locality
            onCityNameReceived(cityName)
        }

        TopBar(cityName = state.locationName, onSettingClicked)

        if (state.isLoading) {
            LoadingScreen()
        }

        if (state.errorMessageId != null) {
            ErrorScreen(state.errorMessageId, onTryAgainClicked)
        }

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
private fun ColumnScope.ErrorScreen(errorMsgId: Int, onTryAgainClicked: () -> Unit) {
    Spacer(modifier = Modifier.weight(0.5f))
    ErrorTextWithAction(
        errorMessageId = errorMsgId,
        modifier = Modifier.padding(16.dp)
    ) {
        onTryAgainClicked()
    }
    Spacer(modifier = Modifier.Companion.weight(0.5f))
}

@Composable
private fun CurrentWeatherWidget(currentWeather: CurrentWeather) {
    Column {
        Subtitle(text = stringResource(id = R.string.home_title_currently))

        Text(
            text = currentWeather.temperature,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.h2
        )

        Subtitle(
            text = stringResource(
                id = R.string.home_feels_like_description,
                currentWeather.feelsLike
            ),
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
private fun HourlyWeatherWidget(hourlyWeatherList: List<HourlyWeather>) {
    Subtitle(text = stringResource(id = R.string.home_today_forecast_title))

    LazyRow(modifier = Modifier.padding(16.dp)) {
        items(hourlyWeatherList) { hourlyWeather ->
            HourlyWeatherRow(hourlyWeather = hourlyWeather)
        }
    }
}

@Composable
private fun HourlyWeatherRow(hourlyWeather: HourlyWeather) {
    Row {
        WeatherIcon(
            iconUrl = hourlyWeather.weather.first().icon,
            contentDescription = hourlyWeather.weather.first().description,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically),
        )
        Column(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically)
        ) {
            Body(
                text = hourlyWeather.temperature,
                modifier = Modifier.padding(4.dp)
            )
            Body(
                text = hourlyWeather.forecastedTime,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
private fun DailyWeatherWidget(dailyWeatherList: List<DailyWeather>) {
    Subtitle(text = stringResource(id = R.string.home_weekly_forecast_title))

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(dailyWeatherList) { dailyWeather ->
            DailyWeatherRow(dailyWeather = dailyWeather)
        }
    }
}

@Composable
private fun DailyWeatherRow(dailyWeather: DailyWeather) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        WeatherIcon(
            iconUrl = dailyWeather.weather.first().icon,
            contentDescription = dailyWeather.weather.first().description,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically),
        )
        Body(
            text = dailyWeather.forecastedTime,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            TemperatureText(
                stringRes = R.string.home_max_temp,
                value = dailyWeather.temperature.max
            )
            TemperatureText(
                stringRes = R.string.home_min_temp,
                value = dailyWeather.temperature.min
            )
        }
    }
}
