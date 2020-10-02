package com.swiftchat.chat

import android.content.Context
import android.content.res.ColorStateList
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.util.AttributeSet

class SwiftChatFAB : FloatingActionButton {

    private var userId: Long = -1

    private var domain: String? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        setOnClickListener {
            SwiftChatActivity.launch(
                context = context,
                userId = userId,
                domain = requireNotNull(domain)
            )
        }

        setImageResource(R.drawable.ic_action_message)

        backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.swift_chat_fab_color
            )
        )
    }

    fun setUserId(id: Long) {
        userId = id
    }

    fun setDomain(name: String) {
        this.domain = name
    }
}