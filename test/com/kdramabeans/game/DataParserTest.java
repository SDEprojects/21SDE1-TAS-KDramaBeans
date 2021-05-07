package com.kdramabeans.game;

import org.junit.Before;
import org.junit.Test;

public class DataParserTest {

    DataParser dp;

    @Before
    public void setUp() throws Exception {
        dp = new DataParser();
    }

    @Test
    public void getItemDescription() {
        System.out.println(dp.getItemDescription("passport"));
    }

    @Test
    public void getItemNames() {
        System.out.println(dp.getItemNames());
    }

    @Test
    public void getItemOptions() {
        System.out.println(dp.getItemOption("passport"));
    }

    @Test
    public void randomEventsTest() {
        System.out.println(dp.getRandomEvents());
    }

    @Test
    public void getStoryIntro() {
        System.out.println(dp.getStoryIntro());
    }
}