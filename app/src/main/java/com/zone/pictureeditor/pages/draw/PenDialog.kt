package com.zone.pictureeditor.pages.draw

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zone.pictureeditor.vm.DrawViewModel

@Composable
fun PenDialog(vm: DrawViewModel) {
    // 画笔宽度
    var strokeWidth = remember { mutableStateOf(vm.pathProperties.value.strokeWidth) }
    // 颜色
    var red = remember { mutableStateOf(vm.pathProperties.value.color.red * 255) }
    var green = remember { mutableStateOf(vm.pathProperties.value.color.green * 255) }
    var blue = remember { mutableStateOf(vm.pathProperties.value.color.blue * 255) }
    Dialog(
        onDismissRequest = { vm.openPenDialog.value = false },
    ) {
        Card {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Stroke Width: ${String.format("%.0f", strokeWidth.value)} dp", fontSize = 25.sp)
                Slider(
                    value = strokeWidth.value,
                    onValueChange = {
                        strokeWidth.value = it
                        vm.pathProperties.value.strokeWidth = it
                    },
                    valueRange = 1f..100f
                )
                Text(
                    text = "Current Color",
                    color = Color(red.value / 255, green.value / 255, blue.value / 255),
                    fontSize = 25.sp
                )
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Red",
                    titleColor = Color.Red,
                    rgb = red.value,
                    onColorChanged = {
                        red.value = it
                        vm.pathProperties.value.color = Color(red.value / 255, green.value / 255, blue.value / 255)
                    }
                )
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Green",
                    titleColor = Color.Green,
                    rgb = green.value,
                    onColorChanged = {
                        green.value = it
                        vm.pathProperties.value.color = Color(red.value / 255, green.value / 255, blue.value / 255)
                    }
                )
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Blue",
                    titleColor = Color.Blue,
                    rgb = blue.value,
                    onColorChanged = {
                        blue.value = it
                        vm.pathProperties.value.color = Color(red.value / 255, green.value / 255, blue.value / 255)
                    }
                )
            }
        }
    }
}

@Composable
fun ColorSlider(
    modifier: Modifier,
    title: String,
    titleColor: Color,
    valueRange: ClosedFloatingPointRange<Float> = 0f..255f,
    rgb: Float,
    onColorChanged: (Float) -> Unit
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {

        Text(text = title.substring(0, 1), color = titleColor, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Slider(
            modifier = Modifier.weight(1f),
            value = rgb,
            onValueChange = { onColorChanged(it) },
            valueRange = valueRange,
            onValueChangeFinished = {}
        )

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = rgb.toInt().toString(),
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.width(30.dp)
        )

    }
}