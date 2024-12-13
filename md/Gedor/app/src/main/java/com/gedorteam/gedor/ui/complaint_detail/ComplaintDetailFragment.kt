package com.gedorteam.gedor.ui.complaint_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.gedorteam.gedor.R
import com.gedorteam.gedor.databinding.FragmentComplaintDetailBinding
import com.gedorteam.gedor.util.formatDateString

class ComplaintDetailFragment : Fragment() {

    private val args: ComplaintDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentComplaintDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComplaintDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val complaint = args.complaintItem
        binding.apply {
            tvComplaint.text = complaint.complaint
            tvCategoryValue.text = complaint.category!!.replaceFirstChar { it.uppercase() }
            tvDate.text = formatDateString(complaint.createdAt)
            tvStatus.text = complaint.status
            tvAddress.text = "${complaint.lokasi}, ${complaint.kecamatan}, ${complaint.kabupaten}"
        }
    }
}