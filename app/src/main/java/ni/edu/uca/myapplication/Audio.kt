package ni.edu.uca.myapplication

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import ni.edu.uca.myapplication.AudioModel
import android.os.Bundle
import ni.edu.uca.myapplication.R
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.myapplication.MusicListAdapter
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import android.widget.Toast
import ni.edu.uca.myapplication.databinding.ActivityAudioBinding
import java.io.File
import java.util.ArrayList

class Audio : AppCompatActivity() {
    private lateinit var binding: ActivityAudioBinding
    var recyclerView: RecyclerView? = null
    private var noMusicTextView: TextView? = null
    var songsList = ArrayList<AudioModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = findViewById(R.id.recycle_view)
        noMusicTextView = findViewById(R.id.no_songs_text)
        if (!checkPermission()) {
            requestPermission()
            return
        }
        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
        )
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null
        )
        while (cursor!!.moveToNext()) {
            val songData = AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2))
            if (File(songData.path).exists()) songsList.add(songData)
        }
        if (songsList.size == 0) {
            binding.noSongsText.visibility = View.VISIBLE
        } else {
            //recyclerview
            binding.recycleView.layoutManager = LinearLayoutManager(this)
            binding.recycleView.adapter = MusicListAdapter(songsList, applicationContext)
        }
    }

    fun checkPermission(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(this@Audio, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@Audio,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this@Audio,
                "READ PERMISSION IS REQUIRED,PLEASE ALLOW FROM SETTINGS",
                Toast.LENGTH_SHORT
            ).show()
        } else ActivityCompat.requestPermissions(
            this@Audio,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            123
        )
    }

    override fun onResume() {
        super.onResume()
        if (recyclerView != null) {
            recyclerView!!.adapter = MusicListAdapter(songsList, applicationContext)
        }
    }
}