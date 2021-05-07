package com.kdramabeans.game;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Story {
    /*
        fields
     */
    //private JSONObject data;
    private JsonNode data;
    private JsonNode scene;
    private DataParser dp;
    private Map<String, Map> options = new HashMap<>();
    private String currentOption;
    private List<Item> sceneItems = new ArrayList<>();
    private List<String> hiddenItems = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    // private JSONObject randomEvent = new RandomEvents().getEvent();
    private JsonNode randomEvent = new DataParser().getRandomEvents();
    private boolean isRestart = false;
    private boolean eventTrigger = false;
    private boolean isAtEnd = false;

    /*ctor
      gets story information from a .json file, makes it into a JSON object, and then saves the current scene
      to be "intro" and saves the items that are in the scene into a List called "sceneItems"
     */
    public Story() throws Exception {
//        InputStreamReader file = new InputStreamReader(this.getClass().getResourceAsStream("/story.json"),
//                StandardCharsets.UTF_8);
//        Object obj = new JSONParser().parse(file);
//        JSONObject jsonObj = (JSONObject) obj;
        dp = new DataParser();
        this.data = dp.getStory();
        //set "intro" as starting scene
        this.scene = dp.getStoryIntro();
        setSceneItems();
    }

    /*
        methods/functions
     */

    //brings user to the next scene by resetting the scene, the items in the scene, and clearing their options
    public void nextScene(boolean isGUI) {
        setScene(isGUI);
        resetOptions();
        sceneItems.clear();
        setSceneItems();
        hiddenItems.clear();
        setHiddenItems();
    }

    // sets the scene, it will check if the scene ends the game or not and display the description
    private void setScene(boolean isGUI) {
        Map newOption = options.get(currentOption);
        String nextScene = (String) newOption.get("nextScene");
        // JSONObject currentScene = (JSONObject) data.get(nextScene);
        JsonNode currentScene = data.get(nextScene);
        if (currentScene.get("ending").asBoolean()) {
            isAtEnd = true;
            if (isGUI) {
                runGUIEnding(currentScene);
            } else {
                runEndingScene(currentScene);
            }
        } else if (eventTrigger) {
            randomOrNextScene(currentScene);
        } else {
            this.scene = currentScene;
        }
    }

    private void runGUIEnding(JsonNode currentScene) {
        String ending = "\nThis is the end of the game, if you want to start again, click the restart button.";
        ((ObjectNode)currentScene).put("description", currentScene.get("description") + ending);
        this.scene = currentScene;
    }

    //if the user has hit a "dead end" they have the option to restart the game
    private void runEndingScene(JsonNode currentScene) {
        String msg = currentScene.get("description").asText();
        System.out.println(msg);
        System.out.println("Do you want to play again? ");
        boolean isRightResponse = false;
        while (!isRightResponse) {
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("yes")) {
                isRightResponse = true;
                isRestart = true;
                isAtEnd = false;
                restartGame();
            } else if (input.equals("no")) {
                System.exit(0);
            }
        }
    }

    //this will set either a "random" scene or user's choice of scene
    private void randomOrNextScene(JsonNode currentScene) {
        Random rand = new Random();
        int n = rand.nextInt(10);
        if (n <= 2) {
            this.scene = randomEvent;
        } else {
            this.scene = currentScene;
        }
    }

    // restarts the game - resets scene back to intro and clears all options and items
    public void restartGame() {
        this.scene = (JsonNode) data.get("intro");
        sceneItems.clear();
        setSceneItems();
        hiddenItems.clear();
        setHiddenItems();
        resetOptions();
    }

    // pulls from items list in story.json and displays choices to the player
    private void setSceneItems() {

        List items = (List) scene.get("items");
        items.forEach(item -> {
            try {
                sceneItems.add(new Item(item.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setHiddenItems() {

        List items = (List) scene.get("hidden");
        items.forEach(item -> {
            try {
                hiddenItems.add(item.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //check if current scene has the item by comparing to the name of each item
    public boolean hasItem(String itemName) {
        boolean result = false;
        for (int index = 0; index < sceneItems.size(); index++) {
            // return sceneItems.contains(itemName);
            if (sceneItems.get(index).getName().equalsIgnoreCase(itemName)) {
                result = true;
            }
        }
        return result;
    }

    //check if current scene has hidden item
    public boolean hasHidden(String itemName) {
        boolean result = false;
        for (int index = 0; index < hiddenItems.size(); index++) {
            if (hiddenItems.get(index).equalsIgnoreCase(itemName)) {
                result = true;
            }
        }
        return result;
    }

    // prints items for the currentScene
    public String printItems() {
        String result = "";
        if (!getEnding()) {
            result += "\nHere are the items you see: ";
            for (int index = 0; index < sceneItems.size(); index++) {
                result += ("\n" + sceneItems.get(index).getName());
            }
        }
        return result;
    }

    // prints the story description
    public String printStory() {
        return "SCENE: \n" + getDescription() + "\n";
    }

    // iterates over story.json and depending on the item picked, will give you certain options
    public String printOptions() {
        String result = "";
        System.out.println(options);
        if (options.size() > 0) {
            System.out.println(options);
            Iterator<Map.Entry<String, Map>> itr1 = options.entrySet().iterator();
            result += "\nHere are your options: ";
            while (itr1.hasNext()) {
                Map.Entry<String, Map> pair = itr1.next();
                //JSONObject msg = (JSONObject) pair.getValue();
                Map msg = pair.getValue();
                result += ("\n" + pair.getKey() + " : " + msg.get("description"));
            }
        }
        return result;
    }

    /*
     getters and setters for private fields
     */
    public void resetOptions() {
        options.clear();
    }

    public void setCurrentOption(String option) {
        this.currentOption = option;
    }

    public Map<String, Map> getOptions() {
        return options;
    }

    // pulls from items.json and sets the options the player can choose
    public void setOptions(String item) {
        String key = Integer.toString(options.size() + 1);
        Item itemObj = sceneItems.stream().filter(obj -> obj.getName().equalsIgnoreCase(item)).findAny().orElse(null);
        if (itemObj.getOption().get("description") != null) {
            options.put(key, itemObj.getOption());
        }
        sceneItems.remove(itemObj);
    }

    public String getDescription() {
        return scene.get("description").asText();
    }

    public boolean getEnding() {
        return scene.get("ending").asBoolean();
    }

    public boolean isRestart() {
        return isRestart;
    }

    public void setRestart(boolean restart) {
        isRestart = restart;
    }

    public void setEventTrigger(boolean eventTrigger) {
        this.eventTrigger = eventTrigger;
    }

    public boolean isAtEnd() {
        return isAtEnd;
    }
}