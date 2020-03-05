package com.example.pictionaryapp.ui.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pictionaryapp.ui.model.PictinaryList
import com.example.pictionaryapp.ui.model.Pictionary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.nio.charset.Charset

class PictionaryViewModel(val activity:Activity){
    var mPictinaryList = MutableLiveData<List<Pictionary>>()



    fun loadJsonFromAsset():String{
        var json :String? = null
        val input = activity.applicationContext.assets.open("pictionary.json")
        val size = input.available()
        val buffer= ByteArray(size)
        input.read(buffer)
        input.close()
        json = String(buffer, Charset.forName("UTF-8"))
        return json

    }

    fun getPictionaryData() {
        val json = loadJsonFromAsset()


        var pictionaryList = Gson().fromJson<List<Pictionary>>(json,object :TypeToken<List<Pictionary>>(){}.type)
        Log.d("ViewMode","data:"+pictionaryList)
        mPictinaryList.value = pictionaryList


    }
}
