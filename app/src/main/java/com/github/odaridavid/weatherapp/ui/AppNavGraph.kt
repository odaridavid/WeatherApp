package com.github.odaridavid.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.odaridavid.weatherapp.ui.home.HomeScreen
import com.github.odaridavid.weatherapp.ui.home.HomeScreenIntent
import com.github.odaridavid.weatherapp.ui.home.HomeViewModel
import com.github.odaridavid.weatherapp.ui.settings.SettingsScreen
import com.github.odaridavid.weatherapp.ui.settings.SettingsScreenIntent
import com.github.odaridavid.weatherapp.ui.settings.SettingsScreenViewState
import com.github.odaridavid.weatherapp.ui.settings.SettingsViewModel

@Composable
fun WeatherAppScreensConfig(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Destinations.HOME.route) {
        composable(Destinations.HOME.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val state = homeViewModel
                .state
                .collectAsState()
                .value

            homeViewModel.processIntent(HomeScreenIntent.LoadWeatherData)

            HomeScreen(
                state = state,
                onSettingClicked = { navController.navigate(Destinations.SETTINGS.route) },
                onTryAgainClicked = { homeViewModel.processIntent(HomeScreenIntent.LoadWeatherData) },
                onCityNameReceived = { cityName ->
                    homeViewModel.processIntent(HomeScreenIntent.DisplayCityName(cityName = cityName))
                }
            )
        }
        composable(Destinations.SETTINGS.route) {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            val state = settingsViewModel
                .state
                .collectAsState(initial = SettingsScreenViewState())
                .value

            settingsViewModel.processIntent(SettingsScreenIntent.LoadSettingScreenData)

            SettingsScreen(
                state = state,
                onBackButtonClicked = { navController.navigate(Destinations.HOME.route) },
                onLanguageChanged = { selectedLanguage ->
                    settingsViewModel.processIntent(
                        SettingsScreenIntent.ChangeLanguage(
                            selectedLanguage
                        )
                    )
                },
                onUnitChanged = { selectedUnit ->
                    settingsViewModel.processIntent(SettingsScreenIntent.ChangeUnits(selectedUnit))
                }
            )
        }
    }
}

enum class Destinations(val route: String) {
    HOME("home"),
    SETTINGS("settings")
}
