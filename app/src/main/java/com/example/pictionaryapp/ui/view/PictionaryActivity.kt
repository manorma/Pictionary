package com.example.pictionaryapp.ui.view

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.pictionaryapp.R
import com.example.pictionaryapp.ui.model.PictinaryList
import com.example.pictionaryapp.ui.model.Pictionary
import com.example.pictionaryapp.ui.viewmodel.PictionaryViewModel
import kotlinx.android.synthetic.main.activity_pictionary.*
import kotlin.random.Random

class PictionaryActivity : AppCompatActivity() {

    lateinit var viewModel: PictionaryViewModel
    var pictinaryList: List<Pictionary>? = null
    var currentPictionary: Pictionary? = null
    var countCorrect = 0
    var currentDifficulty =3
    var round = 1;
    lateinit var timer :CountDownTimer
    var visited = HashMap<Int,ArrayList<Int>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pictionary)
        viewModel = PictionaryViewModel(this)
        viewModel.getPictionaryData()
        initSubscriber()
        initTimer()
        btn_submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val lanswer = answer.text.toString()
                answer.text.clear()
                timer.cancel()
                if (lanswer.toUpperCase().equals(currentPictionary?.answer)) {
                    countCorrect++;
                    if(currentDifficulty < 5 ) {
                        currentDifficulty++;
                    }
                    updateUI(currentDifficulty)

                }
                else{
                    currentDifficulty--
                    updateUI(currentDifficulty)
                }

            }

        })

    }

    private fun initSubscriber() {
        viewModel.mPictinaryList.observe(this, Observer {
            pictinaryList = it
            updateUI(currentDifficulty)
        })
    }

    private fun updateUI(difficulty: Int) {
        Log.d("PictionaryActivity", "updateUI"+difficulty)
        if(round <= 5) {
            tvRound.text = "Round "+round +"/5"
            val pictionary = getPictionaryWithDifficulty(difficulty)
            pictionary?.let {
                loadImage(pictionary?.imageUrl!!)
                var lid = visited.get(difficulty)
                if(lid != null) {
                    lid.add(pictionary?.id)
                    visited.put(difficulty, lid)
                }
                else{
                    visited.put(difficulty, arrayListOf(pictionary?.id))
                }
            }
            timer.start()
            round++

        }
        else{
            showCountView()
            Toast.makeText(this,"Total correct answer : $countCorrect",Toast.LENGTH_SHORT).show()
        }
        //startTimer()
    }

    private fun initTimer() {
       timer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.setText((millisUntilFinished/1000).toString())

            }

            override fun onFinish() {
                updateUI(currentDifficulty)
            }
        }
    }

    fun showCountView(){
        tvCount.visibility = View.VISIBLE
        tvCount.setText("Total correct answer : "+countCorrect+"/5")
        mainContainer.visibility = View.GONE
    }

    fun getPictionaryWithDifficulty(difficulty: Int): Pictionary? {
        Log.d("PictionaryActivity", "getPictionaryWithDifficulty")
        var random = Random.nextInt(1, 5)
        Log.d("PictionaryActivity", "visite " +visited)
        pictinaryList?.forEach {
            if (it.difficulty == difficulty && (!visited.contains(difficulty) || (visited.contains(difficulty) && !visited.get(difficulty)!!.contains(it.id)))) {
                currentPictionary = it
                Log.d("PictionaryActivity", "getPictionaryWithDifficulty" +currentPictionary)
                return it
            }
        }
        return null
    }

    fun loadImage(url: String) {
        Log.d("PictionaryActivity", "loadImage" +url)
        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(iv_pic_image)

    }


}