package ni.edu.uca.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.SeekBar
import ni.edu.uca.myapplication.AudioModel
import android.media.MediaPlayer
import ni.edu.uca.myapplication.MyMediaPlayer
import android.os.Bundle
import ni.edu.uca.myapplication.R
import ni.edu.uca.myapplication.MusicPlayerActivity
import android.widget.SeekBar.OnSeekBarChangeListener
import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import android.widget.ImageView
import ni.edu.uca.myapplication.databinding.ActivityAudioBinding
import ni.edu.uca.myapplication.databinding.ActivityMusicPlayerBinding
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class MusicPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMusicPlayerBinding
    var titleTv: TextView? = null
    var currentTimeTv: TextView? = null
    var totalTimeTv: TextView? = null
    var seekBar: SeekBar? = null
    var pausePlay: ImageView? = null
    var nextBtn: ImageView? = null
    var previousBtn: ImageView? = null
    var musicIcon: ImageView? = null
    var songsList: ArrayList<AudioModel>? = null
    var currentSong: AudioModel? = null
    var mediaPlayer = MyMediaPlayer.getInstance()
    var x = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_music_player)
        titleTv = findViewById(R.id.song_title)
        currentTimeTv = findViewById(R.id.current_time)
        totalTimeTv = findViewById(R.id.total_time)
        seekBar = findViewById(R.id.seek_bar)
        pausePlay = findViewById(R.id.pause_play)
        nextBtn = findViewById(R.id.next)
        previousBtn = findViewById(R.id.previous)
        musicIcon = findViewById(R.id.music_icon_big)
        binding.songTitle.isSelected = true
        songsList = intent.getSerializableExtra("LIST") as ArrayList<AudioModel>?
        setResourcesWithMusic()
        runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    binding.seekBar.progress = mediaPlayer!!.currentPosition
                    binding.currentTime.text = convertToMMSS(mediaPlayer!!.currentPosition.toString() + "")
                    if (mediaPlayer!!.isPlaying) {
                        binding.pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                        binding.musicIconBig.rotation = x++.toFloat()
                    } else {
                        binding.pausePlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                        binding.musicIconBig.rotation = 0f
                    }
                }
                Handler().postDelayed(this, 100)
            }
        })
        binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    fun setResourcesWithMusic() {
        currentSong = songsList!![MyMediaPlayer.currentIndex]
        titleTv!!.text = currentSong!!.title
        totalTimeTv!!.text = convertToMMSS(currentSong!!.duration)
        pausePlay!!.setOnClickListener { v: View? -> pausePlay() }
        nextBtn!!.setOnClickListener { v: View? -> playNextSong() }
        previousBtn!!.setOnClickListener { v: View? -> playPreviousSong() }
        playMusic()
    }

    private fun playMusic() {
        mediaPlayer!!.reset()
        try {
            mediaPlayer!!.setDataSource(currentSong!!.path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            seekBar!!.progress = 0
            seekBar!!.max = mediaPlayer!!.duration
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun playNextSong() {
        if (MyMediaPlayer.currentIndex == songsList!!.size - 1) return
        MyMediaPlayer.currentIndex += 1
        mediaPlayer!!.reset()
        setResourcesWithMusic()
    }

    private fun playPreviousSong() {
        if (MyMediaPlayer.currentIndex == 0) return
        MyMediaPlayer.currentIndex -= 1
        mediaPlayer!!.reset()
        setResourcesWithMusic()
    }

    private fun pausePlay() {
        if (mediaPlayer!!.isPlaying) mediaPlayer!!.pause() else mediaPlayer!!.start()
    }

    companion object {
        @SuppressLint("DefaultLocale")
        fun convertToMMSS(duration: String): String {
            val millis = duration.toLong()
            return String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
            )
        }
    }
}