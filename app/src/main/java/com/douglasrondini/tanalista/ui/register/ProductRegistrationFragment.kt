package com.douglasrondini.tanalista.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.douglasrondini.tanalista.R
import com.douglasrondini.tanalista.databinding.FragmentProductRegistrationBinding
import com.douglasrondini.tanalista.domain.model.Item
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
            val name = binding.edtName.text.toString()
            val category = binding.spnCategory.selectedItem.toString()
            val quantity = binding.txtQuantity.text.toString().toInt()
            val price = binding.edtPrice.text.toString().toDouble()

            val item = Item(
                name = name,
                category = category,
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