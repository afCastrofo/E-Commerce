package br.com.afCastrofo.ecommerce.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import br.com.afCastrofo.ecommerce.R
import br.com.afCastrofo.ecommerce.base.BaseViewModel
import br.com.afCastrofo.ecommerce.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

): BaseViewModel() {

    val loginSuccessful: LiveData<Unit> = SingleLiveData()
    val invalidEmail: LiveData<Unit> = SingleLiveData()
    val invalidPassword: LiveData<Unit> = SingleLiveData()
    
    fun onLoginPressed(email: String, password: String) {
        launch(errorMessage = R.string.verify_data) {
            if(email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                invalidEmail.call()
                throw Exception()
            }
    
            if(password.isBlank()){
                invalidPassword.call()
                throw Exception()
            }
            
            loginSuccessful.call()
        }
    }

}