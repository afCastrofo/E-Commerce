package br.com.afCastrofo.testeframework.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.afCastrofo.testeframework.R
import br.com.afCastrofo.testeframework.base.BaseViewModel
import br.com.afCastrofo.testeframework.data.model.cart.Cart
import br.com.afCastrofo.testeframework.data.model.product.Product
import br.com.afCastrofo.testeframework.data.repository.cart.CartRepository
import br.com.afCastrofo.testeframework.data.repository.product.ProductsRepository
import br.com.afCastrofo.testeframework.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val cartRepository: CartRepository
): BaseViewModel() {

    val products: LiveData<List<Product>> = MutableLiveData()
    val cartUpdated: LiveData<Cart> = MutableLiveData()
    val setupCart: LiveData<Cart> = MutableLiveData()
    val hideCart: LiveData<Unit> = SingleLiveData()
    
    private var mProducts: List<Product> = listOf()

    fun fetchProducts() {
        launch {
            mProducts = productsRepository.fetchProducts()
            products.postValue(mProducts)
        }
    }
    
    fun fetchCart() {
        launch {
            val cart = cartRepository.getCart()
            
            cart?.let {
                if(it.products.isNotEmpty()){
                    setupCart.postValue(it)
                } else {
                    hideCart.call()
                }
            }
        }
    }
    
    fun addProductToCart(product: Product) {
        launch(R.string.error_updating_cart) {
            val cart = cartRepository.updateCart(product)
            
            cart?.let {
                if (it.products.size == 1) {
                    setupCart.postValue(it)
                } else {
                    cartUpdated.postValue(it)
                }
            }
        }
    }
    
    fun filterProducts(filter: String) {
        if(filter.isBlank()) {
            clearFilter()
        } else {
            val filteredProducts = mProducts.filter {
                it.name.lowercase().contains(filter.lowercase().trim())
            }
            products.postValue(filteredProducts)
        }
    }
    
    private fun clearFilter() {
        products.postValue(mProducts)
    }

}