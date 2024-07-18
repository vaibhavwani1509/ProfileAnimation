package com.vaibhavwani.newprofileavatar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.nextUp

@Composable
fun ProfileAvatar(
    profileProgress: Int,
    totalProgress: Int,
    firstName: Char,
) {
    val finalProgress = profileProgress / totalProgress.toFloat()

    var tempProgress by remember { mutableStateOf(0f) }

    val progress by animateFloatAsState(
        targetValue = tempProgress,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = Unit) {
        progress.nextUp()
        delay(100)
        tempProgress = finalProgress
    }
    
    Box(
        modifier = Modifier
            .size(300.dp)
            .clip(CircleShape)
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        listOf(Color.Gray, Color.Black)
                    )
                )
                .padding(16.dp)
        ) {
            val sweep = (progress.coerceIn(0f, 1f) * 360f)
            val totalAngle =  360f
            var angle = 0f
            while (angle < totalAngle) {
                val lineColor = if (sweep != 0f && angle <= sweep) Color.Yellow else Color.Gray
                val lineWidth = if (angle % 90f == 0f) 4.dp.toPx() else 2.dp.toPx()
                rotate(degrees = angle) {
                    drawLine(
                        color = lineColor,
                        start = center.copy(y = center.y - size.minDimension / 2),
                        end = center.copy(y = center.y - size.minDimension / 2 + 30.dp.toPx()),
                        strokeWidth = lineWidth,
                        cap = StrokeCap.Round,
                    )
                }
                angle += 7.5f
            }
        }

        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(58.dp),
            progress = { progress },
            color = Color.Yellow,
            trackColor = Color.LightGray,
            strokeWidth = 10.dp,
            strokeCap = StrokeCap.Round
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(96.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = firstName.toString(),
                color = Color.White,
                fontSize = 96.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun AvatarPreview() {
    ProfileAvatar(profileProgress = 6, totalProgress = 10, firstName = 'V')
}