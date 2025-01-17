package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    App(engine = remember { io.ktor.client.engine.darwin.Darwin.create() })
}