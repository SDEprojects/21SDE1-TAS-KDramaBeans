package com.kdramabeans.game;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

public class DataParser {

    static String STORY_NODE = "story";
    static String ITEMS_NODE = "items";
    static String RANDOMEVENTS_NODE = "randomEvents";
    static String DESCRIPTION_NODE = "description";

    private ObjectMapper mapper;
    private JsonNode root;

    DataParser() {
        try {
            InputStream gameData = getClass().getResourceAsStream("/data.json");
            mapper = new ObjectMapper();
            root = mapper.readTree(gameData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getItemDescription(String itemName) {
        String itemD = root.path(ITEMS_NODE).path(itemName).path(DESCRIPTION_NODE).asText();
        return StringUtils.capitalize(itemName) + ": " + itemD;
    }
}
