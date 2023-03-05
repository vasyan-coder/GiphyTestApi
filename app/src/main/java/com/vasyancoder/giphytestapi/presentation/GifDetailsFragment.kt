package com.vasyancoder.giphytestapi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vasyancoder.giphytestapi.databinding.FragmentGifDetailsBinding
import com.vasyancoder.giphytestapi.databinding.FragmentGiphyListBinding
import com.vasyancoder.giphytestapi.domain.entities.GifDetailEntity
import java.lang.RuntimeException

class GifDetailsFragment : Fragment() {
    private var _binding: FragmentGifDetailsBinding? = null
    private val binding: FragmentGifDetailsBinding
        get() = _binding ?: throw RuntimeException("GifDetailsFragment == null")

    private lateinit var viewModel: GifDetailViewModel

    private var position = 0
    private var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGifDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadGif()
        viewModel = ViewModelProvider(this)[GifDetailViewModel::class.java]
        viewModel.getGifDetail(position)
        viewModel.gifDetail.observe(viewLifecycleOwner) {
            val id = it.id
            val title = it.title
            val username = it.username
            val importDatetime = it.import_datetime
            binding.textGifDetails.text =
                "id=$id\ntitle=$title\nusername=$username\nimportDateTime=$importDatetime"
        }

    }

    private fun loadGif() {
        Glide.with(this)
            .load(url)
            .into(binding.gifImageView)
    }

    private fun parseArgs() {
        val bundle = arguments
        if (bundle != null) {
            position = bundle.getInt(KEY_POSITION)
            url = bundle.getString(KEY_URL, "")
        }
    }

    companion object {

        const val KEY_POSITION = "position"
        const val KEY_URL = "url"

        fun newInstance(position: Int, url: String): GifDetailsFragment {
            val fragment = GifDetailsFragment()
            val bundle: Bundle = Bundle()
            bundle.putInt(KEY_POSITION, position)
            bundle.putString(KEY_URL, url)
            fragment.arguments = bundle
            return fragment
        }
    }
}