package com.example.versteckt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.versteckt.databinding.SimpleEncryptionsBinding

class SimpleEncryptions : Fragment()
{
    private var _binding: SimpleEncryptionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView
    (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )
        : View
    {
        _binding = SimpleEncryptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setSimpleEncryptionHeader(newText: String)
    {
        Communicator.setSimpleEncHeaderName(newText);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        view.findViewById<Button>(R.id.backToRSAButton)?.setOnClickListener()
        {
            findNavController().navigate(R.id.action_SimpleEncryptions_to_RSAMain)
        }

        view.findViewById<Button>(R.id.base2PageButton)?.setOnClickListener()
        {
            val headerText = getString(R.string.simple_encryption_binary_header)
            setSimpleEncryptionHeader(headerText)
            findNavController().navigate(R.id.action_SimpleEncryptions_to_SimpleEncryptionPage)
        }
        view.findViewById<Button>(R.id.base16PageButton)?.setOnClickListener()
        {
            val headerText = getString(R.string.simple_encryption_hexadecimal_header)
            setSimpleEncryptionHeader(headerText)
            findNavController().navigate(R.id.action_SimpleEncryptions_to_SimpleEncryptionPage)
        }
        view.findViewById<Button>(R.id.base64PageButton)?.setOnClickListener()
        {
            val headerText = getString(R.string.simple_encryption_base64_header)
            setSimpleEncryptionHeader(headerText)
            findNavController().navigate(R.id.action_SimpleEncryptions_to_SimpleEncryptionPage)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}