package br.com.afCastrofo.ecommerce.ui.login

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import br.com.afCastrofo.ecommerce.BuildConfig
import br.com.afCastrofo.ecommerce.R
import br.com.afCastrofo.ecommerce.base.BaseFragment
import br.com.afCastrofo.ecommerce.databinding.FragmentLoginBinding
import br.com.afCastrofo.ecommerce.utils.alphaAnimation
import br.com.afCastrofo.ecommerce.utils.enterTranslateYAnimation
import br.com.afCastrofo.ecommerce.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {
    
    override val viewModel: LoginViewModel by viewModels()
    
    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate
    
    override fun initViews() {
        runInitAnimations()
        
        binding.btLogin.setOnClickListener {
            viewModel.onLoginPressed(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
        
        if(BuildConfig.DEBUG) {
            binding.etEmail.setText("a@a.com")
            binding.etPassword.setText("senha123")
        }
    }
    
    override fun initObservers() {
        viewModel.loading.observe(this) { loading ->
            binding.pbLogin.isVisible = loading
            binding.btLogin.isEnabled = !loading
            binding.etEmail.isEnabled = !loading
            binding.etPassword.isEnabled = !loading
        }
        
        viewModel.invalidEmail.observe(this) {
            binding.tilEmail.error = getString(R.string.verify_email)
            binding.etEmail.doBeforeTextChanged { _, _, _, _ ->
                binding.tilEmail.error = null
                binding.tilEmail.isErrorEnabled = false
            }
        }
        
        viewModel.invalidPassword.observe(this) {
            binding.tilPassword.error = getString(R.string.verify_password)
            binding.etPassword.doBeforeTextChanged { _, _, _, _ ->
                binding.tilPassword.error = null
                binding.tilPassword.isErrorEnabled = false
            }
        }
        
        viewModel.loginSuccessful.observe(this) {
            navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
    }
    
    private fun runInitAnimations() {
        binding.lavGroceries.alphaAnimation {
            binding.tvWelcome.alphaAnimation {
                binding.tvInsertData.alphaAnimation {
                    binding.tilEmail.enterTranslateYAnimation()
                    binding.tilPassword.enterTranslateYAnimation(delayMultiplier = 2)
                    binding.btLogin.enterTranslateYAnimation(delayMultiplier = 3)
                }
            }
        }
        
    }
}