package com.example.aienergy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.aienergy.R
import com.example.aienergy.ui.theme.AIEnergyTheme

/*
    主页插图内容
 */

@Composable
fun MainPagePic(
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    val semantics = if (contentDescription != null) {
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        Modifier
    }
    Box(modifier = modifier.then(semantics)) {
        Image(
            painter = painterResource(R.drawable.ic_home_frame),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        )
        Image(
            painter = painterResource(R.drawable.ic_home_back),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseOnSurface)
        )
        Image(
            painter = painterResource(R.drawable.ic_home_onframe),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        )
        Image(
            painter = painterResource(R.drawable.ic_home_bar),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseOnSurface)
        )
        Image(
            painter = painterResource(R.drawable.ic_home_program),
            contentDescription = null,
//            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Image(
            painter = painterResource(R.drawable.ic_home_secondary),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )
        Image(
            painter = painterResource(R.drawable.ic_home_other),
            contentDescription = null,
//            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Image(
            painter = painterResource(R.drawable.ic_home_primary),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = getNewColor(MaterialTheme.colorScheme.primary))
        )
        Image(
            painter = painterResource(R.drawable.ic_home_windows),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseOnSurface)
        )
    }
}

@Composable
fun getNewColor(color: Color): Color {
    if (isSystemInDarkTheme()) {
        var r = color.red - 80f / 255
        var g = color.green - 80f / 255
        var b = color.blue - 80f / 255
        if (r < 0)
            r = 0F
        if (g < 0)
            g = 0F
        if (b < 0)
            b = 0F
        return Color(red = r, green = g, blue = b, alpha = 1f)
    } else {
        var r = color.red + 80f / 255
        var g = color.green + 80f / 255
        var b = color.blue + 80f / 255
        if (r > 1)
            r = 1F
        if (g > 1)
            g = 1F
        if (b > 1)
            b = 1F
        return Color(red = r, green = g, blue = b, alpha = 1f)
    }
}


@Preview
@Composable
fun PicPreview() {
    AIEnergyTheme() {
        androidx.compose.material3.Surface(color = MaterialTheme.colorScheme.background) {
            MainPagePic(contentDescription = null)
        }

    }
}