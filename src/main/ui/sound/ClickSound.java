package ui.sound;

// Represents a sound played when the user interacts with a button.
public class ClickSound extends AbstractSound {

    // Audio file downloaded from mixkit.co, using the Mixkit License (free for educational use).
    //  Link: https://mixkit.co/free-sound-effects/click/?page=2
    public static final String AUDIO_PATH = "data/mixkit-click.wav";    // The path to the audio file.

    // MODIFIES: this
    // EFFECTS: creates a sound through the AbstractSound class,
    //  with audio filed located at AUDIO_PATH.
    public ClickSound() {
        super(AUDIO_PATH);
    }
}
