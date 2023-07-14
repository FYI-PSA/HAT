package com.example.versteckt.modules;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.Base64;

@RequiresApi(api = Build.VERSION_CODES.S)
public class BASE64
{
    public static byte[] encryptData(byte[] data)
    {
        return Base64.getEncoder().encode(data);
    }


    public static byte[] decryptData(byte[] encryptedData)
    {
        return Base64.getDecoder().decode(encryptedData);
    }
}
