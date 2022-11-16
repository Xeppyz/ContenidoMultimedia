package ni.edu.uca.myapplication

import ni.edu.uca.myapplication.AudioModel
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import ni.edu.uca.myapplication.R
import android.annotation.SuppressLint
import android.content.Context
import ni.edu.uca.myapplication.MyMediaPlayer
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import ni.edu.uca.myapplication.MusicPlayerActivity
import android.widget.TextView
import java.util.ArrayList

class MusicListAdapter(var songsList: ArrayList<AudioModel>, var context: Context) :
    RecyclerView.Adapter<MusicListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val songData = songsList[position]
        holder.titleTextView.text = songData.title
        if (MyMediaPlayer.currentIndex == position) {
            holder.titleTextView.setTextColor(Color.parseColor("#FF0000"))
        } else {
            holder.titleTextView.setTextColor(Color.parseColor("#000000"))
        }
        holder.itemView.setOnClickListener { //navigate to another acitivty
            MyMediaPlayer.getInstance()?.reset()
            MyMediaPlayer.currentIndex = position
            val intent = Intent(context, MusicPlayerActivity::class.java)
            intent.putExtra("LIST", songsList)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return songsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView
        var iconImageView: ImageView

        init {
            titleTextView = itemView.findViewById(R.id.music_title_text)
            iconImageView = itemView.findViewById(R.id.icon_view)
        }
    }
}