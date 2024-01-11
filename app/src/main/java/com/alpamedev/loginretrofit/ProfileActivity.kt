package com.alpamedev.loginretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alpamedev.loginretrofit.databinding.ActivityProfileBinding
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class ProfileActivity : AppCompatActivity() {

    private val mBinding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        loadUserProfile()
    }

    private fun loadUserProfile() {
        val url = Constants.BASE_URL + Constants.API_PATH + Constants.USERS_PATH + Constants.TWO_PATH

        val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null, { response ->
            Log.i("response", response.toString())
            val gson = Gson()

            val userJson = response.optJSONObject(Constants.DATA_PROPERTY)?.toString()
            val user: User = gson.fromJson(userJson, User::class.java)

            val supportJson = response.optJSONObject(Constants.SUPPORT_PROPERTY)?.toString()
            val support: Support = gson.fromJson(supportJson, Support::class.java)

            updateUI(user, support)
        }, {
            it.printStackTrace()
            if (it.networkResponse != null && it.networkResponse.statusCode == 400){
                showMessage(getString(R.string.main_error_server))
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
    private fun updateUI(user: User, support: Support) {
        with(mBinding) {
            tvFullName.text = user.getFullName()
            tvEmail.text = user.email

            Glide.with(this@ProfileActivity)
                .load(user.avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(imgProfile)

            tvSupportMessage.text = support.text
            tvSupportUrl.text = support.url
        }
    }

    private fun showMessage(message: String){
        Snackbar.make(mBinding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}