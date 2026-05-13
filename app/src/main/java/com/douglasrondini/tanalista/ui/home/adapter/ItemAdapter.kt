package com.douglasrondini.tanalista.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.douglasrondini.tanalista.databinding.ItemListBinding
import com.douglasrondini.tanalista.domain.model.Item

class ItemAdapter(
    private var items: List<Item>
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ItemViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(p0.context),p0,false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        p0: ItemViewHolder,
        p1: Int
    ) {
        p0.bind(items[p1])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(
        val binding: ItemListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.txtItemName.text = item.name
            binding.txtitemQuantity.text = item.quantity.toString()
            binding.edtItemPrice.setText("R$ ${item.price}")
        }
    }


}