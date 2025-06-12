package com.example.scaffoldcompose.item_views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.scaffoldcompose.models.ItemsResponse

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
    val anotherState by itemViewModel.anotherState.collectAsStateWithLifecycle()

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
            val painter = rememberImagePainter(it.image)
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
                Image(
                    painter = painter,
                    contentDescription = it.name,
                    modifier = Modifier.size(100.dp)
                )

      /*          val context = LocalContext.current
                when (painter.state) {
                    is ImagePainter.State.Loading -> CircularProgressIndicator()
                    ImagePainter.State.Empty -> {}
                    is ImagePainter.State.Error -> Toast.makeText(
                        context, "Error loading image",
                        Toast.LENGTH_SHORT
                    ).show()

                    is ImagePainter.State.Success -> {}
                }*/
            }
        }
    }
}

@Preview
@Composable
fun CharacterResultsPreview() {
    ItemNames(
        state = ItemsViewState(
            itemNames = listOf(
                ItemsResponse(
                    id = "1",
                    name = "John",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
                )
            )
        ),
        navController = NavController(LocalContext.current),
        searchForText = {}
    )
}