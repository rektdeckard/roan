package main;

public class Armor extends Item {
    private int armor;

    // GETTERS & SETTERS
    public int getArmor() {
        return this.armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    // BUILD
    public static abstract class Builder<T extends Builder<T>> extends Item.Builder<T> {
        private int armor = 0;

        public T armor(int armor) {
            this.armor = armor;
            return self();
        }

        public Armor build() {
            return new Armor(this);
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

    private Armor(Builder<?> builder) {
        super(builder);
        this.armor = builder.armor;
    }

}
