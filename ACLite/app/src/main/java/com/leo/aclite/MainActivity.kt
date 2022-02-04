package com.leo.aclite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.leo.aclite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.button.setOnClickListener {
            viewModel.onTryLogin(binding.user.text.toString(), binding.pass.text.toString())
        }

        viewModel.state.observe(this@MainActivity) { state ->
            binding.user.error = state.userError?.let(::getString)
            binding.pass.error = state.passError?.let(::getString)
            binding.button.visibility = if(state.loggingIn) View.GONE else View.VISIBLE
            binding.progress.visibility = if(state.loggingIn) View.VISIBLE else View.GONE

            if(state.loggedIn) {
                startActivity(Intent(this@MainActivity, NextActivity::class.java))
                viewModel.onNavigateToNextScreen()
            }

        }
     }

}