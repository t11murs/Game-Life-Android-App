package com.example.fatcatproject

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fatcatproject.PlayVSBotViewModel.info.AskCard
import com.example.fatcatproject.PlayVSBotViewModel.info.Bot
import com.example.fatcatproject.PlayVSBotViewModel.info.BotRankCards
import com.example.fatcatproject.PlayVSBotViewModel.info.ListTableCount
import com.example.fatcatproject.PlayVSBotViewModel.info.ListTableRanks
import com.example.fatcatproject.PlayVSBotViewModel.info.PlayerRankCards
import com.example.fatcatproject.PlayVSBotViewModel.info.PlayerСards
import com.example.fatcatproject.PlayVSBotViewModel.info.botFound
import com.example.fatcatproject.PlayVSBotViewModel.info.count
import com.example.fatcatproject.PlayVSBotViewModel.info.isPause
import com.example.fatcatproject.PlayVSBotViewModel.info.player1
import com.example.fatcatproject.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityMainBinding

    lateinit var sounds: Sounds
    lateinit var settings: Settings

    private val viewModel: PlayVSBotViewModel by viewModels()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private fun ShowImage(place: ImageView, i: Int) {
        place.setImageResource(i)
        place.visibility = View.VISIBLE
    }

    val imageList: List<Int> = listOf(
        R.drawable.card_2_h,
        R.drawable.card_2_d,
        R.drawable.card_2_c,
        R.drawable.card_2_s,
        R.drawable.card_3_h,
        R.drawable.card_3_d,
        R.drawable.card_3_c,
        R.drawable.card_3_s,
        R.drawable.card_4_h,
        R.drawable.card_4_d,
        R.drawable.card_4_c,
        R.drawable.card_4_s,
        R.drawable.card_5_h,
        R.drawable.card_5_d,
        R.drawable.card_5_c,
        R.drawable.card_5_s,
        R.drawable.card_6_h,
        R.drawable.card_6_d,
        R.drawable.card_6_c,
        R.drawable.card_6_s,
        R.drawable.card_7_h,
        R.drawable.card_7_d,
        R.drawable.card_7_c,
        R.drawable.card_7_s,
        R.drawable.card_8_h,
        R.drawable.card_8_d,
        R.drawable.card_8_c,
        R.drawable.card_8_s,
        R.drawable.card_9_h,
        R.drawable.card_9_d,
        R.drawable.card_9_c,
        R.drawable.card_9_s,
        R.drawable.card_10_h,
        R.drawable.card_10_d,
        R.drawable.card_10_c,
        R.drawable.card_10_s,
        R.drawable.card_j_h,
        R.drawable.card_j_d,
        R.drawable.card_j_c,
        R.drawable.card_j_s,
        R.drawable.card_q_h,
        R.drawable.card_q_d,
        R.drawable.card_q_c,
        R.drawable.card_q_s,
        R.drawable.card_k_h,
        R.drawable.card_k_d,
        R.drawable.card_k_c,
        R.drawable.card_k_s,
        R.drawable.card_a_h,
        R.drawable.card_a_d,
        R.drawable.card_a_c,
        R.drawable.card_a_s
    )

    private fun ShowCardsIfNotFound(placeList: List<ImageView>, imageList: List<Int>) {
        if (PlayerRankCards[PlayerСards.size - 1] in ListTableRanks && PlayerRankCards[PlayerСards.size - 1] != 0) {
            for (j in 0..12) {
                if (ListTableRanks[j] == PlayerRankCards[PlayerСards.size - 1]) {
                    if (PlayerRankCards[PlayerСards.size - 1] == ListTableRanks[j]) {
                        ShowImage(
                            placeList[4 * j + ListTableCount[j]],
                            imageList[PlayerСards[PlayerСards.size - 1] - 1]
                        )
                        ListTableCount[j] += 1
                    }
                }
            }
        } else {
            if (PlayerRankCards[PlayerСards.size - 1] != 0) {
                for (j in 0..12) {
                    if (ListTableRanks[j] == 0) {
                        ShowImage(
                            placeList[4 * j], imageList[PlayerСards[PlayerСards.size - 1] - 1]
                        )
                        ListTableRanks[j] = PlayerRankCards[PlayerСards.size - 1]
                        ListTableCount[j] = 1
                        break
                    }
                }
            }
        }
    }

    fun ShowCardsIfFound(placeList: List<ImageView>, imageList: List<Int>) {
        for (i in 0..12) {
            if (PlayerRankCards[PlayerСards.size - 1] == ListTableRanks[i] && ListTableRanks[i] != 0) {
                ListTableCount[i] = 0
                break
            }
        }
        for (j in 0..12) {
            if (ListTableRanks[j] == PlayerRankCards[PlayerСards.size - 1] && ListTableRanks[j] != 0) {
                for (i in 0..<PlayerСards.size) {
                    if (PlayerRankCards[i] == ListTableRanks[j]) {
                        ShowImage(
                            placeList[4 * j + ListTableCount[j]], imageList[PlayerСards[i] - 1]
                        )
                        ListTableCount[j] += 1
                    }
                }
            }
        }
    }

    private fun HideCardsIfFour(player: Player, placeList: List<ImageView>) {
        for (i in (2..14)) {
            if (player.RankCards.count { it == i } == 4) {
                for (j in 0..12) {
                    if (ListTableRanks[j] == i) {
                        ListTableCount[j] = 0
                        ListTableRanks[j] = 0
                        for (k in 0..3) placeList[4 * j + k].visibility = View.INVISIBLE
                    }
                }
                player.CountCard -= 4 //количество карт игрока уменьшилось на 4
                player.Points += 1
                for (j in (0..<player.RankCards.size)) { //зануляем эти карты
                    if (player.RankCards[j] == i) {
                        player.RankCards[j] = 0
                        player.PlayerCards[j] = 0
                    }
                }
                binding.textPoints.text = "${player.Points}"
            }
        }
    }

    private fun BotIfFourCards(player: Player) {
        for (i in (2..14)) {
            if (player.RankCards.count { it == i } == 4) {
                player.CountCard -= 4 //количество карт игрока уменьшилось на 4
                player.Points += 1
                for (j in (0..<player.RankCards.size)) { //зануляем эти карты
                    if (player.RankCards[j] == i) {
                        player.RankCards[j] = 0
                        player.PlayerCards[j] = 0
                    }
                }
            }
            binding.textBotPoints.text = "${Bot.Points}"
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sounds = Sounds(this)
        settings = Settings()
        sounds.musicGame.start()
        sounds.musicGame.isLooping = true
        val placeList: List<ImageView> = mutableListOf(
            binding.place11,
            binding.place12,
            binding.place13,
            binding.place14,
            binding.place21,
            binding.place22,
            binding.place23,
            binding.place24,
            binding.place31,
            binding.place32,
            binding.place33,
            binding.place34,
            binding.place41,
            binding.place42,
            binding.place43,
            binding.place44,
            binding.place51,
            binding.place52,
            binding.place53,
            binding.place54,
            binding.place61,
            binding.place62,
            binding.place63,
            binding.place64,
            binding.place71,
            binding.place72,
            binding.place73,
            binding.place74,
            binding.place81,
            binding.place82,
            binding.place83,
            binding.place84,
            binding.place91,
            binding.place92,
            binding.place93,
            binding.place94,
            binding.place101,
            binding.place102,
            binding.place103,
            binding.place104,
            binding.place111,
            binding.place112,
            binding.place113,
            binding.place114,
            binding.place121,
            binding.place122,
            binding.place123,
            binding.place124,
            binding.place131,
            binding.place132,
            binding.place133,
            binding.place134
        )
        val buttonList: List<Button> = listOf(
            binding.buttonPlace1,
            binding.buttonPlace2,
            binding.buttonPlace3,
            binding.buttonPlace4,
            binding.buttonPlace5,
            binding.buttonPlace6,
            binding.buttonPlace7,
            binding.buttonPlace8,
            binding.buttonPlace9,
            binding.buttonPlace10,
            binding.buttonPlace11,
            binding.buttonPlace12,
            binding.buttonPlace13,
        )

        suspend fun ShowCardsStart() = coroutineScope() {
            launch(Dispatchers.Default) {
                for (i in (0..12)) buttonList[i].visibility = View.INVISIBLE

                viewModel.Start()

                withContext(Dispatchers.Main) {
                    for (i in (0..<player1.CountCard)) {
                        for (j in (0..12)) {
                            if (ListTableRanks[j] == 0 || ListTableRanks[j] == PlayerRankCards[i]) {
                                ShowImage(
                                    placeList[j * 4 + ListTableCount[j]],
                                    imageList[PlayerСards[i] - 1]
                                )
                                ListTableRanks[j] = PlayerRankCards[i]
                                ListTableCount[j] += 1
                                delay(500)
                                break
                            }
                        }
                    }
                    for (i in (0..12)) buttonList[i].visibility = View.VISIBLE
                }
            }
        }
        launch { ShowCardsStart() }
    }

    val blinkList: List<Int> = listOf(
        R.drawable.blink0000,
        R.drawable.blink0001,
        R.drawable.blink0002,
        R.drawable.blink0003,
        R.drawable.blink0004,
        R.drawable.blink0005,
        R.drawable.blink0006
    )

    suspend fun blinkAnimation() {
        while (true) {
            delay(5000)
            for (i in 0..6) {
                binding.imageSkinEnemy.setImageResource(blinkList[i])
                delay(70)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()

        sounds.doYouHaveSound.setVolume(0.5F, 0.5F)
        sounds.notFoundSound.setVolume(0.5F, 0.5F)
        sounds.meowSound.setVolume(0.5F, 0.5F)
        lateinit var alertDialog: AlertDialog

        fun createDialog() {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Exit App")
            alertDialogBuilder.setMessage("Are you sure you want to exit?")
            alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                Dispatchers.Default.cancel()
                viewModel.Restart()
                finish()
            }
            alertDialogBuilder.setNegativeButton(
                "Cancel",
                { dialogInterface: DialogInterface, i: Int -> })
            alertDialog = alertDialogBuilder.create()
        }

        createDialog()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                alertDialog.show()
            }
        })



        val placeList: MutableList<ImageView> = mutableListOf(
            binding.place11,
            binding.place12,
            binding.place13,
            binding.place14,
            binding.place21,
            binding.place22,
            binding.place23,
            binding.place24,
            binding.place31,
            binding.place32,
            binding.place33,
            binding.place34,
            binding.place41,
            binding.place42,
            binding.place43,
            binding.place44,
            binding.place51,
            binding.place52,
            binding.place53,
            binding.place54,
            binding.place61,
            binding.place62,
            binding.place63,
            binding.place64,
            binding.place71,
            binding.place72,
            binding.place73,
            binding.place74,
            binding.place81,
            binding.place82,
            binding.place83,
            binding.place84,
            binding.place91,
            binding.place92,
            binding.place93,
            binding.place94,
            binding.place101,
            binding.place102,
            binding.place103,
            binding.place104,
            binding.place111,
            binding.place112,
            binding.place113,
            binding.place114,
            binding.place121,
            binding.place122,
            binding.place123,
            binding.place124,
            binding.place131,
            binding.place132,
            binding.place133,
            binding.place134
        )
        val buttonList: List<Button> = listOf(
            binding.buttonPlace1,
            binding.buttonPlace2,
            binding.buttonPlace3,
            binding.buttonPlace4,
            binding.buttonPlace5,
            binding.buttonPlace6,
            binding.buttonPlace7,
            binding.buttonPlace8,
            binding.buttonPlace9,
            binding.buttonPlace10,
            binding.buttonPlace11,
            binding.buttonPlace12,
            binding.buttonPlace13,
        )

        fun BlockButtons() {
            for (i in (0..12)) {
                buttonList[i].visibility = View.INVISIBLE
            }
        }

        fun UnBlockButtons() {
            for (i in (0..12)) {
                if (ListTableRanks[i] != 0) {
                    buttonList[i].visibility = View.VISIBLE
                }
            }
        }

        fun isWinner(): Boolean {
            if (player1.Points == 7) {
                player1.WinnerStatus = true
                return true
            }
            if (Bot.Points == 7) {
                Bot.WinnerStatus = true
                return true
            }
            return false
        }

        fun isNoCards(player: Player): Boolean {
            if (player.CountCard == 0) {
                count += 1
                return true
            }
            return false
        }

        fun MessageWinner() {
            if (player1.WinnerStatus) {
                binding.imageWinner.setImageResource(R.drawable.win)
                binding.imageWinner.visibility = View.VISIBLE
            }
            if (Bot.WinnerStatus) {
                binding.imageWinner.setImageResource(R.drawable.lose)
                binding.imageWinner.visibility = View.VISIBLE
            }
        }

        val animation1List: List<Int> = listOf(
            R.drawable.anim0000,
            R.drawable.anim0001,
            R.drawable.anim0002,
            R.drawable.anim0003,
            R.drawable.anim0004,
            R.drawable.anim0005,
            R.drawable.anim0006,
            R.drawable.anim0007,
            R.drawable.anim0008,
            R.drawable.anim0009,
            R.drawable.anim0010,
            R.drawable.anim0011,
            R.drawable.anim0012,
            R.drawable.anim0013,
        )



        val talkingAnimation: List<Int> = listOf(
            R.drawable.talk0001,
            R.drawable.talk0002,
            R.drawable.talk0003,
            R.drawable.talk0004,
            R.drawable.talk0005,
            R.drawable.talk0006,
            R.drawable.talk0007,
            R.drawable.talk0007,
            R.drawable.talk00008,
            R.drawable.talk0009,
            R.drawable.talk0010,
        )

        suspend fun talking() {
            for (i in 0..9) {
                binding.imageSkinEnemy.setImageResource(talkingAnimation[i])
                delay(60)
            }
        }

        suspend fun animation1() {
            for (i in 0..13) {
                binding.imageAnimation1.setImageResource(animation1List[i])
                delay(70)
            }
            binding.imageAnimation1.setImageResource(animation1List[4])
            delay(80)
            binding.imageAnimation1.setImageResource(animation1List[0])
        }

        fun exitSettings() {
            binding.buttonExitSettings.setOnClickListener {
                isPause = false
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


        fun settings() {
            binding.buttonSettings.setOnClickListener {
                isPause = true
                binding.imageSettings.visibility = View.VISIBLE
                binding.imageExitSettings.visibility = View.VISIBLE
                binding.imageVolume.visibility = View.VISIBLE
                binding.imageMusic.visibility = View.VISIBLE
                binding.buttonMusic.visibility = View.VISIBLE
                binding.buttonVolume.visibility = View.VISIBLE
                settings.volume(binding.buttonVolume, binding.imageVolume, sounds)
                settings.music(binding.buttonMusic, binding.imageMusic, sounds)
//            }
                exitSettings()
            }
        }

        suspend fun ShowCloudCat(){
            binding.imageTalkCat.visibility=View.VISIBLE
            binding.textCat.visibility = View.VISIBLE
            delay(2000)
            binding.imageTalkCat.visibility=View.INVISIBLE
            binding.textCat.visibility=View.INVISIBLE
        }

        suspend fun ShowCloudMe(){
            binding.imageTalkMe.visibility=View.VISIBLE
            binding.textMe.visibility = View.VISIBLE
            delay(2000)
            binding.imageTalkMe.visibility=View.INVISIBLE
            binding.textMe.visibility=View.INVISIBLE
        }

        suspend fun doYouHavePlayer(PlayerRankCards: MutableList<Int>) {
            if (PlayerRankCards[AskCard] < 11) {
                binding.textMe.text = "У вас есть ${PlayerRankCards[AskCard]}?"
                ShowCloudMe()
            }
            if (PlayerRankCards[AskCard] == 11) {
                binding.textMe.text = "У вас есть J?"
                ShowCloudMe()
            }
            if (PlayerRankCards[AskCard] == 12) {
                binding.textMe.text = "У вас есть Q?"
                ShowCloudMe()
            }
            if (PlayerRankCards[AskCard] == 13) {
                binding.textMe.text = "У вас есть K?"
                ShowCloudMe()
            }

            if (PlayerRankCards[AskCard] == 14) {
                binding.textMe.text = "У вас есть A?"
                ShowCloudMe()
            }
        }

        suspend fun doYouHaveBot(RankCards: MutableList<Int>) {
            if (RankCards[AskCard] < 11) {
                binding.textCat.text = "У вас есть ${RankCards[AskCard]}?"
                ShowCloudCat()
            }
            if (RankCards[AskCard] == 11) {
                binding.textCat.text = "У вас есть J?"
                ShowCloudCat()
            }
            if (RankCards[AskCard] == 12) {
                binding.textCat.text = "У вас есть Q?"
                ShowCloudCat()
            }
            if (RankCards[AskCard] == 13) {
                binding.textCat.text = "У вас есть K?"
                ShowCloudCat()
            }

            if (RankCards[AskCard] == 14) {
                binding.textCat.text = "У вас есть A?"
                ShowCloudCat()
            }
        }

        binding.buttonExit.setOnClickListener { alertDialog.show() }

        fun Game() {
            for (i in (0..12)) {
                buttonList[i].setOnClickListener {
                    if (!isPause) {
                        if (isWinner()) {
                            MessageWinner()
                            lifecycleScope.launch{delay(3000)}
                            viewModel.Restart()
                            finish()
                        } else {
                            if (player1.Points >= 4) {
                                binding.imageSkinEnemy.setImageResource(R.drawable.angry)
                            }
                            //если мой ход
                            if (count % 2 == 0) {
                                lifecycleScope.launch {
                                    withContext(Dispatchers.Main) {
                                        //выбор карты
                                        for (t in 0..<player1.RankCards.size) {
                                            if (player1.RankCards[t] == ListTableRanks[i]) {
                                                AskCard = t
                                                break
                                            }
                                        }
                                        doYouHavePlayer(PlayerRankCards)
                                        BlockButtons()
                                        //если нашлась
                                        if (viewModel.isFound(
                                                player1, Bot
                                            )
                                        ) {
                                            binding.imageTalkCat.visibility= View.VISIBLE
                                            binding.textCat.text = "Да"
                                            binding.textCat.visibility=View.VISIBLE
                                            sounds.notFoundSound.start()
                                            talking()
                                            binding.imageTalkCat.visibility=View.INVISIBLE
                                            binding.textCat.visibility=View.INVISIBLE
                                            if (isWinner()) {
                                                MessageWinner()
                                            }
                                            UnBlockButtons()
                                            ShowCardsIfFound(placeList, imageList)
                                            delay(1000)
                                            HideCardsIfFour(player1, placeList)
                                        }
                                        //если не нашлась
                                        else {
                                            GlobalScope.launch {
                                                withContext(Dispatchers.Main) {
                                                    do {
                                                        binding.imageTalkCat.visibility= View.VISIBLE
                                                        binding.textCat.text = "Нет"
                                                        binding.textCat.visibility=View.VISIBLE
                                                        sounds.meowSound.start()
                                                        talking()
                                                        binding.imageTalkCat.visibility=View.INVISIBLE
                                                        binding.textCat.visibility=View.INVISIBLE
                                                        ShowCardsIfNotFound(placeList, imageList)
                                                        delay(1000)
                                                        HideCardsIfFour(player1, placeList)
                                                        //ход бота
                                                        do {
                                                            BotIfFourCards(Bot)
                                                            if (isNoCards(Bot)) break
                                                            //выбор карты бота
                                                            do {
                                                                AskCard =
                                                                    (0..<Bot.PlayerCards.size).random()
                                                            } while (Bot.RankCards[AskCard] == 0)

                                                            doYouHaveBot(BotRankCards)
                                                            sounds.doYouHaveSound.start()
                                                            talking()
                                                            delay(2000)

                                                            if (viewModel.isFound(Bot, player1)) {
                                                                for (k in (0..12)) {
                                                                    if (ListTableRanks[k] == BotRankCards[AskCard] && BotRankCards[AskCard] != 0) {
                                                                        ListTableRanks[k] = 0
                                                                        ListTableCount[k] = 0

                                                                        for (g in (0..3)) {
                                                                            placeList[4 * k + g].visibility =
                                                                                View.INVISIBLE
                                                                        }
                                                                    }
                                                                }
                                                                sounds.meowSound.start()
                                                                talking()
                                                                if (isWinner()) {
                                                                    MessageWinner()
                                                                }
                                                                delay(1000)
                                                            } else {
                                                                animation1()
                                                            }
                                                            if (isWinner()) {
                                                                MessageWinner()
                                                            }
                                                        } while (botFound)
                                                        UnBlockButtons()
                                                    }while (player1.CountCard == 0)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (isWinner()) {
                    MessageWinner()
                }
            }
        }
        launch {
            Game()
            settings()
            blinkAnimation()
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val placeList: MutableList<ImageView> = mutableListOf(
            binding.place11,
            binding.place12,
            binding.place13,
            binding.place14,
            binding.place21,
            binding.place22,
            binding.place23,
            binding.place24,
            binding.place31,
            binding.place32,
            binding.place33,
            binding.place34,
            binding.place41,
            binding.place42,
            binding.place43,
            binding.place44,
            binding.place51,
            binding.place52,
            binding.place53,
            binding.place54,
            binding.place61,
            binding.place62,
            binding.place63,
            binding.place64,
            binding.place71,
            binding.place72,
            binding.place73,
            binding.place74,
            binding.place81,
            binding.place82,
            binding.place83,
            binding.place84,
            binding.place91,
            binding.place92,
            binding.place93,
            binding.place94,
            binding.place101,
            binding.place102,
            binding.place103,
            binding.place104,
            binding.place111,
            binding.place112,
            binding.place113,
            binding.place114,
            binding.place121,
            binding.place122,
            binding.place123,
            binding.place124,
            binding.place131,
            binding.place132,
            binding.place133,
            binding.place134
        )
        var k: Int = 0
        for (j in 0..12) {
            k = 0
            for (i in 0..<player1.PlayerCards.size) {
                if (PlayerRankCards[i] == ListTableRanks[j] && ListTableRanks[j] != 0) {
                    ShowImage(placeList[4 * j + k], imageList[PlayerСards[i] - 1])
                    k += 1
                }
            }
        }
        launch {
            blinkAnimation()
        }
    }

    override fun onStop() {
        super.onStop()
        sounds.musicGame.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        sounds.musicGame.release()
    }
}



