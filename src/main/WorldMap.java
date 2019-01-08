package main;

import java.util.EnumSet;

import static java.util.EnumSet.of;

public class WorldMap {

    // DECLARATIONS
    private static final int mapWidth = 9;
    private static final int mapHeight = 9;
    private static final int mapDepth = 9;
    private static final int d = 5;
    private Scene[][][] scene;

    // GETTERS
    public Scene getScene(int x, int y) {
        return scene[x][y][d];
    }

    public Scene getScene(int x, int y, int z) {
        return scene[x][y][z];
    }

    // CONSTRUCTOR
    WorldMap() {

        //INITIALIZE
        scene = new Scene[mapWidth][mapHeight][mapDepth];

        int i, j;
        for (i = 0; i < mapWidth; i++) {
            for (j = 0; j < mapHeight; j++) {
                scene[i][j][d] = Scene.builder().build();
            }
        }

        // MAP DATA
        String rockFace = "Rock Face";
        String kerosiEncampment = "Kerosi Encampment";
        String mesa = "The Alkhara Mesa";
        String forestEdge = "Forest's Edge";
        String murkyWood = "The Murky Wood";
        String plain = "The Plain";
        String river = "The River";
        String steppe = "The Steppe";

        // START
            scene[4][4][d] = Scene.builder().description("Looking around, you find yourself in a sparse clearing surrounded by dry grasses and scrub. The clearing is bounded to the South and West by towering cliffs of red, sandy stone. To the East lie the massive skeletal remains of some ancient creature, and beyond, a dark forest. To the North the clearing descends gradually to a desolate plain. In the distance you see a mass of snowy mountains stretching across the horizon.").detail("You search the clearing, and find a tightly-coiled length of rope hidden in the tall grass").inventory(Item.builder().name("Rope").build()).build();


        // ROCK FACE
        scene[3][4][d] = Scene.builder().name(rockFace).disallowed(EnumSet.range(Direction.SW, Direction.NW)).climb(Direction.W, Vertical.UP).description("As you approach the rock face, you see its surface is pockmarked with small holes that would make perfect climbing holds. The cliff is sheer, but you think you could manage the climb. Looking up, you spot the face of a young girl watching you from the top of the bluff. Her face is streaked with dark paint, and as your eyes meet she is startled and withdraws quickly from view.").succeed("You successfully scale the sandy cliff, and pulling yourself over the lip you emerge onto a wide plateau.").fail("Part-way up the sandy cliff, you lose your grip and fall into the void. For a brief second you hear the faint sound of music, then you hit the ground with a hard crack.").build();
            scene[3][3][d] = Scene.builder().name(rockFace).disallowed(EnumSet.range(Direction.SE, Direction.NW)).climb(Direction.W, Vertical.UP).description(scene[4][5][d].getDescription()).succeed(scene[4][5][d].getSucceed()).fail(scene[4][5][d].getFail()).build();
            scene[4][3][d] = Scene.builder().name(rockFace).disallowed(EnumSet.range(Direction.E, Direction.SW)).climb(Direction.S, Vertical.UP).description(scene[4][5][d].getDescription()).succeed(scene[4][5][d].getSucceed()).fail(scene[4][5][d].getFail()).build();
            scene[3][5][d] = Scene.builder().name(rockFace).disallowed(EnumSet.range(Direction.SW, Direction.NW)).climb(Direction.W, Vertical.UP).description(scene[4][5][d].getDescription()).succeed(scene[4][5][d].getSucceed()).fail(scene[4][5][d].getFail()).build();


        // ALKHARA MESA
            scene[2][4][6] = Scene.builder().name(kerosiEncampment).disallowed(EnumSet.range(Direction.NE, Direction.SE)).climb(Direction.E, Vertical.DOWN).use(true).description("Spread out before you is an encampment nomadic peoples. Several dozen animal-hide tents dot the landscape, the smoke from cooking fires rises into the open sky like tendrils. Men and women are at work repairing clothes, sharpening tools, tending to young children.").build();
            scene[2][4][6].putCreature(NPC.builder().name("Stranger").maxHealth(100).meleeAttack(6).luck(10).hostile(false).position(2,4).equip(Weapon.builder().name("Fellstaff").damage(16).luck(1).description("The staff is gnarled and heavy, but smooth from use. It seems to have been hewn from three tree limbs that grew together over time. As you pick it up, it vibrates angrily at your touch, then grows calm.").build()).equip(Armor.builder().name("Bramble Guard").armor(16).luck(1).description("You've never seen a set of armor like this. Its interlocking plates are hard and polished, but they seem to have been grown together rather than forged.").build()).build());


        // FOREST'S EDGE
            scene[5][4][d] = Scene.builder().name(forestEdge).disallowed(EnumSet.of(Direction.NE, Direction.SE, Direction.S)).description("The forest looms to the East as you approach the collection bones. The hulking carcass has been picked clean by scavengers and bleached by the relentless sun, but its large white horns reach up to the sky defiantly nonetheless. The grass seems to have retreated from the spot where it lies, as if recoiling from its foulness. You grow uneasy.").detail("Looking closer, you see a tangled leather object near the skull of the beast. It appears to be a bridle of some kind.").inventory(Item.builder().name("Bridle").description("You retrieve the trappings from the foul carcass. While the animal hide material is weathered and faded, it is surprisingly supple in your hands. Something stirs within you.").build()).build();
            scene[5][5][d] = Scene.builder().name(forestEdge).disallowed(of(Direction.NE, Direction.E)).build();


        // THE MURKY WOOD
            scene[6][4][d] = Scene.builder().name(murkyWood).allowed(EnumSet.of(Direction.W, Direction.NW)).description("Creeping into the murky wood, you feel the damp clinging to your tunic. Eerie sounds from creatures you'd rather not meet come from the depths. The trees grow denser as you go, all but blotting out the daylight. Strange flora curl around the roots of the massive trees, and more than once you think you see them moving at the edge of your vision. They seem to be trying to block your path.").detail("It seems these plants are trying to dissuade you from entering any deeper. They are closing in from all around, and you realize that your only way forward might be back the way you came...If you're lucky.").build();


        // THE PLAIN
            scene[3][6][d] = Scene.builder().name(plain).disallowed(EnumSet.of(Direction.W, Direction.SW)).climb(Direction.W, Vertical.UP).description("Flanked to the West by sandy cliffs overhead, the plain extends for miles ahead of you. Endless seas of grassThe same mountains you saw to the North are no closer than they were before. A great black river runs to the East. It seems too swift and wide to cross.").build();
            scene[4][6][d] = Scene.builder().name(scene[3][6][d].getName()).description(scene[3][6][d].getDescription()).detail(scene[3][6][d].getDetail()).build();
            scene[5][6][d] = Scene.builder().name(river).disallowed(EnumSet.range(Direction.NE, Direction.SE)).description("To the East, a river runs high and swift from a recent storm. The remains of an abandoned camp at the riverbank have nearly been overwhelmed by the surge waters, but there may be something of use here yet.").detail("A small canvas satchel lies near the waterlogged fire pit. From the weight you can tell it is not empty.").inventory(Weapon.builder().name("Curious Dagger").damage(12).luck(1).description("The dagger is heavier than it appears. Its cold metal stirs beneath the surface, moving like shadows in fog.").build()).build();
            scene[2][7][d] = Scene.builder().name(plain).disallowed(EnumSet.range(Direction.S, Direction.W)).climb(Direction.W, Vertical.UP).description(plain + " is ").build();
            scene[3][7][d] = Scene.builder().name(plain).disallowed(of(Direction.SW)).description(scene[2][7][d].getDescription()).build();
            scene[4][7][d] = Scene.builder().name(plain).description("As you scan the horizon, you glimpse a roiling cloud of dust heading in your direction. At the head of the cloud is a stampede of bizarre four-legged creatures – equine and dun-colored with two large horns, flowing silver manes, and iridescent black scales down their backs and tails.").detail("Judging by their horns and their size, these may be the creatures whose remains you saw in the clearing.").build();
            scene[5][7][d] = Scene.builder().name(plain).disallowed(EnumSet.range(Direction.NE, Direction.SE)).description(scene[4][7][d].getDescription()).detail(scene[4][7][d].getDetail()).build();


        // THE STEPPE
//            scene[5][7] = Scene.builder().name("The Steppe").build();
//                    ("The Steppe", true, false, true, true);
//            scene[5][7].description = "You cautiously continue toward the raging animals, fully aware they could crush you at any moment.";
//            scene[5][7].creatures.put("Buffalo", new Creature("Buffalo", 25, 10, 0, 0, new Item("horns", 10, 0 ,0, "The creature's horns are alabaster-white and surprisingly sharp. Maybe they could be used as a weapon?")));

    }

}
