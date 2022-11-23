package com.hacked.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.hacked.app.retrofit.ApiClient
import com.hacked.app.retrofit.Constants
import com.hacked.app.retrofit.SessionManager
import com.hacked.app.retrofit.data.ConfirmRequest
import com.hacked.app.retrofit.data.ConfirmResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmFragment : Fragment() {
    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager
    private lateinit var navController: NavController
    private lateinit var buttonConfirm: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiClient = ApiClient()
        sessionManager = SessionManager(requireActivity())
        navController = view.findNavController()
        buttonConfirm = view.findViewById(R.id.button_confirm)
        buttonConfirm.setOnClickListener {
            val textCode: EditText = view.findViewById(R.id.edit_text_confirm)
            confirm(textCode.text.toString())
        }
    }

    private fun confirm(code: String) {
        val confirmRequest = ConfirmRequest(activationCode=code)
        apiClient.getApiService().confirm(confirmRequest)
            .enqueue(object : Callback<ConfirmResponse> {
                override fun onResponse(call: Call<ConfirmResponse>, response: Response<ConfirmResponse>) {
                    val confirmResponse = response.body()
                    Log.d(Constants.LOGGING_RETROFIT, "USER: $confirmResponse")
                    if (confirmResponse != null) {
                        sessionManager.saveAuthToken(confirmResponse.token)
                        navController.navigate(R.id.action_confirmFragment_to_mapsFragment)
                    }

                }

                override fun onFailure(call: Call<ConfirmResponse>, t: Throwable) {
                    Log.d(Constants.LOGGING_RETROFIT, "ERROR: $t")
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm, container, false)
    }
}