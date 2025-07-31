package com.example.fatcatproject

import android.view.View
import android.widget.Button
import android.widget.ImageView

class Settings() {
    fun volume(buttonVolume: Button, imageVolume: ImageView, sounds: Sounds) {
        buttonVolume.setOnClickListener {
            if (PlayVSBotViewModel.info.isVolume) {
                imageVolume.setImageResource(R.drawable.volume_off)
                PlayVSBotViewModel.info.isVolume = false
                sounds.offVolume()
            } else {
                imageVolume.setImageResource(R.drawable.volume_on)
                PlayVSBotViewModel.info.isVolume = true
                sounds.onVolume()
                if(PlayVSBotViewModel.info.isMusic) sounds.onMusic()
            }
        }
    }

    fun music(buttonMusic: Button, imageMusic: ImageView, sounds: Sounds) {
        buttonMusic.setOnClickListener {
            if (PlayVSBotViewModel.info.isMusic) {
                imageMusic.setImageResource(R.drawable.music_off)
                PlayVSBotViewModel.info.isMusic = false
                sounds.offMusic()
            } else {
                imageMusic.setImageResource(R.drawable.music_on)
                PlayVSBotViewModel.info.isMusic = true
                if(PlayVSBotViewModel.info.isVolume)sounds.onMusic()
            }
        }
    }

    fun account(buttonAccount: Button, imageAccount: ImageView){
        buttonAccount.setOnClickListener {
            buttonAccount.visibility = View.VISIBLE
            imageAccount.visibility = View.VISIBLE
        }
    }

}