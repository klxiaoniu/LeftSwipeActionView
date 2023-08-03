package com.test.leftswipeactionview

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.test.leftswipeactionview.view.SwipeActionView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnRecover).setOnClickListener {
            findViewById<SwipeActionView>(R.id.sav1).recover()
            findViewById<SwipeActionView>(R.id.sav2).recover()
        }
    }
}