package `in`.global.agro.ui.adapter

import `in`.global.agro.callbacks.PurchaseOptionsListener
import `in`.global.agro.databinding.PurchaseAdapterBinding
import `in`.global.agro.data.model.PurchaseModel
import `in`.global.agro.data.model.SalesModel
import `in`.global.agro.databinding.AdapterCustomerStatementBinding
import `in`.global.agro.decor.view.VerticalListViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CustomerStatementAdapter() : RecyclerView.Adapter<CustomerStatementAdapter.CustomerStatementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerStatementViewHolder {
        val binding=AdapterCustomerStatementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomerStatementViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CustomerStatementViewHolder, position: Int) {
        val salesModel=differ.currentList[position]
        if (salesModel != null){
            holder.bind(salesModel)
        }
    }


    override fun getItemCount(): Int {
       return differ.currentList.size
    }


  inner  class CustomerStatementViewHolder(private val binding: AdapterCustomerStatementBinding):VerticalListViewHolder(binding) {

        fun bind(salesModel: SalesModel){
           binding.apply {
//               name.text=salesModel.name
//               date.text=salesModel.date
//               product.text=salesModel.product
//               quantity.text=salesModel.quantity
//               price.text=salesModel.price
//               tax.text=salesModel.tax
//               total.text=salesModel.totalAmount
           }

        }



    }


    private val differCallBack=object :DiffUtil.ItemCallback<SalesModel>(){
        override fun areItemsTheSame(oldItem: SalesModel, newItem: SalesModel)=
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SalesModel, newItem: SalesModel)=
            oldItem == newItem
    }

    val differ=AsyncListDiffer(this,differCallBack)



}