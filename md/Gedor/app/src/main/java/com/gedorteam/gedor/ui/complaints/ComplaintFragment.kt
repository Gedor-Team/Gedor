package com.gedorteam.gedor.ui.complaints

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.gedorteam.gedor.R
import com.gedorteam.gedor.databinding.FragmentComplaintBinding

class ComplaintFragment : Fragment() {

    companion object {
        fun newInstance() = ComplaintFragment()
    }

    private lateinit var binding: FragmentComplaintBinding
    private val viewModel: ComplaintViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentComplaintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddFeedback.setOnClickListener {
            redirectToUploadComplaintFragment()
        }
    }

    private fun redirectToUploadComplaintFragment(){
        Navigation.findNavController(view?: View(context)).navigate(R.id.action_navigation_complaints_to_upload_complaint_fragment)
    }
}