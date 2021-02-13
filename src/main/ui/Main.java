package ui;

public class Main {
    public static void main(String[] args) {
        System.out.println("Legend for CLI version:\n"
                         + "M: Mood, S: Sleep");

        MoodWindow mw = new MoodWindow();
        mw.drawUI();
    }
}
