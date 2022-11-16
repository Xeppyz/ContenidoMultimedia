package ni.edu.uca.myapplication

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.*
import androidx.core.content.ContextCompat
import ni.edu.uca.myapplication.databinding.FragmentFotoBinding

class FotoFragment : Fragment() {
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.imgFoto.setImageURI(uri)
        }
    }

    private lateinit var binding: FragmentFotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            inicioFoto()
        }


    }
    private fun inicioFoto(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(
                    binding.button.context, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED->{
                    lanzarFoto()
                }
                   else -> requestPermissionLaucher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            lanzarFoto()

        }


    }
    private val requestPermissionLaucher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted ->
        if (isGranted){
            lanzarFoto()
        }else{
            Toast.makeText(context, "Habilitar los permisos", Toast.LENGTH_SHORT).show()
        }

    }

    private fun lanzarFoto(){
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }



}