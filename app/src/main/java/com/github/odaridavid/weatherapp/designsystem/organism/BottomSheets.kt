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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.odaridavid.weatherapp.R
import com.github.odaridavid.weatherapp.designsystem.WeatherAppTheme
import com.github.odaridavid.weatherapp.designsystem.molecule.MediumBody
import com.github.odaridavid.weatherapp.designsystem.molecule.PositiveButton
import com.github.odaridavid.weatherapp.designsystem.molecule.SmallHeadline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// todo revisit window insets for bottom of sheet cutting off button
@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun MultiSelectBottomSheet(
    title: String,
    items: List<BottomSheetItem>,
    selectedItems: List<BottomSheetItem>,
    sheetState: SheetState,
    crossinline onSaveOption: (List<BottomSheetItem>) -> Unit,
    noinline onDismiss: (() -> Unit)? = null,
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
            if (onDismiss != null) {
                onDismiss()
            }
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

            val selectedItemsState =
                remember { mutableStateListOf(*selectedItems.toTypedArray()) }

            items.forEach { item ->
                Row {
                    Checkbox(
                        checked = selectedItemsState.count { it.id == item.id } > 0,
                        onCheckedChange = { isSelected ->
                            if (isSelected) {
                                if (item.id == NONE_ID) selectedItemsState.clear()
                                // TODO use removeIf once min sdk is 24
                                else selectedItemsState.removeAll { it.id == NONE_ID }

                                selectedItemsState.add(item.copy(isSelected = true))
                            } else {
                                selectedItemsState.removeAll { it.id == item.id }
                            }
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

            MultiSelectSaveButtonSection(
                onSaveOption,
                selectedItemsState,
                sheetState = sheetState,
                coroutineScope = scope,
            )
        }
    }
}

// TODO Fix for long list like languages
@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun SingleSelectBottomSheet(
    title: String,
    items: List<BottomSheetItem>,
    selectedItem: BottomSheetItem,
    sheetState: SheetState,
    crossinline onSaveOption: (BottomSheetItem) -> Unit,
    noinline onDismiss: (() -> Unit)? = null,
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
            if (onDismiss != null) {
                onDismiss()
            }
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

            val selectedItemsState = remember { mutableStateOf(selectedItem) }

            items.forEach { item ->
                Row {
                    RadioButton(
                        selected = selectedItemsState.value.id == item.id,
                        onClick = {
                            selectedItemsState.value = item.copy(isSelected = true)
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

            SingleSelectSaveButtonSection(
                onSaveOption,
                selectedItemsState,
                sheetState = sheetState,
                coroutineScope = scope,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun SingleSelectSaveButtonSection(
    crossinline onSaveOption: (BottomSheetItem) -> Unit,
    selectedItemsState: MutableState<BottomSheetItem>,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
) {
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
            coroutineScope.launch {
                sheetState.hide()
            }
            onSaveOption(selectedItemsState.value)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun MultiSelectSaveButtonSection(
    crossinline onSaveOption: (List<BottomSheetItem>) -> Unit,
    selectedItems: SnapshotStateList<BottomSheetItem>,
    sheetState: SheetState,
    coroutineScope: CoroutineScope
) {
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
            coroutineScope.launch {
                sheetState.hide()
            }
            onSaveOption(selectedItems)
        }
    }
}

data class BottomSheetItem(
    val name: String,
    val id: Int,
    val isSelected: Boolean = false,
)

const val NONE_ID = -1
