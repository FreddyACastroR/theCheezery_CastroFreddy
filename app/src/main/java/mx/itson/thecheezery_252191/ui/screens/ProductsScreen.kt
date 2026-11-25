package mx.itson.thecheezery_252191.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.itson.thecheezery_252191.R
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity
import mx.itson.thecheezery_252191.data.images
import mx.itson.thecheezery_252191.data.salties
import mx.itson.thecheezery_252191.ui.components.BackButton
import mx.itson.thecheezery_252191.ui.components.ScreenTitle
import mx.itson.thecheezery_252191.ui.theme.DisabledGrey
import mx.itson.thecheezery_252191.ui.theme.Less_Purple
import mx.itson.thecheezery_252191.ui.theme.Pinky
import mx.itson.thecheezery_252191.ui.theme.PurpleGrey40
import mx.itson.thecheezery_252191.ui.theme.TheCheezeryTheme
import mx.itson.thecheezery_252191.ui.theme.Very_purple
import mx.itson.thecheezery_252191.ui.viewmodel.ProductItem

@Composable
fun ProductsScreen(
    title: Int,
    products: List<ProductItem>,
    onAddClick: (ProductEntity) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        BackButton(onClick = onBackClick)

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            item {
                ScreenTitle(stringResource(title))
            }
            items(products) { item ->
                ProductRow(item, onAddClick)
            }
        }
    }
}

@Composable
fun ProductRow(item: ProductItem, onAddClick: (ProductEntity) -> Unit) {
    val product = item.product
    val image = images[product.image]
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (image != null) {
            Image(
                painter = painterResource(image),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        } else {
            // los productos agregados por el usuario no tienen imagen, se pone su inicial
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Pinky, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = product.name.take(1).uppercase(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                color = Less_Purple
            )
            if (product.description != null) {
                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "$" + product.price,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Very_purple
        )
        Spacer(modifier = Modifier.width(12.dp))

        FilledIconButton(
            onClick = { onAddClick(product) },
            enabled = !item.inCart,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Very_purple,
                contentColor = Color.White,
                disabledContainerColor = DisabledGrey,
                disabledContentColor = PurpleGrey40
            ),
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.add_cart),
                contentDescription = "Agregar al carrito"
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewShowProducts() {
    TheCheezeryTheme {
        ProductsScreen(
            title = R.string.salties,
            products = salties.map { ProductItem(it, false) },
            onAddClick = {},
            onBackClick = {}
        )
    }
}
