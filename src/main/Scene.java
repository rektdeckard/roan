package main;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Scene {

    // SCENE DETAILS
    private String name;
    private boolean examine;
    private boolean use;
    private boolean climb;

    // ALLOWED DIRECTIONS
    private EnumSet<Direction> allowed;

    // SCENE INVENTORY
    private Map<String, Item> inventory = new HashMap<>();
    private Map<String, Creature> creatures = new HashMap<>();

    // SCENE TEXT
    private String description;
    private String detail;
    private String succeed;
    private String fail;

    // SCENE HAZARDS
    //TODO add hazards

    // METHODS
    void use() {
        if (this.use) {
            //TODO Add use() functionality
        }
    }

    // SETTERS & GETTERS

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean canExamine() {
        return this.examine;
    }

    public void setExamine(boolean examine) {
        this.examine = examine;
    }

    public boolean canUse() {
        return this.use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public boolean canClimb() {
        return this.climb;
    }

    public void setClimb(boolean climb) {
        this.climb = climb;
    }

    public EnumSet<Direction> getAllowed() {
        return this.allowed;
    }

    public EnumSet<Direction> getDisallowed() {
        return EnumSet.complementOf(allowed);
    }

    public void setAllowed(EnumSet<Direction> allowed) {
        this.allowed.addAll(allowed);
    }

    public void setDisallowed(EnumSet<Direction> disallowed) {
        this.allowed.removeAll(disallowed);
    }

    public Map<String, Item> getInventory() {
        return this.inventory;
    }

    public void putInventory(Item item) {
        this.inventory.put(item.getName(), item);
    }

    public void putInventory(Map<String, Item> inventory) {
        this.inventory.putAll(inventory);
    }

    public void removeInventory(Item item) {
        this.inventory.remove(item.getName());
    }

    public void removeInventory() {
        this.inventory.clear();
    }

    public Map<String, Item> takeInventory() {
        Map<String, Item> inventory = this.inventory;
        this.inventory.clear();
        return inventory;
    }

    public Map<String, Creature> getCreatures() {
        return this.creatures;
    }

    public void putCreature(Creature creature) {
        this.creatures.put(creature.getName(), creature);
    }

    public void removeCreature(Creature creature) {
        this.creatures.remove(creature.getName());
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSucceed() {
        return this.succeed;
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    public String getFail() {
        return this.fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    // BUILDER
    public static abstract class Builder<T extends Builder<T>> {

        private String name = "Somewhere in Celedan";
        private boolean examine = true;
        private boolean use;
        private boolean climb;

        // ALLOWED DIRECTIONS
        private EnumSet<Direction> allowed = EnumSet.allOf(Direction.class);

        // SCENE INVENTORY
        private Map<String, Item> inventory = new HashMap<>();
        private Map<String, Creature> creatures = new HashMap<>();

        // SCENE TEXT
        String description = "You shouldn't be here.";
        String detail = "There is nothing to see here.";
        String succeed = "Well done!";
        String fail = "That didn't work.";


        protected abstract T self();

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T examine(boolean examine) {
            this.examine = examine;
            return self();
        }

        public T use(boolean use) {
            this.use = use;
            return self();
        }

        public T climb(boolean climb) {
            this.climb = climb;
            return self();
        }

        public T allowed(EnumSet<Direction> allowed) {
            this.allowed = allowed;
            return self();
        }

        public T disallowed(EnumSet<Direction> disallowed) {
            this.allowed = EnumSet.complementOf(disallowed);
            return self();
        }

        public T inventory(Item item) {
            this.inventory.put(item.getName(), item);
            return self();
        }

        public T creature(Creature creature) {
            this.creatures.put(creature.getName(), creature);
            return self();
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        public T detail(String detail) {
            this.detail = detail;
            return self();
        }

        public T succeed(String succeed) {
            this.succeed = succeed;
            return self();
        }

        public T fail(String fail) {
            this.fail = fail;
            return self();
        }

        public Scene build() {
            return new Scene(this);
        }

    }

    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    public static Builder<?> builder() {
        return new Builder2();
    }

    // SOLE CONSTRUCTOR
    Scene(Builder<?> builder) {
        this.name = builder.name;
        this.examine = builder.examine;
        this.use = builder.use;
        this.climb = builder.climb;
        this.allowed = builder.allowed;
        this.description = builder.description;
        this.detail = builder.detail;
        this.succeed = builder.succeed;
        this.fail = builder.fail;
        this.inventory = builder.inventory;
        this.creatures = builder.creatures;
    }

}
