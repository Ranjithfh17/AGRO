package `in`.global.agro.ui.adapter

import `in`.global.agro.callbacks.PurchaseOptionsListener
import `in`.global.agro.databinding.PurchaseAdapterBinding
import `in`.global.agro.data.model.PurchaseModel
import `in`.global.agro.decor.view.VerticalListViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PurchaseAdapter(private val listener:PurchaseOptionsListener) : RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val binding=PurchaseAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PurchaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val salesModel=differ.currentList[position]
        if (salesModel != null){
            holder.bind(salesModel)
        }
    }


    override fun getItemCount(): Int {
       return differ.currentList.size
    }


  inner  class PurchaseViewHolder(private val binding: PurchaseAdapterBinding):VerticalListViewHolder(binding) {


        fun bind(salesModel: PurchaseModel){
           binding.apply {
               name.text=salesModel.name
               date.text=salesModel.date
               product.text=salesModel.product
               quantity.text=salesModel.quantity
               price.text=salesModel.price
               tax.text=salesModel.tax
               total.text=salesModel.totalAmount
           }

        }


        init {
            binding.totalTitle.isSelected=true
            binding.options.setOnClickListener {
                val position=adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onOptionsClick(differ.currentList[position],binding.options)

                }
            }


        }

    }


    private val differCallBack=object :DiffUtil.ItemCallback<PurchaseModel>(){
        override fun areItemsTheSame(oldItem: PurchaseModel, newItem: PurchaseModel)=
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PurchaseModel, newItem: PurchaseModel)=
            oldItem == newItem
    }

    val differ=AsyncListDiffer(this,differCallBack)



}