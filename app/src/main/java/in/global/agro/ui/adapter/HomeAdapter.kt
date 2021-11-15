package `in`.global.agro.ui.adapter

import `in`.global.agro.data.model.HomeModel
import `in`.global.agro.databinding.AdapterHomeBinding
import `in`.global.agro.decor.view.VerticalListViewHolder
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            AdapterHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val status = differ.currentList[position]
        if (status != null) {
            holder.bind(status)
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class HomeViewHolder(private val binding: AdapterHomeBinding) : VerticalListViewHolder(binding) {

        fun bind(status: HomeModel.Data) {

                binding.title.text=status.label
                binding.value.text=status.value

        }


        init {
            binding.title.isSelected=true
        }


    }


    private val differCallBack = object : DiffUtil.ItemCallback<HomeModel.Data>() {
        override fun areItemsTheSame(oldItem: HomeModel.Data, newItem: HomeModel.Data) =
            oldItem.label == newItem.label

        override fun areContentsTheSame(oldItem: HomeModel.Data, newItem: HomeModel.Data) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)


}