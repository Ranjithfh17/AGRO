package `in`.global.agro.ui.adapter

import `in`.global.agro.data.model.CustomerBalanceModel
import `in`.global.agro.databinding.AdapterCustomerBalanceBinding
import `in`.global.agro.decor.view.VerticalListViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CustomerBalanceAdapter: RecyclerView.Adapter<CustomerBalanceAdapter.CustomerBalanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerBalanceViewHolder {
        val binding = AdapterCustomerBalanceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CustomerBalanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerBalanceViewHolder, position: Int) {
        val salesModel = differ.currentList[position]
        if (salesModel != null) {
            holder.bind(salesModel)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CustomerBalanceViewHolder(private val binding: AdapterCustomerBalanceBinding) :
        VerticalListViewHolder(binding) {

        fun bind(salesModel: CustomerBalanceModel) {
            binding.apply {
                name.text = salesModel.name
                amount.text=salesModel.balance
                mobileNo.text=salesModel.phoneNo
                address.text=salesModel.address
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<CustomerBalanceModel>() {
        override fun areItemsTheSame(oldItem: CustomerBalanceModel, newItem: CustomerBalanceModel) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: CustomerBalanceModel, newItem: CustomerBalanceModel) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)


}