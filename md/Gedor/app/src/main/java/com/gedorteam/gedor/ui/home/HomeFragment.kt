package com.gedorteam.gedor.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.gedorteam.gedor.R
import com.gedorteam.gedor.databinding.FragmentHomeBinding
import com.gedorteam.gedor.di.factories.HomeViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var factory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = HomeViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        val userID = runBlocking {
            viewModel.getUserIDSync()
        }

        if (userID.isNullOrEmpty()) {
            redirectToLoginFragment()
        }

        viewModel.loadUserPreferences()
        lifecycleScope.launch {
            viewModel.userPreferences.collect { preferences ->
                preferences?.let {
                    binding.apply {
                        username = preferences.username
                    }
                }
            }
        }

        binding.tvGreeting.text = "Hello, $username"
    }

    private fun redirectToLoginFragment() {
        Navigation.findNavController(view?: View(context)).navigate(R.id.action_navigation_home_to_login_fragment)
    }
}