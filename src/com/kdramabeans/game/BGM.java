package com.kdramabeans.game;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class BGM extends Thread{
    /*
        FIELDS
     */
    public Clip clip;
    public String song; // .wav files


    public BGM(String song) {
        this.song = song;
        this.start();
//     //========URL MUSIC PLAYER======//
//        Thread musicThread = new Thread(() -> {
//            try {
//                createClip("https://kathyle.dev/songs/goblin.wav");
//            }catch(Exception e){
//                System.out.println("Bad Url: "+e);
//            }
//        });
//        musicThread.start();
    }
    public BGM(String song,boolean isMusic) {
        this(song);
        if(isMusic){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
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

    public void pauseSong(){
        clip.stop();
        this.interrupt();
    }

    public boolean isPlaying(){
        return clip.isRunning();
    }
    //Helper Methods
    private AudioInputStream createAudioStream(String song) throws IOException, UnsupportedAudioFileException {
        URL url = BGM.class.getResource("/" + song);
        return AudioSystem.getAudioInputStream(url);
    }
}
