package br.com.afCastrofo.testeframework.data.model.cart

import br.com.afCastrofo.testeframework.data.model.product.Product

data class CartProduct(
    val id: Long,
    val quantity: Int,
    val products: List<Product>
)