package br.com.afCastrofo.testeframework.data.model.cart

import android.os.Parcelable
import br.com.afCastrofo.testeframework.data.model.product.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    val product: Product,
    var quantity: Int
): Parcelable