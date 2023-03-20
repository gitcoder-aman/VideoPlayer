package com.tech.videoplayer.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tech.videoplayer.databinding.ActivityAllowAccessBinding


class AllowAccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllowAccessBinding
    private val STORAGE_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllowAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //check allow access permission is granted then not show this activity
        val preferences:SharedPreferences = getSharedPreferences("AllowAccess", MODE_PRIVATE)

        val value = preferences.getString("Allow","")
        if(value.equals("OK")){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            val editor:SharedPreferences.Editor = preferences.edit()
            editor.putString("Allow","OK")
            editor.apply()
        }


        binding.allowAccess.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_REQUEST_CODE
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_REQUEST_CODE) {
            for ((index, value) in permissions.withIndex()) {
                val per: String = value
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    val showRationale: Boolean = shouldShowRequestPermissionRationale(per)
                    if (!showRationale) {
                        //user clicked on never ask again
                        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                            .setTitle("App permission")
                            .setMessage("For playing videos, you must allow this allow to access video files on your device." + "\n\n" + "Now follow the below steps" + "\n\n" + "Open Setting from the below button" + "\n" + "Click on Permissions" + "\n" + "Allow access for storage")
                            .setPositiveButton("Open Settings") { _, _ ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri: Uri = Uri.fromParts("package", packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            }
                        alertDialog.create().show()
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            STORAGE_REQUEST_CODE
                        )
                    }
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
