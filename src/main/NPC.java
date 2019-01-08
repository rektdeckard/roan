package main;

public class NPC extends Creature {

    // NPC Statistics (inherited from Creature)

    // NPC Location
    private int xPos;
    private int yPos;
    private int zPos;

    // Constructors
    // BUILD
    public static abstract class Builder<T extends Builder<T>> extends Creature.Builder<T> {
        private int xPos = 2;
        private int yPos = 4;
        private int zPos = 6;

        public T position(int xPos, int yPos) {
            this.xPos = xPos;
            this. yPos = yPos;
            return self();
        }

        public T position(int xPos, int yPos, int zPos) {
            this.xPos = xPos;
            this. yPos = yPos;
            this.zPos = zPos;
            return self();
        }

        public NPC build() {
            return new NPC(this);
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

    protected NPC(Builder<?> builder) {
        super(builder);
        this.xPos = builder.xPos;
        this.yPos = builder.yPos;
        this.zPos = builder.zPos;
    }
}
