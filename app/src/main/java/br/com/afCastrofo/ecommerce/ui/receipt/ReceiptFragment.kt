package br.com.afCastrofo.ecommerce.ui.receipt

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.afCastrofo.ecommerce.R
import br.com.afCastrofo.ecommerce.base.BaseFragment
import br.com.afCastrofo.ecommerce.databinding.FragmentReceiptBinding
import br.com.afCastrofo.ecommerce.utils.convertToCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiptFragment: BaseFragment<FragmentReceiptBinding>() {
    
    override val viewModel: ReceiptViewModel by viewModels()
    
    override val bindingInflater: (LayoutInflater) -> FragmentReceiptBinding
        get() = FragmentReceiptBinding::inflate
    
    private val args: ReceiptFragmentArgs by navArgs()
    
    override fun initViews() {
        binding.tvTotalValue.text = getString(R.string.total_value_string, args.cart.totalValue.convertToCurrency())
        binding.tvTotalItems.text = getString(R.string.total_items, args.cart.totalItems)
        viewModel.finishPurchase(requireContext(), binding.root)
    }
    
    override fun initObservers() {
        viewModel.onReceiptGenerated.observe(this) {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }
}