package com.example.fatcatproject.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import auth.remote.model.Result
import com.example.fatcatproject.Menu.ActivityMenu

import com.example.fatcatproject.databinding.ActivityAccountBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AccountActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAccountBinding.inflate(layoutInflater) }
    private val viewModel: AccountViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSelfProfile()
        initSubscribe()
        setContentView(binding.root)
        initOnClicks()
    }

    private fun initSubscribe() {
        lifecycleScope.launch {
            viewModel.flow.collect {
                when(it) {
                    is Result.Success -> {
                        val user = it.data
                        val jwt = getSharedPreferences("JWT", MODE_PRIVATE).getString("JWT", null)
                        binding.textUserName.text = "Username: ${user.username}"
                        binding.textUserCurrency.text = "Coins: ${user.coins.toString()}"
                    }
                    is Result.Error -> {
                        Toast.makeText(this@AccountActivity, it.e.message, Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }

    private fun initOnClicks() {
        binding.buttonReturn.setOnClickListener{
            val intent = Intent(this@AccountActivity, ActivityMenu::class.java)
            startActivity(intent)
        }
        }
    }
