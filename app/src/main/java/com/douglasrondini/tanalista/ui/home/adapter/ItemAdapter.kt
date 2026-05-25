package com.douglasrondini.tanalista.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.douglasrondini.tanalista.databinding.ItemListBinding
import com.douglasrondini.tanalista.domain.model.Item

class ItemAdapter(
    private val onPlusClick: (Item) -> Unit,
    private val onLessClick: (Item) -> Unit,
    private val onDeleteClick: (Item) -> Unit,
    private val onCheckedChange: (Item, Boolean) -> Unit,
    private val onPriceClick: (Item) -> Unit
) : ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.txtItemName.text = item.name
            binding.txtitemQuantity.text = item.quantity.toString()
            
            // Remove listener before setting state to avoid loops
            binding.checkbox.setOnCheckedChangeListener(null)
            binding.checkbox.isChecked = item.checked
            
            binding.txtItemPrice.text = "R$ %.2f".format(item.price)

            // Listeners
            binding.btnPlus.setOnClickListener { onPlusClick(item) }
            binding.btnLess.setOnClickListener { onLessClick(item) }
            binding.btnDelet.setOnClickListener { onDeleteClick(item) }
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (item.checked != isChecked) {
                    onCheckedChange(item, isChecked)
                }
            }
            binding.txtItemPrice.setOnClickListener { onPriceClick(item) }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}
