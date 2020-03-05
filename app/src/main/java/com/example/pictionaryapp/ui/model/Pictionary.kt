package com.example.pictionaryapp.ui.model

data class Pictionary(val id:Int,
                      val imageUrl:String,
                      val difficulty:Int,
                      val answer:String)

data class PictinaryList(val pictList:List<Pictionary>)