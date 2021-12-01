package com.example.aienergy.ui.components

import android.icu.math.BigDecimal
import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.outlined.House
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.aienergy.ui.theme.AIEnergyTheme
import com.google.accompanist.insets.statusBarsPadding
import kotlin.math.max
import kotlin.math.roundToInt


//----------------------------------------------------
// SmallTopBar（适配沉浸状态栏）
//----------------------------------------------------


@Composable
fun AIeSmallTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(
        scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    ).value
    val foregroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )
    Box(modifier = Modifier.background(backgroundColor)) {
        SmallTopAppBar(
            modifier = modifier,
            actions = actions,
            title = title,
            scrollBehavior = scrollBehavior,
            colors = foregroundColors,
            navigationIcon = navigationIcon
        )
    }
}


//----------------------------------------------------
// LargeTopBar（适配沉浸状态栏）
//----------------------------------------------------


@Composable
fun AIeLargeTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    smallTitle: @Composable () -> Unit = {},
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(
        scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    ).value
    val foregroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .statusBarsPadding()
    ) {
        DisplayTopBar(
            modifier = modifier,
            actions = actions,
            smallTitle = smallTitle,
            title = title,
            scrollBehavior = scrollBehavior,
            colors = foregroundColors,
            navigationIcon = navigationIcon
        )
//        Column() {
//            Text(text = "1234")
//        }
    }
}


//----------------------------------------------------
// main用户信息展示顶栏
//----------------------------------------------------

@Composable
fun AccountDisplay(
    accountNumber: Number? = null,
    balance: BigDecimal? = null,
    days: Number? = null,
    backgroundColor: Color,
) {
    val ripple = rememberRipple()
    Row(
        modifier = Modifier
            .height(156.dp)
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    imageVector = Icons.Outlined.House,
                    contentDescription = null
                )
                Surface(
                    onClick = { /*TODO*/ },
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    indication = ripple,
                    shape = RoundedCornerShape(50),
                    tonalElevation = 5.dp

                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        text = "${accountNumber ?: "添加户号"}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(start = 32.dp)
            ) {
                Text(text = "余额（元）：", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = if (balance == null)
                        "**.**"
                    else
                        DecimalFormat("#,##0.00").format(balance),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "预计可用${days ?: "*"}天",
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            MainPagePic(contentDescription = null, modifier = Modifier.scale(0.8f))

        }
    }
}


//----------------------------------------------------
// main展示顶栏(由于无法嵌套滚动已弃用)
//----------------------------------------------------
@Composable
fun AccountDisplayTopBar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    accountNumber: Number? = null,
    balance: BigDecimal? = null,
    days: Number? = null
) {
    val ripple = rememberRipple()
    AIeLargeTopBar(
        title = {
            Row(
                modifier = Modifier
                    .height(156.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            imageVector = Icons.Filled.Home,
                            contentDescription = null
                        )
                        Surface(
                            onClick = { /*TODO*/ },
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            indication = ripple,
                            shape = RoundedCornerShape(50),
                            tonalElevation = 5.dp

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                text = "${accountNumber ?: "添加户号"}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(start = 32.dp)
                    ) {
                        Text(text = "余额（元）：", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = if (balance == null)
                                "**.**"
                            else
                                DecimalFormat("#,##0.00").format(balance),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "预计可用${days ?: "*"}天",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    MainPagePic(contentDescription = null, modifier = Modifier.scale(0.8f))

                }
            }

        },
        navigationIcon = {
//            IconButton(onClick = { /* doSomething() */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Menu,
//                    contentDescription = "Localized description"
//                )
//            }
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
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )

}

@Preview
@Composable
fun LargeTopBarPreview() {
    AIEnergyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
//            AccountDisplay(,)
        }

    }
}

//----------------------------------------------------
// 封装LargeTopAppBar
//----------------------------------------------------


//@Composable
//fun LargeTopAppBar(
//    title: @Composable () -> Unit,
//    modifier: Modifier = Modifier,
//    navigationIcon: @Composable () -> Unit = {},
//    actions: @Composable RowScope.() -> Unit = {},
//    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
//    scrollBehavior: TopAppBarScrollBehavior? = null
//) {
//    TwoRowsTopAppBar(
//        title = title,
//        titleTextStyle = MaterialTheme.typography.fromToken(TopAppBarLarge.LargeHeadlineFont),
//        smallTitleTextStyle = MaterialTheme.typography.fromToken(TopAppBarSmall.SmallHeadlineFont),
//        titleBottomPadding = LargeTitleBottomPadding,
//        smallTitle = title,
//        modifier = modifier,
//        navigationIcon = navigationIcon,
//        actions = actions,
//        colors = colors,
//        maxHeight = TopAppBarLarge.LargeContainerHeight,
//        pinnedHeight = TopAppBarSmall.SmallContainerHeight,
//        scrollBehavior = scrollBehavior
//    )
//}


@Composable
fun DisplayTopBar(
    title: @Composable () -> Unit,
    smallTitle: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TwoRowsTopAppBar(
        title = title,
        titleTextStyle = MaterialTheme.typography.headlineMedium,
        smallTitleTextStyle = MaterialTheme.typography.titleLarge,
        titleBottomPadding = LargeTitleBottomPadding,
        smallTitle = smallTitle,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors,
        maxHeight = 220.0.dp,
        pinnedHeight = 64.0.dp,
        scrollBehavior = scrollBehavior
    )
}


@Composable
private fun TwoRowsTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    titleTextStyle: TextStyle,
    titleBottomPadding: Dp,
    smallTitle: @Composable () -> Unit,
    smallTitleTextStyle: TextStyle,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    colors: TopAppBarColors,
    maxHeight: Dp,
    pinnedHeight: Dp,
    scrollBehavior: TopAppBarScrollBehavior?
) {
    if (maxHeight <= pinnedHeight) {
        throw IllegalArgumentException(
            "A TwoRowsTopAppBar max height should be greater than its pinned height"
        )
    }
    val pinnedHeightPx: Float
    val maxHeightPx: Float
    val titleBottomPaddingPx: Int
    LocalDensity.current.run {
        pinnedHeightPx = pinnedHeight.toPx()
        maxHeightPx = maxHeight.toPx()
        titleBottomPaddingPx = titleBottomPadding.roundToPx()
    }

    // Set a scroll offset limit that will hide just the title area and will keep the small title
    // area visible.
    SideEffect {
        if (scrollBehavior?.offsetLimit != pinnedHeightPx - maxHeightPx) {
            scrollBehavior?.offsetLimit = pinnedHeightPx - maxHeightPx
        }
    }

    val titleAlpha =
        if (scrollBehavior == null || scrollBehavior.offsetLimit == 0f) {
            1f
        } else {
            1f - (scrollBehavior.offset / scrollBehavior.offsetLimit)
        }
    // Obtain the container Color from the TopAppBarColors.
    // This will potentially animate or interpolate a transition between the container color and the
    // container's scrolled color according to the app bar's scroll state.
    val scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    val appBarContainerColor by colors.containerColor(scrollFraction)

    // Wrap the given actions in a Row.
    val actionsRow = @Composable {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )
    }
    Surface(modifier = modifier, color = appBarContainerColor) {
        Column {
            TopAppBarLayout(
                heightPx = pinnedHeightPx,
                navigationIconContentColor =
                colors.navigationIconContentColor(scrollFraction).value,
                titleContentColor = colors.titleContentColor(scrollFraction).value,
                actionIconContentColor = colors.actionIconContentColor(scrollFraction).value,
                title = smallTitle,
                titleTextStyle = smallTitleTextStyle,
                //小标题透明度
                titleAlpha = 1f,
                navigationIcon = navigationIcon,
                actions = actionsRow,
            )
            TopAppBarLayout(
                heightPx = maxHeightPx - pinnedHeightPx + (scrollBehavior?.offset ?: 0f),
                navigationIconContentColor =
                colors.navigationIconContentColor(scrollFraction).value,
                titleContentColor = colors.titleContentColor(scrollFraction).value,
                actionIconContentColor = colors.actionIconContentColor(scrollFraction).value,
                title = title,
                titleTextStyle = titleTextStyle,
                titleAlpha = titleAlpha,
                titleVerticalArrangement = Arrangement.Bottom,
                titleBottomPadding = titleBottomPaddingPx,
                modifier = Modifier.graphicsLayer { clip = true }
            )
        }
    }
}

@Composable
private fun TopAppBarLayout(
    heightPx: Float,
    navigationIconContentColor: Color,
    titleContentColor: Color,
    actionIconContentColor: Color,
    title: @Composable () -> Unit,
    titleTextStyle: TextStyle,
    modifier: Modifier = Modifier,
    titleAlpha: Float = 1f,
    titleVerticalArrangement: Arrangement.Vertical = Arrangement.Center,
    titleHorizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    titleBottomPadding: Int = 0,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
) {
    Layout(
        {
            Box(
                Modifier
                    .layoutId("navigationIcon")
                    .padding(start = TopAppBarHorizontalPadding)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides navigationIconContentColor,
                    content = navigationIcon
                )
            }
            Box(
                Modifier
                    .layoutId("title")
                    .padding(horizontal = TopAppBarHorizontalPadding)
            ) {
                ProvideTextStyle(value = titleTextStyle) {
                    CompositionLocalProvider(
                        LocalContentColor provides titleContentColor.copy(alpha = titleAlpha),
                        content = title
                    )
                }
            }
            Box(
                Modifier
                    .layoutId("actionIcons")
                    .padding(end = TopAppBarHorizontalPadding)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides actionIconContentColor,
                    content = actions
                )
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        val navigationIconPlaceable =
            measurables.first { it.layoutId == "navigationIcon" }.measure(constraints)
        val actionIconsPlaceable =
            measurables.first { it.layoutId == "actionIcons" }.measure(constraints)

        val maxTitleWidth =
            constraints.maxWidth - navigationIconPlaceable.width - actionIconsPlaceable.width
        val titlePlaceable =
            measurables
                .first { it.layoutId == "title" }
                .measure(constraints.copy(maxWidth = maxTitleWidth))
        // Locate the title's baseline.
        val titleBaseline =
            if (titlePlaceable[LastBaseline] != AlignmentLine.Unspecified) {
                titlePlaceable[LastBaseline]
            } else {
                0
            }

        val layoutHeight = heightPx.roundToInt()

        layout(constraints.maxWidth, layoutHeight) {
            // Navigation icon
            navigationIconPlaceable.placeRelative(
                x = 0,
                y = (layoutHeight - navigationIconPlaceable.height) / 2
            )

            // Title
            titlePlaceable.placeRelative(
                x = when (titleHorizontalArrangement) {
                    Arrangement.Center -> (constraints.maxWidth - titlePlaceable.width) / 2
                    Arrangement.End ->
                        constraints.maxWidth - titlePlaceable.width - actionIconsPlaceable.width
                    // Arrangement.Start.
                    //  TopAppBarTitleInset 确保标题的偏移
                    // 以免导航图标消失.
                    else -> max(TopAppBarTitleInset.roundToPx(), navigationIconPlaceable.width)
                },
                y = when (titleVerticalArrangement) {
                    Arrangement.Center -> (layoutHeight - titlePlaceable.height) / 2
                    // Apply bottom padding from the title's baseline only when the Arrangement is
                    // "Bottom".
                    Arrangement.Bottom ->
                        if (titleBottomPadding == 0) layoutHeight - titlePlaceable.height
                        else layoutHeight - titlePlaceable.height - max(
                            0,
                            titleBottomPadding - titlePlaceable.height + titleBaseline
                        )
                    // Arrangement.Top
                    else -> 0
                }
            )

            // Action icons
            actionIconsPlaceable.placeRelative(
                x = constraints.maxWidth - actionIconsPlaceable.width,
                y = (layoutHeight - actionIconsPlaceable.height) / 2
            )
        }
    }
}


private val MediumTitleBottomPadding = 24.dp
private val LargeTitleBottomPadding = 28.dp
private val TopAppBarHorizontalPadding = 4.dp

private val TopAppBarTitleInset = 16.dp - TopAppBarHorizontalPadding

private const val TopAppBarAnimationDurationMillis = 500


//----------------------------------------------------
// 封装TopAppBar范例
//----------------------------------------------------


/*
@Composable
fun JetchatAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(
        scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    ).value
    val foregroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )
    Box(modifier = Modifier.background(backgroundColor)) {
        CenterAlignedTopAppBar(
            modifier = modifier,
            actions = actions,
            title = title,
            scrollBehavior = scrollBehavior,
            colors = foregroundColors,
            navigationIcon = {
                JetchatIcon(
                    contentDescription = stringResource(id = R.string.navigation_drawer_open),
                    modifier = Modifier
                        .size(64.dp)
                        .clickable(onClick = onNavIconPressed)
                        .padding(16.dp)
                )
            }
        )
    }
}
 */