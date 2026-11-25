package mx.itson.thecheezery_252191.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.itson.thecheezery_252191.R
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity
import mx.itson.thecheezery_252191.data.defaultProducts
import mx.itson.thecheezery_252191.ui.components.BackButton
import mx.itson.thecheezery_252191.ui.components.ErrorText
import mx.itson.thecheezery_252191.ui.components.ScreenTitle
import mx.itson.thecheezery_252191.ui.theme.TheCheezeryTheme
import mx.itson.thecheezery_252191.ui.theme.Very_purple
import mx.itson.thecheezery_252191.ui.viewmodel.AddComboState
import mx.itson.thecheezery_252191.ui.viewmodel.ERROR_POCOS_PRODUCTOS

@Composable
fun AddComboScreen(
    state: AddComboState,
    products: List<ProductEntity>,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onProductClick: (ProductEntity) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
    ) {
        BackButton(onClick = onBackClick)
        ScreenTitle(stringResource(R.string.new_combo))

        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            TextField(
                value = state.name,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.form_name)) },
                isError = state.nameError != null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            ErrorText(state.nameError)
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = state.price,
                onValueChange = onPriceChange,
                label = { Text(stringResource(R.string.form_price)) },
                prefix = { Text("$") },
                isError = state.priceError != null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            ErrorText(state.priceError)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.combo_products),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Very_purple
            )
            ErrorText(state.productsError)
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            items(products) { product ->
                val checked = product.id in state.selected
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .toggleable(
                            value = checked,
                            onValueChange = { onProductClick(product) },
                            role = Role.Checkbox
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = checked, onCheckedChange = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = product.name,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$" + product.price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Very_purple
                    )
                }
            }
        }

        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.form_save))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddComboPreview() {
    val products = defaultProducts.mapIndexed { index, product -> product.copy(id = index + 1) }
    TheCheezeryTheme {
        AddComboScreen(
            state = AddComboState(name = "Desayuno", selected = listOf(1), productsError = ERROR_POCOS_PRODUCTOS),
            products = products,
            onNameChange = {},
            onPriceChange = {},
            onProductClick = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}
