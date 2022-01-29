package br.com.afCastrofo.testeframework.ui.login

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import br.com.afCastrofo.testeframework.R
import br.com.afCastrofo.testeframework.base.BaseFragment
import br.com.afCastrofo.testeframework.databinding.FragmentLoginBinding
import br.com.afCastrofo.testeframework.utils.alphaAnimation
import br.com.afCastrofo.testeframework.utils.translateYAnimation
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
            //TODO navigate to next screen
        }
    }
    
    private fun runInitAnimations() {
        binding.lavGroceries.alphaAnimation {
            binding.tvWelcome.alphaAnimation {
                binding.tvInsertData.alphaAnimation {
                    binding.tilEmail.translateYAnimation()
                    binding.tilPassword.translateYAnimation(delayMultiplier = 2)
                    binding.btLogin.translateYAnimation(delayMultiplier = 3)
                }
            }
        }
        
    }
}