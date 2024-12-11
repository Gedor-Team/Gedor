package com.gedorteam.gedor.ui.complaints

import androidx.lifecycle.ViewModel
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.repositories.ComplaintRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ComplaintViewModel(
    private val complaintRepository: ComplaintRepository,
    private val loginStatePreference: LoginStatePreference
) : ViewModel() {
    fun getUserID(): String =
        runBlocking { loginStatePreference.getUserID().first() ?: "" }

    fun getComplaints(id: Int) = complaintRepository.getComplaints(id)
}