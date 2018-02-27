package com.fsm.entrysystemkeyless.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fsm.entrysystemkeyless.R;
import com.fsm.entrysystemkeyless.action.KESActionType;
import com.fsm.entrysystemkeyless.action.invoker.KESActionInvoker;
import com.fsm.entrysystemkeyless.action.invoker.impl.KESActionInvokerImpl;
import com.fsm.entrysystemkeyless.states.indicators.KESAlarmStatus;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;
import com.fsm.entrysystemkeyless.ui.view.custom.AlarmIndicatorView;

import static com.fsm.entrysystemkeyless.app.KESApplication.DEF_STATE_NAME;
import static com.fsm.entrysystemkeyless.states.transfer.util.KESStatesCache.INSTANCE;


public class KESActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KES_ALARM_STAT = "KES_ALARM_STAT";
    private static final String KES_VALID_MSG = "KES_VALID_MSG";
    private static final String KES_ACTION_TYPE = "KES_ACTION_TYPE";
    private static final String ACTIVE_BUTTON_ID = "ACTIVE_BUTTON_ID";
    private static final String TAG = "KESActivity";

    Button lockButton;
    Button unLockButton;
    Button lock2xButton;
    Button unlock2xButton;
    private TextView verificationTextView;
    private AlarmIndicatorView alarmIndicatorView;

    private SparseBooleanArray btnStates;
    private View[] btnViews;

    private KESActionInvoker kesActionInvoker;

    private KESAlarmStatus currStateAlarmStat;
    private KESActionType currStateActionType;
    private String currStateValidationMsg;
    private int activeBtnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        initializeKeyEntrySystem();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KES_VALID_MSG, currStateValidationMsg);
        outState.putString(KES_ALARM_STAT, currStateAlarmStat.getName());
        outState.putString(KES_ACTION_TYPE, currStateActionType.name());
        outState.putInt(ACTIVE_BUTTON_ID, activeBtnId);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currStateAlarmStat = KESAlarmStatus.valueOf(
                savedInstanceState.getString(KES_ALARM_STAT));
        currStateValidationMsg = savedInstanceState.getString(KES_VALID_MSG);
        currStateActionType = KESActionType.valueOf(
                savedInstanceState.getString(KES_ACTION_TYPE));
        activeBtnId = savedInstanceState.getInt(ACTIVE_BUTTON_ID);
        btnStates.put(activeBtnId, true);
        updateViewStateInfo();

    }

    private void initializeView() {
        lockButton = findViewById(R.id.lock);
        unLockButton = findViewById(R.id.unlock);
        lock2xButton = findViewById(R.id.lock2x);
        unlock2xButton = findViewById(R.id.unlock2x);
        alarmIndicatorView = findViewById(R.id.indicator);
        verificationTextView = findViewById(R.id.verification);

        btnStates = new SparseBooleanArray();
        btnStates.put(R.id.lock, false);
        btnStates.put(R.id.unlock, false);
        btnStates.put(R.id.lock2x, false);
        btnStates.put(R.id.unlock2x, false);

        btnViews = new View[]{lockButton, unLockButton, lock2xButton, unlock2xButton};

        lockButton.setOnClickListener(this);
        unLockButton.setOnClickListener(this);
        lock2xButton.setOnClickListener(this);
        unlock2xButton.setOnClickListener(this);
    }

    private void initializeKeyEntrySystem() {
        kesActionInvoker = KESActionInvokerImpl.initializeWithCurrStateFromCache(
                INSTANCE, DEF_STATE_NAME);
        currStateActionType = kesActionInvoker.getCurrStateActionType();
        KESStateInfo currStateInfo = kesActionInvoker.getCurrStateInfo();
        updateViewStateInfo(currStateInfo);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.lock:
                Log.d(TAG, "click invokeLock");
                makeActiveButtonView(v);
                updateViewStateInfo(kesActionInvoker.invokeLock());
                break;
            case R.id.unlock:
                Log.d(TAG, "click invokeUnlock");
                makeActiveButtonView(v);
                updateViewStateInfo(kesActionInvoker.invokeUnlock());
                break;
            case R.id.lock2x:
                Log.d(TAG, "click invokeLock2x");
                makeActiveButtonView(v);
                updateViewStateInfo(kesActionInvoker.invokeLock2x());
                break;
            case R.id.unlock2x:
                Log.d(TAG, "click invokeUnlock2x");
                makeActiveButtonView(v);
                updateViewStateInfo(kesActionInvoker.invokeUnlock2x());
                break;
        }
    }

    private void makeActiveButtonView(View buttonView) {
        int buttonId = buttonView.getId();
        boolean isActiveState = btnStates.get(buttonId);
        if (!isActiveState) {
            changeButtonViewState(buttonView, true, R.drawable.btn_wrapper_selected);
            makeAllOtherButtonsInactive();
        }
    }

    private void changeButtonViewState(View buttonView,
                                       boolean state,
                                       int resID) {
        int buttonId = buttonView.getId();
        btnStates.put(buttonId, state);
        buttonView.setBackgroundResource(resID);
        if (state) {
            activeBtnId = buttonId;
        }
    }

    private void makeAllOtherButtonsInactive() {
        for (View btnView : btnViews) {
            int id = btnView.getId();
            if (id != activeBtnId) {
                changeButtonViewState(btnView, false, R.drawable.btn_wrapper);
            }
        }
    }

    private void updateViewStateInfo(KESStateInfo currStateInfo) {
        currStateAlarmStat = currStateInfo.getAlarmStatus();
        currStateValidationMsg = currStateInfo.getValidationMsg();
        updateViewStateInfo();
    }

    private void updateViewStateInfo() {
        alarmIndicatorView.setArmed(currStateAlarmStat == KESAlarmStatus.ARMED);
        verificationTextView.setText(currStateValidationMsg);
        Log.d(TAG, "alarmState: " + currStateAlarmStat.getName());
        Log.d(TAG, "lockState: " + currStateValidationMsg);
    }

}
