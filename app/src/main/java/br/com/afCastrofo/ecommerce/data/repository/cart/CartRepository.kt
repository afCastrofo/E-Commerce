package br.com.afCastrofo.ecommerce.data.repository.cart

import br.com.afCastrofo.ecommerce.data.local.PreferencesHelper
import br.com.afCastrofo.ecommerce.data.model.cart.Cart
import br.com.afCastrofo.ecommerce.data.model.cart.CartProduct
import br.com.afCastrofo.ecommerce.data.model.product.Product
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
        
        cart?.let {
            val product = it.products.find { cartProduct -> cartProduct.product.id == addedProduct.id }
            
            if(product != null) {
                product.quantity++
            } else {
                it.products.add(CartProduct(addedProduct, 1))
            }
            
            preferencesHelper.putCart(it)
        }
        
        return cart
    }
    
    fun getCart(): Cart? = preferencesHelper.getCart()
    
    fun getCartProductsGroupedById(): List<CartProduct>? {
        return getCart()?.products
    }
    
    fun removeItemFromCart(cartProduct: CartProduct) {
        val cart = getCart()
        cart?.let {
            cart.products.removeIf { it.product.id == cartProduct.product.id }
            preferencesHelper.putCart(cart)
        }
    }
    
    suspend fun clearCart() {
        val cart = getCart()
        cart?.let {
            it.products.clear()
            preferencesHelper.putCart(it)
        }
    }
    
}