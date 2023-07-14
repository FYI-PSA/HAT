package com.example.versteckt

class Communicator
{
    companion object
    {
        private var simpleEncHeaderName = ""
        private var publicKeyB64Value = ""
        fun setSimpleEncHeaderName(newText: String)
        {
            simpleEncHeaderName = newText
        }
        fun getSimpleEncHeaderName(): String
        {
            return simpleEncHeaderName
        }
        fun setPublicKey(publicKeyB64String: String)
        {
            publicKeyB64Value = publicKeyB64String
        }
        fun getPublicKey(): String
        {
            return publicKeyB64Value
        }
    }
}