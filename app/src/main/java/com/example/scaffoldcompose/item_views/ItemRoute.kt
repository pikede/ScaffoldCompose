package com.example.scaffoldcompose.item_views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch

@Composable
fun ItemNameRoute(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    ItemNames(
        navController = navController,
        modifier = modifier
    )
}

@Composable
private fun ItemNames(
    navController: NavController,
    modifier: Modifier = Modifier,
    itemViewModel: ItemViewModel = hiltViewModel<ItemViewModel>(),
) {
    val state by itemViewModel.state.collectAsStateWithLifecycle()

    ItemNames(
        state = state,
        navController = navController,
        searchForText = { itemViewModel.searchFor(it) },
        getMore = { itemViewModel.getMore() },
        modifier = modifier.padding(40.dp)
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun ItemNames(
    state: ItemsViewState,
    navController: NavController,
    searchForText: (String) -> Unit,
    getMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (!scrollState.isScrollInProgress && scrollState.firstVisibleItemIndex != 0)
                        coroutineScope.launch { scrollState.animateScrollToItem(0) }
                }
            ) {
                Icon(Icons.Filled.AddCircle, "Top")
            }
        }) { paddingValues ->

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            item {
                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        searchForText(it)
                    }
                )
            }
            itemsIndexed(state.itemNames) { index, it ->
                if (index == state.itemNames.lastIndex) {
//                    getMore()
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable {
                            /*navController.navigate(DestinationRoutes.Locations(locationId = it.id)) {
                                launchSingleTop = true
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }*/
                        }) {
                    Text(text = it.name)
                }
            }
            if (state.isLoading) {
                item {
                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun CharacterResultsPreview() {
    ItemNames(
        state = ItemsViewState(
            itemNames = listOf()
        ),
        getMore = {},
        navController = NavController(LocalContext.current),
        searchForText = {}
    )
}