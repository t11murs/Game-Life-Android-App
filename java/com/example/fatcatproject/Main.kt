package com.example.fatcatproject

fun main() {
    var cards: Cards = Cards(false)
    var Player1Сards: MutableList<Int> = MutableList(7){0}
    var Player2Сards: MutableList<Int> = MutableList(7){0}
    var Player1SignCards: MutableList<Int> = MutableList(7){0}
    var Player2SignCards: MutableList<Int> = MutableList(7){0}
    var Bot: Player = Player("Bot", Player2Сards, Player2SignCards, 0,false, 0)
    var player1: Player = Player("Player1", Player1Сards, Player1SignCards, 0,false, 0, )
    var player2: Player = Player("Player2", Player2Сards, Player2SignCards , 0, false, 0 )
    val GameStartBot: GameVSBot = GameVSBot(cards, player1, Bot, 0)
    val GameStartPlayer: GameVSPlayer = GameVSPlayer(cards, player1, player2, 0)
    GameStartBot.Start()
}