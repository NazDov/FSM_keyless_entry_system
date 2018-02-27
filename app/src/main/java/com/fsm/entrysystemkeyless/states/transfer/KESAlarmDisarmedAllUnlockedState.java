package com.fsm.entrysystemkeyless.states.transfer;


import com.fsm.entrysystemkeyless.action.KESActionType;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;
import com.fsm.entrysystemkeyless.states.KESTransferState;
import com.fsm.entrysystemkeyless.states.transfer.util.KESStatesCache;

import java.util.EnumMap;
import java.util.Map;

import static com.fsm.entrysystemkeyless.action.KESActionType.*;
import static com.fsm.entrysystemkeyless.states.indicators.KESAlarmStatus.DISARMED;
import static com.fsm.entrysystemkeyless.states.indicators.KESLockStatus.ALL_UNLOCKED;

public class KESAlarmDisarmedAllUnlockedState extends KESTransferState {


    KESAlarmDisarmedAllUnlockedState() {
        super(new KESStateInfo(DISARMED, ALL_UNLOCKED), LOCK);
    }

    public KESAlarmDisarmedAllUnlockedState(KESStatesCache cache) {
        super(new KESStateInfo(DISARMED, ALL_UNLOCKED), LOCK);
        this.cache = cache;
    }

    KESAlarmDisarmedAllUnlockedState(KESActionType actionType) {
        super(new KESStateInfo(DISARMED, ALL_UNLOCKED), actionType);
    }

    @Override
    protected Map<KESActionType, KESTransferState> configureOutputStates() {
        Map<KESActionType, KESTransferState> outputStates = new EnumMap<>(KESActionType.class);
        outputStates.put(LOCK, load(KESAlarmDisarmedAllLockedState.class, cache, LOCK));
        outputStates.put(UNLOCK, load(this.getClass(), cache, UNLOCK));
        outputStates.put(LOCK2X, load(KESAlarmArmedAllLockedState.class, cache, LOCK2X));
        outputStates.put(UNLOCK2X, load(this.getClass(), cache, UNLOCK2X));
        return outputStates;
    }

    @Override
    public KESTransferState getOutputState(KESActionType actionType) {
        return super.getOutputState(actionType);
    }
}
