package br.com.afCastrofo.ecommerce.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.afCastrofo.ecommerce.utils.SingleLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {
    
    val loading: LiveData<Boolean>
        get() = mLoading
    private val mLoading = MutableLiveData<Boolean>()
    
    val message: LiveData<Int>
        get() = mMessage
    private val mMessage = MutableLiveData<Int>()
    
    protected fun <T> LiveData<T>.postValue(data: T) {
        if (this is MutableLiveData<T>) {
            postValue(data)
        }
    }
    
    protected fun <T> LiveData<T>.call() {
        if (this is SingleLiveData<T>) {
            call()
        }
    }
    
    protected fun launch(
        @StringRes errorMessage: Int? = null,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend CoroutineScope.() -> Unit
    ) {
        mLoading.postValue(true)
        viewModelScope.launch(dispatcher) {
            try {
                block.invoke(this)
                mLoading.postValue(false)
            } catch (e: Exception) {
                e.printStackTrace()
                mLoading.postValue(false)
                errorMessage?.let {
                    mMessage.postValue(it)
                }
            }
        }
    }
    
}