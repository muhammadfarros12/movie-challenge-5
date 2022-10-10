package com.farroos.movie.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.farroos.movie.R
import com.farroos.movie.databinding.FragmentDetailBinding
import com.farroos.movie.utils.urlImage


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    val binding get() = _binding as FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetail(args.movieId)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            it.findNavController().popBackStack()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingContainer.visibility = View.VISIBLE
            } else {
                binding.loadingContainer.visibility = View.GONE
            }
        }

        viewModel.detail.observe(viewLifecycleOwner) {

            Glide.with(binding.imgBackdrop)
                .load(urlImage + it?.backdropPath)
                .error(R.drawable.ic_broken)
                .into(binding.imgBackdrop)

            Glide.with(binding.imgDetail)
                .load(urlImage + it?.posterPath)
                .error(R.drawable.ic_broken)
                .into(binding.imgDetail)

            binding.txtJudul.text = it?.originalTitle
            binding.txtOverview.text = it?.overview

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}