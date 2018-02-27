package com.fsm.entrysystemkeyless.states.indicators;

/**
 * Created by user on 24.02.18.
 */

public enum KESLockStatus {
    ALL_LOCKED("ALL_LOCKED", "All doors are locked"),
    ALL_UNLOCKED("ALL_UNLOCKED", "All doors are unlocked"),
    DRIVER_UNLOCKED("DRIVER_UNLOCKED", "Driver door is unlocked. Passengers door is locked");

    private String name;
    private String description;

    KESLockStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
