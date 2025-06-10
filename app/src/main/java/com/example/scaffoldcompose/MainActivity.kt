package com.example.scaffoldcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.scaffoldcompose.item_views.ItemNameRoute
import com.example.scaffoldcompose.location.LocationsRoute
import com.example.scaffoldcompose.ui.theme.ScaffoldComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .fillMaxSize()
                ) { innerPadding ->
                    ScaffoldApp(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}



data object DestinationRoutes {
    @Serializable
    object ItemNames

    @Serializable
    data class Locations(val locationId: Int)
}

@Composable
fun ScaffoldApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = DestinationRoutes.ItemNames
    ) {
        composable<DestinationRoutes.ItemNames> {
            ItemNameRoute(navController = navController, modifier = modifier)
        }
        composable<DestinationRoutes.Locations> { navBackStackEntry ->
            /*
            * This is an example of how to retrieve data from navBackStackEntry in a type safe way, and pass values to the composable.
            * other way is in the viewmodel using savedStateHandle (which is currently used for the app).
            * */
//            val locationId = navBackStackEntry.toRoute<DestinationRoutes.Locations>().locationId
            LocationsRoute(
                navController = navController,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    ScaffoldComposeTheme {
        ScaffoldApp()
    }
}
