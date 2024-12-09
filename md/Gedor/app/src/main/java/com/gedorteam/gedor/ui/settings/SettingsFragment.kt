package com.gedorteam.gedor.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gedorteam.gedor.R
import com.gedorteam.gedor.databinding.FragmentSettingsBinding
import com.gedorteam.gedor.di.factories.SettingsViewModelFactory

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var factory: SettingsViewModelFactory
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = SettingsViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[SettingsViewModel::class.java]

        viewModel.getUserIDSync().observe(viewLifecycleOwner) { userID ->
            if (userID.isNullOrEmpty()) {
                redirectToLoginFragment()
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.clearPreferences()
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