package com.gustavo.architectureapp.presentation.view.composeui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.data.enums.PlatformEnum
import com.gustavo.architectureapp.data.enums.PlatformEnum.*

@ExperimentalMaterialApi
open class PlatformListCompose {
    @Composable
    fun PlatformGrid(clickAction: (platformId: Int) -> Unit) {
        Box(
            modifier = Modifier.clipToBounds().background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            ConstraintLayout {
                val (firstColumn, secondColumn) = createRefs()
                Column(
                    modifier = Modifier.constrainAs(firstColumn) {
                        top.linkTo(parent.top, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        absoluteLeft.linkTo(parent.absoluteLeft, margin = 16.dp)
                    }
                ) {
                    PlatformItem(XBOX_ONE, clickAction)
                    PlatformItem(XBOX_SERIES, clickAction)
                    PlatformItem(PC, clickAction)
                }
                Column(
                    modifier = Modifier.constrainAs(secondColumn) {
                        top.linkTo(parent.top, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        absoluteLeft.linkTo(firstColumn.absoluteRight, margin = 16.dp)
                        absoluteRight.linkTo(parent.absoluteRight, margin = 16.dp)
                    }
                ) {
                    PlatformItem(PS4, clickAction)
                    PlatformItem(PS5, clickAction)
                    PlatformItem(NINTENDO_SWITCH, clickAction)
                }
            }
        }
    }

    @Composable
    private fun PlatformItem(platform: PlatformEnum, clickAction: (platformId: Int) -> Unit) {
        Card(
            onClick = { clickAction(platform.platformId) },
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.Black,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp),
            elevation = 15.dp,
        ) {
            when (platform) {
                XBOX_ONE -> ComposeXboxOneImage()
                XBOX_SERIES -> ComposeXboxSeriesXImage()
                PS4 -> ComposePS4Image()
                PS5 -> ComposePS5Image()
                PC -> ComposePCImage()
                NINTENDO_SWITCH -> ComposeNintendoSwitchImage()
            }
        }
    }

    @Composable
    private fun ComposeXboxOneImage() {
        Image(
            painter = painterResource(id = R.drawable.xbox_one_platform_icon),
            contentDescription = "Games for XBOX One",
            modifier = Modifier.padding(8.dp)
        )
    }

    @Composable
    private fun ComposeXboxSeriesXImage() {
        Image(
            painter = painterResource(id = R.drawable.xbox_series_platform_icon),
            contentDescription = "Games for XBOX Series",
            modifier = Modifier.padding(8.dp)
        )
    }

    @Composable
    private fun ComposePS4Image() {
        Image(
            painter = painterResource(id = R.drawable.ps4_platform_icon),
            contentDescription = "Games for Playstation 4",
            modifier = Modifier.padding(8.dp)
        )
    }

    @Composable
    private fun ComposePS5Image() {
        Image(
            painter = painterResource(id = R.drawable.ps5_platform_icon),
            contentDescription = "Games for Playstation 5",
            modifier = Modifier.padding(8.dp)
        )
    }

    @Composable
    private fun ComposePCImage() {
        Image(
            painter = painterResource(id = R.drawable.pc_platform_icon),
            contentDescription = "Games for Computer",
            modifier = Modifier.padding(8.dp)
        )
    }

    @Composable
    private fun ComposeNintendoSwitchImage() {
        Image(
            painter = painterResource(id = R.drawable.nintendo_switch_platform_icon),
            contentDescription = "Games for Nintendo Switch",
            modifier = Modifier.padding(8.dp)
        )
    }


    @Preview
    @Composable
    fun PreviewPlatformGrid() {
        PlatformGrid({})
    }

}