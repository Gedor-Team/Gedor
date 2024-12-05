package com.gedorteam.gedor.ui.report

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gedorteam.gedor.R
import com.gedorteam.gedor.databinding.FragmentUploadComplaintBinding

class UploadComplaintFragment : Fragment() {

    companion object {
        fun newInstance() = UploadComplaintFragment()
    }

    private lateinit var binding: FragmentUploadComplaintBinding
    private val viewModel: UploadComplaintViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadComplaintBinding.inflate(inflater, container, false)
        return binding.root
    }
}