package com.fsm.entrysystemkeyless.states.transfer.util;

import android.support.annotation.NonNull;

import com.fsm.entrysystemkeyless.BaseTestApp;
import com.fsm.entrysystemkeyless.BuildConfig;
import com.fsm.entrysystemkeyless.util.KESStatesJSONLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = BaseTestApp.class)
public class KESStatesConfigTest {

    private static final String STATES = "states";

    private KESStatesConfig config;

    private KESStatesJSONLoader jsonLoader;

    private KESStatesCache cache;


    @Before
    public void setup() {
        jsonLoader = mock(KESStatesJSONLoader.class);
        cache = mock(KESStatesCache.class);
        config = new KESStatesConfig(jsonLoader, cache);
    }


    @Test
    public void testCallConfig() throws JSONException {
        //given
        config = createStub();
        JSONObject jsonObject = mock(JSONObject.class);
        when(jsonLoader.get()).thenReturn(jsonObject);
        JSONArray jsonArr = new JSONArray();
        when(jsonObject.getJSONArray(STATES)).thenReturn(jsonArr);

        //when
        config.config();

        //then
        verify(jsonLoader).get();
        verify(jsonObject).getJSONArray(STATES);
    }

    @NonNull
    private KESStatesConfig createStub() {
        return new KESStatesConfig(jsonLoader, cache) {
            @Override
            void parseJSONArray(JSONArray statesArr) throws JSONException {

            }
        };
    }

}
