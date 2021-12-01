package com.example.aienergy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FeedsCard(
    number: Number,
) {
    Surface(
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(28.dp),

        ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row() {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "智能达人",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(text = "新人入手自动炒菜机，妈妈再也不用担心", style = MaterialTheme.typography.titleMedium)
                }
                Box(
                    Modifier
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.CenterEnd
                ) {

                }
            }
            Row() {
                Surface(
                    shape = RoundedCornerShape(40),
                    tonalElevation = (-1).dp
                ) {
                    Box(
                        modifier = Modifier
//                            .padding(12.dp)

                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp),

                        text = "一小时前",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    )
                }
            }
        }
    }
}

@Composable
fun FeedsTitleBar(){
    Row(
        modifier = Modifier.padding(0.dp)
    ) {
        TitleBar(title = "更多推荐")
    }
}

@Preview
@Composable
fun CardPreview() {
    MaterialTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            FeedsCard(1)
        }
    }
}