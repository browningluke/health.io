package persistence;

import org.json.JSONObject;

import java.io.*;

import model.Timeline;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String path;

    public JsonWriter(String path) {
        this.path = path;
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(path));
    }

    public void write(Timeline tl) {
        JSONObject json = tl.toJson();
        saveToFile(json.toString(TAB));
    }

    public void close() {
        writer.close();
    }

    private void saveToFile(String json) {
        writer.print(json);
    }
}
