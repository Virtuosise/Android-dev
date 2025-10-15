package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
    private val songname = listOf("Eminem - Mockingbird", "КиШ - Лесник", "Linkin Park - What ive done")
    private var index = 0
    private val handler = Handler(Looper.getMainLooper())
    private var flag = false

    private val upSB = object : Runnable {
        override fun run() {
            if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying && !flag) {
                seekBar.progress = mediaPlayer.currentPosition
            }
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        seekBar = findViewById(R.id.seekBar)
        pause = findViewById(R.id.pause)
        next = findViewById(R.id.next)
        prev = findViewById(R.id.prev)
        textview = findViewById(R.id.textview)

        setupMP()
        Click()
    }





    private fun setupMP() {
        mediaPlayer = MediaPlayer.create(this, songs[index])
        seekBar.max = mediaPlayer.duration
        textview.text = songname[index]

        mediaPlayer.setOnCompletionListener {
            NextSong()
        }
    }

    private fun Click() {
        pause.setOnClickListener {
            PlayPause()
        }

        next.setOnClickListener {
            NextSong()
        }

        prev.setOnClickListener {
            PrevSong()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                flag = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                flag = false
            }
        })
    }

    private fun PlayPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            pause.text = "Play"
            handler.removeCallbacks(upSB)
        } else {
            mediaPlayer.start()
            pause.text = "Pause"
            handler.post(upSB)
        }
    }

    private fun NextSong() {
        index = (index + 1) % songs.size
        changeSong()
    }

    private fun PrevSong() {
        index = (index - 1 + songs.size) % songs.size
        changeSong()
    }

    private fun changeSong() {
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(this, songs[index])
        seekBar.max = mediaPlayer.duration
        textview.text = songname[index]
        handler.post(upSB)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(upSB)
    }
}