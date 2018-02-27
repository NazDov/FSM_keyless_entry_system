package com.fsm.entrysystemkeyless.states;


import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;

public abstract class KESState {

    private KESStateInfo stateInfo;

    public KESState(KESStateInfo stateInfo) {
        checkNotNull(stateInfo, "no stateInfo provided");
        this.stateInfo = stateInfo;
    }

    void checkNotNull(Object stateInfo, String errorMsg) {
        if (stateInfo == null) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public KESStateInfo getStateInfo() {
        return stateInfo;
    }

}
