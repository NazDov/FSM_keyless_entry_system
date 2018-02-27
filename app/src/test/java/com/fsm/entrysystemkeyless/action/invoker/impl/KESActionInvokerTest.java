package com.fsm.entrysystemkeyless.action.invoker.impl;

import com.fsm.entrysystemkeyless.BaseTestApp;
import com.fsm.entrysystemkeyless.BuildConfig;
import com.fsm.entrysystemkeyless.action.KESActionType;
import com.fsm.entrysystemkeyless.states.KESTransferState;
import com.fsm.entrysystemkeyless.states.indicators.KESAlarmStatus;
import com.fsm.entrysystemkeyless.states.indicators.KESLockStatus;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.fsm.entrysystemkeyless.action.KESActionType.*;
import static com.fsm.entrysystemkeyless.states.indicators.KESAlarmStatus.*;
import static com.fsm.entrysystemkeyless.states.indicators.KESLockStatus.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = BaseTestApp.class)
public class KESActionInvokerTest {


    private KESTransferState state;

    private KESActionInvokerImpl actionExecutor = new KESActionInvokerImpl();

    @Before
    public void setUp() {
        state = mock(KESTransferState.class);
        actionExecutor.setCurrState(state);
    }

    @Test
    public void testDefaultCurrentStateNotNull() {
        KESTransferState currState = actionExecutor.getCurrState();
        assertNotNull(currState);
    }

    @Test
    public void testReturnValidCurrStateInfo() {
        //given
        when(state.getStateInfo()).thenReturn(testStateInfo(DISARMED,
                ALL_UNLOCKED));

        //when
        KESStateInfo stateInfo = actionExecutor.getCurrStateInfo();

        //then
        assertNotNull(stateInfo);
        assertThat(stateInfo, is(testStateInfo(DISARMED, ALL_UNLOCKED)));
    }

    @Test
    public void testReturnValidCurrActionType() {
        //given
        when(state.getActionType()).thenReturn(UNLOCK);

        //when
        KESActionType actionType = actionExecutor.getCurrStateActionType();

        //then
        assertNotNull(actionType);
        assertThat(actionType, is(UNLOCK));
    }


    @Test
    public void testInvokeLockOperation() {
        //given
        when(state.transfer(actionExecutor)).thenReturn(
                testStateInfo(DISARMED, ALL_LOCKED));

        //when
        KESStateInfo expectedStateInfo = new KESStateInfo(DISARMED, ALL_LOCKED);
        KESStateInfo actualStateInfo = actionExecutor.invokeLock();

        //then
        assertNotNull(actualStateInfo);
        assertThat(expectedStateInfo, is(actualStateInfo));

        verify(state, atMost(1)).setActionType(LOCK);
        verify(state, atMost(1)).transfer(actionExecutor);
        verifyNoMoreInteractions(state);
    }

    @Test
    public void testInvokeUnlockOperation() {
        //given
        when(state.transfer(actionExecutor)).thenReturn(
                testStateInfo(DISARMED, DRIVER_UNLOCKED));

        //when
        KESStateInfo expectedStateInfo = new KESStateInfo(DISARMED, DRIVER_UNLOCKED);
        KESStateInfo actualStateInfo = actionExecutor.invokeUnlock();

        //then
        assertNotNull(actualStateInfo);
        assertThat(expectedStateInfo, is(actualStateInfo));

        verify(state, atMost(1)).setActionType(UNLOCK);
        verify(state, atMost(1)).transfer(actionExecutor);
        verifyNoMoreInteractions(state);
    }

    @Test
    public void testInvokeLock2XOperation() {
        //given
        when(state.transfer(actionExecutor)).thenReturn(
                testStateInfo(ARMED, ALL_LOCKED));

        //when
        KESStateInfo expectedStateInfo = new KESStateInfo(ARMED, ALL_LOCKED);
        KESStateInfo actualStateInfo = actionExecutor.invokeLock2x();

        //then
        assertNotNull(actualStateInfo);
        assertThat(expectedStateInfo, is(actualStateInfo));

        verify(state, atMost(1)).setActionType(LOCK2X);
        verify(state, atMost(1)).transfer(actionExecutor);
        verifyNoMoreInteractions(state);
    }

    @Test
    public void testInvokeUnlock2XOperation() {
        //given
        when(state.transfer(actionExecutor)).thenReturn(
                testStateInfo(DISARMED, DRIVER_UNLOCKED));

        //when
        KESStateInfo expectedStateInfo = new KESStateInfo(DISARMED, DRIVER_UNLOCKED);
        KESStateInfo actualStateInfo = actionExecutor.invokeUnlock2x();

        //then
        assertNotNull(actualStateInfo);
        assertThat(expectedStateInfo, is(actualStateInfo));

        verify(state, atMost(1)).setActionType(UNLOCK2X);
        verify(state, atMost(1)).transfer(actionExecutor);
        verifyNoMoreInteractions(state);
    }

    private KESStateInfo testStateInfo(KESAlarmStatus alarmStatus, KESLockStatus lockStatus) {
        return new KESStateInfo(alarmStatus, lockStatus);
    }
}
