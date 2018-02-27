package com.fsm.entrysystemkeyless.states;

import com.fsm.entrysystemkeyless.action.invoker.KESActionInvoker;
import com.fsm.entrysystemkeyless.action.KESActionType;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;
import com.fsm.entrysystemkeyless.states.transfer.util.KESStatesCache;

import java.util.Map;

/**
 *
 */
public abstract class KESTransferState extends KESState {

    private KESActionType actionType;
    private Map<KESActionType, KESTransferState> outputStates;
    protected KESStatesCache cache;


    public KESTransferState(KESStateInfo stateInfo,
                            KESActionType actionType) {
        super(stateInfo);
        checkNotNull(actionType, "actionType is null");
        this.actionType = actionType;
    }


    public KESStateInfo transfer(KESActionInvoker actionInvoker) {
        KESTransferState outputState = getOutputState(actionType);
        if (outputState == null) {
            throw new IllegalStateException();
        }
        actionInvoker.setCurrState(outputState);
        return outputState.getStateInfo();
    }

    protected KESTransferState getOutputState(KESActionType actionType) {
        if (outputStates == null) {
            outputStates = configureOutputStates();
        }
        return outputStates.get(actionType);
    }

    protected abstract Map<KESActionType, KESTransferState> configureOutputStates();

    protected KESTransferState load(Class<? extends KESTransferState> cl,
                          KESStatesCache fromCache,
                          KESActionType newActionType) {
        return fromCache
                .get(cl.getSimpleName())
                .setActionType(newActionType);
    }

    public KESTransferState setActionType(KESActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public KESActionType getActionType() {
        return actionType;
    }

    public void setOutputStates(Map<KESActionType, KESTransferState> outputStates) {
        this.outputStates = outputStates;
    }

    public void setCache(KESStatesCache cache) {
        this.cache = cache;
    }
}
