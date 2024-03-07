package uk.ac.york.student.audio.sound;

import lombok.Getter;
import uk.ac.york.student.audio.AudioManager;

/**
 * Singleton class that manages the sound for the game
 */
public class SoundManager implements AudioManager {
    /**
     * The instance of the sound manager
     */
    @Getter
    private static final SoundManager instance = new SoundManager();

    /**
     * Called when the game is started
     */
    @Override
    public void onEnable() {

    }

    /**
     * Called when the game is stopped
     */
    @Override
    public void onDisable() {

    }
}