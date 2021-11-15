package `in`.global.agro.ui.fragments.sales.adapter

import `in`.global.agro.callbacks.SalesOptionsListener
import `in`.global.agro.data.model.Products
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.databinding.AdapterSalesDetailsBinding
import `in`.global.agro.decor.view.VerticalListViewHolder
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SalesDetailAdapter(private val salesOptionsListener: SalesOptionsListener) :
    RecyclerView.Adapter<SalesDetailAdapter.SalesDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesDetailViewHolder {
        val binding =
            AdapterSalesDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SalesDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SalesDetailViewHolder, position: Int) {

        val salesModel = differ.currentList[position]
        holder.bind(salesModel)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SalesDetailViewHolder(val binding: AdapterSalesDetailsBinding) :
        VerticalListViewHolder(binding) {

        val list= mutableListOf<Products>()

        fun bind(salesModel: SalesModel) {



            for (items in salesModel.products) {

                list.add(items)

            }



            for (i in list){
                binding.product.text=i.product
            }



        }

        init {
//            binding.options.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    salesOptionsListener.onOptionsClick(
//                        differ.currentList[position],
//                        binding.options
//                    )
//                }
//            }

        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<SalesModel>() {
        override fun areItemsTheSame(oldItem: SalesModel, newItem: SalesModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SalesModel, newItem: SalesModel) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)


}