package com.github.odaridavid.weatherapp.ui.settings

import com.github.odaridavid.weatherapp.designsystem.organism.BottomSheetItem
import com.github.odaridavid.weatherapp.model.ExcludedData
import com.github.odaridavid.weatherapp.model.SupportedLanguage
import com.github.odaridavid.weatherapp.model.TimeFormat
import com.github.odaridavid.weatherapp.model.Units

// TODO Fix repetition of mapping
fun ExcludedData.toBottomSheetModel(isSelected: Boolean = false): BottomSheetItem {
    return BottomSheetItem(
        id = this.id,
        name = this.name,
        isSelected = isSelected
    )
}

fun BottomSheetItem.toExcludedData(): ExcludedData {
    return ExcludedData.entries.first { it.id == this.id }
}

fun TimeFormat.toBottomSheetModel(isSelected: Boolean = false): BottomSheetItem {
    return BottomSheetItem(
        id = this.ordinal,
        name = this.value,
        isSelected = isSelected
    )
}

fun BottomSheetItem.toTimeFormat(): TimeFormat {
    return TimeFormat.entries.first { it.ordinal == this.id }
}

fun Units.toBottomSheetModel(isSelected: Boolean = false): BottomSheetItem {
    return BottomSheetItem(
        id = this.ordinal,
        name = this.value,
        isSelected = isSelected
    )
}

fun BottomSheetItem.toUnits(): Units {
    return Units.entries.first { it.ordinal == this.id }
}

fun SupportedLanguage.toBottomSheetModel(isSelected: Boolean = false): BottomSheetItem {
    return BottomSheetItem(
        id = this.ordinal,
        name = this.languageName,
        isSelected = isSelected
    )
}

fun BottomSheetItem.toSupportedLanguage(): SupportedLanguage {
    return SupportedLanguage.entries.first { it.ordinal == this.id }
}
