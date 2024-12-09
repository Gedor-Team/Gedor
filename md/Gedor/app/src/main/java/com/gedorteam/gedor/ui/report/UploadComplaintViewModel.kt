package com.gedorteam.gedor.ui.report

import androidx.lifecycle.ViewModel
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.repositories.ComplaintRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody

class UploadComplaintViewModel(
    private val complaintRepository: ComplaintRepository,
    private val loginStatePreference: LoginStatePreference
) : ViewModel() {
    fun uploadComplaint(complaint: RequestBody) =
        complaintRepository.uploadComplaint(complaint)

    fun getUserID(): String =
        runBlocking { loginStatePreference.getUserID().first() ?: "" }

    fun isSpam(complaint: RequestBody) = complaintRepository.predictSpam(complaint)

    fun predictCategory(complaint: RequestBody) = complaintRepository.predictCategory(complaint)
}