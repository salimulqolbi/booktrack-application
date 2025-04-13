package com.example.booktrack.utils

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booktrackapplication.R

@Composable
fun SearchBar2(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier
) {

    val animationProgress: State<Float> = animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = if (active) AnimationEnterFloatSpec else AnimationExitFloatSpec, label = ""
    )

    var isFocused by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isSearchActive by rememberSaveable { mutableStateOf(false) }

    BackHandler(enabled = isSearchActive) {
        isSearchActive = false
        focusManager.clearFocus()
    }

    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .height(52.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    onActiveChange(true)
                }
            },
        placeholder = {
            Text(
                text = placeholderText,
                color = Color(0xff888997),
                fontSize = 12.sp,
                maxLines = 1,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.height(48.dp)
            )
        },
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = onSearch) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_icon),
                    contentDescription = "Search",
                    tint = Color(0xff2846CF)
                )
            }
        }
    )
}

const val AnimationEnterDurationMillis: Int = MotionTokens.DurationLong4.toInt()
const val AnimationExitDurationMillis: Int = MotionTokens.DurationMedium3.toInt()
const val AnimationDelayMillis: Int = MotionTokens.DurationShort2.toInt()
val AnimationEnterEasing = MotionTokens.EasingEmphasizedDecelerateCubicBezier
val AnimationExitEasing = CubicBezierEasing(0.0f, 1.0f, 0.0f, 1.0f)
val AnimationEnterFloatSpec: FiniteAnimationSpec<Float> = tween(
    durationMillis = AnimationEnterDurationMillis,
    delayMillis = AnimationDelayMillis,
    easing = AnimationEnterEasing,
)
val AnimationExitFloatSpec: FiniteAnimationSpec<Float> = tween(
    durationMillis = AnimationExitDurationMillis,
    delayMillis = AnimationDelayMillis,
    easing = AnimationExitEasing,
)