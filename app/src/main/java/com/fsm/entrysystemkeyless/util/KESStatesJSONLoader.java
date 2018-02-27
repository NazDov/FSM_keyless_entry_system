package com.fsm.entrysystemkeyless.util;

<<<<<<< HEAD
import android.content.Context;
=======
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
>>>>>>> init commit

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 */
public class KESStatesJSONLoader {

    private String json;

<<<<<<< HEAD
=======
    @TargetApi(Build.VERSION_CODES.KITKAT)
>>>>>>> init commit
    public KESStatesJSONLoader(Context context, String jsonFileName) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(openAssets(context, jsonFileName)))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            this.json = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject get() {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream openAssets(Context context, String jsonFileName) throws IOException {
        return context.getAssets().open(jsonFileName);
    }
}
