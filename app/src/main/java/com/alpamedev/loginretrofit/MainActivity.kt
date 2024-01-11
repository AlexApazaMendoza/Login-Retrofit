package com.alpamedev.loginretrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alpamedev.loginretrofit.databinding.ActivityMainBinding
import com.alpamedev.loginretrofit.retrofit.LoginResponse
import com.alpamedev.loginretrofit.retrofit.RegisterResponse
import com.alpamedev.loginretrofit.retrofit.RetrofitConfig
import com.alpamedev.loginretrofit.retrofit.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.swType.setOnCheckedChangeListener { button, checked ->
            button.text = if (checked) getString(R.string.main_type_login)
            else getString(R.string.main_type_register)

            mBinding.btnLogin.text = button.text
        }

        mBinding.btnLogin.setOnClickListener {
            login()
        }

        mBinding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun login() {
        val userRequest = UserRequest(
            mBinding.etEmail.text.toString().trim(),
            mBinding.etPassword.text.toString().trim()
        )

        if (mBinding.swType.isChecked) {
            RetrofitConfig.loginService.login(userRequest).enqueue(object: Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            Log.i("response", response.toString())
                            val token = response.body()?.token ?: Constants.ERROR_VALUE
                            val result = "${Constants.TOKEN_PROPERTY}: $token"
                            updateUI(result)
                        }
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }
                        else -> {
                            updateUI(getString(R.string.main_error_response))
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    t.printStackTrace()
                    updateUI(getString(R.string.main_error_response))
                }
            })

        } else {
            RetrofitConfig.registerService.register(userRequest).enqueue(object: Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            Log.i("response", response.toString())
                            val id = response.body()?.id ?: Constants.ERROR_VALUE
                            val token = response.body()?.token ?: Constants.ERROR_VALUE
                            val result = "${Constants.ID_PROPERTY}: $id, ${Constants.TOKEN_PROPERTY}: $token"
                            updateUI(result)
                        }
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }
                        else -> {
                            updateUI(getString(R.string.main_error_response))
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    t.printStackTrace()
                    updateUI(getString(R.string.main_error_response))
                }
            })
        }
    }

    private fun updateUI(result: String) {
        mBinding.tvResult.visibility = View.VISIBLE
        mBinding.tvResult.text = result
    }
}