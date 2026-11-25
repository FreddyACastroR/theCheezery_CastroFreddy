package mx.itson.thecheezery_252191.data

import mx.itson.thecheezery_252191.R
import mx.itson.thecheezery_252191.data.database.entity.ProductEntity
import mx.itson.thecheezery_252191.domain.Category

// productos con los que se llena la base de datos la primera vez que se abre la app

val coldDrinks = listOf(
    ProductEntity(name = "Caramel Frap", price = 5f, image = "caramel_frap", category = Category.COLD_DRINKS.name, description = "Caramel syrup meets coffee, milk and ice and whipped cream and buttery caramel sauce layer the love on top."),
    ProductEntity(name = "Chocolate Frap", price = 6f, image = "chocolate_frap", category = Category.COLD_DRINKS.name, description = "Rich mocha-flavored sauce meets up with chocolaty chips, milk and ice for a blender bash."),
    ProductEntity(name = "Cold Brew", price = 3f, image = "coldbrew", category = Category.COLD_DRINKS.name, description = "Created by steeping medium-to-coarse ground coffee in room temperature water for 12 hours or longer."),
    ProductEntity(name = "Matcha Latte", price = 4f, image = "matcha", category = Category.COLD_DRINKS.name, description = "Leafy taste of matcha green tea powder with creamy milk and a little sugar for a flavor balance that will leave you feeling ready and raring to go."),
    ProductEntity(name = "Oreo Milkshake", price = 7f, image = "oreomilkshake", category = Category.COLD_DRINKS.name, description = "Chocolate ice cream, and oreo cookies. Topped with whipped cream with cocoa and chocolate syrup."),
    ProductEntity(name = "Peanut Milkshake", price = 7f, image = "peanutmilkshake", category = Category.COLD_DRINKS.name, description = "Vanilla ice cream, mixed with peanut butter and chocolate."),
)

val hotDrinks = listOf(
    ProductEntity(name = "Latte", price = 6f, image = "latte", category = Category.HOT_DRINKS.name, description = "Coffee drink made with espresso and steamed milk"),
    ProductEntity(name = "Hot chocolate", price = 5f, image = "hotchocolate", category = Category.HOT_DRINKS.name, description = "Heated drink consisting of shaved chocolate, topped with marshmallows."),
    ProductEntity(name = "Espresso", price = 4f, image = "espresso", category = Category.HOT_DRINKS.name, description = "Full-flavored, concentrated form of coffee."),
    ProductEntity(name = "Chai Latte", price = 6f, image = "chailatte", category = Category.HOT_DRINKS.name, description = "Spiced tea concentrate with milk"),
    ProductEntity(name = "Capuccino", price = 7f, image = "capuccino", category = Category.HOT_DRINKS.name, description = "A cappuccino is an espresso-based coffee drink, prepared with steamed foam."),
    ProductEntity(name = "American coffee", price = 2f, image = "americano", category = Category.HOT_DRINKS.name, description = "Espresso with hot water"),
)

val sweets = listOf(
    ProductEntity(name = "Blueberry cake", price = 6f, image = "blueberrycake", category = Category.SWEETS.name, description = "Vanilla cake flavor, topped with cheese topping and blueberries."),
    ProductEntity(name = "Chocolate cupcake", price = 3f, image = "chocolatecupcake", category = Category.SWEETS.name, description = "Chocolate cupcakes topped with butter cream and cherries"),
    ProductEntity(name = "Lemon tartalette", price = 4f, image = "lemontartalette", category = Category.SWEETS.name, description = "Pastry shell with a lemon flavored filling"),
    ProductEntity(name = "Red Velvet cake", price = 6f, image = "redvelvetcake", category = Category.SWEETS.name, description = "Soft, moist, buttery cake topped with an easy cream cheese frosting."),
    ProductEntity(name = "Cherry cheesecake", price = 7f, image = "strawberrycheesecake", category = Category.SWEETS.name, description = "This cherry topped cheesecake is positively creamy and delicious and will be your new favorite dessert."),
    ProductEntity(name = "Tiramisu", price = 6f, image = "tiramisu", category = Category.SWEETS.name, description = "Coffee-flavored Italian dessert"),
)

val salties = listOf(
    ProductEntity(name = "Chicken crepes", price = 6f, image = "chickencrepes", category = Category.SALTIES.name, description = "Fine crepes stuffed with Alfredo chicken, spinach and mushrooms."),
    ProductEntity(name = "Club Sandwich", price = 5f, image = "clubsandwich", category = Category.SALTIES.name, description = "A delicious sandwich served with french fries."),
    ProductEntity(name = "Panini", price = 4f, image = "ic_panini", category = Category.SALTIES.name, description = "Sandwich made with Italian bread served warmed by grilling."),
    ProductEntity(name = "Philly cheese steak", price = 6f, image = "phillycheesesteak", category = Category.SALTIES.name, description = "Smothered in grilled onions, green peppers, mushrooms, and Provolone."),
    ProductEntity(name = "Nachos", price = 7f, image = "nachos", category = Category.SALTIES.name, description = "Tortilla chips layered with beef and melted cheddar cheese. Served with fried beans, guacamole, pico de gallo, and sour topping."),
)

val defaultProducts = hotDrinks + coldDrinks + salties + sweets

// en la bd se guarda el nombre de la imagen y aqui se busca su drawable
// (los R.drawable cambian cada que se compila, por eso no se guardan)
val images = mapOf(
    "caramel_frap" to R.drawable.caramel_frap,
    "chocolate_frap" to R.drawable.chocolate_frap,
    "coldbrew" to R.drawable.coldbrew,
    "matcha" to R.drawable.matcha,
    "oreomilkshake" to R.drawable.oreomilkshake,
    "peanutmilkshake" to R.drawable.peanutmilkshake,
    "latte" to R.drawable.latte,
    "hotchocolate" to R.drawable.hotchocolate,
    "espresso" to R.drawable.espresso,
    "chailatte" to R.drawable.chailatte,
    "capuccino" to R.drawable.capuccino,
    "americano" to R.drawable.americano,
    "blueberrycake" to R.drawable.blueberrycake,
    "chocolatecupcake" to R.drawable.chocolatecupcake,
    "lemontartalette" to R.drawable.lemontartalette,
    "redvelvetcake" to R.drawable.redvelvetcake,
    "strawberrycheesecake" to R.drawable.strawberrycheesecake,
    "tiramisu" to R.drawable.tiramisu,
    "chickencrepes" to R.drawable.chickencrepes,
    "clubsandwich" to R.drawable.clubsandwich,
    "ic_panini" to R.drawable.ic_panini,
    "phillycheesesteak" to R.drawable.phillycheesesteak,
    "nachos" to R.drawable.nachos,
)
