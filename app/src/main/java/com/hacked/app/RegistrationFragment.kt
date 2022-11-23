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
import com.hacked.app.retrofit.data.RegistrationRequest
import com.hacked.app.retrofit.data.RegistrationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationFragment : Fragment() {
    private lateinit var apiClient: ApiClient
    private lateinit var navController: NavController
    private lateinit var buttonRegistration: Button
    private lateinit var buttonLogin: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiClient = ApiClient()
        navController = view.findNavController()
        buttonRegistration = view.findViewById(R.id.button_registration)
        buttonLogin = view.findViewById(R.id.button_login)
        buttonRegistration.setOnClickListener {
            val textNickName: EditText = view.findViewById(R.id.edit_text_nickname)
            val textEmail: EditText = view.findViewById(R.id.edit_text_email)
            val textPassword: EditText = view.findViewById(R.id.edit_text_password)
            registration(textNickName.text.toString(), textEmail.text.toString(), textPassword.text.toString())
        }
        buttonLogin.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun registration(userName: String, email: String, password: String) {
        val registrationRequest = RegistrationRequest(userName=userName, email=email, password=password)
        apiClient.getApiService().registration(registrationRequest)
            .enqueue(object : Callback<RegistrationResponse> {
                override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                    val registrationResponse = response.body()
                    Log.d(Constants.LOGGING_RETROFIT, "USER: $registrationResponse")
                    if (registrationResponse != null) {
                        navController.navigate(R.id.action_registrationFragment_to_confirmFragment)
                    }
                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    Log.d(Constants.LOGGING_RETROFIT, "ERROR: $t")
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }
}