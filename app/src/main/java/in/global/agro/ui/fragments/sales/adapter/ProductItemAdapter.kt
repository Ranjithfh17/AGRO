package `in`.global.agro.ui.fragments.sales.adapter

import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.data.model.ProductItem
import `in`.global.agro.databinding.AdapterCustomerBalanceBinding
import `in`.global.agro.databinding.AdapterProductItemBinding
import `in`.global.agro.decor.view.VerticalListViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ProductItemAdapter : RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val binding = AdapterProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ProductItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        val productItem = differ.currentList[position]
        if (productItem != null) {
            holder.bind(productItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ProductItemViewHolder(private val binding: AdapterProductItemBinding) :
        VerticalListViewHolder(binding) {

        fun bind(productItem: ProductItem) {
            binding.apply {

                productName.text = productItem.product
                quantity.text = productItem.quantity
                totQuantity.text = productItem.quantity
                price.text = productItem.price
                totPrice.text = productItem.price
                totalAmount.text = productItem.subtotal
                tax.text = productItem.tax
                discount.text = productItem.discount


            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem) =
            oldItem.product == newItem.product

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)


}