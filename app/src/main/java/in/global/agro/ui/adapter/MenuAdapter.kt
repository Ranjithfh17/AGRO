package `in`.global.agro.ui.adapter

import `in`.global.agro.callbacks.MenuClickListener
import `in`.global.agro.data.model.Menu
import `in`.global.agro.databinding.AdapterMenuBinding
import `in`.global.agro.decor.view.VerticalListViewHolder
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MenuAdapter(private val listener: MenuClickListener) : RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val binding = AdapterMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemViewHolder(binding)


    }


    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val menu = differ.currentList[position]




        if (menu != null) {
            holder.bind(menu)
        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class MenuItemViewHolder(val binding: AdapterMenuBinding) :
        VerticalListViewHolder(binding) {

        lateinit var subMenuAdapter: SubMenuAdapter



        init {

            setSubMenuAdapter(binding.subMenuRecyclerview)

        }


        private fun setSubMenuAdapter(subMenuRecyclerview: RecyclerView) {
            subMenuAdapter = SubMenuAdapter()
            subMenuRecyclerview.adapter = subMenuAdapter
            subMenuRecyclerview.layoutManager = LinearLayoutManager(itemView.context)
        }


        fun bind(menu: Menu) {
            binding.title.text = menu.title
            binding.menuImage.setImageResource(menu.image)
            subMenuAdapter.differ.submitList(menu.list)

            if (menu.isSubMenu){
                binding.expandImage.visibility=View.VISIBLE
            }else{
                binding.expandImage.visibility=View.GONE
            }



            binding.root.setOnClickListener {

                if (menu.isSubMenu && binding.subMenuRecyclerview.visibility == View.GONE) {
                    binding.subMenuRecyclerview.visibility = View.VISIBLE

                } else {
                    binding.subMenuRecyclerview.visibility = View.GONE

                }

                if (!menu.isSubMenu ){
                    val position=adapterPosition

                    if (position != RecyclerView.NO_POSITION){
                        listener.onMenuClickListener(differ.currentList[position])
                    }
                }


            }
        }
    }



    private val differCallback = object : DiffUtil.ItemCallback<Menu>() {
        override fun areItemsTheSame(oldItem: Menu, newItem: Menu) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Menu, newItem: Menu) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)


}