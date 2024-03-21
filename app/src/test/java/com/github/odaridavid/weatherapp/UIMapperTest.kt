package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.designsystem.organism.BottomSheetItem
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units
import com.github.odaridavid.weatherapp.ui.settings.toBottomSheetModel
import com.github.odaridavid.weatherapp.ui.settings.toExcludedData
import com.github.odaridavid.weatherapp.ui.settings.toSupportedLanguage
import com.github.odaridavid.weatherapp.ui.settings.toTimeFormat
import com.github.odaridavid.weatherapp.ui.settings.toUnits
import org.junit.Test

class UIMapperTest {

    @Test
    fun `when we map units to bottom sheet items, then we get the expected items`() {
        val expectedItems = listOf(
            BottomSheetItem(
                id = 0,
                name = "standard",
                isSelected = false
            ),
            BottomSheetItem(
                id = 1,
                name = "metric",
                isSelected = false
            ),
            BottomSheetItem(
                id = 2,
                name = "imperial",
                isSelected = false
            ),
        )

        val actual = Units.entries.map { it.toBottomSheetModel() }

        assert(actual == expectedItems) {
            "Expected $expectedItems but was $actual"
        }
    }

    @Test
    fun `when we map time format to bottom sheet items, then we get the expected items`() {
        val expectedItems = listOf(
            BottomSheetItem(
                id = 0,
                name = "24 hours",
                isSelected = false
            ),
            BottomSheetItem(
                id = 1,
                name = "12 hours",
                isSelected = false
            ),
        )

        val actual = TimeFormat.entries.map { it.toBottomSheetModel() }

        assert(actual == expectedItems) {
            "Expected $expectedItems but was $actual"
        }
    }

    @Test
    fun `when we map excluded data to bottom sheet items, then we get the expected items`() {
        val expectedItems = listOf(
            BottomSheetItem(
                id = 0,
                name = "CURRENT",
                isSelected = false
            ),
            BottomSheetItem(
                id = 1,
                name = "HOURLY",
                isSelected = false
            ),
            BottomSheetItem(
                id = 2,
                name = "DAILY",
                isSelected = false
            ),
            BottomSheetItem(
                id = 3,
                name = "MINUTELY",
                isSelected = false
            ),
            BottomSheetItem(
                id = 4,
                name = "ALERTS",
                isSelected = false
            ),
            BottomSheetItem(
                id = -1,
                name = "NONE",
                isSelected = false
            ),
        )

        val actual = ExcludedData.entries.map { it.toBottomSheetModel() }

        assert(actual == expectedItems) {
            "Expected $expectedItems but was $actual"
        }
    }

    @Test
    fun `when we map supported language to bottom sheet items, then we get the expected items`() {
        val expectedItems = listOf(
            BottomSheetItem(
                id = 0,
                name = "Afrikaans",
                isSelected = false
            ),
            BottomSheetItem(
                id = 1,
                name = "Albanian",
                isSelected = false
            ),
            BottomSheetItem(
                id = 2,
                name = "Arabic",
                isSelected = false
            ),
            BottomSheetItem(
                id = 3,
                name = "Azerbaijani",
                isSelected = false
            ),
        )

        val actual = SupportedLanguage.entries.take(4).map { it.toBottomSheetModel() }

        assert(actual == expectedItems) {
            "Expected: $expectedItems \n Actual: $actual"
        }
    }

    @Test
    fun `when we map bottom sheet item to supported language, then we get the expected language`() {
        val expectedLanguage = SupportedLanguage.AFRIKAANS
        val bottomSheetItem = BottomSheetItem(
            id = 0,
            name = "Afrikaans",
            isSelected = false
        )

        val actual = bottomSheetItem.toSupportedLanguage()

        assert(actual == expectedLanguage) {
            "Expected: $expectedLanguage \n Actual: $actual"
        }
    }

    @Test
    fun `when we map bottom sheet item to units, then we get the expected units`() {
        val expectedUnits = Units.METRIC
        val bottomSheetItem = BottomSheetItem(
            id = 1,
            name = "metric",
            isSelected = false
        )

        val actual = bottomSheetItem.toUnits()

        assert(actual == expectedUnits) {
            "Expected: $expectedUnits \n Actual: $actual"
        }
    }

    @Test
    fun `when we map bottom sheet item to time format, then we get the expected time format`() {
        val expectedTimeFormat = TimeFormat.TWENTY_FOUR_HOUR
        val bottomSheetItem = BottomSheetItem(
            id = 0,
            name = "24 hours",
            isSelected = false
        )

        val actual = bottomSheetItem.toTimeFormat()

        assert(actual == expectedTimeFormat) {
            "Expected: $expectedTimeFormat \n Actual: $actual"
        }
    }

    @Test
    fun `when we map bottom sheet item to excluded data, then we get the expected excluded data`() {
        val expectedExcludedData = ExcludedData.CURRENT
        val bottomSheetItem = BottomSheetItem(
            id = 0,
            name = "CURRENT",
            isSelected = false
        )

        val actual = bottomSheetItem.toExcludedData()

        assert(actual == expectedExcludedData) {
            "Expected: $expectedExcludedData \n Actual: $actual"
        }
    }
}
