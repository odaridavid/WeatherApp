package com.github.odaridavid.weatherapp.ui.settings

import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.core.model.TimeFormat
import com.github.odaridavid.weatherapp.designsystem.organism.BottomSheetItem

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