package main;

import java.util.HashMap;

class Scene {

    // Scene name
    String name = "Somewhere in Celedan";

    // Available Directions
    boolean north;
    boolean east;
    boolean south;
    boolean west;

    // Viable Actions
    boolean examine = true;
    boolean use;
    boolean climb;

    // Scene Inventory
    HashMap<String, Item> inventory = new HashMap<>();
    HashMap<String, Creature> creatures = new HashMap<>();


    // Scene Text
    String description = "You shouldn't be here.";
    String detail = "There is nothing to see here.";
    String succeed = "Well done!";
    String fail = "That didn't work.";

    // Hazards

    // Methods
    void use() {
        if (this.use) {
            //TODO Add use() functionality
        }
    }

    // Constructors
    Scene() {
        north = true;
        east = true;
        south = true;
        west = true;
    }

    Scene(String name, boolean north, boolean east, boolean south, boolean west) {
        this.name = name;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    Scene(String name, boolean north, boolean east, boolean south, boolean west, Item item) {
        this(name, north, east, south, west);
        inventory.put(item.name, item);
    }

}
