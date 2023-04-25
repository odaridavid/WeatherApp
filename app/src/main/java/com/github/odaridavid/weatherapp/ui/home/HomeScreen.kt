package com.github.odaridavid.weatherapp.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.model.CurrentWeather
import com.github.odaridavid.weatherapp.core.model.DailyWeather
import com.github.odaridavid.weatherapp.core.model.HourlyWeather
import com.github.odaridavid.weatherapp.ui.getCityName

@Composable
fun HomeScreen(
    state: HomeScreenViewState,
    onSettingClicked: () -> Unit,
    onTryAgainClicked: () -> Unit,
    onCityNameReceived: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {

        LocalContext.current.getCityName(
            latitude = state.defaultLocation.latitude,
            longitude = state.defaultLocation.longitude
        ) { address ->
            val cityName = address.locality
            onCityNameReceived(cityName)
        }

        HomeTopBar(cityName = state.locationName, onSettingClicked)

        if (state.isLoading) {
            Spacer(modifier = Modifier.weight(0.5f))
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }

        if (state.errorMessageId != null) {
            Spacer(modifier = Modifier.weight(0.5f))
            ErrorText(
                errorMessageId = state.errorMessageId,
                modifier = Modifier.padding(16.dp)
            ) {
                onTryAgainClicked()
            }
            Spacer(modifier = Modifier.weight(0.5f))
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
private fun HomeTopBar(cityName: String, onSettingClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.ic_settings_dark else R.drawable.ic_settings),
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
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
private fun HourlyWeatherWidget(hourlyWeatherList: List<HourlyWeather>) {
    Text(
        text = stringResource(R.string.home_today_forecast_title),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .horizontalScroll(rememberScrollState())
    ) {

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
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically),
        )
        Column(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = hourlyWeather.temperature,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = hourlyWeather.forecastedTime,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.body2
            )
        }
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
            style = MaterialTheme.typography.subtitle1
        )
        for (dailyWeather in dailyWeatherList) {
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
        // TODO Flatten the data, ui model
        AsyncImage(
            model = dailyWeather.weather.first().icon,
            contentDescription = dailyWeather.weather.first().description,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically),
        )
        Text(
            text = dailyWeather.forecastedTime,
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

@Composable
private fun ErrorText(
    @StringRes errorMessageId: Int,
    modifier: Modifier,
    onTryAgainClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = errorMessageId),
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.body1
        )
        Button(
            onClick = { onTryAgainClicked() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.home_error_try_again),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }
}
