package com.example.expensetracker.navagation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.AddExpense
import com.example.expensetracker.HomeScreen
import com.example.expensetracker.R
import com.example.expensetracker.data.StatsScreen
import com.example.expensetracker.ui.theme.PurpleGrey80
import com.example.expensetracker.ui.theme.Zinc

@Composable
fun NavScreen(){
    val navController= rememberNavController()
    var bottomBarVis by remember{
        mutableStateOf(true)
    }
    Scaffold(bottomBar = {
        AnimatedVisibility(visible = bottomBarVis) {
            NavigationBottomBar(navController = navController, items = listOf(navItem("home", R.drawable.ic_home),navItem("stats",R.drawable.ic_stats)) )

        }
        }) {

        NavHost(navController = navController, startDestination = "home" , modifier = Modifier.padding(it)) {
            composable(route="home"){
                bottomBarVis=true
                HomeScreen(navController)
            }
            composable(route="add"){
                bottomBarVis=false
                AddExpense(navController)
            }
            composable(route = "stats"){
                bottomBarVis=true
                StatsScreen(navController = navController)
            }
        }
    }
    }


data class navItem(
    val route:String,
    val icon:Int
)

@Composable
fun NavigationBottomBar(navController: NavController, items: List<navItem>){
   val navBackStackEntry=navController.currentBackStackEntryAsState()
    val currentRoute=navBackStackEntry.value?.destination?.route

    BottomAppBar {
        items.forEach{item->
            NavigationBarItem(selected = currentRoute==item.route,
                onClick = { navController.navigate(item.route){
                    popUpTo(navController.graph.startDestinationId){
                        saveState=true
                    }
                    launchSingleTop=true
                    restoreState=true
                } },
                icon = {
                    Icon(painterResource(id = item.icon), null)
                },
                alwaysShowLabel = false,
                colors= NavigationBarItemColors(
                    selectedIconColor = Zinc,
                    selectedTextColor = Zinc,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified,
                    selectedIndicatorColor = Color.Unspecified
                )
            )
        }
    }

}