package com.gedorteam.gedor.di.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gedorteam.gedor.data.local.preferences.LoginStatePreference
import com.gedorteam.gedor.data.repositories.ComplaintRepository
import com.gedorteam.gedor.di.Injection
import com.gedorteam.gedor.ui.report.UploadComplaintViewModel

class ComplaintDataViewModelFactory private constructor(
    private val complaintRepository: ComplaintRepository,
    private val loginStatePreference: LoginStatePreference
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when (modelClass) {
            UploadComplaintViewModel::class.java -> {
                return UploadComplaintViewModel(complaintRepository, loginStatePreference) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ComplaintDataViewModelFactory? = null
        fun getInstance(context: Context): ComplaintDataViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ComplaintDataViewModelFactory(
                    Injection.provideComplaintRepository(),
                    Injection.provideLoginStatePreference(context)
                )
            }.also {
                instance = it
            }
    }
}