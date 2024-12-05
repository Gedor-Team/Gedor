package com.gedorteam.gedor.ui.settings

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.gedorteam.gedor.R
import com.gedorteam.gedor.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener {
            redirectToLoginFragment()
        }

        binding.cardAccount.setOnClickListener {
            redirectToAccountFragment()
        }
    }

    private fun redirectToLoginFragment() {
        Navigation.findNavController(view?: View(context)).navigate(R.id.action_navigation_settings_to_login_fragment)
    }

    private fun redirectToAccountFragment() {
        Navigation.findNavController(view?: View(context)).navigate(R.id.action_navigation_settings_to_account_fragment)
    }
}