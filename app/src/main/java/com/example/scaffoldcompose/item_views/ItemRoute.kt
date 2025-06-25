package com.example.scaffoldcompose.item_views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
        modifier = modifier.padding(40.dp)
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun ItemNames(
    state: ItemsViewState,
    navController: NavController,
    searchForText: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    LazyColumn(modifier) {
        item {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    searchForText(it)
                }
            )
        }
        items(state.itemNames) {
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
    }
}

@Preview
@Composable
fun CharacterResultsPreview() {
    /*ItemNames(
        state = ItemsViewState(
            itemNames = listOf(

            )
        ),
        navController = NavController(LocalContext.current),
        searchForText = {}
    )*/
}