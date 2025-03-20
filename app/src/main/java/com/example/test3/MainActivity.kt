package com.example.test3

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import android.util.TypedValue
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.test3.ViewModelGame.data.size
import com.example.test3.ViewModelGame.data.cells
import com.example.test3.ViewModelGame.data.neighbors
import com.example.test3.ViewModelGame.data.speed
import com.example.test3.ViewModelGame.data.status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: ViewModelGame by viewModels()
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var seekBar: SeekBar

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //получаем интент, который запустил нашу активность
        val intent = intent
        size = intent.getIntExtra("EXTRA_SIZE", 20)

        //определение поля
        val tableLayout = findViewById<TableLayout>(R.id.tableButtons)
        tableLayout.isShrinkAllColumns = false
        tableLayout.isStretchAllColumns = true

        //инициализация некоторых вещей
        buttons = Array(size) { Array(size) { Button(this) } }
        seekBar = findViewById(R.id.speedSeekBar)
        val textSpeed: TextView = findViewById(R.id.textSpeed)
        cells = Array(size) { Array(size) { 0 } }
        neighbors = Array(size) { Array(size) { 0 } }
        val startButton: Button = findViewById(R.id.buttonStart)
        startButton.setBackgroundColor(0xff1ba805.toInt())

        //получаем размеры таблицы и кнопок
        val tableSize = dpToPx(410)
        val buttonSize = tableSize / size

        // Добавляем строки и кнопки в TableLayout
        var buttonIndex = 1
        for (i in 0 until size) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            tableRow.weightSum = size.toFloat()

            for (j in 0 until size) {
                val button = Button(this)
                buttons[i][j] = button
                button.layoutParams = TableRow.LayoutParams(
                    0,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1f
                ).apply {
                    height = buttonSize
                    width = buttonSize
                    setMargins(4, 5, 4, 5)
                }
                button.setPadding(0, 0, 0, 0)
                button.setBackgroundColor(Color.WHITE)
                buttonIndex++
                tableRow.addView(button)
            }
            tableLayout.addView(tableRow)
        }

        //прописываем нажатия кнопок на смену цвета и "оживление" клетки 
        for (i in 0 until size) {
            for (j in 0 until size) {
                buttons[i][j].setOnClickListener {
                    if (cells[i][j] == 0) {
                        buttons[i][j].setBackgroundColor(Color.RED)
                        cells[i][j] = 1
                    } else {
                        cells[i][j] = 0
                        buttons[i][j].setBackgroundColor(Color.WHITE)
                    }
                }
            }
        }
        //прописываем функционал изменения скорости
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textSpeed.text = "speed: $progress"
                speed = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        fun showCells() {
            for (i in 0 until size) {
                for (j in 0 until size) {
                    if (cells[i][j] == 1)
                        buttons[i][j].setBackgroundColor(Color.RED)
                    else
                        buttons[i][j].setBackgroundColor(Color.WHITE)
                }
            }
        }

        fun enablingButtons() {
            for (i in 0 until size) {
                for (j in 0 until size) {
                    buttons[i][j].setEnabled(status)
                }
            }
        }

        //прописываем функционал кнопки старта
        startButton.setOnClickListener {
            lifecycleScope.launch {
                if (!status) {
                    enablingButtons()
                    startButton.text = "Stop"
                    startButton.setBackgroundColor(Color.RED)
                    status = true
                    while (status) {
                        viewModel.countNeighbors(cells, neighbors)
                        viewModel.updateGrid(cells, neighbors)
                        showCells()
                        when(speed){
                            1 -> delay(1000)
                            2 -> delay(900)
                            3 -> delay(800)
                            4 -> delay(700)
                            5 -> delay(600)
                            6 -> delay(500)
                            7 -> delay(400)
                            8 -> delay(300)
                            9 -> delay(200)
                            10 -> delay(100)
                        }
                    }
                } else {
                    startButton.text = "Start"
                    startButton.setBackgroundColor(0xff1ba805.toInt())
                    enablingButtons()
                    status = false
                }
            }
        }
    }

}


