package com.gedorteam.gedor.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gedorteam.gedor.R
import com.gedorteam.gedor.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.gedorteam.gedor.data.repositories.Result
import com.gedorteam.gedor.di.factories.LoginViewModelFactory
import com.toxicbakery.bcrypt.Bcrypt

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var factory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private var snackBar: Snackbar? = null

    private var usernameTextWatcher: TextWatcher? = null
    private var passwordTextWatcher: TextWatcher? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = LoginViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        viewModel.getUserIDSync().observe(viewLifecycleOwner) { userID ->
            if (!userID.isNullOrEmpty()) {
                toHomeFragment()
            }
        }

        binding.apply {
            btnLogin.setOnClickListener {
                if (edLoginUsername.text.toString().isNotEmpty() &&
                    inputLayoutLoginUsername.error == null &&
                    edLoginPassword.text.toString().isNotEmpty() &&
                    inputLayoutLoginPassword.error == null)
                {
                    login()
                } else {
                    if (edLoginUsername.text.toString().isEmpty()) {
                        inputLayoutLoginUsername.error = "This field cannot be empty"
                    } else {
                        inputLayoutLoginUsername.error = null
                    }

                    if (edLoginPassword.text.toString().isEmpty()) {
                        inputLayoutLoginPassword.error = "This field cannot be empty"
                    } else {
                        inputLayoutLoginPassword.error = null
                    }
                }
            }

            btnSignUp.setOnClickListener {
                toRegisterFragment()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        validateUsername()
        validatePassword()
    }

    override fun onPause() {
        super.onPause()

        binding.edLoginUsername.removeTextChangedListener(usernameTextWatcher)
        binding.edLoginPassword.removeTextChangedListener(passwordTextWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        snackBar?.dismiss()
    }

    private fun toHomeFragment() {
        Navigation.findNavController(view?: View(context)).navigate(R.id.action_login_fragment_to_navigation_home)
    }

    private fun toRegisterFragment() {
        Navigation.findNavController(view?: View(context)).navigate(R.id.action_login_fragment_to_register_fragment)
    }

    private fun login() {
        binding.apply {
            val username: String = edLoginUsername.text.toString()
            val password: String = edLoginPassword.text.toString()

            viewModel.login(username).observe(viewLifecycleOwner) {
                response ->
                if (response != null) {
                    when(response) {
                        Result.Loading -> {
                            progressCircular.visibility = View.VISIBLE
                            scrollView.alpha = 0.5F
                        }
                        is Result.Error -> {
                            progressCircular.visibility = View.GONE
                            scrollView.alpha = 1F
                            if (response.error == "User not found") {
                                inputLayoutLoginUsername.error = response.error
                            }
                        }
                        is Result.Success -> {
                            if (isPasswordMatch(password, response.data?.password?.toByteArray())) {
                                viewModel.saveLoginState(
                                    response.data?.userID.toString(),
                                    response.data?.username.toString(),
                                    response.data?.email.toString(),
                                    response.data?.phoneNumber.toString()
                                )
                                progressCircular.visibility = View.GONE
                                scrollView.alpha = 1F
                            } else {
                                progressCircular.visibility = View.GONE
                                scrollView.alpha = 1F
                                inputLayoutLoginPassword.error = "Incorrect password"
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isPasswordMatch(password: String, hashedPassword: ByteArray?): Boolean {
        if (hashedPassword == null) return false
        return Bcrypt.verify(password, hashedPassword)
    }

    private fun validateUsername() {
        binding.apply {
            usernameTextWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d("asd", "text changed somehow")
                    inputLayoutLoginUsername.isErrorEnabled = true
                    if (s.toString().isNotEmpty()) {
                        inputLayoutLoginUsername.error = null
                        inputLayoutLoginUsername.isErrorEnabled = false
                    } else {
                        inputLayoutLoginUsername.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            }
            edLoginUsername.addTextChangedListener(usernameTextWatcher)
        }
    }

    private fun validatePassword() {
        binding.apply {
            passwordTextWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().isNotEmpty()) {
                        inputLayoutLoginPassword.error = null
                        inputLayoutLoginPassword.isErrorEnabled = false
                    } else {
                        inputLayoutLoginPassword.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            }
            edLoginPassword.addTextChangedListener(passwordTextWatcher)
        }
    }

    private fun showSnackBar(message: String) {
        snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackBar?.setAction("Dismiss") {
            snackBar?.dismiss()
        }

        snackBar?.show()
    }
}