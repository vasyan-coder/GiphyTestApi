package com.vasyancoder.giphytestapi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vasyancoder.giphytestapi.databinding.FragmentGiphyListBinding
import java.lang.RuntimeException

class GiphyListFragment : Fragment() {
    private var _binding: FragmentGiphyListBinding? = null
    private val binding: FragmentGiphyListBinding
    get() = _binding ?: throw RuntimeException("GiphyListFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGiphyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}