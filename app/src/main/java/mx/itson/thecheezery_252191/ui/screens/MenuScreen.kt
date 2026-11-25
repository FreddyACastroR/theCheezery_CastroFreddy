package mx.itson.thecheezery_252191.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.thecheezery_252191.R
import mx.itson.thecheezery_252191.domain.Category
import mx.itson.thecheezery_252191.ui.theme.Brighter_Pink
import mx.itson.thecheezery_252191.ui.theme.Pinky
import mx.itson.thecheezery_252191.ui.theme.TheCheezeryTheme
import mx.itson.thecheezery_252191.ui.theme.Very_purple

@Composable
fun MenuScreen(
    onCategoryClick: (Category) -> Unit,
    onCombosClick: () -> Unit,
    onAddProductClick: () -> Unit,
    onCartClick: () -> Unit
) {
    val degradado = Brush.verticalGradient(listOf(Brighter_Pink, Pinky, Very_purple))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onCartClick,
            modifier = Modifier
                .align(Alignment.End)
                .size(56.dp)
                .background(Brush.linearGradient(listOf(Pinky, Very_purple)), CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.cart),
                contentDescription = "Carrito",
                tint = Color.White
            )
        }

        Image(
            painter = painterResource(R.drawable.grupo2),
            contentDescription = "Logo",
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(degradado)
            ) {
                MenuButton(stringResource(R.string.hot_drinks)) { onCategoryClick(Category.HOT_DRINKS) }
                MenuButton(stringResource(R.string.salties)) { onCategoryClick(Category.SALTIES) }
                MenuButton(stringResource(R.string.combos)) { onCombosClick() }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(degradado)
            ) {
                MenuButton(stringResource(R.string.cold_drinks)) { onCategoryClick(Category.COLD_DRINKS) }
                MenuButton(stringResource(R.string.sweets)) { onCategoryClick(Category.SWEETS) }
                MenuButton(stringResource(R.string.add_new_product)) { onAddProductClick() }
            }
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Medium)
    }
}

@Preview(showSystemUi = true)
@Composable
fun MenuPreview() {
    TheCheezeryTheme {
        MenuScreen(onCategoryClick = {}, onCombosClick = {}, onAddProductClick = {}, onCartClick = {})
    }
}
