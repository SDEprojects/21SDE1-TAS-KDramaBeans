package com.kdramabeans.game;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Item {
    /*
      FIELDS
     */
    private JSONObject data;
    private String name;
    private String description;
    private Map option;

    /*
      CTOR - reads in items.json file and converts to JSONObject to be called in the Game class
     */

    public Item() throws Exception {
        InputStreamReader file = new InputStreamReader(this.getClass().getResourceAsStream("/items.json"),
                StandardCharsets.UTF_8);
        Object obj = new JSONParser().parse(file);
        JSONObject jsonObj = (JSONObject) obj;
        this.data = jsonObj;
    }

    public Item(String item) throws Exception {
        this();
        this.name = item;
        this.description = dp.getItemDescription(item);
        setOption(item);
    }

    /*
      METHODS/FUNCTIONS
     */

    // function to grab item description
    public String getItemDescription(String itemName) {
        JSONObject itemObj = (JSONObject) data.get(itemName);
        String itemD = (String) itemObj.get("description");
        return StringUtils.capitalize(itemName) + ": " + itemD;
    }

    /*
      GETTERS/SETTER
     */
    DataParser dp = new DataParser();
    public String getName() {
        return name;
    }

    public Map getOption() {
        return option;
    }

    public void setOption(String itemObj) {
        //this.option = (Map) itemObj.get("option");
        this.option = dp.getItemOption(itemObj);
    }
}