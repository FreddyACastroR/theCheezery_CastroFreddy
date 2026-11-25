package mx.itson.thecheezery_252191.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import mx.itson.thecheezery_252191.ui.screens.CartScreen
import mx.itson.thecheezery_252191.ui.screens.LoginScreen
import mx.itson.thecheezery_252191.ui.screens.MenuScreen
import mx.itson.thecheezery_252191.ui.screens.ProductsScreen
import mx.itson.thecheezery_252191.ui.screens.WelcomeScreen
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
                onCartClick = {
                    if (puedeNavegar(entry)) navController.navigate(Cart)
                }
            )
        }
        composable<Products> { entry ->
            val category = entry.toRoute<Products>().category
            // los productos salen de la bd, el remember es para no crear la consulta en cada recomposicion
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
    }
}
