package persistence;

import org.json.JSONObject;

import java.io.*;

import model.Timeline;

// Represents a writer that writes a timeline as json data to a specific path.
public class JsonWriter {
    private static final int TAB = 4;   // The indentation level of the JSON object.
    private PrintWriter writer;         // The writer object.
    private String path;                // Represents the path to write to.

    // MODIFIES: this
    // EFFECTS: creates a new JsonWriter instance set at a specific path.
    public JsonWriter(String path) {
        this.path = path;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer at the specified path.
    //          Throws FileNotFoundException if file at path cannot be opened.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(path));
    }

    // MODIFIES: this
    // EFFECTS: convert Timeline to a json representation and then save to file.
    public void write(Timeline tl) {
        JSONObject json = tl.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer at the specified path.
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes the JSON string to file at the specified path.
    private void saveToFile(String json) {
        writer.print(json);
    }
}
