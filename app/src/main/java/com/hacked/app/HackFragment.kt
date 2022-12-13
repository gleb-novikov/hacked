package com.hacked.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.hacked.app.hacking.GameCallback
import com.hacked.app.hacking.MiniGame
import com.hacked.app.retrofit.ApiClient
import com.hacked.app.retrofit.Constants
import com.hacked.app.retrofit.SessionManager
import com.hacked.app.retrofit.data.User
import com.hacked.app.retrofit.data.UserRequest
import com.hacked.app.retrofit.data.UserResponse
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.pow
import kotlin.math.roundToInt

class HackFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        apiClient = ApiClient()
        sessionManager = SessionManager(requireActivity())
        getPlayerData()
        val win = object : GameCallback {
            override fun onWin() {
                addWinDrop()
                Toasty.success(requireContext(), "Успешный взлом!", Toast.LENGTH_SHORT, true).show()
                requireView().findNavController().popBackStack()
            }

            override fun onLose() {
                Toasty.error(requireContext(), "Взлом провалился", Toast.LENGTH_SHORT, true).show()
                requireView().findNavController().popBackStack()
            }
        }
        return MiniGame(requireContext(), 0, win, viewLifecycleOwner)
    }

    private fun addWinDrop() {
        val money: Int = user.money + (Math.random() * 300).roundToInt()
        var exp: Int = user.exp + (Math.random() * 100).roundToInt()
        var level: Int = user.level
        val maxExp: Int = level.toDouble().pow(2.0).roundToInt() * 100
        if (exp >= maxExp) {
            level += exp / maxExp
            exp %= maxExp
        }
        updatePlayerData(user.userName, level, exp, money)
    }

    private fun getPlayerData() {
        apiClient.getApiService(requireContext()).getUser()
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    val data = response.body()
                    Log.d(Constants.LOGGING_RETROFIT, "USER: ${data?.user}")
                    if (data != null)
                        user = data.user
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d(Constants.LOGGING_RETROFIT, "ERROR: $t")
                }
            })
    }

    private fun updatePlayerData(userName: String, level: Int, exp: Int, money: Int) {
        apiClient.getApiService(requireContext()).updateUser(UserRequest(userName=userName, level=level, exp=exp, money=money))
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    val data = response.body()
                    Log.d(Constants.LOGGING_RETROFIT, "USER: ${data?.user}")
                    if (data != null)
                        user = data.user
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d(Constants.LOGGING_RETROFIT, "ERROR: $t")
                }
            })
    }
}