package com.fsm.entrysystemkeyless.ui.view.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.fsm.entrysystemkeyless.R;


public class AlarmIndicatorView extends AppCompatTextView {

    public AlarmIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setArmed(boolean isArmed) {
        if (isArmed) {
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.armedIndicatorColor));
            setText(R.string.armed_indicator_tag);
        } else {
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.disarmedIndicatorColor));
            setText(R.string.disarmed_indicator_tag);
        }
    }
}
