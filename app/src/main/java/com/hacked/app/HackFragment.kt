package com.hacked.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.maps.SupportMapFragment

class HackFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hack, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonHack: ImageButton = view.findViewById(R.id.button_hack)
        buttonHack.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }
}