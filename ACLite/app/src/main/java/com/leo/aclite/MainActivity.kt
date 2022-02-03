package com.leo.aclite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.leo.aclite.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progress.visibility = View.GONE

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                binding.button.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE

                val success = tryLogin(binding.user.text.toString(), binding.pass.text.toString() )
                if (success) {
                    startActivity(Intent(this@MainActivity, NextActivity::class.java))
                    finish()
                }
                else {
                    binding.button.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                }

            }

        }
    }

    private suspend fun tryLogin(user: String, pass: String): Boolean {
        delay(2000)
        if (!user.contains('@')) {
            binding.user.error = getString(R.string.user_error)
        }
        else {
            binding.user.error = null
        }

        if (pass.length < 5) {
            binding.pass.error = getString(R.string.pass_error)
        }
        else {
            binding.pass.error = null
        }
        return binding.user.error == null && binding.pass.error == null

    }
}