package auth.ui.screens.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import auth.remote.model.Result
import auth.ui.screens.login.LoginActivity
import com.example.fatcatproject.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val viewModel: RegistrationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initOnClicks()
        initSubscribe()
    }

    private fun initSubscribe() {
        lifecycleScope.launch {
            viewModel.flow.collect {
                when(it) {
                    is Result.Success -> {
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                    is Result.Error -> {
                        Toast.makeText(this@RegisterActivity, it.e.message, Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }

    private fun initOnClicks() {
        binding.buttonSignup.setOnClickListener {
            val email = binding.editTextMail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val name = binding.editTextName.text.toString()

            //TODO: validate data
            viewModel.register(email, password, name)
        }
    }
}