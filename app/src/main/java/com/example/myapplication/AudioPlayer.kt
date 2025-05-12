package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class AudioPlayer : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var seekBar: SeekBar
    private lateinit var pause: Button
    private lateinit var next: Button
    private lateinit var prev: Button
    private lateinit var textview: TextView

    private val songs = listOf(
        R.raw.eminem_mockingbird,
        R.raw.kish_lesnik,
        R.raw.linkinpark_what_ive_done
    )
    private val songNames = listOf("Eminem - Mockingbird", "КиШ - Лесник", "Linkin Park - What ive done")
    private var currentSongIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private var isUserSeeking = false

    private val updateSeekBar = object : Runnable {
        override fun run() {
            if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying && !isUserSeeking) {
                seekBar.progress = mediaPlayer.currentPosition
            }
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        initViews()
        setupMediaPlayer()
        setupListeners()
    }

    private fun initViews() {
        seekBar = findViewById(R.id.seekBar)
        pause = findViewById(R.id.pause)
        next = findViewById(R.id.next)
        prev = findViewById(R.id.prev)
        textview = findViewById(R.id.textview)
    }

    private fun setupMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex])
        seekBar.max = mediaPlayer.duration
        textview.text = songNames[currentSongIndex]

        mediaPlayer.setOnCompletionListener {
            playNextSong()
        }
    }

    private fun setupListeners() {
        pause.setOnClickListener {
            togglePlayPause()
        }

        next.setOnClickListener {
            playNextSong()
        }

        prev.setOnClickListener {
            playPreviousSong()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && ::mediaPlayer.isInitialized) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isUserSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isUserSeeking = false
            }
        })
    }

    private fun togglePlayPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            pause.text = "Play"
            handler.removeCallbacks(updateSeekBar)
        } else {
            mediaPlayer.start()
            pause.text = "Pause"
            handler.post(updateSeekBar)
        }
    }

    private fun playNextSong() {
        currentSongIndex = (currentSongIndex + 1) % songs.size
        changeSong()
    }

    private fun playPreviousSong() {
        currentSongIndex = (currentSongIndex - 1 + songs.size) % songs.size
        changeSong()
    }

    private fun changeSong() {
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex])
        mediaPlayer.start()
        seekBar.max = mediaPlayer.duration
        textview.text = songNames[currentSongIndex]
        pause.text = "Pause"
        handler.post(updateSeekBar)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(updateSeekBar)
    }
}