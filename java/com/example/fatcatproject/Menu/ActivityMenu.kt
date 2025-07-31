package com.example.fatcatproject.Menu

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fatcatproject.MainActivity
import com.example.fatcatproject.PlayVSBotViewModel
import com.example.fatcatproject.Settings
import com.example.fatcatproject.Sounds
import com.example.fatcatproject.account.AccountActivity
import com.example.fatcatproject.databinding.ActivityMenuBinding

class ActivityMenu : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    lateinit var sounds: Sounds
    lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingsMenu()
    }

    override fun onResume() {
        super.onResume()
        sounds = Sounds(this)
        settings = Settings()
        sounds.musicMenu.start()
        sounds.musicMenu.isLooping = true

        binding.buttonVsBot.setOnClickListener() {
            val intent = Intent(this@ActivityMenu, MainActivity::class.java)
            startActivity(intent)

        }
        lateinit var alertDialog: AlertDialog
        fun createDialog() {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Exit App")
            alertDialogBuilder.setMessage("Are you sure you want to exit?")
            alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                finish()
            }
            alertDialogBuilder.setNegativeButton("Cancel", { dialogInterface: DialogInterface, i: Int -> })
            alertDialog = alertDialogBuilder.create()
        }

        createDialog()
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                alertDialog.show()
            }
        })

        binding.buttonExit.setOnClickListener{alertDialog.show()}
    }

    fun exitSettings() {
        binding.buttonExitSettings.setOnClickListener {
            PlayVSBotViewModel.info.isPause = false
            binding.imageSettings.visibility = View.INVISIBLE
            binding.imageExitSettings.visibility = View.INVISIBLE
            binding.imageVolume.visibility = View.INVISIBLE
            binding.imageMusic.visibility = View.INVISIBLE
            binding.imageAccount.visibility = View.INVISIBLE
            binding.buttonMusic.visibility = View.INVISIBLE
            binding.buttonVolume.visibility = View.INVISIBLE
            binding.buttonAccount.visibility = View.INVISIBLE
        }
    }


    fun settingsMenu() {
        binding.buttonSettings.setOnClickListener {
            PlayVSBotViewModel.info.isPause = true
            binding.imageSettings.visibility = View.VISIBLE
            binding.imageExitSettings.visibility = View.VISIBLE
            binding.imageVolume.visibility = View.VISIBLE
            binding.imageMusic.visibility = View.VISIBLE
            binding.imageAccount.visibility = View.VISIBLE
            binding.buttonMusic.visibility = View.VISIBLE
            binding.buttonVolume.visibility = View.VISIBLE
            binding.buttonAccount.visibility = View.VISIBLE
            settings.volume(binding.buttonVolume, binding.imageVolume, sounds)
            settings.music(binding.buttonMusic, binding.imageMusic, sounds)
            settings.account(binding.buttonAccount, binding.imageAccount )
            binding.buttonAccount.setOnClickListener {
                startActivity(Intent(this@ActivityMenu, AccountActivity::class.java))
                finish()
            }
            exitSettings()
        }
    }

    override fun onStop() {
        super.onStop()
        sounds.musicMenu.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        sounds.musicMenu.release()
    }
}


