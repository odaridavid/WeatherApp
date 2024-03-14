package com.github.odaridavid.weatherapp

import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.SupportedLanguage
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import com.github.odaridavid.weatherapp.core.model.Units
import com.github.odaridavid.weatherapp.designsystem.organism.BottomSheetItem
import com.github.odaridavid.weatherapp.ui.settings.toBottomSheetModel
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
}
