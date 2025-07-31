package com.example.fatcatproject

import android.content.Context
import android.media.MediaPlayer

class Sounds(context: Context) {
        val musicMenu: MediaPlayer = MediaPlayer.create(context, R.raw.menu_music)
        val musicGame: MediaPlayer = MediaPlayer.create(context, R.raw.music_game_volume)
        val meowSound = MediaPlayer.create(context, R.raw.meow)
        val notFoundSound = MediaPlayer.create(context, R.raw.didnt_found)
        val doYouHaveSound = MediaPlayer.create(context, R.raw.do_you_have)

    fun offVolume(){
        musicGame.setVolume(0F, 0F)
        musicMenu.setVolume(0F, 0F)
        doYouHaveSound.setVolume(0F, 0F)
        notFoundSound.setVolume(0F, 0F)
        meowSound.setVolume(0F, 0F)
    }

    fun onVolume(){
        doYouHaveSound.setVolume(0.5F, 0.5F)
        notFoundSound.setVolume(0.5F, 0.5F)
        meowSound.setVolume(0.5F, 0.5F)
    }

    fun offMusic(){
        musicGame.setVolume(0F, 0F)
        musicMenu.setVolume(0F, 0f)
    }

    fun onMusic(){
        musicGame.setVolume(1F, 1F)
        musicMenu.setVolume(1F, 1F)
    }

}