package com.fsm.entrysystemkeyless.states.transfer;

import com.fsm.entrysystemkeyless.action.KESActionType;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;
import com.fsm.entrysystemkeyless.states.KESTransferState;
import com.fsm.entrysystemkeyless.states.transfer.util.KESStatesCache;

import java.util.EnumMap;
import java.util.Map;

import static com.fsm.entrysystemkeyless.action.KESActionType.*;
import static com.fsm.entrysystemkeyless.states.indicators.KESAlarmStatus.DISARMED;
import static com.fsm.entrysystemkeyless.states.indicators.KESLockStatus.ALL_LOCKED;

/**
 *
 */
public class KESAlarmDisarmedAllLockedState extends KESTransferState {


    KESAlarmDisarmedAllLockedState(KESActionType actionType) {
        super(new KESStateInfo(DISARMED, ALL_LOCKED), actionType);
    }

    KESAlarmDisarmedAllLockedState() {
        super(new KESStateInfo(DISARMED, ALL_LOCKED), LOCK);
    }

    public KESAlarmDisarmedAllLockedState(KESStatesCache cache) {
        super(new KESStateInfo(DISARMED, ALL_LOCKED), LOCK);
        this.cache = cache;
    }

    @Override
    protected Map<KESActionType, KESTransferState> configureOutputStates() {
        Map<KESActionType, KESTransferState> outputStates = new EnumMap<>(KESActionType.class);
        outputStates.put(LOCK, load(this.getClass(), cache, LOCK));
        outputStates.put(UNLOCK, load(KESAlarmDisarmedDriverUnlockedState.class, cache, UNLOCK));
        outputStates.put(LOCK2X, load(KESAlarmArmedAllLockedState.class, cache, LOCK2X));
        outputStates.put(UNLOCK2X, load(KESAlarmDisarmedAllUnlockedState.class, cache, UNLOCK2X));
        return outputStates;
    }

    @Override
    public KESTransferState getOutputState(KESActionType actionType) {
        return super.getOutputState(actionType);
    }
}
