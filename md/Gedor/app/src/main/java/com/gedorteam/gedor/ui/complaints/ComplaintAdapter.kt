package com.gedorteam.gedor.ui.complaints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gedorteam.gedor.data.response.ComplaintResponseItem
import com.gedorteam.gedor.databinding.ItemComplaintBinding
import com.gedorteam.gedor.util.formatDateString

class ComplaintAdapter : ListAdapter<ComplaintResponseItem, ComplaintAdapter.ComplaintViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ComplaintViewHolder(
        private val binding: ItemComplaintBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(complaint: ComplaintResponseItem) {
            binding.tvComplaint.text = complaint.complaint
            binding.tvDateCreated.text = formatDateString(complaint.createdAt)
            binding.tvStatus.text = complaint.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {
        val binding = ItemComplaintBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComplaintViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
        val complaint = getItem(position)
        holder.bind(complaint)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(complaint)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ComplaintResponseItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ComplaintResponseItem>() {
            override fun areItemsTheSame(
                oldItem: ComplaintResponseItem,
                newItem: ComplaintResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ComplaintResponseItem,
                newItem: ComplaintResponseItem
            ): Boolean {
                return oldItem.complaintID == newItem.complaintID
            }
        }
    }
}