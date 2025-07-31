package com.example.fatcatproject

open class Player (val name: String, var PlayerCards: MutableList<Int>, var RankCards: MutableList<Int>, var CountCard: Int, var WinnerStatus: Boolean, var Points: Int) {
    var SignsHave: MutableList<Boolean> = MutableList<Boolean>(15){false}
    // раздача карт
    open fun GetCards(player: Player) {
        var i = 0
        while(CountCard!=7){
            if (Cards.CardsList.shuffleList[i] != 0) {
                PlayerCards[CountCard] = Cards.CardsList.shuffleList[i]
                Cards.CardsList.shuffleList[i] = 0
                CountCard += 1
            }
            i+=1
        }
        Cards.CardsList.updateCards(PlayerCards, RankCards)
    }
}