package com.d121211030.articartwork
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.d121211030.articartwork.artworkdetail.ArtworkDetailScreen
import com.d121211030.articartwork.artworklist.ArtworkListScreen
import com.d121211030.articartwork.ui.theme.ArticArtworkTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArticArtworkTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "artwork_list_screen"
                ) {
                    composable("artwork_list_screen") {
                        ArtworkListScreen(navController = navController)
                    }
                    composable(
                        "artwork_detail_screen/{artworkTitle}",
                        arguments = listOf(
                            navArgument("artworkTitle") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val artworkTitle = remember {
                            it.arguments?.getString("artworkTitle")
                        }
                        ArtworkDetailScreen(
                            artworkTitle = artworkTitle?.lowerCase(Locale.ROOT) ?: "",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}