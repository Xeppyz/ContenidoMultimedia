package ni.edu.uca.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import ni.edu.uca.myapplication.databinding.FragmentFotoBinding
import ni.edu.uca.myapplication.databinding.FragmentInicioBinding


class InicioFragment : Fragment() {

private lateinit var binding: FragmentInicioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInicioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFoto.setOnClickListener {

            it.findNavController().navigate(R.id.action_inicioFragment_to_fotoFragment)
        }

        binding.btnVideo.setOnClickListener {

            it.findNavController().navigate(R.id.action_inicioFragment_to_videoFragment)
        }
    }


}