package br.com.afCastrofo.testeframework.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.afCastrofo.testeframework.data.model.product.Product
import br.com.afCastrofo.testeframework.databinding.AdapterProductsBinding
import br.com.afCastrofo.testeframework.utils.convertToCurrency
import com.bumptech.glide.Glide
import java.util.*

class ProductsAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder<AdapterProductsBinding>>() {
    
    var onAddToCart: ((Product) -> Unit)? = null
    
    override fun getItemCount(): Int = products.size
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<AdapterProductsBinding> {
        return ViewHolder(
            AdapterProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    
    override fun onBindViewHolder(holder: ViewHolder<AdapterProductsBinding>, position: Int) {
        val product = products[position]
        
        holder.binding.apply {
            Glide.with(root.context)
                .load(product.image)
                .centerCrop()
                .into(ivProductImage)
            
            tvProductName.text = product.name
            tvProductPrice.text = product.value.convertToCurrency()
            
            tvAddToCart.setOnClickListener {
                onAddToCart?.invoke(product)
            }
        }
    }
    
    inner class ViewHolder<VB : ViewBinding>(
        val binding: VB
    ) : RecyclerView.ViewHolder(binding.root)
}
