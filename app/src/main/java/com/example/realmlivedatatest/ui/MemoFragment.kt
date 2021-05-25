package com.example.realmlivedatatest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.realmlivedatatest.databinding.FragmentMemoBinding
import com.example.realmlivedatatest.model.Memo

class MemoFragment : Fragment() {

    private val viewModel by viewModels<MemoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMemoBinding.inflate(inflater, container, false)
            .apply {
                val adapter = ListAdapterImpl()
                recyclerViewMemo.adapter = adapter

                viewModel.memos.observe(viewLifecycleOwner, {
                    adapter.submitList(it) {
                        adapter.notifyItemInserted(0)
                        recyclerViewMemo.smoothScrollToPosition(0)
                    }
                })

                buttonInput.setOnClickListener {
                    val contents = editTextMemo.text.toString()
                    viewModel.addMemo(contents)
                }
            }

        return binding.root
    }

    inner class ListAdapterImpl : ListAdapter<Memo, ViewHolder>(DiffUtilImpl()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent.context))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }

    class ViewHolder(
        private val view: TextView
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Memo) {
            view.text = item.contents
        }
    }

    class DiffUtilImpl : DiffUtil.ItemCallback<Memo>() {
        override fun areItemsTheSame(oldItem: Memo, newItem: Memo) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Memo, newItem: Memo) =
            oldItem.contents == newItem.contents
    }
}