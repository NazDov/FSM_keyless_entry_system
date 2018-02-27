package com.fsm.entrysystemkeyless.action.invoker.impl;

import com.fsm.entrysystemkeyless.action.invoker.KESActionInvoker;
import com.fsm.entrysystemkeyless.action.KESActionType;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;
import com.fsm.entrysystemkeyless.states.KESTransferState;
import com.fsm.entrysystemkeyless.states.transfer.util.KESStatesCache;

import static com.fsm.entrysystemkeyless.action.KESActionType.*;

/**
 *
 */
public class KESActionInvokerImpl implements KESActionInvoker {

    private KESTransferState currState;

    public static KESActionInvoker initializeWithCurrState(KESTransferState state) {
        return new KESActionInvokerImpl(state);
    }

    public static KESActionInvoker initializeWithCurrStateFromCache(KESStatesCache cache,
                                                                    String className) {
        return new KESActionInvokerImpl(getDefaultState(cache, className));
    }

    private static KESTransferState getDefaultState(KESStatesCache cache, String className) {
        return cache.get(className);
    }

    private KESActionInvokerImpl(KESTransferState state) {
        this.currState = state;
    }

    KESActionInvokerImpl() {

    }

    @Override
    public KESStateInfo invokeLock() {
        return execute(LOCK);
    }

    @Override
    public KESStateInfo invokeUnlock() {
        return execute(UNLOCK);
    }

    @Override
    public KESStateInfo invokeLock2x() {
        return execute(LOCK2X);
    }

    @Override
    public KESStateInfo invokeUnlock2x() {
        return execute(UNLOCK2X);
    }

    private KESStateInfo execute(KESActionType actionType) {
        currState.setActionType(actionType);
        return currState.transfer(this);
    }

    @Override
    public void setCurrState(KESTransferState state) {
        this.currState = state;
    }

    KESTransferState getCurrState() {
        return currState;
    }


    @Override
    public KESStateInfo getCurrStateInfo() {
        return currState.getStateInfo();
    }

    @Override
    public KESActionType getCurrStateActionType() {
        return currState.getActionType();
    }
}
