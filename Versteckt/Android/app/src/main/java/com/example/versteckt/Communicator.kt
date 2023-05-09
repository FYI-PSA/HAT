package com.example.versteckt

class Communicator
{
    companion object
    {
        private var simpleEncHeaderName = ""
        fun setSimpleEncHeaderName(newText: String)
        {
            simpleEncHeaderName = newText
        }
        fun getSimpleEncHeaderName(): String
        {
            return simpleEncHeaderName
        }
    }
}