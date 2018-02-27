package com.fsm.entrysystemkeyless.states.transfer.util;

import com.fsm.entrysystemkeyless.states.KESTransferState;
import com.fsm.entrysystemkeyless.states.transfer.KESAlarmArmedAllLockedState;
import com.fsm.entrysystemkeyless.states.transfer.KESAlarmDisarmedAllLockedState;
import com.fsm.entrysystemkeyless.states.transfer.KESAlarmDisarmedAllUnlockedState;
import com.fsm.entrysystemkeyless.states.transfer.KESAlarmDisarmedDriverUnlockedState;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum KESStatesCache {
    INSTANCE;


    private Map<String, KESTransferState> cache = new HashMap<>();

    KESStatesCache() {

    }

    public void preloadWithDefault() {
        cache.put(KESAlarmArmedAllLockedState.class.getSimpleName(),
                new KESAlarmArmedAllLockedState(this));
        cache.put(KESAlarmDisarmedAllLockedState.class.getSimpleName(),
                new KESAlarmDisarmedAllLockedState(this));
        cache.put(KESAlarmDisarmedAllUnlockedState.class.getSimpleName(),
                new KESAlarmDisarmedAllUnlockedState(this));
        cache.put(KESAlarmDisarmedDriverUnlockedState.class.getSimpleName(),
                new KESAlarmDisarmedDriverUnlockedState(this));
    }

    public KESTransferState get(String key) {
        return cache.get(key);
    }

}
