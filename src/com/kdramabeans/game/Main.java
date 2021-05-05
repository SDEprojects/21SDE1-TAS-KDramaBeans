package com.kdramabeans.game;

public class Main {

    public static void main(String[] args) throws Exception {
        try {
            new Game();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}