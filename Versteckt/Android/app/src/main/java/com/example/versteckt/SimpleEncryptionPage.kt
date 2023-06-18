package com.example.versteckt

import android.content.ClipData
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        var headerName = getSimpleEncryptionHeader()

        val binaryText = (getString(R.string.simple_encryption_binary_header)).toString()
        val hexadecimalText = (getString(R.string.simple_encryption_hexadecimal_header)).toString()
        val base64Text = (getString(R.string.simple_encryption_base64_header)).toString()

        view.findViewById<TextView>(R.id.simpleEncryptionPageHeader)?.text = headerName
        view.findViewById<Button>(R.id.backToSimpleEncryptionsPageButton)?.setOnClickListener()
        {
            findNavController().navigate(R.id.action_SimpleEncryptionPage_to_SimpleEncryptions)
        }

        view.findViewById<Button>(R.id.simpleEncryptButton)?.setOnClickListener()
        {
            val textEnterField = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.simpleEncryptTextEnter)
            val currentData = textEnterField.text.toString()
            var newData = "";
            if (headerName == binaryText)
            {
                newData = DataHandler.textToBinary(currentData, " ")
            }
            else if (headerName == hexadecimalText)
            {
                newData = DataHandler.textToHex(currentData, " ", false)
            }
            else if (headerName == base64Text)
            {
                newData = DataHandler.textToBase64(currentData);
            }
            else
            {
                newData = currentData
            }
            textEnterField.setText(newData)
        }
        view.findViewById<Button>(R.id.deleteContentsButton)?.setOnClickListener()
        {
            val textEnterField = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.simpleEncryptTextEnter)
            textEnterField.setText("");
        }
        view.findViewById<Button>(R.id.copyContentsButton)?.setOnClickListener()
        {
            val text = (view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.simpleEncryptTextEnter)).getText()
            val clipdata = ClipData.newPlainText(getString(R.string.copied_label), text)
            (activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(clipdata);
         }



        view.findViewById<Button>(R.id.simpleDecryptButton)?.setOnClickListener()
        {
            val textEnterField = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.simpleEncryptTextEnter)
            val currentData = textEnterField.text.toString()
            var newData = "";
            if (headerName == (binaryText))
            {
                newData = DataHandler.binaryToText(currentData, " ")
            }
            else if (headerName == (hexadecimalText))
            {
                newData = DataHandler.hexToText(currentData, " ")
            }
            else if (headerName == (base64Text))
            {
                newData = DataHandler.base64ToText(currentData)
            }
            else
            {
                newData = currentData
            }
            textEnterField.setText(newData)
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}