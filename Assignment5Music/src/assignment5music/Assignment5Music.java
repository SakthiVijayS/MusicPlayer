/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment5music;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author shakt
 */
public class Assignment5Music {

   private CountDownLatch latch;
    private CountDownLatch doOctaveLatch;
    
    public static void main(String[] args) {
        new Assignment5Music().playTones();
    }
    
    public void playTones() {
        latch = new CountDownLatch(2);
        doOctaveLatch = new CountDownLatch(2); // Separate latch for "do-octaves"
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        executor.execute(() -> {
            playTone("do");
            playTone("mi");
            playTone("sol");
            playTone("si");
            playDoOctave();
            latch.countDown();
        });
        
        executor.execute(() -> {
            playTone("re");
            playTone("fa");
            playTone("la");
            playDoOctave();
            latch.countDown();
        });
        
        executor.shutdown();
        
        try {
            latch.await(); // Wait for the main tones to finish playing
            doOctaveLatch.await(); // Wait for the "do-octaves" to finish playing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void playTone(String tone) {
        FilePlayer filePlayer = new FilePlayer();
        filePlayer.play(tone + ".wav");
    }
    
    public synchronized void playDoOctave() {
        FilePlayer filePlayer = new FilePlayer();
        
        Thread doOctaveThread = new Thread(() -> {
            filePlayer.play("do-octave.wav");
            doOctaveLatch.countDown(); // Count down the "do-octave" latch
        });
        
        doOctaveThread.start();
    }
}
