package com.example.versteckt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.versteckt.databinding.SimpleEncryptionsBinding

class SimpleEncryptions : Fragment() {
    private var _binding: SimpleEncryptionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = SimpleEncryptionsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.backToRSAButton).setOnClickListener()
        {
            findNavController().navigate(R.id.action_SimpleEncryptions_to_RSAMain)
        }
        view.findViewById<Button>(R.id.base2PageButton).setOnClickListener()
        {
            findNavController().navigate(R.id.action_SimpleEncryptions_to_SimpleEncryptionPage)
        }
        view.findViewById<Button>(R.id.base16PageButton).setOnClickListener()
        {
            findNavController().navigate(R.id.action_SimpleEncryptions_to_SimpleEncryptionPage)
        }
        view.findViewById<Button>(R.id.base64PageButton).setOnClickListener()
        {
            findNavController().navigate(R.id.action_SimpleEncryptions_to_SimpleEncryptionPage)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}