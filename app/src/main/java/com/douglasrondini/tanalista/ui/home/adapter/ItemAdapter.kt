package com.douglasrondini.tanalista.ui.home.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.douglasrondini.tanalista.databinding.ItemListBinding
import com.douglasrondini.tanalista.domain.model.Item
import java.text.NumberFormat
import java.util.Locale

class ItemAdapter(
    private var items: List<Item>,
    private val onPlusClick: (Item) -> Unit,
    private val onLessClick: (Item) -> Unit,
    private val onDeleteClick: (Item) -> Unit,
    private val onCheckedChange: (Item, Boolean) -> Unit,
    private val onPriceClick: (Item) -> Unit
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
            binding.checkbox.isChecked = item.checked
            binding.txtItemPrice.text = "R$ %.2f".format(item.price)
            // Botões
            binding.btnPlus.setOnClickListener { onPlusClick(item) }
            binding.btnLess.setOnClickListener { onLessClick(item) }
            binding.btnDelet.setOnClickListener { onDeleteClick(item) }
            binding.checkbox.setOnCheckedChangeListener { _, isChecked -> onCheckedChange(item, isChecked) }
            binding.txtItemPrice.setOnClickListener { onPriceClick(item) }


        }


    }


}