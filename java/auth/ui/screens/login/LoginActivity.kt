package auth.ui.screens.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import auth.remote.model.Result
import auth.remote.model.response.LoginResponse
import auth.ui.screens.register.RegisterActivity
import com.example.fatcatproject.Menu.ActivityMenu


import com.example.fatcatproject.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryGetJwt()
        initOnClicks()
        initSubscribe()
    }


    private fun initSubscribe() {
        lifecycleScope.launch {
            viewModel.flow.collect { result ->
                when(result) {
                    is Result.Success-> {
                        if (result.data.token.isEmpty()) Toast.makeText(this@LoginActivity, "email/password not correct", Toast.LENGTH_LONG).show()
                        else {
                            saveJwt(result)
                            startActivity(Intent(this@LoginActivity, ActivityMenu::class.java))
                            finish()
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(this@LoginActivity, result.e.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }

    }

    private fun tryGetJwt() {
        getSharedPreferences("JWT", MODE_PRIVATE).edit().putString("JWT", null).commit()
        val jwt = getSharedPreferences("JWT", MODE_PRIVATE).getString("JWT", null) ?: return
        val intent = Intent(this@LoginActivity, ActivityMenu::class.java)
        startActivity(intent)
        finish()
    }
    private fun saveJwt(result: Result.Success<LoginResponse>) {
        getSharedPreferences("JWT", MODE_PRIVATE).edit().putString("JWT", result.data.token).commit()
    }

    private fun initOnClicks() {
        binding.textViewNotRegisteredYet.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.buttonSignup.setOnClickListener {
            tryLogin()
        }
        binding.noConnection.setOnClickListener{
            val intent = Intent(this@LoginActivity, ActivityMenu::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun tryLogin() {
        val email = binding.editTextMail.text.toString()
        val password = binding.editTextPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Введите данные", Toast.LENGTH_LONG).show()
            return
        }
        viewModel.login(email, password)

    }
}