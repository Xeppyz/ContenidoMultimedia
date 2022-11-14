package ni.edu.uca.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import ni.edu.uca.myapplication.databinding.FragmentFotoBinding
import ni.edu.uca.myapplication.databinding.FragmentVideoBinding


class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.vvVideo.setVideoURI(uri)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(layoutInflater)



        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "video/*"

            startActivityForResult(intent, 200)
        }

        binding.vvVideo.setOnPreparedListener {

            it.start()
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode ==200){
            if (data != null){
                val uri: Uri = data.data!!
                binding.vvVideo.setVideoURI(uri)

                val mediaController = MediaController(activity)
                mediaController.setAnchorView(binding.vvVideo)
                binding.vvVideo.setMediaController(mediaController)
                binding.vvVideo.requestFocus()
            }
            else{
                Toast.makeText(activity, "No tenes na", Toast.LENGTH_SHORT).show()
            }
        }
    }
}