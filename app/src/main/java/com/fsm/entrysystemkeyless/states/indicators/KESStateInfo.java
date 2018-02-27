package com.fsm.entrysystemkeyless.states.indicators;

/**
 * Created by user on 24.02.18.
 */

public class KESStateInfo {

    private final KESAlarmStatus alarmStatus;
    private final KESLockStatus lockStatus;
    private String validationMsg;

    public KESStateInfo(KESAlarmStatus alarmStatus,
                        KESLockStatus lockStatus) {
        this.alarmStatus = alarmStatus;
        this.lockStatus = lockStatus;
    }

    public KESAlarmStatus getAlarmStatus() {
        return alarmStatus;
    }

    public KESLockStatus getLockStatus() {
        return lockStatus;
    }

    public void setValidationMsg(String validationMsg) {
        this.validationMsg = validationMsg;
    }

    public String getValidationMsg() {
        return validationMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KESStateInfo stateInfo = (KESStateInfo) o;

        if (alarmStatus != stateInfo.alarmStatus) return false;
        return lockStatus == stateInfo.lockStatus;
    }

    @Override
    public int hashCode() {
        int result = alarmStatus != null ? alarmStatus.hashCode() : 0;
        result = 31 * result + (lockStatus != null ? lockStatus.hashCode() : 0);
        return result;
    }
}
