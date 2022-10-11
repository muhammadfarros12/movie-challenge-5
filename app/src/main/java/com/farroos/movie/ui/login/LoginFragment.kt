package com.farroos.movie.ui.login

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
import com.farroos.movie.data.resource.Status
import com.farroos.movie.databinding.FragmentLoginBinding
import com.farroos.movie.ui.ViewModelFactory


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding as FragmentLoginBinding

    private lateinit var viewModel: LoginViewModel

    private var sharedPref: SharedPreference? = null
    private var status = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(view.context)
        viewModel = ViewModelProvider(requireActivity(), factory)[LoginViewModel::class.java]

        sharedPref = SharedPreference(view.context)

        status = sharedPref?.getPrefKeyStatus("login_status") == true
        if (status) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.apply {
            txtRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {

                viewModel.login(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
                )

                viewModel.loginStatus.observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            if (it.data != null) {
                                sharedPref?.saveKey(it.data)
                                sharedPref?.saveKeyState(true)
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            } else {
                                Toast.makeText(context, "User tidak ditemukan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }

                /*val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()

                if (edtUsername.text.isNullOrBlank() || edtPassword.text.isNullOrBlank()) {
                    Toast.makeText(
                        context,
                        "Lengkapi Field Diatas terlebih dahulu",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    val usernamePref = sharedPref?.getPrefKey("username")
                    val passwordPref = sharedPref?.getPrefKey("password")
                    if (usernamePref.equals(username) && passwordPref.equals(password)) {
                        sharedPref?.saveKeyState(true)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }*/

            }

            binding.txtRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }


    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()

    }


}