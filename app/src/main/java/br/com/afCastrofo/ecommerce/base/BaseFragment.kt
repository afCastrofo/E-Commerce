package br.com.afCastrofo.ecommerce.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import br.com.afCastrofo.ecommerce.utils.navigateUp

abstract class BaseFragment<VB: ViewBinding>: Fragment() {
    
    protected val binding: VB
        get() = mViewBinding as VB
    
    private var mViewBinding: VB? = null
    
    abstract val viewModel: BaseViewModel
    abstract val bindingInflater: (LayoutInflater) -> VB
    
    abstract fun initViews()
    abstract fun initObservers()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = bindingInflater.invoke(layoutInflater)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        requireActivity().onBackPressedDispatcher.addCallback {
            navigateUp()
        }
        
        initBaseObservers()
        initViews()
        initObservers()
    }
    
    private fun initBaseObservers() {
        viewModel.message.observe(this) {
            showToast(it)
        }
    }
    
    protected fun showToast(stringId: Int) {
        Toast.makeText(requireContext(), stringId, Toast.LENGTH_SHORT).show()
    }
}