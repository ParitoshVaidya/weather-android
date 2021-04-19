package com.supersnippets.weather

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.supersnippets.weather.databinding.ActivityErrorBinding

class ErrorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRetry.setOnClickListener {
            val intent = Intent(this@ErrorActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}