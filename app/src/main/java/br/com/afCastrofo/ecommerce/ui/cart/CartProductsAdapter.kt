package br.com.afCastrofo.ecommerce.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.afCastrofo.ecommerce.data.model.cart.CartProduct
import br.com.afCastrofo.ecommerce.databinding.AdapterCartProductsBinding
import br.com.afCastrofo.ecommerce.utils.convertToCurrency
import com.bumptech.glide.Glide

class CartProductsAdapter(
    private val products: MutableList<CartProduct>
) : RecyclerView.Adapter<CartProductsAdapter.ViewHolder<AdapterCartProductsBinding>>() {
    
    var removeFromCart: ((CartProduct) -> Unit)? = null
    
    override fun getItemCount(): Int = products.size
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<AdapterCartProductsBinding> {
        return ViewHolder(
            AdapterCartProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    
    override fun onBindViewHolder(holder: ViewHolder<AdapterCartProductsBinding>, position: Int) {
        val product = products[position].product
        val quantity = products[position].quantity
        
        holder.binding.apply {
            Glide.with(root.context)
                .load(product.image)
                .centerCrop()
                .into(ivProductImage)
            
            tvProductName.text = product.name
            tvProductQuantity.text = "x$quantity"
            tvProductPrice.text = product.value.convertToCurrency()
            
            tvRemoveFromCart.setOnClickListener {
                removeFromCart?.invoke(products[position])
                products.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }
    
    inner class ViewHolder<VB : ViewBinding>(
        val binding: VB
    ) : RecyclerView.ViewHolder(binding.root)
}
