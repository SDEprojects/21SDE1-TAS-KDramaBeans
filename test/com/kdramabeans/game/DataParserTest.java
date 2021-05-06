package com.kdramabeans.game;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class DataParserTest {

    DataParser dp;
    JSONObject randomEvent;

    @Before
    public void setUp() throws Exception {
        dp = new DataParser();
        randomEvent = new RandomEvents().getEvent();
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
    public void getRandTest() {
        System.out.println(randomEvent);
    }
}