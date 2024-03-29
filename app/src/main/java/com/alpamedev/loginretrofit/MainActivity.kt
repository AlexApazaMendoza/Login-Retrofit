package com.alpamedev.loginretrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alpamedev.loginretrofit.databinding.ActivityMainBinding
import com.alpamedev.loginretrofit.retrofit.RetrofitConfig
import com.alpamedev.loginretrofit.retrofit.entities.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

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
            loginOrRegister()
        }

        mBinding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun loginOrRegister() {
        val userRequest = UserRequest(
            mBinding.etEmail.text.toString().trim(),
            mBinding.etPassword.text.toString().trim()
        )

        lifecycleScope.launch(Dispatchers.IO) {
            if (mBinding.swType.isChecked) {
                try {
                    val response = RetrofitConfig.loginService.login(userRequest)
                    Log.i("response", response.toString())
                    val token = response.token ?: Constants.ERROR_VALUE
                    val result = "${Constants.TOKEN_PROPERTY}: $token"
                    updateUI(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                    updateUI(getMessageByException(e))
                }
            } else {
                try {
                    val response = RetrofitConfig.registerService.register(userRequest)
                    Log.i("response", response.toString())
                    val id = response.id ?: Constants.ERROR_VALUE
                    val token = response.token ?: Constants.ERROR_VALUE
                    val result = "${Constants.ID_PROPERTY}: $id, ${Constants.TOKEN_PROPERTY}: $token"
                    updateUI(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                    updateUI(getMessageByException(e))
                }
            }
        }
    }

    private fun getMessageByException(e: Exception): String {
        return when (e) {
            is HttpException -> {
                if (e.code() == 400) {
                    getString(R.string.main_error_server)
                } else {
                    getString(R.string.main_error_response)
                }
            }
            else -> getString(R.string.main_error_response)
        }
    }

    private suspend fun updateUI(result: String) = withContext(Dispatchers.Main) {
        mBinding.tvResult.visibility = View.VISIBLE
        mBinding.tvResult.text = result
    }
}