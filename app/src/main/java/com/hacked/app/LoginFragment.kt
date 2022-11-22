package com.hacked.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.findNavController

class LoginFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegistration: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        buttonLogin = view.findViewById(R.id.button_login)
        buttonRegistration = view.findViewById(R.id.button_registration)
        buttonLogin.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_mapsFragment)
        }
        buttonRegistration.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}