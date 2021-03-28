package ui.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

// Represents a generic sound & handles loading and playing the audio file.
public abstract class AbstractSound {

    private Clip clip;          // The clip to be played

    // MODIFIES: this
    // EFFECTS: defines an abstract constructor which loads the audio stream,
    //  and initializes the clip object. If the audio fails to load for any reason,
    //  simply fail silently for this time, since it doesn't concern the user.
    public AbstractSound(String path) {
        AudioInputStream audioInputStream;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            this.clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Bulk catch all exceptions and fail safely since the user can't do anything about it.
        }
    }

    // MODIFIES: this
    // EFFECTS: plays the clip's audio if it is initialized.
    //  Check prevents NullPointer if audio failed to load.
    public void play() {
        if (clip != null) {
            clip.start();
        }
    }
}
