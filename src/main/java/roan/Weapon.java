package roan;

public class Weapon extends Item {
    private int damage;

    // GETTERS & SETTERS
    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // BUILD
    public static abstract class Builder<T extends Builder<T>> extends Item.Builder<T> {
        private int damage = 0;

        public T damage(int damage) {
            this.damage = damage;
            return self();
        }

        public Weapon build() {
            return new Weapon(this);
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

    private Weapon(Builder<?> builder) {
        super(builder);
        this.damage = builder.damage;
    }

}
