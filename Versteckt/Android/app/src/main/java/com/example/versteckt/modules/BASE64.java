package com.example.versteckt.modules;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.Base64;

public class BASE64 
{

    @RequiresApi(api = Build.VERSION_CODES.S)
    public static String encryptData(byte[] data)
    {
        return Base64.getEncoder().encodeToString(data);
    }


    @RequiresApi(api = Build.VERSION_CODES.S)
    public static byte[] decryptData(String encryptedData)
    {
        return Base64.getDecoder().decode(encryptedData);
    }
}
