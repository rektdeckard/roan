package test;

public class Creature {

    // CREATURE STATISTICS
    private String name;
    private int maxHealth;
    private int health;
    private int meleeAttack;
    private int rangedAttack;
    private int luck;
    private boolean hostile = true;


    // CREATURE TEXT
    String description;

    // METHODS

    // GETTERS & SETTERS
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        if (maxHealth <= 0) {
            maxHealth = 10;
        }
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public int getHealth() {
        return this.health;
    }

    public boolean setHealth(int n) {
        //TODO should it be void return type?
        this.health += n;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
            System.out.println(this.name + " looks refreshed.");
            return true;
        } else if (n > 0) {
            System.out.println(this.name + " looks stronger.");
            return true;
        } else if (health <= 0) {
            System.out.println(this.name + " has fallen.");
            //TODO kill method
            return false;
        } else {
            System.out.println(this.name + " was injured.");
            return true;
        }
    }

    public int getMeleeAttack() {
        return this.meleeAttack;
    }

    public void setMeleeAttack(int meleeAttack) {
        if (meleeAttack < 0) {
            meleeAttack = 0;
        }
        this.meleeAttack = meleeAttack;
    }

    public int getRangedAttack() {
        return this.rangedAttack;
    }

    public void setRangedAttack(int rangedAttack) {
        if (rangedAttack < 0) {
            rangedAttack = 0;
        }
        this.rangedAttack = rangedAttack;
    }

    public int getLuck() {
        return this.luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public boolean isHostile() {
        return this.hostile;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // SOLE CONSTRUCTOR
    private Creature(CreatureBuilder builder) {
        this.name = builder.name;
        this.maxHealth = builder.maxHealth;
        this.health = builder.health;
        this.meleeAttack = builder.meleeAttack;
        this.rangedAttack = builder.rangedAttack;
        this.luck = builder.luck;
        this.description = builder.description;
    }

    // BUILDER
    public static class CreatureBuilder {

        // CREATURE STATISTICS
        private String name;
        private int maxHealth;
        private int health;
        private int meleeAttack = 0;
        private int rangedAttack = 0;
        private int luck = 0;
        private boolean hostile = true;

        // CREATURE TEXT
        String description = "A fearsome beast appeared.";

        public CreatureBuilder(String name, int maxHealth) {
            this.name = name;
            this.maxHealth = maxHealth;
            this.health = maxHealth;
        }

        public CreatureBuilder setMeleeAttack(int meleeAttack) {
            if (meleeAttack < 0) {
                meleeAttack = 0;
            }
            this.meleeAttack = meleeAttack;
            return this;
        }

        public CreatureBuilder setRangedAttack(int rangedAttack) {
            if (rangedAttack < 0) {
                rangedAttack = 0;
            }
            this.rangedAttack = rangedAttack;
            return this;
        }

        public CreatureBuilder setLuck(int luck) {
            this.luck = luck;
            return this;
        }

        public CreatureBuilder setHostile(boolean hostile) {
            this.hostile = hostile;
            return this;
        }

        public CreatureBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Creature build() {
            return new Creature(this);
        }

    }

}