package com.fsm.entrysystemkeyless.states.indicators;

/**
 * Created by user on 24.02.18.
 */

public enum KESAlarmStatus {
    ARMED("ARMED"),
    DISARMED("DISARMED");

    private String name;

    KESAlarmStatus(String status) {
        this.name = status;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
