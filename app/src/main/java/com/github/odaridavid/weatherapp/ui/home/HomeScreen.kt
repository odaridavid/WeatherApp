package com.github.odaridavid.weatherapp.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.common.getCityName
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.LargeLabel
import com.github.odaridavid.weatherapp.designsystem.molecule.MediumBody
import com.github.odaridavid.weatherapp.designsystem.molecule.RemoteImage
import com.github.odaridavid.weatherapp.designsystem.organism.ForecastedTime
import com.github.odaridavid.weatherapp.designsystem.organism.HomeTopBar
import com.github.odaridavid.weatherapp.designsystem.organism.Temperature
import com.github.odaridavid.weatherapp.designsystem.organism.TemperatureHeadline
import com.github.odaridavid.weatherapp.designsystem.templates.ErrorScreen
import com.github.odaridavid.weatherapp.designsystem.templates.LoadingScreen
import com.github.odaridavid.weatherapp.model.CurrentWeather
import com.github.odaridavid.weatherapp.model.DailyWeather
import com.github.odaridavid.weatherapp.model.HourlyWeather

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
        } else {
            state.weather?.current?.let { currentWeather ->
                CurrentWeatherWidget(currentWeather = currentWeather)
            } ?: run {
                EmptySectionWidget(
                    label = stringResource(id = R.string.home_title_currently),
                    weatherType = stringResource(id = R.string.home_weather_type_currently)
                )
            }

            state.weather?.hourly?.let { hourlyWeather ->
                HourlyWeatherWidget(hourlyWeatherList = hourlyWeather)
            } ?: run {
                EmptySectionWidget(
                    label = stringResource(id = R.string.home_today_forecast_title),
                    weatherType = stringResource(id = R.string.home_weather_type_hourly)
                )
            }

            state.weather?.daily?.let { dailyWeather ->
                DailyWeatherWidget(dailyWeatherList = dailyWeather)
            } ?: run {
                EmptySectionWidget(
                    label = stringResource(id = R.string.home_weekly_forecast_title),
                    weatherType = stringResource(id = R.string.home_weather_type_daily)
                )
            }
        }
    }
}

@Composable
private fun EmptySectionWidget(label: String, weatherType: String) {
    Column {
        LargeLabel(
            text = label,
            modifier = Modifier.padding(
                horizontal = WeatherAppTheme.dimens.medium,
                vertical = WeatherAppTheme.dimens.small
            )
        )
        MediumBody(
            text = stringResource(R.string.home_enable_weather_in_settings, weatherType),
            modifier = Modifier
                .fillMaxWidth()
                .padding(WeatherAppTheme.dimens.medium),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CurrentWeatherWidget(currentWeather: CurrentWeather) {
    Column {
        LargeLabel(
            text = stringResource(id = R.string.home_title_currently),
            modifier = Modifier.padding(
                horizontal = WeatherAppTheme.dimens.medium,
                vertical = WeatherAppTheme.dimens.small
            )
        )

        TemperatureHeadline(temperature = currentWeather.temperature)

        LargeLabel(
            text = stringResource(
                id = R.string.home_feels_like_description,
                currentWeather.feelsLike
            ),
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(
                horizontal = WeatherAppTheme.dimens.medium,
                vertical = WeatherAppTheme.dimens.small
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HourlyWeatherWidget(hourlyWeatherList: List<HourlyWeather>) {
    LargeLabel(
        text = stringResource(id = R.string.home_today_forecast_title),
        modifier = Modifier.padding(
            horizontal = WeatherAppTheme.dimens.medium,
            vertical = WeatherAppTheme.dimens.small
        )
    )

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
        RemoteImage(
            url = hourlyWeather.weather.first().icon,
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
    LargeLabel(
        text = stringResource(id = R.string.home_weekly_forecast_title),
        modifier = Modifier.padding(
            horizontal = WeatherAppTheme.dimens.medium,
            vertical = WeatherAppTheme.dimens.small
        )
    )

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
        RemoteImage(
            url = dailyWeather.weather.first().icon,
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
        Spacer(modifier = Modifier.weight(WeatherAppTheme.weight.FULL))
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
