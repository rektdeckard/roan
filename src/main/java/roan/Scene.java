package roan;

import java.util.ArrayList;
import java.util.EnumSet;

public class Scene {

    // SCENE DETAILS
    private String name;
    private boolean examine;
    private boolean use;

    // ALLOWED DIRECTIONS
    private EnumSet<Direction> allowed;
    private EnumSet<Vertical> allowedVertical;
    private Direction climbDirection;
    private Vertical climbVertical;

    // SCENE INVENTORY
    private ArrayList<Item> inventory;
    private ArrayList<Creature> creatures;

    // SCENE TEXT
    private String description;
    private String detail;
    private String succeed;
    private String fail;

    // SCENE HAZARDS
    //TODO add hazards

    // METHODS
    void activity() {
        if (this.use) {
            //TODO Add use() functionality
        }
    }

    public void disposeOf(Creature creature) {
        this.putInventory(creature.getInventory());
        this.creatures.remove(creature);
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

    public Direction getClimbDirection() {
        return this.climbDirection;
    }

    public void setClimbDirection(Direction climbDirection) {
        this.climbDirection = climbDirection;
    }

    public Vertical getClimbVertical() {
        return this.climbVertical;
    }

    public void setClimbVertical(Vertical climbVertical) {
        this.climbVertical = climbVertical;
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

    public EnumSet<Vertical> getVertical() {
        return allowedVertical;
    }

    public void setVertical(EnumSet<Vertical> verticals) {
        this.allowedVertical = verticals;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public void putInventory(Item item) {
        this.inventory.add(item);
    }

    public void putInventory(ArrayList<Item> inventory) {
        this.inventory.addAll(inventory);
    }

    public void removeInventory(Item item) {
        this.inventory.remove(item);
    }

    public void clearInventory() {
        this.inventory.clear();
    }

    public ArrayList<Creature> getCreatures() throws NullPointerException {
        try {
            return this.creatures;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void putCreature(Creature creature) {
        this.creatures.add(creature);
    }

    public void removeCreature(Creature creature) {
        this.creatures.remove(creature);
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

        // ALLOWED DIRECTIONS
        private EnumSet<Direction> allowed = EnumSet.allOf(Direction.class);
        private EnumSet<Vertical> allowedVertical = EnumSet.noneOf(Vertical.class);
        private Direction climbDirection = null;
        private Vertical climbVertical = null;

        // SCENE INVENTORY
        private ArrayList<Item> inventory = new ArrayList<>();
        private ArrayList<Creature> creatures = new ArrayList<>();

        // SCENE TEXT
        String description = "You shouldn't be here.";
        String detail = "There is nothing of interest here.";
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

        public T climb(Direction direction, Vertical vertical) {
            this.climbDirection = direction;
            this.climbVertical = vertical;
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

        public T vertical(EnumSet<Vertical> verticals) {
            this.allowedVertical = verticals;
            return self();
        }

        public T inventory(Item item) {
            this.inventory.add(item);
            return self();
        }

        public T creature(Creature creature) {
            this.creatures.add(creature);
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
        this.allowed = builder.allowed;
        this.allowedVertical = builder.allowedVertical;
        this.climbDirection = builder.climbDirection;
        this.climbVertical = builder.climbVertical;
        this.description = builder.description;
        this.detail = builder.detail;
        this.succeed = builder.succeed;
        this.fail = builder.fail;
        this.inventory = builder.inventory;
        this.creatures = builder.creatures;
    }

}
