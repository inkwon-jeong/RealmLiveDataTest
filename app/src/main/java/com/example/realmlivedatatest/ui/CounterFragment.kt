package com.example.realmlivedatatest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.realmlivedatatest.databinding.FragmentCounterBinding

class CounterFragment : Fragment() {

    private val viewModel by viewModels<CounterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCounterBinding.inflate(inflater, container, false)
            .apply {
                viewModel.count.observe(viewLifecycleOwner, {
                    textViewCount.text = "$it"
                })

                buttonIncrement.setOnClickListener {
                    viewModel.incrementCount()
                }

                buttonAutoIncrement.setOnClickListener {
                    viewModel.autoIncrementCount()
                }
            }

        return binding.root
    }

}