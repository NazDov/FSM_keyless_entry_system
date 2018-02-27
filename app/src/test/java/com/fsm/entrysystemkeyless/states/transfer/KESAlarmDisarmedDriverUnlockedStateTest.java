package com.fsm.entrysystemkeyless.states.transfer;

import android.support.annotation.NonNull;

import com.fsm.entrysystemkeyless.BaseTestApp;
import com.fsm.entrysystemkeyless.BuildConfig;
import com.fsm.entrysystemkeyless.action.KESActionType;
import com.fsm.entrysystemkeyless.action.invoker.KESActionInvoker;
import com.fsm.entrysystemkeyless.action.invoker.impl.KESActionInvokerImpl;
import com.fsm.entrysystemkeyless.states.KESTransferState;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;
import com.fsm.entrysystemkeyless.states.transfer.util.KESStatesCache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.EnumMap;
import java.util.Map;

import static com.fsm.entrysystemkeyless.action.KESActionType.*;
import static com.fsm.entrysystemkeyless.states.indicators.KESAlarmStatus.*;
import static com.fsm.entrysystemkeyless.states.indicators.KESLockStatus.*;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = BaseTestApp.class)
public class KESAlarmDisarmedDriverUnlockedStateTest {


    private KESAlarmDisarmedDriverUnlockedState disarmedDriverUnlockedState;

    @Before
    public void setup() {
        disarmedDriverUnlockedState = new KESAlarmDisarmedDriverUnlockedState();
        disarmedDriverUnlockedState.setOutputStates(testOutputStates());
    }

    @SuppressWarnings("all")
    @Test(expected = IllegalArgumentException.class)
    public void testThrowExceptionWhenNoDefaultActionTypeProvided() {
        KESActionType kesActionType = null;
        disarmedDriverUnlockedState = new KESAlarmDisarmedDriverUnlockedState(kesActionType);
    }

    @Test
    public void testHasValidStateInfo() {
        KESStateInfo kesStateInfo = disarmedDriverUnlockedState.getStateInfo();
        assertNotNull(kesStateInfo);
        assertThat(kesStateInfo, is(testStateInfo()));
    }

    @NonNull
    private KESStateInfo testStateInfo() {
        return new KESStateInfo(DISARMED, DRIVER_UNLOCKED);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetValidOutputState() {
        //given
        Map<KESActionType, KESTransferState> transferStateMap = (EnumMap<KESActionType, KESTransferState>) mock(EnumMap.class);
        disarmedDriverUnlockedState.setOutputStates(transferStateMap);
        when(transferStateMap.get(LOCK)).thenReturn(stubTransferState);

        //when
        KESTransferState actualOutputState = disarmedDriverUnlockedState.getOutputState(LOCK);

        //then
        assertNotNull(actualOutputState);
        assertThat(actualOutputState.getStateInfo(), is(stubTransferState.getStateInfo()));
        verify(transferStateMap, times(1)).get(LOCK);
    }

    @Test
    public void testOperationInvokerSetCurrStateCalledOnTransfer() {
        //given
        KESActionInvoker operationInvoker = mock(KESActionInvokerImpl.class);

        //when
        disarmedDriverUnlockedState.transfer(operationInvoker);

        //then
        verify(operationInvoker).setCurrState(any(KESTransferState.class));
    }

    @Test
    public void testValidCurrStateGetsPassedDownToOptionInvoker() {
        //given
        KESActionInvoker operationInvoker = mock(KESActionInvokerImpl.class);

        //when
        disarmedDriverUnlockedState.transfer(operationInvoker);

        //then
        ArgumentCaptor<KESTransferState> captor = ArgumentCaptor.forClass(KESTransferState.class);
        verify(operationInvoker).setCurrState(captor.capture());
        KESTransferState transferState = captor.getValue();

        assertNotNull(transferState);
        assertThat(transferState.getStateInfo(), is(testStateInfo()));
    }

    @Test
    public void testConfigureOutputStates() {
        //given
        KESStatesCache cache = mock(KESStatesCache.class);
        disarmedDriverUnlockedState.setCache(cache);
        when(cache.get(any(String.class))).thenReturn(mock(KESTransferState.class));

        //when
        disarmedDriverUnlockedState.configureOutputStates();

        //then
        verify(cache, times(4)).get(any(String.class));
        verifyNoMoreInteractions(cache);
    }

    private KESTransferState stubTransferState = new KESTransferState(
            new KESStateInfo(ARMED, ALL_LOCKED), LOCK
    ) {
        @Override
        protected Map<KESActionType, KESTransferState> configureOutputStates() {
            return null;
        }
    };

    private Map<KESActionType, KESTransferState> testOutputStates() {
        Map<KESActionType, KESTransferState> outputStates = new EnumMap<>(KESActionType.class);
        outputStates.put(LOCK, new KESAlarmArmedAllLockedState(LOCK));
        outputStates.put(UNLOCK, disarmedDriverUnlockedState.setActionType(UNLOCK));
        outputStates.put(LOCK2X, new KESAlarmArmedAllLockedState(LOCK2X));
        outputStates.put(UNLOCK2X, disarmedDriverUnlockedState.setActionType(UNLOCK2X));
        return outputStates;
    }
}
