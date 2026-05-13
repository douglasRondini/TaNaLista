package com.douglasrondini.tanalista.ui.home

import android.R.attr.category
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.douglasrondini.tanalista.R
import com.douglasrondini.tanalista.databinding.FragmentHomeBinding
import com.douglasrondini.tanalista.domain.model.Item
import com.douglasrondini.tanalista.ui.home.adapter.CategoryAdapter
import com.douglasrondini.tanalista.ui.home.adapter.ItemAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.Locale


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var itemAdapter: ItemAdapter
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCategories()
        viewModel.loadAllItems()
        setUp()
        observSetUp()

    }

    private fun setUp() {
        categoryAdapter = CategoryAdapter(emptyList()) { category ->
            //add logica para atualizar a lista de itns por categoria
            binding.txtTitleList2.text = category.name
            viewModel.lodItemsByCategory(category.name)
            Toast.makeText(requireContext(), "Categoria: ${category.name}", Toast.LENGTH_SHORT)
                .show()
        }
        binding.rvCategorias.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        itemAdapter = ItemAdapter(
            emptyList(),
            onPlusClick = { item ->
                val updated = item.copy(quantity = item.quantity + 1)
                viewModel.updateItem(updated)
            },
            onLessClick = { item ->
                if (item.quantity > 0) {
                    val updated = item.copy(quantity = item.quantity - 1)
                    viewModel.updateItem(updated)
                }
            },
            onDeleteClick = { item ->
                viewModel.deleteItem(item)
            },
            onCheckedChange = { item, isChecked ->
                val updated = item.copy(checked = isChecked)
                viewModel.updateItem(updated)
            },
            onPriceClick = {item ->
                showPriceDialog(item)
            }
        )

        binding.rvListItens.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemAdapter
        }
    }

    private fun observSetUp() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categories ->
                    categoryAdapter.updateData(categories)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { items ->
                    itemAdapter.updateData(items)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.totalPrice.collect { total ->
                    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                    binding.txtPriceComp.text = currencyFormat.format(total)
                }
            }
        }
    }

    private fun showPriceDialog(item: Item) {
        val editText = EditText(requireContext()).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            setText(item.price.toString())

            // Quando o usuário clicar no campo, limpa o texto
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    this.setText("") // zera o campo
                }
            }
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Atualizar preço")
            .setView(editText)
            .setPositiveButton("Salvar") { _, _ ->
                val newPrice = editText.text.toString().replace(",", ".").toDoubleOrNull()
                if (newPrice != null) {
                    viewModel.updateItem(item.copy(price = newPrice))
                } else {
                    Toast.makeText(requireContext(), "Valor inválido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }



}