package com.gedorteam.gedor.ui.report

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gedorteam.gedor.R
import com.gedorteam.gedor.databinding.FragmentUploadComplaintBinding
import com.gedorteam.gedor.di.factories.ComplaintDataViewModelFactory
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import com.gedorteam.gedor.data.repositories.Result
import com.google.android.material.snackbar.Snackbar

class UploadComplaintFragment : Fragment() {

    private lateinit var binding: FragmentUploadComplaintBinding

    private lateinit var factory: ComplaintDataViewModelFactory
    private lateinit var viewModel: UploadComplaintViewModel
    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadComplaintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ComplaintDataViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[UploadComplaintViewModel::class.java]

        validateAddress()
        validateKecamatan()
        validateKabupaten()
        validateProvinsi()
        validateComplaint()

        binding.apply {
            btnUploadComplaint.setOnClickListener {
                val complaint = edComplaint.text.toString()
                predictSpam(complaint)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        snackBar?.dismiss()
    }

    private fun redirectToComplaintsFragment() {
        Navigation.findNavController(view?: View(context)).navigate(R.id.action_upload_complaint_fragment_to_navigation_complaints)
    }

    private fun predictSpam(complaint: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("input", complaint)

        val requestBody = jsonObject.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        viewModel.isSpam(requestBody).observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showSnackBar(response.error)
                    }
                    is Result.Success -> {
                        val prediction = response.data
                        if (prediction) {
                            showDialog("Failed", "Your message was flagged as spam and could not be submitted. Please review your input and try again.")
                        } else {
                            predictComplaintCategory(complaint)
                        }
                    }
                }
            }
        }
    }

    private fun predictComplaintCategory(complaint: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("input", complaint)

        val requestBody = jsonObject.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        viewModel.predictCategory(requestBody).observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    Result.Loading -> {
                        // Not needed
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showSnackBar(response.error)
                    }
                    is Result.Success -> {
                        val category = response.data
                        uploadComplaint(complaint, category)
                    }
                }
            }
        }
    }

    private fun uploadComplaint(complaint: String, category: String) {
        binding.apply {
            val userID = viewModel.getUserID().toInt()
            val govsID = 1
            val address = edAddress.text.toString()
            val kecamatan = edKecamatan.text.toString()
            val kabupaten = edKabupaten.text.toString()
            val provinsi = edProvinsi.text.toString()
            val status = "Menunggu konfirmasi"

            val jsonObject = JsonObject()
            jsonObject.apply {
                addProperty("userID", userID)
                addProperty("govID", govsID)
                addProperty("lokasi", address)
                addProperty("kecamatan", kecamatan)
                addProperty("kabupaten", kabupaten)
                addProperty("provinsi", provinsi)
                addProperty("complaint", complaint)
                addProperty("status", status)
                addProperty("category", category)
            }

            val requestBody = jsonObject.toString()
                .toRequestBody("application/json".toMediaTypeOrNull())

            viewModel.uploadComplaint(requestBody).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        Result.Loading -> {
                            // Not needed
                        }
                        is Result.Error -> {
                            showLoading(false)
                            showSnackBar(response.error)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            showDialog("Success", "Your complaint has been successfully sent.")
                        }
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

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)

        builder.setPositiveButton("Continue") { dialog, _ ->
            dialog.dismiss()
            redirectToComplaintsFragment()
        }

        builder.create().show()
    }

    private fun showSnackBar(message: String) {
        snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackBar?.setAction("Dismiss") {
            snackBar?.dismiss()
        }

        snackBar?.show()
    }

    private fun validateAddress() {
        binding.apply {
            edAddress.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputLayoutAddress.isErrorEnabled = true
                    if (s.toString().isNotEmpty()) {
                        inputLayoutAddress.error = null
                        inputLayoutAddress.isErrorEnabled = false
                    } else {
                        inputLayoutAddress.error = "Address cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            })
        }
    }

    private fun validateKecamatan() {
        binding.apply {
            edKecamatan.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputLayoutKecamatan.isErrorEnabled = true
                    if (edKecamatan.text.toString().isNotEmpty()) {
                        inputLayoutKecamatan.error = null
                        inputLayoutKecamatan.isErrorEnabled = false
                    } else {
                        inputLayoutKecamatan.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            })
        }
    }

    private fun validateKabupaten() {
        binding.apply {
            edKabupaten.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputLayoutKabupaten.isErrorEnabled = true
                    if (edKabupaten.text.toString().isNotEmpty()) {
                        inputLayoutKabupaten.error = null
                        inputLayoutKabupaten.isErrorEnabled = false
                    } else {
                        inputLayoutKabupaten.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            })
        }
    }

    private fun validateProvinsi() {
        binding.apply {
            edProvinsi.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputLayoutProvinsi.isErrorEnabled = true
                    if (edProvinsi.text.toString().isNotEmpty()) {
                        inputLayoutProvinsi.error = null
                        inputLayoutProvinsi.isErrorEnabled = false
                    } else {
                        inputLayoutProvinsi.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            })
        }
    }

    private fun validateComplaint() {
        binding.apply {
            edComplaint.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    inputLayoutComplaint.isErrorEnabled = true
                    if (edComplaint.text.toString().isNotEmpty()) {
                        inputLayoutComplaint.error = null
                        inputLayoutComplaint.isErrorEnabled = false
                    } else {
                        inputLayoutComplaint.error = "This field cannot be empty"
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

            })
        }
    }

}