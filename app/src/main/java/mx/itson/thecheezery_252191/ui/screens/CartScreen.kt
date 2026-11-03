package mx.itson.thecheezery_252191.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.itson.thecheezery_252191.R
import mx.itson.thecheezery_252191.data.coldDrinks
import mx.itson.thecheezery_252191.data.salties
import mx.itson.thecheezery_252191.domain.Product
import mx.itson.thecheezery_252191.ui.components.BackButton
import mx.itson.thecheezery_252191.ui.components.ScreenTitle
import mx.itson.thecheezery_252191.ui.theme.Less_Purple
import mx.itson.thecheezery_252191.ui.theme.Pinky
import mx.itson.thecheezery_252191.ui.theme.TheCheezeryTheme
import mx.itson.thecheezery_252191.ui.theme.Very_purple

@Composable
fun CartScreen(
    cart: List<Product>,
    total: Float,
    onRemoveClick: (Product) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        BackButton(onClick = onBackClick)
        ScreenTitle(stringResource(R.string.my_order))

        if (cart.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.cart_empty),
                    fontSize = 20.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ) {
                items(cart) { product ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "$" + product.price,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Very_purple
                        )
                        IconButton(onClick = { onRemoveClick(product) }) {
                            Icon(
                                painter = painterResource(R.drawable.delete),
                                contentDescription = "Quitar",
                                tint = Pinky
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(R.string.total),
                fontSize = 40.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$" + total,
                fontSize = 50.sp,
                lineHeight = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Less_Purple
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewCart() {
    TheCheezeryTheme {
        CartScreen(
            cart = listOf(salties[2], coldDrinks[2], coldDrinks[3]),
            total = 11f,
            onRemoveClick = {},
            onBackClick = {}
        )
    }
}
