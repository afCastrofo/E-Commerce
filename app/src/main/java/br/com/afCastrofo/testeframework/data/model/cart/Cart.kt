package br.com.afCastrofo.testeframework.data.model.cart

import android.os.Parcelable
import br.com.afCastrofo.testeframework.data.model.product.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    val products: ArrayList<Product>
): Parcelable {
    
    val totalItems: Int
        get() = products.size
    
    val totalValue: Double
        get() = products.sumOf { it.value }
    
}