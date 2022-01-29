package br.com.afCastrofo.testeframework.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.afCastrofo.testeframework.R
import br.com.afCastrofo.testeframework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

): BaseViewModel() {

    val loginSuccessful: LiveData<Unit> = MutableLiveData()
    val invalidEmail: LiveData<Unit> = MutableLiveData()
    val invalidPassword: LiveData<Unit> = MutableLiveData()
    
    fun onLoginPressed(email: String, password: String) {
        launch(errorMessage = R.string.verify_data) {
            if(email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                invalidEmail.postValue(Unit)
                throw Exception()
            }
    
            if(password.isBlank()){
                invalidPassword.postValue(Unit)
                throw Exception()
            }
            
            loginSuccessful.postValue(Unit)
        }
    }

}