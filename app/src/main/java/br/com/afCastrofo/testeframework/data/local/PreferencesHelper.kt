package br.com.afCastrofo.testeframework.data.local

import android.content.Context
import android.os.Parcelable
import androidx.core.content.edit
import br.com.afCastrofo.testeframework.BuildConfig
import br.com.afCastrofo.testeframework.data.model.cart.Cart
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesHelper @Inject constructor(
    @ApplicationContext val context: Context
) {
    
    companion object {
        private const val SHARED_PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}.PreferencesHelper.SHARED_PREFERENCES_NAME"
        
        private const val CART = "PreferencesHelper.CART"
    }
    
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    
    fun putCart(cart: Cart) {
        putParcelable(CART, cart)
    }
    
    fun getCart(): Cart? = getParcelable(CART)
    
    private fun putParcelable(key: String, parcelable: Parcelable) {
        sharedPreferences.edit {
            putString(key, Gson().toJson(parcelable))
        }
    }
    
    private inline fun <reified T> getParcelable(key: String): T? {
        val stringParcelable = sharedPreferences.getString(key, null)
        return Gson().fromJson(stringParcelable, T::class.java)
    }
    
}