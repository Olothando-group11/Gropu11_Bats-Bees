import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    public static void main(String[] args) {
        // Start playing background music
        playMusic("background.wav");

        // Keep the program running to allow music to play
        try {
            Thread.sleep(Long.MAX_VALUE); // Sleep indefinitely
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle interruptions
        }
    }

    // Method to play background music
    static void playMusic(String filename) {
        try {
            Clip clip = AudioSystem.getClip(); // Create a clip
            clip.open(AudioSystem.getAudioInputStream(new File(filename))); // Load the audio file
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }
}