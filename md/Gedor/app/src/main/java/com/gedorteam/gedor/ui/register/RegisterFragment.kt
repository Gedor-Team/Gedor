package com.gedorteam.gedor.ui.register

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.gedorteam.gedor.R
import com.gedorteam.gedor.data.repositories.Result
import com.gedorteam.gedor.databinding.FragmentRegisterBinding
import com.gedorteam.gedor.di.factories.UserAccountViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val factory: UserAccountViewModelFactory = UserAccountViewModelFactory.getInstance()
    private val registerViewModel: RegisterViewModel by viewModels {
        factory
    }

    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateUsername()
        validateEmail()
        validatePhoneNumber()
        validatePassword()
        validateConfirmPassword()

        binding.apply {
            btnLogin.setOnClickListener {
                redirectToLoginActivity()
            }

            btnSignUp.setOnClickListener {
                if (
                    edRegisterUsername.text.toString().isNotEmpty() &&
                    inputLayoutRegisterUsername.error == null &&
                    edRegisterEmail.text.toString().isNotEmpty() &&
                    inputLayoutRegisterEmail.error == null &&
                    edRegisterPhoneNumber.text.toString().isNotEmpty() &&
                    inputLayoutRegisterPhoneNumber.error == null &&
                    edRegisterPassword.text.toString().isNotEmpty() &&
                    inputLayoutRegisterPassword.error == null &&
                    edRegisterConfirmPassword.text.toString().isNotEmpty() &&
                    inputLayoutRegisterConfirmPassword.error == null
                ) {
                    register()
                } else {
                    showSnackBar("Please follow the instructions to fill in all the fields")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        snackBar?.dismiss()
    }

    private fun redirectToLoginActivity() {
        Navigation.findNavController(view?: View(context)).navigate(R.id.action_register_fragment_to_login_fragment)
    }

    private fun register() {
        binding.apply {
            val username: String = edRegisterUsername.text.toString()
            val email: String = edRegisterEmail.text.toString()
            val phoneNumber: String = edRegisterPhoneNumber.text.toString()
            val password: String = edRegisterPassword.text.toString()

            val jsonObject = JsonObject()
            jsonObject.addProperty("username", username)
            jsonObject.addProperty("email", email)
            jsonObject.addProperty("phoneNumber", phoneNumber)
            jsonObject.addProperty("password", password)

            val requestBody =
                jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

            registerViewModel.register(requestBody).observe(viewLifecycleOwner) {
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
                            showSnackBar(response.error)
                        }
                        is Result.Success -> {
                            progressCircular.visibility = View.GONE
                            scrollView.alpha = 1F
                            showSuccessDialog()
                        }
                    }
                }
            }
        }
    }

    private fun validateUsername() {
        binding.apply {
            edRegisterUsername.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputLayoutRegisterUsername.isErrorEnabled = true
                    if (s.toString().isNotEmpty()) {
                        if (s.toString().length < 6) {
                            inputLayoutRegisterUsername.error = "Username must be at least 6 characters"
                        } else {
                            inputLayoutRegisterUsername.error = null
                            inputLayoutRegisterUsername.isErrorEnabled = false
                        }
                    } else {
                        inputLayoutRegisterUsername.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            })
        }
    }

    private fun validateEmail() {
        binding.apply {
            edRegisterEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputLayoutRegisterEmail.isErrorEnabled = true
                    if (s.toString().isNotEmpty()) {
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                            inputLayoutRegisterEmail.error = "Invalid email"
                        } else {
                            inputLayoutRegisterEmail.error = null
                            inputLayoutRegisterEmail.isErrorEnabled = false
                        }
                    } else {
                        inputLayoutRegisterEmail.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }
            })
        }
    }

    private fun validatePhoneNumber() {
        binding.apply {
            edRegisterPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputLayoutRegisterPhoneNumber.isErrorEnabled = true
                    if (s.toString().isNotEmpty()) {
                        if (s.toString().length in 10..15) {
                            inputLayoutRegisterPhoneNumber.error = null
                            inputLayoutRegisterPhoneNumber.isErrorEnabled = false
                        } else {
                            inputLayoutRegisterPhoneNumber.error = "Invalid phone number"
                        }
                    } else {
                        inputLayoutRegisterPhoneNumber.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }
            })
        }
    }

    private fun validatePassword() {
        binding.apply {
            edRegisterPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().length < 8) {
                        inputLayoutRegisterPassword.isErrorEnabled = true
                        inputLayoutRegisterPassword.error = "Password must be at least 8 characters"
                    } else {
                        inputLayoutRegisterPassword.error = null
                        inputLayoutRegisterPassword.isErrorEnabled = false
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            })
        }
    }

    private fun validateConfirmPassword() {
        binding.apply {
            edRegisterConfirmPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputLayoutRegisterConfirmPassword.isErrorEnabled = true
                    if (s.toString().isNotEmpty()) {
                        if (s.toString() != edRegisterPassword.text.toString()) {
                            inputLayoutRegisterConfirmPassword.error = "Passwords do not match"
                        } else {
                            inputLayoutRegisterConfirmPassword.error = null
                            inputLayoutRegisterConfirmPassword.isErrorEnabled = false
                        }
                    } else {
                        inputLayoutRegisterConfirmPassword.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            })
        }
    }

    private fun showSnackBar(message: String) {
        snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackBar?.setAction("Dismiss") {
            snackBar?.dismiss()
        }

        snackBar?.show()
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Success")
            .setMessage("Your account has been successfully created.")

        builder.setPositiveButton("Continue") { dialog, _ ->
            dialog.dismiss()
            redirectToLoginActivity()
        }

        builder.create().show()
    }
}