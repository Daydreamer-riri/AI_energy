package com.example.aienergy.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

//----------------------------------------------------
// 重新封装NavigationBarItem实现自定义效果
//----------------------------------------------------

@Composable
fun RowScope.MyNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors()
) {
    val styledIcon = @Composable {
        val iconColor by colors.iconColor(selected = selected)
        CompositionLocalProvider(LocalContentColor provides iconColor, content = icon)
    }

    val styledLabel: @Composable (() -> Unit)? = label?.let {
        @Composable {
            val style = MaterialTheme.typography.labelMedium
            val textColor by colors.textColor(selected = selected)
            CompositionLocalProvider(LocalContentColor provides textColor) {
                ProvideTextStyle(style, content = label)
            }
        }
    }

    Box(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    color = MaterialTheme.colorScheme.primary
                ),
            )
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        val animationProgress: Float by animateFloatAsState(
            targetValue = if (selected) 1f else 0f,
            animationSpec = spring(
                dampingRatio = 2f,
                stiffness = Spring.StiffnessMedium
            )
        )

        val indicator = @Composable {
            Box(
                Modifier
                    .layoutId(IndicatorLayoutIdTag)
                    .background(
                        color = colors.indicatorColor.copy(alpha = animationProgress),
                        shape = IndicatorShape
                    )
            )
        }

        NavigationBarItemBaselineLayout(
            indicator = indicator,
            icon = styledIcon,
            label = styledLabel,
            alwaysShowLabel = alwaysShowLabel,
            animationProgress = animationProgress
        )
    }
}


/**
 * Base layout for a [NavigationBarItem].
 *
 * @param indicator indicator for this item when it is selected
 * @param icon icon for this item
 * @param label text label for this item
 * @param alwaysShowLabel whether to always show the label for this item. If false, the label will
 * only be shown when this item is selected.
 * @param animationProgress progress of the animation, where 0 represents the unselected state of
 * this item and 1 represents the selected state. This value controls other values such as indicator
 * size, icon and label positions, etc.
 */
@Composable
private fun NavigationBarItemBaselineLayout(
    indicator: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit)?,
    alwaysShowLabel: Boolean,
    animationProgress: Float,
) {
    Layout({
        if (animationProgress > 0) {
            indicator()
        }

        Box(Modifier.layoutId(IconLayoutIdTag)) { icon() }

        if (label != null) {
            Box(
                Modifier
                    .layoutId(LabelLayoutIdTag)
                    .alpha(if (alwaysShowLabel) 1f else animationProgress)
                    .padding(horizontal = NavigationBarItemHorizontalPadding)
            ) { label() }
        }
    }) { measurables, constraints ->
        val iconPlaceable =
            measurables.first { it.layoutId == IconLayoutIdTag }.measure(constraints)

        val totalIndicatorWidth = iconPlaceable.width + (IndicatorHorizontalPadding * 2).roundToPx()
        val animatedIndicatorWidth = (totalIndicatorWidth * animationProgress).roundToInt()
        val indicatorHeight = iconPlaceable.height + (IndicatorVerticalPadding * 2).roundToPx()
        val indicatorPlaceable =
            measurables
                .firstOrNull { it.layoutId == IndicatorLayoutIdTag }
                ?.measure(
                    Constraints.fixed(
                        width = animatedIndicatorWidth,
                        height = indicatorHeight
                    )
                )

        val labelPlaceable =
            label?.let {
                measurables
                    .first { it.layoutId == LabelLayoutIdTag }
                    .measure(
                        // Measure with loose constraints for height as we don't want the label to take up more
                        // space than it needs
                        constraints.copy(minHeight = 0)
                    )
            }

        if (label == null) {
            placeIcon(iconPlaceable, indicatorPlaceable, constraints)
        } else {
            placeLabelAndIcon(
                labelPlaceable!!,
                iconPlaceable,
                indicatorPlaceable,
                constraints,
                alwaysShowLabel,
                animationProgress
            )
        }
    }
}

/**
 * Places the provided [iconPlaceable], and possibly [indicatorPlaceable] if it exists, in the
 * center of the provided [constraints].
 */
private fun MeasureScope.placeIcon(
    iconPlaceable: Placeable,
    indicatorPlaceable: Placeable?,
    constraints: Constraints
): MeasureResult {
    val width = constraints.maxWidth
    val height = constraints.maxHeight

    val iconX = (width - iconPlaceable.width) / 2
    val iconY = (height - iconPlaceable.height) / 2

    return layout(width, height) {
        indicatorPlaceable?.let {
            val indicatorX = (width - it.width) / 2
            val indicatorY = (height - it.height) / 2
            it.placeRelative(indicatorX, indicatorY)
        }
        iconPlaceable.placeRelative(iconX, iconY)
    }
}

/**
 * Places the provided [labelPlaceable], [iconPlaceable], and [indicatorPlaceable] in the correct
 * position, depending on [alwaysShowLabel] and [animationProgress].
 *
 * When [alwaysShowLabel] is true, the positions do not move. The [iconPlaceable] will be placed
 * near the top of the item and the [labelPlaceable] will be placed near the bottom, according to
 * the spec.
 *
 * When [animationProgress] is 1 (representing the selected state), the positions will be the same
 * as above.
 *
 * Otherwise, when [animationProgress] is 0, [iconPlaceable] will be placed in the center, like in
 * [placeIcon], and [labelPlaceable] will not be shown.
 *
 * When [animationProgress] is animating between these values, [iconPlaceable] and [labelPlaceable]
 * will be placed at a corresponding interpolated position.
 *
 * [indicatorPlaceable] will always be placed in such a way that it shares the same center as
 * [iconPlaceable].
 *
 * @param labelPlaceable text label placeable inside this item
 * @param iconPlaceable icon placeable inside this item
 * @param indicatorPlaceable indicator placeable inside this item, if it exists
 * @param constraints constraints of the item
 * @param alwaysShowLabel whether to always show the label for this item. If true, icon and label
 * positions will not change. If false, positions transition between 'centered icon with no label'
 * and 'top aligned icon with label'.
 * @param animationProgress progress of the animation, where 0 represents the unselected state of
 * this item and 1 represents the selected state. Values between 0 and 1 interpolate positions of
 * the icon and label.
 */
private fun MeasureScope.placeLabelAndIcon(
    labelPlaceable: Placeable,
    iconPlaceable: Placeable,
    indicatorPlaceable: Placeable?,
    constraints: Constraints,
    alwaysShowLabel: Boolean,
    animationProgress: Float,
): MeasureResult {
    val height = constraints.maxHeight

    val baseline = labelPlaceable[LastBaseline]
    // Label should be `ItemVerticalPadding` from the bottom
    val labelY = height - baseline - NavigationBarItemVerticalPadding.roundToPx()

    // Icon (when selected) should be `ItemVerticalPadding` from the top
    val selectedIconY = NavigationBarItemVerticalPadding.roundToPx()
    val unselectedIconY =
        if (alwaysShowLabel) selectedIconY else (height - iconPlaceable.height) / 2

    // How far the icon needs to move between unselected and selected states.
    val iconDistance = unselectedIconY - selectedIconY

    // The interpolated fraction of iconDistance that all placeables need to move based on
    // animationProgress.
    val offset = (iconDistance * (1 - animationProgress)).roundToInt()

    val containerWidth = constraints.maxWidth

    val labelX = (containerWidth - labelPlaceable.width) / 2
    val iconX = (containerWidth - iconPlaceable.width) / 2

    return layout(containerWidth, height) {
        indicatorPlaceable?.let {
            val indicatorX = (containerWidth - it.width) / 2
            val indicatorY = selectedIconY - IndicatorVerticalPadding.roundToPx()
            it.placeRelative(indicatorX, indicatorY + offset)
        }
        if (alwaysShowLabel || animationProgress != 0f) {
            labelPlaceable.placeRelative(labelX, labelY + offset)
        }
        iconPlaceable.placeRelative(iconX, selectedIconY + offset)
    }
}

private const val IndicatorLayoutIdTag: String = "indicator"

private const val IconLayoutIdTag: String = "icon"

private const val LabelLayoutIdTag: String = "label"

//private const val ItemAnimationDurationMillis: Int = 100

private val NavigationBarItemHorizontalPadding: Dp = 4.dp

/*@VisibleForTesting*/
internal val NavigationBarItemVerticalPadding: Dp = 16.dp

private val IndicatorShape: Shape = RoundedCornerShape(16.0.dp)

private val IndicatorHorizontalPadding: Dp =
    (64.0.dp - 24.0.dp) / 2

private val IndicatorVerticalPadding: Dp =
    (32.0.dp - 24.0.dp) / 2