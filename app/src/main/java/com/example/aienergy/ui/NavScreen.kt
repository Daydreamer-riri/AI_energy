package com.example.aienergy.ui

import com.example.aienergy.R

sealed class NavScreen(val route: String, val resourceId: Int)
{
    object Home : NavScreen("home", R.string.home)
    object Event : NavScreen("event", R.string.event)
    object Mine : NavScreen("mine", R.string.mine)



}


