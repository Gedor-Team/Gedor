package com.gedorteam.gedor.ui.report

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gedorteam.gedor.R

class UserComplaintFragment : Fragment() {

    companion object {
        fun newInstance() = UserComplaintFragment()
    }

    private val viewModel: UserComplaintViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_user_complaint, container, false)
    }
}