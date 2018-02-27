package com.fsm.entrysystemkeyless.states.transfer.util;

import com.fsm.entrysystemkeyless.states.KESTransferState;
import com.fsm.entrysystemkeyless.states.indicators.KESStateInfo;
import com.fsm.entrysystemkeyless.util.KESStatesJSONLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class KESStatesConfig {

    private static final String STATES = "states";
    private static final String NAME = "name";
    private static final String VALIDATION_MSG = "validationMsg";

    private KESStatesJSONLoader fileReader;
    private KESStatesCache cache;

    public KESStatesConfig(KESStatesJSONLoader fileReader,
                           KESStatesCache cache) {
        this.fileReader = fileReader;
        this.cache = cache;
    }

    KESStatesConfig() {

    }

    public void config() {
        JSONObject file = fileReader.get();
        try {
            JSONArray statesArr = file.getJSONArray(STATES);
            parseJSONArray(statesArr);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    void parseJSONArray(JSONArray statesArr) throws JSONException {
        for (int index = 0; index < statesArr.length(); index++) {
            parseJSON(statesArr, index);
        }
    }

    void parseJSON(JSONArray statesArr, int index) throws JSONException {
        JSONObject statesObj = statesArr.getJSONObject(index);
        String stateName = statesObj.getString(NAME);
        String validMsg = statesObj.getString(VALIDATION_MSG);

        KESTransferState transferState = cache.get(stateName);
        if (transferState == null) {
            throw new RuntimeException("no matching state instance found with name: " + stateName);
        }

        KESStateInfo stateInfo = transferState.getStateInfo();
        stateInfo.setValidationMsg(validMsg);
    }
}
