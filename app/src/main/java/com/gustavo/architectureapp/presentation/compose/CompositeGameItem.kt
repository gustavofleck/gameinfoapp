package com.gustavo.architectureapp.presentation.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class CompositeGameItem {

    @Composable
    fun GameItem() {
        Text("Deu bom")
    }

    @Preview
    @Composable
    fun PreviewGameItem() {
        GameItem()
    }
}