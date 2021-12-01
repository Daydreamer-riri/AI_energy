package com.example.aienergy.data


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class Func(
    val id: Long,
    val name: String,
    val Icon: ImageVector,
    val tags: Set<String> = emptySet()
)

/**
 * Static data
 */

var funcs = listOf(
    Func(
        id = 1L,
        name = "电费账单",
        Icon =  Icons.Filled.ReceiptLong
    ),
    Func(
        id = 2L,
        name = "用能分析",
        Icon =  Icons.Filled.PieChart
    ),
    Func(
        id = 3L,
        name = "电量电费",
        Icon =  Icons.Filled.OfflineBolt
    ),
    Func(
        id = 4L,
        name = "缴费",
        Icon =  Icons.Filled.Payments
    ),
    Func(
        id = 5L,
        name = "峰谷电变更",
        Icon =  Icons.Filled.TrendingUp
    ),
)