package com.example.ttsclaudebrowser.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ttsclaudebrowser.ui.screens.BookmarkScreen
import com.example.ttsclaudebrowser.ui.screens.HistoryScreen
import com.example.ttsclaudebrowser.ui.screens.MainScreen
import com.example.ttsclaudebrowser.ui.screens.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    ScaffoldWithNavigationBar(navController)
}

@Composable
fun ScaffoldWithNavigationBar(navController: NavHostController) {
    androidx.compose.material3.Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val items = listOf(
                    NavigationItem.Main,
                    NavigationItem.Bookmark,
                    NavigationItem.History,
                    NavigationItem.Settings
                )
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Navigation(navController, innerPadding)
    }
}

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Main.route,
        Modifier.padding(innerPadding)
    ) {
        composable(
            route = "main_screen/{url}",
            arguments = listOf(navArgument("url") { defaultValue = "https://claude.ai/new" })
        ) { backStackEntry ->
            MainScreen(
                viewModel = hiltViewModel(),
                navController = navController,
            )
        }
        composable(NavigationItem.Bookmark.route) { BookmarkScreen(navController = navController) }
        composable(NavigationItem.History.route) { HistoryScreen(navController = navController) }
        composable(NavigationItem.Settings.route) { SettingsScreen(navController = navController) }
    }
}


sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Main : NavigationItem("main_screen", "ホーム", Icons.Filled.Home)
    object Bookmark : NavigationItem("bookmark_screen", "ブックマーク", Icons.Filled.Bookmark)
    object History : NavigationItem("history_screen", "履歴", Icons.Filled.History)
    object Settings : NavigationItem("settings_screen", "設定", Icons.Filled.Settings)
}