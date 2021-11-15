package `in`.global.agro.ui.adapter

import `in`.global.agro.callbacks.ProductCallback
import `in`.global.agro.callbacks.PurchaseOptionsListener
import `in`.global.agro.data.model.ProductModel
import `in`.global.agro.databinding.PurchaseAdapterBinding
import `in`.global.agro.data.model.PurchaseModel
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.databinding.AdapterCustomerStatementBinding
import `in`.global.agro.databinding.AdapterProductBinding
import `in`.global.agro.decor.view.VerticalListViewHolder
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val listener: ProductCallback) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            AdapterProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productModel = differ.currentList[position]
        if (productModel != null) {
            holder.bind(productModel)
        }
    }



    override fun getItemCount(): Int {
        return differ.currentList.size
    }



    inner class ProductViewHolder(private val binding: AdapterProductBinding) :
        VerticalListViewHolder(binding) {


        @SuppressLint("SetTextI18n")
        fun bind(productModel: ProductModel) {
            binding.apply {
                productName.text = productModel.productName
                productPrice.text="₹"+productModel.price
            }
        }


        init {
            binding.deleteProduct.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteProduct(differ.currentList[position])
                }
            }
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onSelectProduct(differ.currentList[position])
                }
            }
        }
    }



    private val differCallBack = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)




}