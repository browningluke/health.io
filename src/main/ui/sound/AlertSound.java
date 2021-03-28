package ui.sound;

// Represents a sound played when the program requires the user's attention.
public class AlertSound extends AbstractSound {

    // Audio file downloaded from mixkit.co, using the Mixkit License (free for educational use).
    //  Link: https://mixkit.co/free-sound-effects/click/
    public static final String AUDIO_PATH = "data/mixkit-error.wav";    // The path to the audio file.

    // MODIFIES: this
    // EFFECTS: creates a sound through the AbstractSound class,
    //  with audio filed located at AUDIO_PATH.
    public AlertSound() {
        super(AUDIO_PATH);
    }
}
