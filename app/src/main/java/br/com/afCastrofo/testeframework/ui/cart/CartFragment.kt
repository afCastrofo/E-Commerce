package br.com.afCastrofo.testeframework.ui.cart

import android.print.PrintAttributes
import android.print.pdf.PrintedPdfDocument
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import br.com.afCastrofo.testeframework.base.BaseFragment
import br.com.afCastrofo.testeframework.data.model.cart.Cart
import br.com.afCastrofo.testeframework.data.model.cart.CartProduct
import br.com.afCastrofo.testeframework.databinding.FragmentCartBinding
import br.com.afCastrofo.testeframework.utils.convertToCurrency
import br.com.afCastrofo.testeframework.utils.navigate
import br.com.afCastrofo.testeframework.utils.navigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment: BaseFragment<FragmentCartBinding>() {
    
    override val viewModel: CartViewModel by viewModels()
    
    override val bindingInflater: (LayoutInflater) -> FragmentCartBinding
        get() = FragmentCartBinding::inflate
    
    override fun initViews() {
        binding.ivBack.setOnClickListener {
            navigateUp()
        }
    }
    
    override fun initObservers() {
        viewModel.products.observe(this) {
            setupRecycler(it)
        }
        
        viewModel.cart.observe(this) { cart ->
            setupSummaryData(cart)
    
            binding.btFinishPurchase.setOnClickListener {
                navigate(CartFragmentDirections.actionCartFragmentToReceiptFragment(cart))
            }
        }
        
        viewModel.cartEmpty.observe(this) {
            navigateUp()
        }
    }
    
    private fun setupRecycler(products: List<CartProduct>) {
        val adapter = CartProductsAdapter(products.toMutableList())
        adapter.removeFromCart = {
            viewModel.removeItemFromCart(it)
        }
        
        binding.rvCartProducts.adapter = adapter
    }
    
    private fun setupSummaryData(cart: Cart) {
        binding.tvTotalItems.text = cart.totalItems.toString()
        binding.tvValue.text = cart.totalValue.convertToCurrency()
    }
    
}