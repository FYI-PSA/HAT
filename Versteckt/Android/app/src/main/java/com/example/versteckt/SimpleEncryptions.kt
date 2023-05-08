package com.example.versteckt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            /* Toast.makeText(activity, "OwO *notices your toast*", Toast.LENGTH_SHORT).show(); */
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}