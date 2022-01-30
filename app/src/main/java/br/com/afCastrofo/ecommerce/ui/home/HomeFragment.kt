package br.com.afCastrofo.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.afCastrofo.ecommerce.R
import br.com.afCastrofo.ecommerce.base.BaseFragment
import br.com.afCastrofo.ecommerce.data.model.cart.Cart
import br.com.afCastrofo.ecommerce.data.model.product.Product
import br.com.afCastrofo.ecommerce.databinding.FragmentHomeBinding
import br.com.afCastrofo.ecommerce.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    
    override val viewModel: HomeViewModel by viewModels()
    
    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    
    override fun initViews() {
        viewModel.fetchProducts()
        viewModel.fetchCart()
    
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
    }
    
    override fun initObservers() {
        viewModel.products.observe(this) {
            setupRecycler(it)
            setupSearch()
        }
        
        viewModel.setupCart.observe(this) {
            setupCart(it)
        }
        
        viewModel.cartUpdated.observe(this) {
            updateCart(it)
        }
        viewModel.hideCart.observe(this) {
            binding.clCart.isVisible = false
        }
    }
    
    private fun setupRecycler(products: List<Product>) {
        val adapter = ProductsAdapter(products)
        adapter.onAddToCart = {
            viewModel.addProductToCart(it)
        }
        
        binding.rvProducts.adapter = adapter
    }
    
    private fun setupSearch() {
        binding.etSearch.doAfterTextChanged {
            val searchedText = it.toString()
            viewModel.filterProducts(searchedText)
        }
        
        binding.etSearch.setOnEditorActionListener { _, i, _ ->
            if(i == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                binding.etSearch.clearFocus()
            }
            true
        }
    }
    
    private fun updateCart(cart: Cart) {
        binding.tvItemsInCart.text = getString(R.string.items_in_cart, cart.totalItems)
        binding.tvTotalPrice.text = cart.totalValue.convertToCurrency()
    }
    
    private fun setupCart(cart: Cart) {
        updateCart(cart)
        
        lifecycleScope.launchWhenResumed {
            binding.clCart.enterTranslateYAnimation(duration = 200L, delayMultiplier = 0)
        }
        
        binding.clCart.setOnClickListener {
            navigate(HomeFragmentDirections.actionHomeFragmentToCartFragment(), animation = TransitionAnimation.TRANSLATE_FROM_DOWN)
        }
    }
}