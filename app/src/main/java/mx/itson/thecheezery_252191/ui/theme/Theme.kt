package mx.itson.thecheezery_252191.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Pinky,
    secondary = Very_purple,
    tertiary = Less_Purple,
    background = OffWhite,
    surface = OffWhite
)

@Composable
fun TheCheezeryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
