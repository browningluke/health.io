package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: converts and returns this as a JSON object
    JSONObject toJson();
}
