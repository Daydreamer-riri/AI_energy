package com.example.aienergy.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aienergy.data.Func
import com.example.aienergy.data.funcs
import com.example.aienergy.ui.theme.AIEnergyTheme

@Composable
fun FunCollection(
    modifier: Modifier = Modifier,
    onFuncClick: (Long) -> Unit,
    funcs: List<Func>
) {
    Column(modifier = modifier) {
        TitleBar(title = "你的应用")
        Funcs(funcs = funcs, onFuncClick = onFuncClick)
    }
}

@Composable
private fun Funcs(
    funcs: List<Func>,
    onFuncClick: (Long) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 24.dp)
    ) {
        items(funcs) { func ->
            FuncItem(func, onFuncClick)
        }
    }
}

@Composable
fun FuncItem(func: Func, onFuncClick: (Long) -> Unit, modifier: Modifier = Modifier) {
    val ripple = rememberRipple(color = MaterialTheme.colorScheme.primary)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Surface(
            tonalElevation = 1.dp,
            shadowElevation = 0.dp,
            modifier = modifier
                .size(96.dp),

            shape = RoundedCornerShape(28.dp)
        ) {
            Box(modifier = Modifier.clickable(
                indication = ripple,
                interactionSource = remember { MutableInteractionSource() }) { onFuncClick(func.id) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = func.Icon,
                    contentDescription = null,
                    modifier = modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
        Text(
            text = func.name,
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }

}

@Preview
@Composable
fun FuncItemPreview() {
    AIEnergyTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            var func = funcs.first()
            FuncItem(func = func, onFuncClick = {})
        }

    }
}

@Preview
@Composable
fun FuncsPreview() {
    AIEnergyTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            Funcs(funcs = funcs, onFuncClick = {})
        }

    }
}
