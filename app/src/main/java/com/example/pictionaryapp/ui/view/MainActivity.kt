package com.example.pictionaryapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pictionaryapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                navigateToRound()
            }

        })
    }

    private fun navigateToRound() {
        val intent = Intent(this,PictionaryActivity::class.java)
        startActivity(intent)
    }


}
