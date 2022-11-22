package com.hacked.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.findNavController

class ConfirmFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var buttonConfirm: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        buttonConfirm = view.findViewById(R.id.button_confirm)
        buttonConfirm.setOnClickListener {
            navController.navigate(R.id.action_confirmFragment_to_mapsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm, container, false)
    }
}