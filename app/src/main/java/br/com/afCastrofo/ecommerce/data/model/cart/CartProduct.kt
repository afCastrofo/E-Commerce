package br.com.afCastrofo.ecommerce.data.model.cart

import android.os.Parcelable
import br.com.afCastrofo.ecommerce.data.model.product.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    val product: Product,
    var quantity: Int
): Parcelable