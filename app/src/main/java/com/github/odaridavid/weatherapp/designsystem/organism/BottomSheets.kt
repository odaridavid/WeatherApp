package com.github.odaridavid.weatherapp.designsystem.organism

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

// todo reuse some logic for single select and multi select
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiSelectBottomSheet(
    title: String,
    items: List<BottomSheetItem>,
    selectedItems: List<BottomSheetItem>,
    sheetState: SheetState,
    onSaveState: (List<BottomSheetItem>) -> Unit,
    onDismiss: (() -> Unit)? = null,
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
            modifier = Modifier
                .fillMaxWidth()
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

            LazyColumn() {
                items(items) { item ->
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
            }
            MultiSelectSaveButtonSection(
                sheetState = sheetState,
                coroutineScope = scope,
                onSaveState = onSaveState,
                selectedItemsState = selectedItemsState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleSelectBottomSheet(
    title: String,
    items: List<BottomSheetItem>,
    selectedItem: BottomSheetItem,
    sheetState: SheetState,
    onSaveState: (BottomSheetItem) -> Unit,
    onDismiss: (() -> Unit)? = null,
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
            LazyColumn(Modifier.weight(1f, false)) {
                items(items) { item ->
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
            }
            SingleSelectSaveButtonSection(
                sheetState = sheetState,
                coroutineScope = scope,
                onSaveState = onSaveState,
                selectedItemState = selectedItemsState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleSelectSaveButtonSection(
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    onSaveState: (BottomSheetItem) -> Unit,
    selectedItemState: MutableState<BottomSheetItem>,
) {
    SaveButton(
        sheetState = sheetState,
        coroutineScope = coroutineScope,
    ) {
        onSaveState(selectedItemState.value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiSelectSaveButtonSection(
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    onSaveState: (List<BottomSheetItem>) -> Unit,
    selectedItemsState: SnapshotStateList<BottomSheetItem>,
) {
    SaveButton(
        sheetState = sheetState,
        coroutineScope = coroutineScope,
    ) {
        onSaveState(selectedItemsState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SaveButton(
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    onSaveState: () -> Unit,
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
            onSaveState()
        }
    }
}

data class BottomSheetItem(
    val name: String,
    val id: Int,
    val isSelected: Boolean = false,
)

const val NONE_ID = -1
