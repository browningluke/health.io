package persistence;

import model.DateCode;
import model.Day;
import model.Mood;
import model.Timeline;
import model.activities.Activity;
import model.activities.DefaultActivities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads a timeline from json data at a specific path.
public class JsonReader {

    private String path;            // Represents the path to read from.


    // MODIFIES: this
    // EFFECTS: creates a new JsonReader instance set at a specific path.
    public JsonReader(String path) {
        this.path = path;
    }

    // EFFECTS: reads the json file and returns a Timeline instance that it represents.
    //          throws IOException if an error occurs when reading from the file.
    public Timeline read() throws IOException {
        String jsonData = readFile(path);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseTimeline(jsonObject);
    }

    // CITATION: this method was taken from the CPSC210 JsonSerializationDemo project.
    // EFFECTS: returns the string read from the file located at specified path.
    private String readFile(String path) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: returns a new Timeline parsed from the JSON object.
    private Timeline parseTimeline(JSONObject jsonObject) {
        JSONArray jsonDayList = jsonObject.getJSONArray("timeline");

        ArrayList<Day> dayList = new ArrayList<>();
        addDays(dayList, jsonDayList);

        return new Timeline(dayList);
    }

    // EFFECTS: parses days from the JSON array, and adds them to the dayList instance.
    private void addDays(ArrayList<Day> dayList, JSONArray jsonDayList) {
        for (Object json : jsonDayList) {
            JSONObject day = (JSONObject) json;
            addDay(dayList, day);
        }
    }

    // EFFECTS: parses a day instance from the JSON object.
    private void addDay(ArrayList<Day> dayList, JSONObject jsonDay) {
        int sleep = jsonDay.getInt("sleep");
        String id = jsonDay.getString("id");
        JSONArray jsonMoods = jsonDay.getJSONArray("moods");

        DateCode dc = new DateCode(id);
        ArrayList<Mood> moodList = new ArrayList<>();
        addMoods(moodList, jsonMoods);

        assert moodList.size() <= Day.MAXMOODS;

        dayList.add(new Day(dc, sleep, moodList));
    }

    // EFFECTS: parses moods from the JSON array, and adds them to the moodList instance.
    private void addMoods(ArrayList<Mood> moodList, JSONArray jsonMoods) {
        for (Object json : jsonMoods) {
            JSONObject mood = (JSONObject) json;
            addMood(moodList, mood);
        }
    }

    // EFFECTS: parses a mood instance from JSON object.
    private void addMood(ArrayList<Mood> moodList, JSONObject jsonMood) {
        int score = jsonMood.getInt("score");
        JSONArray jsonActivities = jsonMood.getJSONArray("activities");

        Mood mood = new Mood();
        mood.setMoodScore(score);
        addActivities(mood, jsonActivities);

        moodList.add(mood);
    }

    // EFFECTS: parses activities from the JSON array, and adds them to the mood instance.
    private void addActivities(Mood mood, JSONArray jsonActivities) {
        for (Object json : jsonActivities) {
            JSONObject activity = (JSONObject) json;
            addActivity(mood, activity);
        }
    }

    // REQUIRES: defaultActivities.getActivity(jsonActivity.getString("name")) is NOT null.
    // EFFECTS: parses the activity name from JSON object, gets a new instance
    //          from the default activities, and adds it to the mood instance.
    private void addActivity(Mood mood, JSONObject jsonActivity) {
        String activityName = jsonActivity.getString("name");

        Activity activity = DefaultActivities.getInstance()
                .getActivity(activityName);

        mood.addActivity(activity);
    }

}
