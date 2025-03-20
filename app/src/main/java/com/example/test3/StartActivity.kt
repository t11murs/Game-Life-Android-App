package com.example.test3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import android.widget.TextView
import com.example.test3.ViewModelGame.data.size

class StartActivity : AppCompatActivity() {

    lateinit var seekBar: SeekBar
    lateinit var numberOfCells: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        numberOfCells = findViewById(R.id.numberOfCells)
        seekBar = findViewById(R.id.seekBar)

        //функционал кнопки
        val buttonNavigate:Button = findViewById(R.id.buttonNavigate)
        buttonNavigate.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply{
                putExtra("EXTRA_SIZE", size)
            }
            startActivity(intent)
        }

        //функционал сикбара
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                numberOfCells.text = "$progress"
                if (seekBar != null) {
                    size = seekBar.progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    size = seekBar.progress
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    size = seekBar.progress
                }
            }
        })

    }

}