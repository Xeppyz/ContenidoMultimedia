package ni.edu.uca.myapplication

import android.media.MediaPlayer
import ni.edu.uca.myapplication.MyMediaPlayer

object MyMediaPlayer {
    private var instance: MediaPlayer? = null
    @JvmName("getInstance1")
    fun getInstance(): MediaPlayer? {
        if (instance == null) {
            instance = MediaPlayer()
        }
        return instance
    }

    var currentIndex = -1
}