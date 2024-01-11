package com.alpamedev.loginretrofit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alpamedev.loginretrofit.databinding.ActivityMainBinding
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

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
        val typeMethod = if (mBinding.swType.isChecked) Constants.LOGIN_PATH else Constants.REGISTER_PATH

        val url = Constants.BASE_URL + Constants.API_PATH + typeMethod

        val email = mBinding.etEmail.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        val jsonParams = JSONObject()
        if (email.isNotEmpty()){
            jsonParams.put(Constants.EMAIL_PARAM, email)
        }
        if (password.isNotEmpty()){
            jsonParams.put(Constants.PASSWORD_PARAM, password)
        }

        val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, jsonParams, { response ->
            Log.i("response", response.toString())

            val id = response.optString(Constants.ID_PROPERTY, Constants.ERROR_VALUE)
            val token = response.optString(Constants.TOKEN_PROPERTY, Constants.ERROR_VALUE)

            val result = if (id.equals(Constants.ERROR_VALUE)) "${Constants.TOKEN_PROPERTY}: $token"
            else "${Constants.ID_PROPERTY}: $id, ${Constants.TOKEN_PROPERTY}: $token"

            updateUI(result)
        }, {
            it.printStackTrace()
            if (it.networkResponse.statusCode == 400){
                updateUI(getString(R.string.main_error_server))
            }
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()

                params["Content-Type"] = "application/json"
                return params
            }
        }

        LoginApplication.reqResAPI.addToRequestQueue(jsonObjectRequest)
    }

    private fun updateUI(result: String) {
        mBinding.tvResult.visibility = View.VISIBLE
        mBinding.tvResult.text = result
    }
}