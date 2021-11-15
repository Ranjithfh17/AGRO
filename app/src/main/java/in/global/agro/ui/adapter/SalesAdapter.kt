package `in`.global.agro.ui.adapter

import `in`.global.agro.callbacks.SalesDetailListener
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.databinding.SalesAdapterBinding
import `in`.global.agro.decor.view.VerticalListViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SalesAdapter(private val salesDetailListener: SalesDetailListener) :
    RecyclerView.Adapter<SalesAdapter.SalesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        val binding =
            SalesAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SalesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) {
        val salesModel = differ.currentList[position]
        if (salesModel != null) {
            holder.bind(salesModel)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SalesViewHolder(private val binding: SalesAdapterBinding) :
        VerticalListViewHolder(binding) {


        fun bind(salesModel: SalesModel) {
            binding.apply {
                name.text = salesModel.name
                initial.text=salesModel.name.substring(0,1)

            }

        }


        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    salesDetailListener.onDetailClick(
                        differ.currentList[position]
                    )
                }
            }

        }

    }


    private val differCallBack = object : DiffUtil.ItemCallback<SalesModel>() {
        override fun areItemsTheSame(oldItem: SalesModel, newItem: SalesModel) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: SalesModel, newItem: SalesModel) =
            oldItem == newItem
    }


    val differ = AsyncListDiffer(this, differCallBack)


}