package com.example.aienergy.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aienergy.ui.components.MyNavigationBarItem
import com.example.aienergy.ui.screen.mainscreens.ScreenEvent
import com.example.aienergy.ui.screen.mainscreens.ScreenMain
import com.example.aienergy.ui.screen.mainscreens.ScreenMine
import com.google.accompanist.insets.navigationBarsHeight

//密封类，封装主页底部导航栏的导航


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomMainView(modifier: Modifier = Modifier) {


    //导航栏未选择项图表
    val unselectedIcons = listOf(
        Icons.Outlined.Home,
        Icons.Outlined.Message,
        Icons.Outlined.Person
    )
    //导航栏已选择项图表
    val selectedIcons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Message,
        Icons.Filled.Person
    )
    //导航栏的导航表
    val bottomNavScreen = listOf(
        NavScreen.Home,
        NavScreen.Event,
        NavScreen.Mine,
    )


    val navController = rememberNavController()
    Surface(modifier = modifier) {
        Scaffold(
            bottomBar = {
                Column() {
//                    CompositionLocalProvider(
//                        LocalRippleTheme provides ClearRippleTheme
//                    ) {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            bottomNavScreen.forEachIndexed { index, screen ->
                                val selected =
                                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                                //导航栏项
                                MyNavigationBarItem(
                                    icon = {
                                        Icon(

                                            imageVector = if (selected) {
                                                selectedIcons[index]
                                            } else {
                                                unselectedIcons[index]
                                            },
                                            contentDescription = screen.resourceId.toString()
                                        )
                                    },
                                    label = {
                                        Text(
                                            stringResource(screen.resourceId),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    },
                                    selected = selected,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    },
                                )
//                            }
                        }
                    }
                    //适配导航条
                    Spacer(
                        Modifier
                            .navigationBarsHeight()
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                    )
                }

            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = NavScreen.Home.route,
                Modifier.padding(innerPadding)
            ) {
                composable(NavScreen.Home.route) { ScreenMain(navController) }
                composable(NavScreen.Event.route) { ScreenEvent(navController) }
                composable(NavScreen.Mine.route) { ScreenMine(navController) }
            }
        }
    }
}


//object ClearRippleTheme : RippleTheme {
//    @Composable
//    override fun defaultColor(): Color = Color.Transparent
//
//    @Composable
//    override fun rippleAlpha() = RippleAlpha(
//        draggedAlpha = 0.0f,
//        focusedAlpha = 0.0f,
//        hoveredAlpha = 0.0f,
//        pressedAlpha = 0.0f,
//    )
//}

