package com.fsm.entrysystemkeyless.app;


import android.app.Application;

import com.fsm.entrysystemkeyless.states.transfer.util.KESStatesCache;
import com.fsm.entrysystemkeyless.states.transfer.util.KESStatesConfig;
import com.fsm.entrysystemkeyless.util.KESStatesJSONLoader;

public class KESApplication extends Application {

    private static final String DESCRIPTION_JSON = "description.json";
    public static final String DEF_STATE_NAME = "KESAlarmDisarmedAllUnlockedState";

    KESStatesConfig statesConfig;
    KESStatesJSONLoader jsonLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        jsonLoader = new KESStatesJSONLoader(getApplicationContext(), DESCRIPTION_JSON);
        KESStatesCache cacheInstance = KESStatesCache.INSTANCE;
        cacheInstance.preloadWithDefault();
        statesConfig = new KESStatesConfig(jsonLoader, cacheInstance);
        statesConfig.config();
    }
}
