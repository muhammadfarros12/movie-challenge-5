package com.farroos.movie.ui.profile.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farroos.movie.R
import com.farroos.movie.data.SharedPreference
import com.farroos.movie.data.local.User
import com.farroos.movie.databinding.FragmentUpdateProfileBinding
import com.farroos.movie.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class UpdateProfileFragment : Fragment() {

    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding as FragmentUpdateProfileBinding

    private lateinit var viewModel: UpdateProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shared = SharedPreference(view.context)
        val factory = ViewModelFactory(view.context)
        viewModel =
            ViewModelProvider(requireActivity(), factory)[UpdateProfileViewModel::class.java]

        val data = arguments?.getParcelable<User>("user")

        if (data != null) {
            binding.apply {
                edtEmail.setText(data.email)
                edtUsername.setText(data.username)
                edtFullname.setText(data.fullname)
                edtAddress.setText(data.address)
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
        }

        binding.btnUpdate.setOnClickListener {
            val email = binding.edtEmail.toString()
            val username = binding.edtUsername.toString()
            val fullname = binding.edtFullname.toString()
            val address = binding.edtAddress.toString()
            val password = data?.password
            val id = data?.id


            val user = User(
                id = id!!,
                email = email,
                fullname = fullname,
                username = username,
                address = address,
                password = password!!
            )

            viewModel.update(id.toInt(), username, fullname, email, password, address)
            shared.saveKey(user)

        }

        viewModel.saved.observe(viewLifecycleOwner) {
            val check = it.getContentIffNotHandled() ?: return@observe
            if (check) {
                Snackbar.make(binding.root, "User Berhasil Diupdate", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
            } else {
                Snackbar.make(binding.root, "User Gagal Diupdate", Snackbar.LENGTH_LONG).show()

            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}