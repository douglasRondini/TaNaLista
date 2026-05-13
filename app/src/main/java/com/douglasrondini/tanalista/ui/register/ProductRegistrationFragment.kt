package com.douglasrondini.tanalista.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.douglasrondini.tanalista.databinding.FragmentProductRegistrationBinding
import com.douglasrondini.tanalista.domain.model.Item
import com.douglasrondini.tanalista.util.FieldValidator
import com.douglasrondini.tanalista.util.ValidationResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductRegistrationFragment : Fragment() {
    private lateinit var binding: FragmentProductRegistrationBinding
    private val viewModel: ProductRegistrationViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        observeInsertItem()
    }

    fun setUp() {
        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text?.toString()?.trim()
            val category = binding.spnCategory.selectedItem?.toString()
            val quantityText = binding.txtQuantity.text?.toString()
            val priceText = binding.edtPrice.text?.toString()

            // Validações usando a classe util
            when (val result = FieldValidator.validateName(name)) {
                is ValidationResult.Error -> {
                    binding.edtName.error = result.message
                    return@setOnClickListener
                }
                else -> {}
            }

            when (val result = FieldValidator.validateCategory(category)) {
                is ValidationResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else -> {}
            }

            val quantity = when (val result = FieldValidator.validateQuantity(quantityText)) {
                is ValidationResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else -> quantityText?.toIntOrNull() ?: 1
            }

            val price = when (val result = FieldValidator.validatePrice(priceText)) {
                is ValidationResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else -> priceText?.replace(",", ".")?.toDoubleOrNull() ?: 0.0
            }

            val item = Item(
                name = name.orEmpty(),
                category = category.orEmpty(),
                quantity = quantity,
                price = price,
                checked = false
            )

            viewModel.insertItem(item)
        }
    }


    private fun observeInsertItem() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.insertState.collect { success ->
                    success?.let {
                        if (it) {
                            Snackbar.make(
                                binding.root,
                                "Produto cadastrado com sucesso!",
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Erro ao cadastrar produto!",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }


}