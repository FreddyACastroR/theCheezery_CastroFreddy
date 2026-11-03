package mx.itson.thecheezery_252191.domain

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import mx.itson.thecheezery_252191.R

@Keep
@Serializable
enum class Category(val title: Int) {
    HOT_DRINKS(R.string.hot_drinks),
    COLD_DRINKS(R.string.cold_drinks),
    SALTIES(R.string.salties),
    SWEETS(R.string.sweets)
}
