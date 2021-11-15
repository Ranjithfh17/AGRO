package `in`.global.agro.ui.adapter

import `in`.global.agro.data.model.ReceiptModel
import `in`.global.agro.databinding.AdapterReceiptBinding

import `in`.global.agro.decor.view.VerticalListViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ReceiptAdapter : RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        val binding = AdapterReceiptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReceiptViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        val status = differ.currentList[position]

        if (status != null) {
            holder.bind(status)
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ReceiptViewHolder(private val binding:AdapterReceiptBinding) : VerticalListViewHolder(binding) {

        fun bind(receiptModel: ReceiptModel) {

            binding.apply {
                name.text = receiptModel.name
                date.text = receiptModel.date
                payType.text = receiptModel.payType
                payAccount.text = receiptModel.payAccount

                if (receiptModel.balance != null) {
                    binding.balanceGroup.visibility = View.VISIBLE
                    balance.text = receiptModel.balance
                } else {
                    binding.balanceGroup.visibility = View.GONE

                }

                total.text = receiptModel.amount

            }

        }

    }


    private val differCallBack = object : DiffUtil.ItemCallback<ReceiptModel>() {
        override fun areItemsTheSame(oldItem: ReceiptModel, newItem: ReceiptModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ReceiptModel, newItem: ReceiptModel) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)




}