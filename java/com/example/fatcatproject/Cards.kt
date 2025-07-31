package com.example.fatcatproject

class Cards(var shuffleStatus: Boolean) {

    object CardsList{
        var shuffleList: MutableList<Int> = MutableList(52) {0}
        var shuffleRanks: MutableList<Int> = MutableList(52) {0}
        //функция для распределения карт по номеру
        fun updateCards(ShuffleList: MutableList<Int>, ShuffleSigns: MutableList<Int>){
            for(i in(0..ShuffleList.size-1)){
                if(ShuffleList[i]!=0)
                    ShuffleSigns[i]=(ShuffleList[i]-1)/4+2
                else ShuffleSigns[i]=0
            }
        }
    }

    // функция тасовки карт
    fun shuffle() {
        shuffleStatus = true
        for (i in (0..51)) {
            CardsList.shuffleList[i] = i + 1
        }
        repeat(200) {
            val a = (0..51).random()
            val b = (0..51).random()
            if (a != b) {
                var c = CardsList.shuffleList[a]
                CardsList.shuffleList[a] = CardsList.shuffleList[b]
                CardsList.shuffleList[b] = c
            }
        }
        for(i in(0..47)){
            if(CardsList.shuffleList[i] == CardsList.shuffleList[i+1] &&
                CardsList.shuffleList[i+1] == CardsList.shuffleList[i+2] &&
                CardsList.shuffleList[i+2] == CardsList.shuffleList[i+3])
                shuffle()
        }
        CardsList.updateCards(CardsList.shuffleList, CardsList.shuffleRanks)
    }

    //функция расставления карт в правильном порядке (1-52)
    fun UnShuffle(){
        shuffleStatus = false
        for (i in (0..51)) {
            CardsList.shuffleList[i - 1] = i
        }
        Cards.CardsList.updateCards(CardsList.shuffleList, CardsList.shuffleRanks)
    }
}
