package com.booktrace.booktrace.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.booktrace.booktrace.Inicio

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun LightThemePreview(){
    BooktraceTheme(darkTheme = false) {
        Inicio()
    }
}