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
        private int damage = 5;

        public T damage(int damage) {
            this.damage = damage;
            return self();
        }

        public T random() {
            WeaponPrefix prefix = WeaponPrefix.randomPrefix();
            WeaponSuffix suffix = WeaponSuffix.randomSuffix();
            String name = prefix.name + " " + suffix.name;
            int damage = prefix.damage + suffix.damage;
            int luck = prefix.luck + suffix.luck;
            int value = prefix.value + suffix.value;
            this.name(name);
            this.damage(damage);
            this.luck(luck);
            this.value(value);
            return self();
        }

        public T random(WeaponPrefix prefix) {
            WeaponSuffix suffix = WeaponSuffix.randomSuffix();
            String name = prefix.name + " " + suffix.name;
            int damage = prefix.damage + suffix.damage;
            int luck = prefix.luck + suffix.luck;
            int value = prefix.value + suffix.value;
            this.name(name);
            this.damage(damage);
            this.luck(luck);
            this.value(value);
            return self();
        }

        public T random(WeaponSuffix suffix) {
            WeaponPrefix prefix = WeaponPrefix.randomPrefix();
            String name = prefix.name + " " + suffix.name;
            int damage = prefix.damage + suffix.damage;
            int luck = prefix.luck + suffix.luck;
            int value = prefix.value + suffix.value;
            this.name(name);
            this.damage(damage);
            this.luck(luck);
            this.value(value);
            return self();
        }

        public T generic(WeaponSuffix suffix) {
            String name = suffix.name;
            int damage = suffix.damage;
            int luck = suffix.luck;
            int value = suffix.value;
            this.name(name);
            this.damage(damage);
            this.luck(luck);
            this.value(value);
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
