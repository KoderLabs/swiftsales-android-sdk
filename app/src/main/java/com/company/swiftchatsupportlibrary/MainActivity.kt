package com.company.swiftchatsupportlibrary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.swift.chat.library.SwiftChatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            SwiftChatActivity.launch(this)
        }
    }
}