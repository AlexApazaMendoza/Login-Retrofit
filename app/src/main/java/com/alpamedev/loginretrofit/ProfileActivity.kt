package com.alpamedev.loginretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alpamedev.loginretrofit.databinding.ActivityProfileBinding
import com.alpamedev.loginretrofit.retrofit.RetrofitConfig
import com.alpamedev.loginretrofit.retrofit.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

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
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitConfig.userService.getUser()
                Log.i("response", response.toString())
                updateUI(response.data, response.support)
            } catch (e: Exception) {
                e.printStackTrace()
                showMessage(getMessageByException(e))
            }
        }
    }

    private fun getMessageByException(e: Exception): String {
        return when (e) {
            is HttpException -> {
                if (e.code() == 404) {
                    getString(R.string.main_error_user_not_found)
                } else {
                    getString(R.string.main_error_response)
                }
            }
            else -> getString(R.string.main_error_response)
        }
    }

    private suspend fun updateUI(user: User, support: Support) = withContext(Dispatchers.Main) {
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

    private suspend fun showMessage(message: String) = withContext(Dispatchers.Main) {
        Snackbar.make(mBinding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}