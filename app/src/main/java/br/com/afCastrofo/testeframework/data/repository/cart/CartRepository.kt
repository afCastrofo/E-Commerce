package br.com.afCastrofo.testeframework.data.repository.cart

import br.com.afCastrofo.testeframework.data.local.PreferencesHelper
import br.com.afCastrofo.testeframework.data.model.cart.Cart
import br.com.afCastrofo.testeframework.data.model.product.Product
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val preferencesHelper: PreferencesHelper
) {
    
    init {
        if(getCart() == null){
            preferencesHelper.putCart(
                Cart(arrayListOf())
            )
        }
    }
    
    fun updateCart(addedProduct: Product): Cart? {
        val cart = getCart()
        
        cart?.let { it ->
            it.products.add(addedProduct)
            preferencesHelper.putCart(it)
        }
        
        return cart
    }
    
    fun getCart(): Cart? = preferencesHelper.getCart()
    
}