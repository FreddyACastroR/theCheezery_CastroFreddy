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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.itson.thecheezery_252191.R
import mx.itson.thecheezery_252191.data.coldDrinks
import mx.itson.thecheezery_252191.data.database.entity.ComboEntity
import mx.itson.thecheezery_252191.data.database.relation.ComboWithProducts
import mx.itson.thecheezery_252191.data.hotDrinks
import mx.itson.thecheezery_252191.data.salties
import mx.itson.thecheezery_252191.data.sweets
import mx.itson.thecheezery_252191.ui.components.BackButton
import mx.itson.thecheezery_252191.ui.components.ScreenTitle
import mx.itson.thecheezery_252191.ui.theme.Less_Purple
import mx.itson.thecheezery_252191.ui.theme.TheCheezeryTheme
import mx.itson.thecheezery_252191.ui.theme.Very_purple

@Composable
fun CombosScreen(
    combos: List<ComboWithProducts>,
    onNewComboClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        BackButton(onClick = onBackClick)
        ScreenTitle(stringResource(R.string.combos))

        if (combos.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.combos_empty),
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
                    .padding(horizontal = 24.dp)
            ) {
                items(combos) { item ->
                    ComboRow(item)
                }
            }
        }

        Button(
            onClick = onNewComboClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.new_combo))
        }
    }
}

@Composable
fun ComboRow(item: ComboWithProducts) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.combo.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Less_Purple
            )
            Text(
                text = item.products.joinToString(", ") { it.name },
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$" + item.combo.price,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Very_purple
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CombosPreview() {
    TheCheezeryTheme {
        CombosScreen(
            combos = listOf(
                ComboWithProducts(ComboEntity(name = "Desayuno", price = 9f), listOf(hotDrinks[0], salties[2])),
                ComboWithProducts(ComboEntity(name = "Tarde dulce", price = 10f), listOf(coldDrinks[3], sweets[5]))
            ),
            onNewComboClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CombosEmptyPreview() {
    TheCheezeryTheme {
        CombosScreen(combos = emptyList(), onNewComboClick = {}, onBackClick = {})
    }
}
