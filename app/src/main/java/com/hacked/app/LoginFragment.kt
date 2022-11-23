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
import com.hacked.app.retrofit.Constants.LOGGING_RETROFIT
import com.hacked.app.retrofit.data.LoginRequest
import com.hacked.app.retrofit.data.LoginResponse
import com.hacked.app.retrofit.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var navController: NavController
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegistration: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiClient = ApiClient()
        sessionManager = SessionManager(requireActivity())
        navController = view.findNavController()
        checkLogin()
        buttonLogin = view.findViewById(R.id.button_login)
        buttonRegistration = view.findViewById(R.id.button_registration)
        buttonLogin.setOnClickListener {
            val textEmail: EditText = view.findViewById(R.id.edit_text_email)
            val textPassword: EditText = view.findViewById(R.id.edit_text_password)
            login(textEmail.text.toString(), textPassword.text.toString())
        }
        buttonRegistration.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email=email, password=password)
        apiClient.getApiService().login(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val loginResponse = response.body()
                    Log.d(LOGGING_RETROFIT, "TOKEN: ${loginResponse?.token}")
                    if (loginResponse != null) {
                        sessionManager.saveAuthToken(loginResponse.token)
                        checkLogin()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d(LOGGING_RETROFIT, "ERROR: $t")
                }
            })
    }

    private fun checkLogin() {
        val token = sessionManager.fetchAuthToken()
        if (token != null) {
            navController.navigate(R.id.action_loginFragment_to_mapsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
}