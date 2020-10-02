package com.swift.chat.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

public class SwiftChatFAB extends FloatingActionButton {

    public SwiftChatFAB(Context context) {
        super(context);
        initView();
    }

    public SwiftChatFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SwiftChatFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    void initView() {
        setOnClickListener(v ->
                SwiftChatActivity.launch(getContext())
        );

        setImageResource(R.drawable.ic_action_message);

        setBackgroundTintList(
                ColorStateList.valueOf(
                        ContextCompat.getColor(
                                getContext(),
                                R.color.swift_chat_fab_color
                        )
                )
        );
    }
}
