package com.hacked.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController

class RegistrationFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var buttonRegistration: Button
    private lateinit var buttonLogin: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        buttonRegistration = view.findViewById(R.id.button_registration)
        buttonLogin = view.findViewById(R.id.button_login)
        buttonRegistration.setOnClickListener {
            navController.navigate(R.id.action_registrationFragment_to_confirmFragment)
        }
        buttonLogin.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }
}