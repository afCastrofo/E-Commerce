package br.com.afCastrofo.testeframework.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.afCastrofo.testeframework.base.BaseViewModel
import br.com.afCastrofo.testeframework.data.model.cart.Cart
import br.com.afCastrofo.testeframework.data.model.cart.CartProduct
import br.com.afCastrofo.testeframework.data.repository.cart.CartRepository
import br.com.afCastrofo.testeframework.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
): BaseViewModel() {
    
    val cart: LiveData<Cart> = MutableLiveData()
    val products: LiveData<List<CartProduct>> = MutableLiveData()
    val cartEmpty: LiveData<Unit> = SingleLiveData()
    
    init {
        fetchProducts()
        fetchCart()
    }
    
    private fun fetchCart() {
        launch {
            cartRepository.getCart()?.let {
                if(it.products.isEmpty()){
                    cartEmpty.call()
                } else {
                    cart.postValue(it)
                }
            }
        }
    }
    
    private fun fetchProducts() {
        launch {
            cartRepository.getCartProductsGroupedById()?.let {
                products.postValue(it)
            }
        }
    }
    
    fun removeItemFromCart(cartProduct: CartProduct) {
        cartRepository.removeItemFromCart(cartProduct)
        fetchCart()
    }
    
}