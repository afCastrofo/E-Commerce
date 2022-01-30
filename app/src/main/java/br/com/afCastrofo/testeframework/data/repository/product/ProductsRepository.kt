package br.com.afCastrofo.testeframework.data.repository.product

import br.com.afCastrofo.testeframework.R
import br.com.afCastrofo.testeframework.data.model.product.Product
import javax.inject.Inject

class ProductsRepository @Inject constructor(

) {

    fun fetchProducts(): List<Product> =
        listOf(
            Product(0, "Maçã", 1.0, R.drawable.ic_apple),
            Product(1, "Pera", 1.0, R.drawable.ic_pear),
            Product(2, "Banana", 0.5, R.drawable.ic_banana),
            Product(3, "Abacaxi", 2.0, R.drawable.pinapple),
            Product(4, "Manga", 1.5, R.drawable.ic_mango_fruit)
        )
    
}
