package com.farroos.movie.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farroos.movie.R
import com.farroos.movie.data.SharedPreference
import com.farroos.movie.databinding.FragmentRegisterBinding
import com.farroos.movie.ui.ViewModelFactory


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding as FragmentRegisterBinding

    private lateinit var viewModel: RegisterViewModel

    private var sharedPref: SharedPreference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(view.context)
        val factory = ViewModelFactory(view.context)
        viewModel = ViewModelProvider(requireActivity(), factory)[RegisterViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            val email = binding.edtEmail.text.toString()
            val fullname = binding.edtFullname.text.toString()
            val address = binding.edtAddress.text.toString()
            viewModel.saved(username, fullname, email, password, address)

            /*if (binding.edtUsername.text.isNullOrBlank() || binding.edtEmail.text.isNullOrBlank() || binding.edtPassword.text.isNullOrBlank()) {
                Toast.makeText(context, "Lengkapi Field diatas", Toast.LENGTH_SHORT).show()
            } else {
                sharedPref?.saveKey(username, email, password)
                Toast.makeText(context, "Akun Telah Berhasil dibuat", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }*/
        }

        viewModel.saved.observe(viewLifecycleOwner) {
            val check = it.getContentIffNotHandled() ?: return@observe

            if (check) {
                Toast.makeText(context, "User Berhasil dibuat", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(context, "User Gagal dibuat", Toast.LENGTH_LONG).show()
            }

        }


    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}