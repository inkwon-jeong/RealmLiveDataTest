package com.example.realmlivedatatest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.realmlivedatatest.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
            .apply {
                buttonMemo.setOnClickListener {
                    findNavController().navigate(
                        MainFragmentDirections
                            .actionMainFragmentToMemoFragment()
                    )
                }

                buttonCounter.setOnClickListener {
                    findNavController().navigate(
                        MainFragmentDirections
                            .actionMainFragmentToCounterFragment()
                    )
                }
            }

        return binding.root
    }
}