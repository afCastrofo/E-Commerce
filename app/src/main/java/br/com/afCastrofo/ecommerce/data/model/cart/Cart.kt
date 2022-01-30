package br.com.afCastrofo.ecommerce.data.model.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    val products: ArrayList<CartProduct>
): Parcelable {
    
    val totalItems: Int
        get() = products.sumOf { it.quantity }
    
    val totalValue: Double
        get() = products.sumOf { it.product.value * it.quantity }
    
}