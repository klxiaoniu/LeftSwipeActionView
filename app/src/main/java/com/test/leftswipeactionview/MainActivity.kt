package com.test.leftswipeactionview

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.leftswipeactionview.view.SwipeActionView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sav1=findViewById<SwipeActionView>(R.id.sav1)
        val sav2=findViewById<SwipeActionView>(R.id.sav2)
//        sav1.callback = object : SwipeActionView.Callback {
//            override fun onSwipeFinished() {
//                sav1.visibility = View.GONE
//            }
//        }
//        sav2.callback = object : SwipeActionView.Callback {
//            override fun onSwipeFinished() {
//                sav2.visibility = View.GONE
//            }
//        }
        sav1.callback = {
            sav1.visibility = View.GONE
        }
        sav2.callback = {
            sav2.visibility = View.GONE
        }
        val rv = findViewById<RecyclerView>(R.id.rv)
        val adapter = MyAdapter()
        rv.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager

        val data = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11","12","13","14","15","16","17","18","19","20")
        adapter.setData(data)

        findViewById<Button>(R.id.btnRecover).setOnClickListener {
            sav1.recover()
            sav2.recover()
            adapter.setData(data)
        }
    }
}