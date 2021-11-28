package com.example.aienergy.ui.screen.mainscreens

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aienergy.ui.components.AIeLargeTopBar
import com.example.aienergy.ui.components.AccountDisplayTopBar
import com.example.aienergy.ui.theme.AIEnergyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMain(navController: NavHostController? = null) {

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()

    @OptIn(ExperimentalMaterial3Api::class)
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Top(scrollBehavior = scrollBehavior)
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..75).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )

}

@Composable
fun Top(scrollBehavior: TopAppBarScrollBehavior? = null) {
    AccountDisplayTopBar(scrollBehavior = scrollBehavior)

}

@Preview
@Composable
fun MainPreview() {
    AIEnergyTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            ScreenMain(navController = null)
        }
    }
}