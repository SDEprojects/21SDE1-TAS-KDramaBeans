package com.kdramabeans.game;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class BGM extends Thread {

    public Clip clip;
    public String song; // .wav files


    public BGM(String song) {
        this.song = song;
        this.start();
    }

    public void run() {
        try {
            clip = AudioSystem.getClip();
            clip.open(createAudioStream(song));
            clip.start();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    public void playSong() {
        clip.start();
    }

    public void pauseSong() {
        clip.stop();
        this.interrupt();
    }

    public boolean isPlaying() {
        return clip.isRunning();
    }

    //Helper Methods
    private AudioInputStream createAudioStream(String song) throws IOException, UnsupportedAudioFileException {
        URL url = BGM.class.getResource("/" + song);
        return AudioSystem.getAudioInputStream(url);
    }
}