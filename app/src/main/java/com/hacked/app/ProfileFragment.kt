package com.hacked.app

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hacked.app.retrofit.ApiClient
import com.hacked.app.retrofit.Constants
import com.hacked.app.retrofit.SessionManager
import com.hacked.app.retrofit.data.User
import com.hacked.app.retrofit.data.UserNameRequest
import com.hacked.app.retrofit.data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var user: User
    private lateinit var textNickname: TextView
    private lateinit var textLevel: TextView
    private lateinit var textExp: TextView
    private lateinit var textMoney: TextView
    private lateinit var buttonEdit: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiClient = ApiClient()
        sessionManager = SessionManager(requireActivity())
        textNickname = view.findViewById(R.id.text_nickname)
        textLevel = view.findViewById(R.id.text_level)
        textExp = view.findViewById(R.id.text_exp)
        textMoney = view.findViewById(R.id.text_money)
        buttonEdit = view.findViewById(R.id.button_edit)
        buttonEdit.setOnClickListener {
            showEditNickNameDialog()
        }
        getPlayerData()
    }

    private fun getPlayerData() {
        apiClient.getApiService(requireContext()).getUser()
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    val data = response.body()
                    Log.d(Constants.LOGGING_RETROFIT, "USER: ${data?.user}")
                    if (data != null)
                        user = data.user
                    textNickname.text = data?.user?.userName ?: ""
                    textLevel.text = data?.user?.level.toString()
                    textExp.text = data?.user?.exp.toString()
                    textMoney.text = data?.user?.money.toString()
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d(Constants.LOGGING_RETROFIT, "ERROR: $t")
                }
            })
    }

    private fun updatePlayerNickname(nickname: String) {
        apiClient.getApiService(requireContext()).updateUserName(UserNameRequest(userName=nickname))
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    val data = response.body()
                    Log.d(Constants.LOGGING_RETROFIT, "USER: ${data?.user}")
                    if (data != null)
                        user = data.user
                    textNickname.text = data?.user?.userName ?: ""
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d(Constants.LOGGING_RETROFIT, "ERROR: $t")
                }
            })
    }

    private fun showEditNickNameDialog() {
        val editText = EditText(requireContext())
        editText.setText(user.userName)
        AlertDialog.Builder(requireContext())
            .setMessage("Новый никнейм")
            .setPositiveButton("Сохранить") { _, _ -> updatePlayerNickname(editText.text.toString())}
            .setView(editText)
            .create()
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}