package com.example.zulfa4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        dbHelper = UserDatabaseHelper(this)

        val etUsername = findViewById<EditText>(R.id.txtuser)
        val etPassword = findViewById<EditText>(R.id.txtpass)
        val btnlogin = findViewById<Button>(R.id.klik)

        btnlogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,"Harap isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        val isValid = dbHelper.checkUser(username, password)
        if (isValid) {
            Toast.makeText(this,"login berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent (this, halamanutama::class.java)

            intent.putExtra("username", username) // kirim data username
            startActivity(intent)
            // Tutup MainActivity agar tidak bisa balik pakai tombol back
            finish()
        } else {
            Toast.makeText(this, "Login gagal: user tidak ditemukan", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}