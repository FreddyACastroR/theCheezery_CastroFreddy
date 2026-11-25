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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
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
import mx.itson.thecheezery_252191.domain.Category
import mx.itson.thecheezery_252191.ui.components.BackButton
import mx.itson.thecheezery_252191.ui.components.ErrorText
import mx.itson.thecheezery_252191.ui.components.ScreenTitle
import mx.itson.thecheezery_252191.ui.theme.TheCheezeryTheme
import mx.itson.thecheezery_252191.ui.theme.Very_purple
import mx.itson.thecheezery_252191.ui.viewmodel.AddProductState

@Composable
fun AddProductScreen(
    state: AddProductState,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        BackButton(onClick = onBackClick)
        ScreenTitle(stringResource(R.string.new_product))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
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
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            ErrorText(state.priceError)
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = state.description,
                onValueChange = onDescriptionChange,
                label = { Text(stringResource(R.string.form_description)) },
                maxLines = 3,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.form_category),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Very_purple
            )
            for (category in Category.entries) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .selectable(
                            selected = state.category == category,
                            onClick = { onCategoryChange(category) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = state.category == category, onClick = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = stringResource(category.title), fontSize = 16.sp, color = Color.DarkGray)
                }
            }
            ErrorText(state.categoryError)
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onSaveClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.form_save))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddProductPreview() {
    TheCheezeryTheme {
        AddProductScreen(
            state = AddProductState(name = "Bagel", price = "3.5", category = Category.SALTIES),
            onNameChange = {},
            onPriceChange = {},
            onDescriptionChange = {},
            onCategoryChange = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}
