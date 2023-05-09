package com.example.versteckt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.versteckt.databinding.MainRsaPageBinding

class RSAMain : Fragment() 
{
    private var _binding: MainRsaPageBinding? = null

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
        _binding = MainRsaPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        view.findViewById<Button>(R.id.goToSimpleEncryptionsButton).setOnClickListener()
        {
            findNavController().navigate(R.id.action_RSAMain_to_SimpleEncryptions)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() 
    {
        super.onDestroyView()
        _binding = null
    }
}