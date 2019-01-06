package main;

import java.util.EnumSet;

import static java.util.EnumSet.of;

public class WorldMap {

    // Initialization of map coordinate array
    private int mapWidth = 9;
    private int mapHeight = 9;
    private Scene[][] location = new Scene[mapWidth][mapHeight];

    // GETTERS
    public Scene getLocation(int x, int y) {
        return location[x][y];
    }

    WorldMap() {
        // Generate generic map tiles
        int i, j;
        for (i = 0; i < mapWidth; i++) {
            for (j = 0; j < mapHeight; j++) {
                location[i][j] = Scene.builder().build();
            }
        }

        // MAP DATA

        { // (4,4) Starting scene
            location[4][4] = Scene.builder().description("You are in a sparse clearing surrounded by dry grasses and scrub. The clearing is bounded to the South and West by towering cliffs of red, sandy stone. To the East lie the massive skeletal remains of some ancient creature, and beyond, a dark forest. To the North the clearing descends gradually to a desolate plain. In the distance you see a mass of snowy mountains stretching across the horizon.").build();
        }

        { // (3,4), (3,3) and (4,3) Rock face
            location[3][4] = Scene.builder().name("Rock Face").disallowed(EnumSet.range(Direction.SW, Direction.NW)).climb(true).description("As you approach the rock face, you see its surface is pockmarked with small holes that would make perfect climbing holds. The cliff is sheer, but you think you could manage the climb. Looking up, you sight the face of a young girl watching you from the top of the bluff. Her face is streaked with dark paint, and as your eyes meet she is startled and withdraws quickly from view.").succeed("You successfully scale the sandy cliff, and pulling yourself over the lip you emerge onto a wide plateau.").fail("Part-way up the sandy cliff, you lose your grip and fall into the void. For a brief second you hear the faint sound of music, then you hit the ground with a hard crack.").build();
            location[3][3] = Scene.builder().name("Rock Face").disallowed(EnumSet.range(Direction.SE, Direction.NW)).climb(true).description(location[4][5].getDescription()).succeed(location[4][5].getSucceed()).fail(location[4][5].getFail()).build();
            location[4][3] = Scene.builder().name("Rock Face").disallowed(EnumSet.range(Direction.SE, Direction.SW)).climb(true).description(location[4][5].getDescription()).succeed(location[4][5].getSucceed()).fail(location[4][5].getFail()).build();
        }

        { // (2, 4) Kerosi Encampment
            location[2][4] = Scene.builder().name("Kerosi Encampment").disallowed(EnumSet.range(Direction.NE, Direction.SE)).use(true).description("Spread out before you is an encampment nomadic peoples. Several dozen animal-hide tents dot the landscape, the smoke from cooking fires rises into the open sky like tendrils. Men and women are at work repairing clothes, sharpening tools, tending to young children.").build();
            location[2][4].putCreature(NPC.builder().name("Stranger").maxHealth(100).meleeAttack(6).luck(10).position(3,5).inventory(Weapon.builder().name("fellstaff").damage(16).luck(1).description("The staff is gnarled and heavy, but smooth from use. It seems to have been hewn from three tree limbs that grew together over time. As you pick it up, it vibrates angrily at your touch, then grows calm.").build()).inventory(Armor.builder().name("bramble guard").armor(16).luck(1).description("You've never seen a set of armor like this. Its interlocking plates are hard and polished, but they seem to have been grown together rather than forged.").build()).build());
        }

        { // (5,4) Forest's Edge
            location[5][4] = Scene.builder().name("The Forest's Edge").disallowed(EnumSet.of(Direction.NE, Direction.SE, Direction.S)).description("The forest looms over you as you approach the collection bones. The hulking carcass has been picked clean by scavengers and bleached by the relentless sun, but its large white horns reach up to the sky defiantly nonetheless. The grass seems to have retreated from the spot where it lies, as if recoiling from its foulness. You grow uneasy.").detail("Looking closer, you see a tangled leather object near the skull of the beast. It appears to be a bridle of some kind.").inventory(Item.builder().name("bridle").description("You retrieve the trappings from the foul carcass. While the animal hide material is weathered and faded, it is surprisingly supple in your hands. Something stirs within you.").build()).build();
        }

        { // (6,4) The Murky Wood
            location[6][4] = Scene.builder().name("The Murky Wood").allowed(of(Direction.W)).description("Creeping into the murky wood, you feel the damp clinging to your tunic. Eerie sounds from creatures you'd rather not meet come from the depths. The trees grow denser as you go, all but blotting out the daylight. Strange flora curl around the roots of the massive trees, and more than once you think you see them moving at the edge of your vision. They seem to be trying to block your path.").detail("It seems these plants are trying to dissuade you from entering any deeper. They are closing in from all around, and you realize that your only way forward might be back the way you came...If you're lucky.").build();
        }

        { // (4,6) The Plain
            location[4][6] = Scene.builder().name("The Plain").description("Flanked to the West by the sandy cliffs overhead, the plain descends to a vast steppe dotted with dead-looking trees. A great black river runs to the East. It seems to swift and wide to cross. The same mountains you saw to the North are no closer than they were before. As you scan the horizon, you glimpse a roiling cloud of dust heading in your direction. At the head of the cloud is a stampede of bizarre four-legged creatures – equine and dun-colored with two large horns, flowing silver manes, and iridescent black scales down their backs and tails.").detail("Judging by their horns and their size, these may be the creatures whose remains you saw in the clearing.").build();
        }

        { // (5,7) The Steppe
            location[5][7] = Scene.builder().name("The Steppe").build();
                    ("The Steppe", true, false, true, true);
            location[5][7].description = "You cautiously continue toward the raging animals, fully aware they could crush you at any moment.";
            location[5][7].creatures.put("Buffalo", new Creature("Buffalo", 25, 10, 0, 0, new Item("horns", 10, 0 ,0, "The creature's horns are alabaster-white and surprisingly sharp. Maybe they could be used as a weapon?")));
        }

        { // (7, 6) The River
            location[7][6] = new Scene("The River", true, false, false, true, new Item("dagger", 10, 0 ,0, "The dagger is heavier than it appears. Its cold metal stirs beneath the surface, reflecting the light like ripples on a pond."));
            location[7][6].description = "";
        }

    }

}
