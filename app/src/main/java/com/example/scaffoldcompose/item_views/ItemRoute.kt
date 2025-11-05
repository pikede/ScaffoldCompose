package com.example.scaffoldcompose.item_views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi

@Composable
fun ItemNameRoute(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    ItemNames(modifier = modifier)
}

@Composable
private fun ItemNames(
    modifier: Modifier = Modifier,
    clocksViewModel: ClocksViewModel = hiltViewModel<ClocksViewModel>(),
) {
    val clocks by clocksViewModel.clocks.collectAsStateWithLifecycle()

    ItemNames(
        clocks = clocks,
        addTime = { clocksViewModel.addTime() },
        setVisible = { clock: Clock -> clocksViewModel.setVisible(clock) }, //why isn't setVisible::clock working
        modifier = modifier.padding(40.dp)
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun ItemNames(
    clocks: List<Clock>,
    addTime: () -> Unit,
    setVisible: (Clock) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        itemsIndexed(clocks) { index, clock ->
            if (index == clocks.lastIndex) {
                addTime()
            }
            if (!clock.isVisible) {
                setVisible(clock)
            }
            Row(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = clock.getTime(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview
@Composable
fun ClocksPreview() {

}