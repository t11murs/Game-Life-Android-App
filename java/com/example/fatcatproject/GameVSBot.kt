package com.example.fatcatproject

class GameVSBot(var cards: Cards, var player: Player, var bot: Player, var count: Int) {
    var AskCard: Int = -1

    //поиск карты
    fun isFound(player1: Player, bot: Player): Boolean {
        //карта не нашлась
        if (player1.RankCards[AskCard] in bot.RankCards && player1.RankCards[AskCard] != 0) {
            for (i in (0..<bot.RankCards.size)) { //просматриваем все карты игрока 2
                if (bot.RankCards[i] == player1.RankCards[AskCard]) {
                    player1.PlayerCards.add(bot.PlayerCards[i]) // добавление данной карты 1 игроку
                    player1.RankCards.add(bot.RankCards[i])
                    bot.PlayerCards[i] = 0
                    bot.RankCards[i] = 0
                    player1.CountCard += 1
                    bot.CountCard -= 1
                }
            }
            AskCard = -1
            return true // продолжение хода этого игрока
        }
        // карта не нашлась
        else {
            count += 1
            for (i in (0..51)) {
                if (Cards.CardsList.shuffleList[i] != 0) {
                    player1.RankCards.add(Cards.CardsList.shuffleRanks[i]) //игрок получает верхнюю карту колоды
                    player1.PlayerCards.add(Cards.CardsList.shuffleList[i])
                    player1.CountCard += 1 //количество его карт увеличилось на 1
                    Cards.CardsList.shuffleList[i] = 0; //обнуление верхней карты колоды
                    Cards.CardsList.updateCards(
                        Cards.CardsList.shuffleList,
                        Cards.CardsList.shuffleRanks
                    ) //обновление нуумерации карт
                    break
                }
            }
            return false // переход хода
        }
    }

    //проверка есть ли у игрока 4 карты одной ценности
    fun isFourCards(player: Player) {
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
        }
    }


    // менюшка
    fun testPrint(playerOne: Player, playerTwo: Player) {
        println(count)
        if (count % 2 == 0) {
            println(playerOne.PlayerCards)
            println(playerOne.RankCards)
            println(playerOne.SignsHave.slice(2..14))
            println(playerOne.CountCard)
            println(playerTwo.PlayerCards)
            println(playerTwo.RankCards)
            println(playerTwo.SignsHave.slice(2..14))
            println(playerTwo.CountCard)
            println("Player1 Choose Card")
        } else {
            println(playerTwo.PlayerCards)
            println(playerTwo.RankCards)
            println(playerTwo.SignsHave.slice(2..14))
            println(playerTwo.CountCard)
            println(playerOne.PlayerCards)
            println(playerOne.RankCards)
            println(playerOne.SignsHave.slice(2..14))
            println(playerOne.CountCard)
            println("Bot Choose Card")
        }
    }

    // проверка на победу
    fun isWinner(): Boolean {
        if (player.CountCard == 0 && bot.CountCard == 0) {
            if (player.Points > bot.Points) { // количество очков первого больше, чем у второго
                player.WinnerStatus = true
                println("player1 wins")
            } else {
                bot.WinnerStatus = true // количесвто очков первого меньше, чем у второго
                println("player2 wins")
            }
            return true
        }
        return false
    }

    // проверка кончились ли игрока карты
    fun isNoCards(playerOne: Player, playerTwo: Player) {
        if (playerOne.CountCard == 0)
            PlayerMove(playerTwo, playerOne)
    }
    //считываем карту
    fun Scan(number: Int): Int{
        return number
    }

    // ход игрока
    fun PlayerMove(playerOne: Player, playerTwo: Player) {

        //проверка на победу
        if (isWinner()) return

        // проверка наличия у игрока 4 карт одго знака
        isFourCards(playerOne)
        isFourCards(playerTwo)

        // Вывод для карт игроков для тестов
        testPrint(playerOne, playerTwo)

        //проверяем кончились ли карты у игрока
        isNoCards(playerOne, playerTwo)

        //считывание выбора игрока
        if (count % 2 == 0)
            AskCard = readln().toInt() - 1
        else {
            AskCard = (0..<bot.CountCard).random()
            println(AskCard + 1)
        }

        // просит несуществующую карту
        if (playerOne.PlayerCards[AskCard] == 0) {
            print("BAD! Try again\n")
            PlayerMove(playerOne, playerTwo)
        }

        //переход хода или продолжение хода данного игрока
        if (AskCard!=-1) {
            if (isFound(playerOne, playerTwo))
                PlayerMove(playerOne, playerTwo)
            else {
                PlayerMove(playerTwo, playerOne)
            }
        }

        return
    }

    // начало игры
    fun Start() {
        cards.shuffle() //рандом карт
        player.GetCards(player) // раздача карт 1 и 2 игрокам
        bot.GetCards(bot)
        Cards.CardsList.updateCards(Cards.CardsList.shuffleList, Cards.CardsList.shuffleRanks)
    }
}