package com.example.scaffoldcompose.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.scaffoldcompose.DestinationRoutes

@Composable
fun LocationsRoute(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Locations(
        navController = navController,
        modifier = modifier
    )
}

@Composable
private fun Locations(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LocationsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Locations(
        state = state,
        navController = navController,
        modifier = modifier.padding(40.dp)
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun Locations(
    state: LocationsViewState,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    Column {
        Button(onClick = {
            navController.navigate(route = DestinationRoutes.ItemNames) {
                popUpTo(0){
                    inclusive = true
                } //todo why is this not working
                launchSingleTop = true
            }
        }) {
            Text("Go back")
        }

        Row(
            modifier = modifier
                .padding(20.dp)
        ) {
            Text(text = "welcome to locations screen with ${state.locationId}")
            Text(text = state.locations?.name.orEmpty())
        }
    }
}
