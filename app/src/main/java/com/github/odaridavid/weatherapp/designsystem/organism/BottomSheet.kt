package com.github.odaridavid.weatherapp.designsystem.organism

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.core.model.ExcludedData
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.MediumBody
import com.github.odaridavid.weatherapp.designsystem.molecule.PositiveButton
import com.github.odaridavid.weatherapp.designsystem.molecule.SmallHeadline

// todo use excluded data for now ,but refactor to be generic for other setting options
// todo revisit window insets for bottom of sheet cutting off button
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    title: String,
    items: List<ExcludedData>,
    selectedExcludedData: List<ExcludedData>,
    onDismiss: () -> Unit,
    onSaveOption: (List<ExcludedData>) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        windowInsets = WindowInsets.navigationBars,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SmallHeadline(
                text = title,
                modifier = Modifier.padding(
                    vertical = WeatherAppTheme.dimens.small,
                    horizontal = WeatherAppTheme.dimens.medium
                )
            )

            val selectedItems =
                remember { mutableStateListOf(*selectedExcludedData.toTypedArray()) }

            items.forEach { item ->
                var isChecked by remember { mutableStateOf(selectedExcludedData.contains(item)) }
                Row {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isSelected ->
                            if (isSelected) {
                                selectedItems.add(item)
                            } else {
                                selectedItems.remove(item)
                            }
                            isChecked = isSelected
                        }
                    )
                    MediumBody(
                        text = item.name,
                        modifier = Modifier.padding(
                            horizontal = WeatherAppTheme.dimens.small,
                            vertical = WeatherAppTheme.dimens.medium
                        )
                    )
                }
            }

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                PositiveButton(
                    text = stringResource(id = R.string.settings_save),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(WeatherAppTheme.dimens.medium)

                ) {
                    onSaveOption(selectedItems)
                }
            }
        }
    }
}
