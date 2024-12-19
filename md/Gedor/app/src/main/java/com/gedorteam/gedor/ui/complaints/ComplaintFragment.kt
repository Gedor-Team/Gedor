package com.gedorteam.gedor.ui.complaints

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gedorteam.gedor.R
import com.gedorteam.gedor.data.repositories.Result
import com.gedorteam.gedor.data.response.ComplaintResponseItem
import com.gedorteam.gedor.databinding.FragmentComplaintBinding
import com.gedorteam.gedor.di.factories.ComplaintDataViewModelFactory

class ComplaintFragment : Fragment() {

    private lateinit var binding: FragmentComplaintBinding
    private lateinit var factory: ComplaintDataViewModelFactory
    private lateinit var viewModel: ComplaintViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentComplaintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ComplaintDataViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[ComplaintViewModel::class.java]

        binding.emptyOrErrorMessage.visibility = View.GONE
        binding.rvUserComplaint.layoutManager = LinearLayoutManager(requireContext())
        getComplaint()

        binding.btnAddFeedback.setOnClickListener {
            redirectToUploadComplaintFragment()
        }

    }

    private fun redirectToUploadComplaintFragment(){
        Navigation.findNavController(view?: View(context)).navigate(R.id.action_navigation_complaints_to_upload_complaint_fragment)
    }

    private fun getComplaint() {
        val userID = viewModel.getUserID().toInt()
        viewModel.getComplaints(userID).observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        binding.emptyOrErrorMessage.visibility = View.VISIBLE
                        binding.emptyOrErrorMessage.text = "No data found"
                    }

                    is Result.Success -> {
                        setComplaintData(response.data)
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setComplaintData(data: List<ComplaintResponseItem?>?) {
        val adapter = ComplaintAdapter()
        adapter.submitList(data)
        binding.rvUserComplaint.adapter = adapter

        adapter.setOnItemClickCallback(object : ComplaintAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ComplaintResponseItem) {
//                val bundle = Bundle()
//                bundle.putParcelable("complaint", data)
                showSelectedComplaintItem(data)
            }
        })
    }

    private fun showSelectedComplaintItem(data: ComplaintResponseItem) {
        val toComplaintDetailFragment = ComplaintFragmentDirections.actionNavigationComplaintsToComplaintDetailFragment(data)
        findNavController().navigate(toComplaintDetailFragment)
    }
}