/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package multithreadedplayer;

/**
 *
 * @author shakt
 */
import java.io.File;
import java.util.concurrent.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MultiThreadedPlayer {
      private CountDownLatch latch;
  //
    
    public static void main(String[] args) {
        new MultiThreadedPlayer().playTones();
    }
   
    
   public void playTones() {
        latch = new CountDownLatch(2);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        executor.execute(() -> {
            playTone("do");
            playTone("do");
            playTone("sol");
            playTone("sol");
            playTone("la");
            playTone("la");
            playTone("sol");
            playTone("fa");
            playTone("fa");
            playTone("mi");
            playTone("mi");
            playTone("re");
            playTone("re");
            playTone("do");
            latch.countDown();
        });
        
        executor.execute(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            playTone("so");
            playTone("so");
            playTone("fa");
            playTone("fa");
            playTone("mi");
            playTone("mi");
            playTone("re");
            playTone("so");
            playTone("so");
            playTone("fa");
            playTone("fa");
            playTone("mi");
            playTone("mi");
            playTone("re");
            playTone("do");
        });
        
        executor.shutdown();
    }
    
    public synchronized void playTone(String tone) {
        if (tone.equals("do-octave")) {
            playDoOctave();
        } else {
            FilePlayer filePlayer = new FilePlayer();
            filePlayer.play(tone + ".wav");
        }
    }
    
    public synchronized void playDoOctave() {
        FilePlayer filePlayer = new FilePlayer();
        filePlayer.play("do-octave.wav");
        
        try {
            File file = new File("do-octave");
            // Get the audio input stream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            
            // Get the clip and calculate the duration
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            long duration = clip.getMicrosecondLength() / 1000; // Convert to milliseconds
            
            // Adjust the latch count based on the duration
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        latch.countDown();
    }
}