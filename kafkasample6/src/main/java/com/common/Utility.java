package com.common;

import org.json.JSONException;
import org.json.JSONObject;
// import org.springframework.boot.configurationprocessor.json.JSONObject;
// import org.springframework.boot.configurationprocessor.json.JSONException;

public class Utility {
    public static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
        JSONObject mergedJSON = new JSONObject();
        try {
            // getNames(): Get an array of field names from a JSONObject.
            mergedJSON = new JSONObject(json1, JSONObject.getNames(json1));
            for (String key : JSONObject.getNames(json2)) {
                // get(): Get the value object associated with a key.
                mergedJSON.put(key, json2.get(key));
            }
        } catch (JSONException e) {
            // RunttimeException: Constructs a new runtime exception with the specified detail message.
            // The cause is not initialized, and may subsequently be initialized by a call to initCause.
            throw new RuntimeException("JSON Exception" + e);
        }
        return mergedJSON;
    }
}
