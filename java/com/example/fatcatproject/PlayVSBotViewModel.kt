package com.example.fatcatproject

import androidx.lifecycle.ViewModel
import com.example.fatcatproject.Cards.CardsList.shuffleList
import com.example.fatcatproject.Cards.CardsList.shuffleRanks
import com.example.fatcatproject.Cards.CardsList.updateCards
import com.example.fatcatproject.PlayVSBotViewModel.info.Bot
import com.example.fatcatproject.PlayVSBotViewModel.info.cards
import com.example.fatcatproject.PlayVSBotViewModel.info.player1

class PlayVSBotViewModel() : ViewModel() {

    object info {
        var cards: Cards = Cards(false)
        val PlayerName: String = "Player1"
        var PlayerСards: MutableList<Int> = MutableList(7) { 0 }
        var PlayerRankCards: MutableList<Int> = MutableList<Int>(7) { 0 }
        var BotCards: MutableList<Int> = MutableList(7) { 0 }
        var BotRankCards: MutableList<Int> = MutableList<Int>(7) { 0 }
        var player1: Player = Player(PlayerName, PlayerСards, PlayerRankCards, 0, false, 0)
        var Bot: Player = Player("Bot", BotCards, BotRankCards, 0, false, 0)
        var AskCard: Int = -1
        var count: Int = 0
        var ListTableRanks: MutableList<Int> = MutableList<Int>(13) { 0 }
        var ListTableCount: MutableList<Int> = MutableList<Int>(13) { 0 }
        var botFound: Boolean = false
        var isPause: Boolean = false
        var isMusic: Boolean = true
        var isVolume: Boolean = true
    }

    //поиск карты
    fun isFound(player1: Player, player2: Player): Boolean {
        //карта нашлась
        if (player1.RankCards[info.AskCard] in player2.RankCards && player1.RankCards[info.AskCard] != 0) {
            for (i in (0..<player2.RankCards.size)) { //просматриваем все карты игрока 2
                if (player2.RankCards[i] == player1.RankCards[info.AskCard]) {
                    player1.PlayerCards.add(player2.PlayerCards[i]) // добавление данной карты 1 игроку
                    player1.RankCards.add(player2.RankCards[i])
                    player2.PlayerCards[i] = 0
                    player2.RankCards[i] = 0
                    player1.CountCard += 1
                    player2.CountCard -= 1
                }
            }
            info.botFound = true
            return true // продолжение хода этого игрока
        }
        // карта не нашлась
        else {
            info.botFound = false
            info.count += 1
            for (i in (0..51)) {
                if (shuffleList[i] != 0) {
                    player1.RankCards.add(shuffleRanks[i]) //игрок получает верхнюю карту колоды
                    player1.PlayerCards.add(shuffleList[i])
                    player1.CountCard += 1 //количество его карт увеличилось на 1
                    shuffleList[i] = 0; //обнуление верхней карты колоды
                    updateCards(
                        shuffleList,
                        shuffleRanks
                    ) //обновление нуумерации карт
                    break
                }
            }
            return false // переход хода
        }
    }


    // начало игры
    fun Start() {
        cards.shuffle() //рандом карт
        player1.GetCards(player1) // раздача карт 1 и 2 игрокам
        Bot.GetCards(Bot)
        updateCards(shuffleList, shuffleRanks)
    }

    fun Restart() {
        info.ListTableRanks = MutableList<Int>(13) { 0 }
        info.ListTableCount = MutableList<Int>(13) { 0 }
        info.PlayerСards = MutableList(7) { 0 }
        info.PlayerRankCards = MutableList<Int>(7) { 0 }
        info.BotCards = MutableList(7) { 0 }
        info.BotRankCards = MutableList<Int>(7) { 0 }
        player1 = Player(info.PlayerName, info.PlayerСards, info.PlayerRankCards, 0, false, 0)
        Bot = Player("Bot", info.BotCards, info.BotRankCards, 0, false, 0)
        cards.shuffle() //рандом карт
        player1.GetCards(player1) // раздача карт 1 и 2 игрокам
        Bot.GetCards(Bot)
        updateCards(shuffleList, shuffleRanks)
    }
}
