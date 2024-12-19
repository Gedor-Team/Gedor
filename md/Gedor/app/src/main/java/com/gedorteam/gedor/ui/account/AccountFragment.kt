package com.gedorteam.gedor.ui.account

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gedorteam.gedor.data.repositories.Result
import com.gedorteam.gedor.databinding.FragmentAccountBinding
import com.gedorteam.gedor.di.factories.AccountViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var factory: AccountViewModelFactory
    private lateinit var viewModel: AccountViewModel

    private var snackBar: Snackbar? = null

    private lateinit var userID: String
    private var username: String? = null
    private var email: String? = null
    private var phoneNumber: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = AccountViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[AccountViewModel::class.java]

        viewModel.loadUserPreferences()

        lifecycleScope.launch {
            viewModel.userPreferences.collect { preferences ->
                preferences?.let {
                    binding.apply {
                        if (preferences.userID != null) userID = preferences.userID
                        username = preferences.username
                        email = preferences.email
                        phoneNumber = preferences.phoneNumber

                        edEditUsername.setText(username)
                        edEditEmail.setText(email)
                        edEditPhoneNumber.setText(phoneNumber)
                    }
                }
            }
        }

        binding.btnEditProfile.setOnClickListener {
            updateUserInfo()
        }
    }

    private fun updateUserInfo() {
        val newUsername = binding.edEditUsername.text.toString()
        val newEmail = binding.edEditEmail.text.toString()
        val newPhoneNumber = binding.edEditPhoneNumber.text.toString()

        val jsonObject = JsonObject()

        jsonObject.apply {
            if (newUsername.isNotEmpty() && newUsername != username) addProperty("username", newUsername)
            if (newEmail.isNotEmpty() && newEmail != email) addProperty("email", newEmail)
            if (newPhoneNumber.isNotEmpty() && newPhoneNumber != phoneNumber) addProperty("phoneNumber", newPhoneNumber)
        }

        val requestBody = jsonObject.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        viewModel.updateUserInfo(userID, requestBody).observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showSnackBar(response.error)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        showSnackBar("Profile updated successfully")
                        viewModel.saveUserInfo(userID, newUsername, newEmail, newPhoneNumber)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressCircular.visibility = View.VISIBLE
                scrollView.isEnabled = false
                scrollView.alpha = 0.5f
            } else {
                progressCircular.visibility = View.GONE
                scrollView.isEnabled = true
                scrollView.alpha = 1f
            }
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