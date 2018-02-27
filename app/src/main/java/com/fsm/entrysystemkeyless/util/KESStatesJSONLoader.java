package com.fsm.entrysystemkeyless.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.content.Context;

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
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
