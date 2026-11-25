package mx.itson.thecheezery_252191.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import mx.itson.thecheezery_252191.R
import mx.itson.thecheezery_252191.ui.screens.AddComboScreen
import mx.itson.thecheezery_252191.ui.screens.AddProductScreen
import mx.itson.thecheezery_252191.ui.screens.CartScreen
import mx.itson.thecheezery_252191.ui.screens.CombosScreen
import mx.itson.thecheezery_252191.ui.screens.LoginScreen
import mx.itson.thecheezery_252191.ui.screens.MenuScreen
import mx.itson.thecheezery_252191.ui.screens.ProductsScreen
import mx.itson.thecheezery_252191.ui.screens.WelcomeScreen
import mx.itson.thecheezery_252191.ui.viewmodel.AddComboViewModel
import mx.itson.thecheezery_252191.ui.viewmodel.AddProductViewModel
import mx.itson.thecheezery_252191.ui.viewmodel.LoginViewModel
import mx.itson.thecheezery_252191.ui.viewmodel.ProductItem
import mx.itson.thecheezery_252191.ui.viewmodel.ShopViewModel

private fun puedeNavegar(entry: NavBackStackEntry): Boolean {
    return entry.lifecycle.currentState == Lifecycle.State.RESUMED
}

@Composable
fun CheezeryNavHost() {
    val navController = rememberNavController()
    val shopViewModel: ShopViewModel = viewModel(factory = ShopViewModel.Factory)

    NavHost(navController = navController, startDestination = Welcome) {
        composable<Welcome> { entry ->
            WelcomeScreen(
                onStartClick = {
                    if (puedeNavegar(entry)) navController.navigate(Login)
                }
            )
        }
        composable<Login> { entry ->
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                state = loginViewModel.state,
                onEmailChange = { loginViewModel.onEmailChange(it) },
                onPasswordChange = { loginViewModel.onPasswordChange(it) },
                onLoginClick = {
                    if (puedeNavegar(entry) && loginViewModel.login()) {
                        navController.navigate(Menu) {
                            popUpTo<Welcome> { inclusive = true }
                        }
                    }
                },
                onBackClick = {
                    if (puedeNavegar(entry)) navController.popBackStack()
                }
            )
        }
        composable<Menu> { entry ->
            MenuScreen(
                onCategoryClick = { category ->
                    if (puedeNavegar(entry)) navController.navigate(Products(category))
                },
                onCombosClick = {
                    if (puedeNavegar(entry)) navController.navigate(Combos)
                },
                onAddProductClick = {
                    if (puedeNavegar(entry)) navController.navigate(AddProduct)
                },
                onCartClick = {
                    if (puedeNavegar(entry)) navController.navigate(Cart)
                }
            )
        }
        composable<Products> { entry ->
            val category = entry.toRoute<Products>().category
            val products by remember(category) { shopViewModel.productsOf(category) }
                .collectAsState(initial = emptyList())
            ProductsScreen(
                title = category.title,
                products = products.map { ProductItem(it, shopViewModel.isInCart(it)) },
                onAddClick = { shopViewModel.addToCart(it) },
                onBackClick = {
                    if (puedeNavegar(entry)) navController.popBackStack()
                }
            )
        }
        composable<Cart> { entry ->
            CartScreen(
                cart = shopViewModel.cart,
                total = shopViewModel.total,
                onRemoveClick = { shopViewModel.removeFromCart(it) },
                onBackClick = {
                    if (puedeNavegar(entry)) navController.popBackStack()
                }
            )
        }
        composable<Combos> { entry ->
            val combos by shopViewModel.combos.collectAsState()
            CombosScreen(
                combos = combos,
                onNewComboClick = {
                    if (puedeNavegar(entry)) navController.navigate(AddCombo)
                },
                onBackClick = {
                    if (puedeNavegar(entry)) navController.popBackStack()
                }
            )
        }
        composable<AddCombo> { entry ->
            val addComboViewModel: AddComboViewModel = viewModel(factory = AddComboViewModel.Factory)
            val products by addComboViewModel.products.collectAsState(initial = emptyList())
            LaunchedEffect(addComboViewModel.saved) {
                if (addComboViewModel.saved) navController.popBackStack<AddCombo>(inclusive = true)
            }

            AddComboScreen(
                state = addComboViewModel.state,
                products = products,
                onNameChange = { addComboViewModel.onNameChange(it) },
                onPriceChange = { addComboViewModel.onPriceChange(it) },
                onProductClick = { addComboViewModel.onProductClick(it) },
                onSaveClick = { addComboViewModel.saveCombo() },
                onBackClick = {
                    if (puedeNavegar(entry)) navController.popBackStack()
                }
            )
        }
        composable<AddProduct> { entry ->
            val context = LocalContext.current
            val addProductViewModel: AddProductViewModel = viewModel(factory = AddProductViewModel.Factory)

            LaunchedEffect(addProductViewModel.saved) {
                if (addProductViewModel.saved) {
                    Toast.makeText(context, R.string.product_saved, Toast.LENGTH_SHORT).show()
                    navController.popBackStack<AddProduct>(inclusive = true)
                }
            }

            AddProductScreen(
                state = addProductViewModel.state,
                onNameChange = { addProductViewModel.onNameChange(it) },
                onPriceChange = { addProductViewModel.onPriceChange(it) },
                onDescriptionChange = { addProductViewModel.onDescriptionChange(it) },
                onCategoryChange = { addProductViewModel.onCategoryChange(it) },
                onSaveClick = { addProductViewModel.saveProduct() },
                onBackClick = {
                    if (puedeNavegar(entry)) navController.popBackStack()
                }
            )
        }
    }
}
