package com.fsm.entrysystemkeyless.action.invoker;

import com.fsm.entrysystemkeyless.action.KESActionType;
import com.fsm.entrysystemkeyless.states.KESTransferState;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;

/**
 *
 */
public interface KESActionInvoker {

    KESStateInfo invokeLock();

    KESStateInfo invokeUnlock();

    KESStateInfo invokeLock2x();

    KESStateInfo invokeUnlock2x();

    void setCurrState(KESTransferState state);

    KESStateInfo getCurrStateInfo();

    KESActionType getCurrStateActionType();
}
