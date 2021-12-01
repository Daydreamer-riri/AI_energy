package com.example.aienergy.ui.screen.mainscreens

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.aienergy.data.funcs
import com.example.aienergy.ui.components.*
import com.example.aienergy.ui.theme.AIEnergyTheme
import com.google.accompanist.insets.statusBarsPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMain(navController: NavHostController? = null) {


    @OptIn(ExperimentalMaterial3Api::class)

    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(
        scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    ).value


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Top(scrollBehavior = scrollBehavior)
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    AccountDisplay(
                        backgroundColor = backgroundColor,
                    )
                }
                //功能入口
                item {
                    FunCollection(funcs = funcs, onFuncClick = {/*TODO*/ })
                    println(funcs)
                }

                item {
                    FeedsTitleBar()
                }
                val list = (0..10).map { it.toString() }
                items(count = list.size) {
                    Feeds(number = 1)
                }
            }
        }
    )

}

@Composable
fun Top(scrollBehavior: TopAppBarScrollBehavior? = null) {
    AIeSmallTopBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = "AIEnergy",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                ),
                color = MaterialTheme.colorScheme.primary
            )
        },
        onNavIconPressed = {},
        title = {},
        modifier = Modifier.statusBarsPadding()
    )

}

@Composable
fun Feeds(number: Number) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        FeedsCard(number = 1)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainPreview() {
    AIEnergyTheme() {
        val decayAnimationSpec = rememberSplineBasedDecay<Float>()
        val scrollBehavior = remember(decayAnimationSpec) {
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
        }
        Surface(color = MaterialTheme.colorScheme.background) {
            ScreenMain(navController = null)
        }
    }
}

@Preview
@Composable
fun RowPreview() {
    AIEnergyTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item { FunCollection(funcs = funcs, onFuncClick = {/*TODO*/ }) }
            }
        }
    }
}