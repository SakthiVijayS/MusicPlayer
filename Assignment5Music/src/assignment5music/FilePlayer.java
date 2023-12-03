package assignment5music;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Classs that will play music files at given paths
 * 
 * @author Jesus Molina
 *
 */

public class FilePlayer {

    public void play(String filePath) {
        try {
            File file = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
            clip.close();
            audioInputStream.close();
        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }
    }
}