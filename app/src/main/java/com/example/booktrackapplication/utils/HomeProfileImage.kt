package com.example.booktrack.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeProfileImage(
    drawableRes: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier
            .size(52.dp)
            .clip(CircleShape),
        painter = painterResource(id = drawableRes),
        contentDescription = description
    )
}