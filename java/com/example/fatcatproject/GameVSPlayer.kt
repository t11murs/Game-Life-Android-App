package com.example.fatcatproject

class GameVSPlayer(var cards: Cards, var player1: Player, var player2: Player, var count: Int){
    //счетчик раундов

    //поиск карты
    fun isFound(player1: Player, bot: Player, AskCard: Int): Boolean {
        //карта не нашлась
        if (player1.RankCards[AskCard] in player2.RankCards && player1.RankCards[AskCard] != 0) {
            for (i in (0..<player2.RankCards.size)) { //просматриваем все карты игрока 2
                if (player2.RankCards[i] == player1.RankCards[AskCard]) {
                    player1.PlayerCards.add(player2.PlayerCards[i]) // добавление данной карты 1 игроку
                    player1.RankCards.add(player2.RankCards[i])
                    player2.PlayerCards[i] = 0
                    player2.RankCards[i] = 0
                    player1.CountCard += 1
                    player2.CountCard -= 1
                }
            }
            return true // продолжение хода этого игрока
        }
        // карта не нашлась
        else {
            count+=1
            for (i in (0..51)) {
                if (Cards.CardsList.shuffleList[i] != 0) {
                    player1.RankCards.add(Cards.CardsList.shuffleRanks[i]) //игрок получает верхнюю карту колоды
                    player1.PlayerCards.add(Cards.CardsList.shuffleList[i])
                    player1.CountCard += 1 //количество его карт увеличилось на 1
                    Cards.CardsList.shuffleList[i] = 0; //обнуление верхней карты колоды
                    Cards.CardsList.updateCards(Cards.CardsList.shuffleList, Cards.CardsList.shuffleRanks) //обновление нуумерации карт
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
            println("Player2 Choose Card")
        }
    }
    // проверка на победу
    fun isWinner(): Boolean {
        if (player1.CountCard == 0 && player2.CountCard == 0) {
            if (player1.Points > player2.Points) { // количество очков первого больше, чем у второго
                player1.WinnerStatus = true
                println("player1 wins")
            } else {
                player2.WinnerStatus = true // количесвто очков первого меньше, чем у второго
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
        val AskCard = readln().toInt() - 1

        // просит несуществующую карту
        if (playerOne.PlayerCards[AskCard] == 0) {
            print("BAD! Try again\n")
            PlayerMove(playerOne, playerTwo)
        }

        //переход хода или продолжение хода данного игрока
        if (isFound(playerOne, playerTwo, AskCard))
            PlayerMove(playerOne, playerTwo)
        else {
            PlayerMove(playerTwo, playerOne)
            count += 1
        }
        return
    }
    // начало игры
    fun Start() {
        cards.shuffle() //рандом карт
        println(Cards.CardsList.shuffleList)
        println(Cards.CardsList.shuffleRanks) //вывод колоды карт
        player1.GetCards(player1) // раздача карт 1 и 2 игрокам
        player2.GetCards(player2)
        Cards.CardsList.updateCards(Cards.CardsList.shuffleList, Cards.CardsList.shuffleRanks)
        println(Cards.CardsList.shuffleList)
        println(Cards.CardsList.shuffleRanks) // вывод колоды карт после раздачи
        PlayerMove(player1, player2)
    }
}