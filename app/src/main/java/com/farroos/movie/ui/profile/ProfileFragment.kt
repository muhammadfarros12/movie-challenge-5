package com.farroos.movie.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farroos.movie.R
import com.farroos.movie.data.SharedPreference
import com.farroos.movie.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding as FragmentProfileBinding

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = SharedPreference(view.context)
        val factory = ProfileViewModelProvider(sharedPref)
        viewModel = ViewModelProvider(requireActivity(), factory)[ProfileViewModel::class.java]

        viewModel.apply {
            getUserData()

            usename.observe(viewLifecycleOwner) {
                binding.edtUsername.setText(it)
            }
            fullname.observe(viewLifecycleOwner) {
                binding.edtFullname.setText(it)
            }
            email.observe(viewLifecycleOwner) {
                binding.edtEmail.setText(it)
            }
            address.observe(viewLifecycleOwner) {
                binding.edtAddress.setText(it)
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.btnLogout.setOnClickListener {
            sharedPref.clearUsername()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment(
                    viewModel.sendDataToUpdate()
                )
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}