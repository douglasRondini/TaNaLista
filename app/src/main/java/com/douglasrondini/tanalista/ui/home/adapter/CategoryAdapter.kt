package com.douglasrondini.tanalista.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.douglasrondini.tanalista.databinding.ItemCategoryBinding
import com.douglasrondini.tanalista.domain.model.Category

class CategoryAdapter(
    private var categories: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(p0.context), p0, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        p0: CategoryViewHolder,
        p1: Int
    ) {
        p0.bind(categories[p1])
    }

    override fun getItemCount(): Int = categories.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.txtCategory.text = category.name
            binding.root.setOnClickListener { onClick(category) }
        }
    }
}