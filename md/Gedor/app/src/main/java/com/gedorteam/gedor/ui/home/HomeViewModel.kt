package com.gedorteam.gedor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.repositories.ComplaintRepository

class HomeViewModel(
    private val complaintRepository: ComplaintRepository,
    private val loginStatePreference: LoginStatePreference
) : ViewModel() {
    fun getUserID(): LiveData<String?> {
        return loginStatePreference.getUserID().asLiveData()
    }
}