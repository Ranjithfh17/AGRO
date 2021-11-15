package `in`.global.agro.ui.adapter

import `in`.global.agro.data.model.SubMenu
import `in`.global.agro.databinding.AdapterSubMenuBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SubMenuAdapter : RecyclerView.Adapter<SubMenuAdapter.SubMenuItemViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubMenuItemViewHolder {
        val binding =
            AdapterSubMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubMenuItemViewHolder(binding)

    }


    override fun onBindViewHolder(holder: SubMenuItemViewHolder, position: Int) {
        val menu = differ.currentList[position]

        if (menu != null) {
            holder.bind(menu)
        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


   inner class SubMenuItemViewHolder(val binding: AdapterSubMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.subClick.setOnClickListener {
                val position=adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    subMenuListener.onSubMenuClick(differ.currentList[position],binding.root)
                }
            }
        }

        fun bind(menu: SubMenu) {
            binding.subTitle.text = menu.title
            binding.subImageView.setImageResource(menu.image)
            menu.icon?.let { binding.subImageViewAdd.setImageResource(it) }
        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<SubMenu>() {
        override fun areItemsTheSame(oldItem: SubMenu, newItem: SubMenu) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: SubMenu, newItem: SubMenu) =
            oldItem == newItem
    }


    val differ = AsyncListDiffer(this, differCallback)


    companion object{


        lateinit var subMenuListener:SubMenuListener

        interface SubMenuListener {

            fun onSubMenuClick(subMenu: SubMenu,viewGroup: ViewGroup)
        }

        fun setOnSubMenuListener(subMenuListener: SubMenuListener){
            this.subMenuListener=subMenuListener
        }
    }




}