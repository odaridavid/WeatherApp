package com.github.odaridavid.weatherapp.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.common.getCityName
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.HourlyWeather
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.ErrorTextWithAction
import com.github.odaridavid.weatherapp.designsystem.molecule.ForecastedTime
import com.github.odaridavid.weatherapp.designsystem.molecule.Subtitle
import com.github.odaridavid.weatherapp.designsystem.molecule.Temperature
import com.github.odaridavid.weatherapp.designsystem.molecule.TemperatureHeadline
import com.github.odaridavid.weatherapp.designsystem.molecule.WeatherIcon
import com.github.odaridavid.weatherapp.designsystem.organism.HomeTopBar
import com.github.odaridavid.weatherapp.designsystem.templates.LoadingScreen

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

        HomeTopBar(cityName = state.locationName, onSettingClicked)

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
    Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.half))
    ErrorTextWithAction(
        errorMessageId = errorMsgId,
        modifier = Modifier.padding(WeatherAppTheme.dimens.medium)
    ) {
        onTryAgainClicked()
    }
    Spacer(modifier = Modifier.Companion.weight(WeatherAppTheme.weight.half))
}

@Composable
private fun CurrentWeatherWidget(currentWeather: CurrentWeather) {
    Column {
        Subtitle(text = stringResource(id = R.string.home_title_currently))

        TemperatureHeadline(temperature = currentWeather.temperature)

        Subtitle(
            text = stringResource(
                id = R.string.home_feels_like_description,
                currentWeather.feelsLike
            ),
            color = MaterialTheme.colors.secondary
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HourlyWeatherWidget(hourlyWeatherList: List<HourlyWeather>) {
    Subtitle(text = stringResource(id = R.string.home_today_forecast_title))

    LazyRow(modifier = Modifier.padding(WeatherAppTheme.dimens.medium)) {
        items(hourlyWeatherList) { hourlyWeather ->
            HourlyWeatherRow(
                hourlyWeather = hourlyWeather,
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

@Composable
private fun HourlyWeatherRow(hourlyWeather: HourlyWeather, modifier: Modifier) {
    Row(modifier = modifier) {
        WeatherIcon(
            iconUrl = hourlyWeather.weather.first().icon,
            contentDescription = hourlyWeather.weather.first().description,
            modifier = Modifier
                .padding(WeatherAppTheme.dimens.extraSmall)
                .align(Alignment.CenterVertically),
        )
        Column(
            modifier = Modifier
                .padding(WeatherAppTheme.dimens.extraSmall)
                .align(Alignment.CenterVertically)
        ) {
            Temperature(text = hourlyWeather.temperature)
            ForecastedTime(text = hourlyWeather.forecastedTime)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DailyWeatherWidget(dailyWeatherList: List<DailyWeather>) {
    Subtitle(text = stringResource(id = R.string.home_weekly_forecast_title))

    LazyColumn(modifier = Modifier.padding(WeatherAppTheme.dimens.medium)) {
        items(dailyWeatherList) { dailyWeather ->
            DailyWeatherRow(dailyWeather = dailyWeather, modifier = Modifier.animateItemPlacement())
        }
    }
}

@Composable
private fun DailyWeatherRow(dailyWeather: DailyWeather, modifier: Modifier) {
    Row(
        modifier = modifier
            .padding(WeatherAppTheme.dimens.small)
            .fillMaxWidth()
    ) {
        WeatherIcon(
            iconUrl = dailyWeather.weather.first().icon,
            contentDescription = dailyWeather.weather.first().description,
            modifier = Modifier
                .padding(WeatherAppTheme.dimens.extraSmall)
                .align(Alignment.CenterVertically),
        )
        ForecastedTime(
            text = dailyWeather.forecastedTime,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.full))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Temperature(
                text = stringResource(
                    id = R.string.home_max_temp,
                    dailyWeather.temperature.max
                )
            )
            Temperature(
                text = stringResource(
                    id = R.string.home_min_temp,
                    dailyWeather.temperature.min
                )
            )
        }
    }
}
