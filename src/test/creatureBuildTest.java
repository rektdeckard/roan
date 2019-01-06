package test;

public class creatureBuildTest {

    public static void main(String[] args) {
       Creature creature = new Creature.CreatureBuilder("Henry", 200).setLuck(10).setDescription("He likes tuna fish sandwiches").setMeleeAttack(16).build();
       System.out.println(creature.toString());
    }
}
