package br.com.afCastrofo.testeframework.data.repository.product

import br.com.afCastrofo.testeframework.data.local.PreferencesHelper
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val preferencesHelper: PreferencesHelper
): ProductsRepository {



}
