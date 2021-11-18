package com.example.travelspend

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.travelspend.databinding.ActivityMainBinding
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.account.AccountAuthManager
import com.huawei.hms.support.account.request.AccountAuthParams
import com.huawei.hms.support.account.request.AccountAuthParamsHelper
import com.huawei.hms.support.account.service.AccountAuthService
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val REQUEST_CODE = 8888
        const val TAG = "MainActivity"
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.huaweiId.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val authParams: AccountAuthParams =
            AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken()
                .setEmail()
                .createParams()
        val service: AccountAuthService =
            AccountAuthManager.getService(this@MainActivity, authParams)
        startActivityForResult(service.signInIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 8888) {
            val authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data)
            if (authAccountTask.isSuccessful) {
                // The sign-in is successful, and the user's ID information and ID token are obtained.
                val authAccount = authAccountTask.result
                txtToken.text = authAccount.displayName
                // Obtain the ID type (0: HUAWEI ID; 1: AppTouch ID).
                Toast.makeText(this, "Inició sesión", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

                Log.i(TAG, "accountFlag:" + authAccount.accountFlag);
            } else {
                Toast.makeText(this, "No inició sesión", Toast.LENGTH_SHORT).show()

                // The sign-in failed. No processing is required. Logs are recorded for fault locating.
                Log.e(
                    TAG,
                    "sign in failed : " + (authAccountTask.exception as ApiException).statusCode
                )
            }
        }
    }

}