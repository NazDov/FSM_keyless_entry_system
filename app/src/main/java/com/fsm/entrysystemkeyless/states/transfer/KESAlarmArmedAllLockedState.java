package com.fsm.entrysystemkeyless.states.transfer;

import com.fsm.entrysystemkeyless.action.KESActionType;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;
import com.fsm.entrysystemkeyless.states.KESTransferState;
import com.fsm.entrysystemkeyless.states.transfer.util.KESStatesCache;

import java.util.EnumMap;
import java.util.Map;

import static com.fsm.entrysystemkeyless.action.KESActionType.*;
import static com.fsm.entrysystemkeyless.states.indicators.KESAlarmStatus.ARMED;
import static com.fsm.entrysystemkeyless.states.indicators.KESLockStatus.ALL_LOCKED;

/**
 *
 */
public class KESAlarmArmedAllLockedState extends KESTransferState {

    KESAlarmArmedAllLockedState(KESActionType actionType) {
        super(new KESStateInfo(ARMED, ALL_LOCKED), actionType);
    }

    KESAlarmArmedAllLockedState() {
        super(new KESStateInfo(ARMED, ALL_LOCKED), LOCK);
    }

    public KESAlarmArmedAllLockedState(KESStatesCache cache) {
        super(new KESStateInfo(ARMED, ALL_LOCKED), LOCK);
        this.cache = cache;
    }


    @Override
    protected Map<KESActionType, KESTransferState> configureOutputStates() {
        Map<KESActionType, KESTransferState> outputStates = new EnumMap<>(KESActionType.class);
        outputStates.put(LOCK, load(this.getClass(), cache, LOCK));
        outputStates.put(UNLOCK, load(KESAlarmDisarmedDriverUnlockedState.class, cache, UNLOCK));
        outputStates.put(LOCK2X, load(this.getClass(), cache, LOCK2X));
        outputStates.put(UNLOCK2X, load(KESAlarmDisarmedAllUnlockedState.class, cache, UNLOCK2X));
        return outputStates;
    }

    @Override
    public KESTransferState getOutputState(KESActionType actionType) {
        return super.getOutputState(actionType);
    }
}
