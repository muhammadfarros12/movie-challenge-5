package com.farroos.movie.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farroos.movie.R
import com.farroos.movie.data.SharedPreference
import com.farroos.movie.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = SharedPreference(view.context)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.homeToolbar.inflateMenu(R.menu.menu)

        binding.txtUsername.text = getString(R.string.username, sharedPref.getPrefKey("username"))

        val adapter = HomeAdapter()

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        /*binding.homeToolbar.setOnClickListener {
            when (it.id) {
                R.id.profile -> {
                    findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                    true
                }
                else -> false
            }
        }*/

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.nowPlaying.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.d("Home Fragment", it.toString())
        }

        viewModel.errorStatus.observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(binding.root, "Load Data Gagal", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.profile -> {
                    findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                    true
                }
                else -> false
            }
        }

    }
/*
    private fun observeData() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.recyclerView.isVisible = !isLoading
        }

        viewModel.errorState.observe(viewLifecycleOwner) { errorData ->
            binding.txtError.isVisible = errorData.first
            errorData.second?.message.let {
                binding.txtError.text = it
            }
        }

        viewModel.result.observe(viewLifecycleOwner) {
            adapter
        }
    }*/

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}