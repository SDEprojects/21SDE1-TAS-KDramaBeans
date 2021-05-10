package com.kdramabeans.game;

public class Main {

    public static void main(String[] args) throws Exception {
        try {
            new Gui();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}