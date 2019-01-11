package roan;

public class Item {

    // ITEM STATISTICS
    private String name;
    private int luck;
    private int value;

    // ITEM TEXT
    private String description;

    // METHODS
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLuck() {
        return this.luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // BUILDER
    public static abstract class Builder<T extends Builder<T>> {

        // ITEM STATISTICS
        private String name;
        private int luck = 0;
        private int value = 0;

        // ITEM TEXT
        private String description;

        protected abstract T self();

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T luck(int luck) {
            this.luck = luck;
            return self();
        }

        public T value(int value) {
            this.value = value;
            return self();
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        public Item build() {
            return new Item(this);
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
    protected Item(Builder<?> builder) {
        this.name = builder.name;
        this.luck = builder.luck;
        this.value = value;
    }

}
