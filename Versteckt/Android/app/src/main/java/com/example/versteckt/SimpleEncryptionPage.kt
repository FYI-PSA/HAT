package com.example.versteckt

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.versteckt.databinding.SimpleEncryptionPageBinding
import com.example.versteckt.modules.*

class SimpleEncryptionPage : Fragment()
{
    private var _binding: SimpleEncryptionPageBinding? = null

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
        _binding = SimpleEncryptionPageBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun getSimpleEncryptionHeader(): String
    {
        return Communicator.getSimpleEncHeaderName()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        var headerName = getSimpleEncryptionHeader();

        view.findViewById<TextView>(R.id.simpleEncryptionPageHeader)?.text = headerName;
        view.findViewById<Button>(R.id.backToSimpleEncryptionsPageButton)?.setOnClickListener()
        {
            findNavController().navigate(R.id.action_SimpleEncryptionPage_to_SimpleEncryptions)
        }

        view.findViewById<Button>(R.id.simpleEncryptButton)?.setOnClickListener()
        {
            val textEnterField = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.simpleEncryptTextEnter)
            val currentData = textEnterField.text.toString();
            var encryptedData = "";
            if (headerName == ((getString(R.string.simple_encryption_binary_header)).toString()))
            {
                encryptedData = BASE2.dataStringToBinaryString(currentData, " ");
            }
            else
            {
                encryptedData = currentData;
            }
            textEnterField.setText(encryptedData);
        }

        view.findViewById<Button>(R.id.simpleDecryptButton)?.setOnClickListener()
        {
            val textEnterField = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.simpleEncryptTextEnter)
            val encryptedData = textEnterField.text.toString();
            var data = "";
            if (headerName == ((getString(R.string.simple_encryption_binary_header)).toString()))
            {
                data = BASE2.binaryStringToDataString(encryptedData, " ");
            }
            else
            {
                data = encryptedData;
            }
            textEnterField.setText(data);
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}