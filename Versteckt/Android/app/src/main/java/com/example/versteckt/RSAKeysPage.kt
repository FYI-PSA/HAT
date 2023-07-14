package com.example.versteckt

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.versteckt.databinding.RsaKeysPageBinding
import com.example.versteckt.modules.BASE64
import com.example.versteckt.modules.RSA


@RequiresApi(Build.VERSION_CODES.S)
class RSAKeysPage : Fragment()
{

    private var _binding: RsaKeysPageBinding? = null

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
        _binding = RsaKeysPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var publicKeyB64String:String = "";

    private fun readPublicKey()
    {
        val keys = RSA.readKeyPairAndroidFile("PUBLIC.KEY", "PRIVATE.KEY", context);
        publicKeyB64String = if (keys == null) { "" } else { (String(BASE64.encryptData(keys.public.encoded))) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        readPublicKey()
        view.findViewById<Button>(R.id.backToRSAButton_FromKeys)?.setOnClickListener()
        {
            findNavController().navigate(R.id.action_RSAKeys_to_RSAMain)
        }
        view.findViewById<Button>(R.id.generateNewPairButton)?.setOnClickListener()
        {
            RSA.generateKeyPairToAndroidFile("PUBLIC.KEY", "PRIVATE.KEY", context);
            readPublicKey()
            Toast.makeText(context, getString(R.string.rsa_keys_generated_key_toast), Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.copyPublicKeyButton)?.setOnClickListener()
        {
            readPublicKey()
            if (publicKeyB64String == "")
            {
                Toast.makeText(context, getString(R.string.rsa_keys_no_keys_toast), Toast.LENGTH_LONG).show()
            }
            else
            {
                (activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(ClipData.newPlainText(getString(R.string.copied_label), publicKeyB64String))
                Toast.makeText(context, getString(R.string.copied_label), Toast.LENGTH_SHORT).show()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}