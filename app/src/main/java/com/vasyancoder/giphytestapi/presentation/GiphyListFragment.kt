package com.vasyancoder.giphytestapi.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasyancoder.giphytestapi.databinding.FragmentGiphyListBinding
import java.lang.RuntimeException

class GiphyListFragment : Fragment() {
    private var _binding: FragmentGiphyListBinding? = null
    private val binding: FragmentGiphyListBinding
        get() = _binding ?: throw RuntimeException("GiphyListFragment == null")

    private lateinit var viewModel: GiphyListViewModel

    private lateinit var gifListAdapter: GifListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGiphyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GiphyListViewModel::class.java]
        viewModel.getGifItemList()
        viewModel.gifList.observe(viewLifecycleOwner) {
            gifListAdapter.submitList(it)
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(binding.gifList) {
            gifListAdapter = GifListAdapter()
            adapter = gifListAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val TAG = "GiphyListFragment"
    }
}