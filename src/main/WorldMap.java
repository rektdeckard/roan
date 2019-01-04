package main;

class WorldMap {

    // Initialization of map coordinate array
    int mapWidth = 9;
    int mapHeight = 9;
    Scene[][] location = new Scene[mapWidth][mapHeight];

    WorldMap() {
        // MAP DATA
        int i, j;
        for (i = 0; i < mapWidth; i++) {
            for (j = 0; j < mapHeight; j++) {
                location[i][j] = new Scene();
            }
        }

        { // (5,5) Starting scene
            location[5][5] = new Scene();
            location[5][5].description = "You are in a sparse clearing surrounded by dry grasses and scrub. The clearing is bounded to the South and West by towering cliffs of red, sandy stone. To the East lie the massive skeletal remains of some ancient creature, and beyond, a dark forest. To the North the clearing descends gradually to a desolate plain. In the distance you see a mass of snowy mountains stretching across the horizon.";
        }

        { // (4,5) and (5, 4) Rock face
            location[4][5] = new Scene("The Rock Face", true, true, false, false);
            location[4][5].climb = true;
            location[4][5].description = "As you approach the rock face, you see its surface is pockmarked with small holes that would make perfect climbing holds. The cliff is sheer, but you think you could manage the climb. Looking up, you sight the face of a young girl watching you from the top of the bluff. Her face is streaked with dark paint, and as your eyes meet she is startled and withdraws quickly from view.";
            location[4][5].succeed = "You successfully scale the sandy cliff, and pulling yourself over the lip you emerge onto a wide plateau.";
            location[4][5].fail = "Part-way up the sandy cliff, you lose your grip and fall into the void. For a brief second you hear the faint sound of music, then you hit the ground with a hard crack.";
            // Duplicate area
            location[5][4] = location[4][4] = location[4][5];
        }

        { // (3, 5) Kerosi Encampment
            location[3][5] = new Scene("The Kerosi Encampment", true, true, true, true);
            location[3][5].use = true;
            location[3][5].description = "Spread out before you is an encampment nomadic peoples. Several dozen animal-hide tents dot the landscape, the smoke from cooking fires rises into the open sky like tendrils. Men and women are at work repairing clothes, sharpening tools, tending to young children.";
            location[3][5].creatures.put("Stranger", new NPC("Stranger", 100, 10, 10, 10, new Item("fellstaff", 16, 0, 0, "The staff is gnarled and heavy, but smooth from use. It seems to have been hewn from three tree limbs that grew together over time. As you pick it up, it vibrates angrily at your touch, then grows calm."), new Item("bramble guard", 16, 1, "You've never seen a set of armor like this. Its interlocking plates are hard and polished, but they seem to have been grown together rather than forged.")));

        }

        { // (6,5) Forest's Edge
            location[6][5] = new Scene("The Forest's Edge", true, true, false, true, new Item("bridle", "You retrieve the trappings from the foul carcass. While the animal hide material is weathered and faded, it is surprisingly supple in your hands. Something stirs within you."));
            location[6][5].description = "The forest looms over you as you approach the collection bones. The hulking carcass has been picked clean by scavengers and bleached by the relentless sun, but its large white horns reach up to the sky defiantly nonetheless. The grass seems to have retreated from the spot where it lies, as if recoiling from its foulness. You grow uneasy.";
            location[6][5].detail = "Looking closer, you see a tangled leather object near the skull of the beast. It appears to be a bridle of some kind.";
        }

        { // (7,5) The Murky Wood
            location[7][5] = new Scene("The Murky Wood", false, false, false, true);
            location[7][5].description = "Creeping into the murky wood, you feel the damp clinging to your tunic. Eerie sounds from creatures you'd rather not meet come from the depths. The trees grow denser as you go, all but blotting out the daylight. Strange flora curl around the roots of the massive trees, and more than once you think you see them moving at the edge of your vision. They seem to be trying to block your path.";
            location[7][5].detail = "It seems these plants are trying to dissuade you from entering any deeper. They are closing in from all around, and you realize that your only way forward might be back the way you came...If you're lucky.";
        }

        { // (5,6) The Plain
            location[5][6] = new Scene("The Plain", true, true, true, true);
            location[5][6].description = "Flanked to the West by the sandy cliffs overhead, the plain descends to a vast steppe dotted with dead-looking trees. A great black river runs to the East. It seems to swift and wide to cross. The same mountains you saw to the North are no closer than they were before. As you scan the horizon, you glimpse a roiling cloud of dust heading in your direction. At the head of the cloud is a stampede of bizarre four-legged creatures – equine and dun-colored with two large horns, flowing silver manes, and iridescent black scales down their backs and tails.";
            location[5][6].detail = "Judging by their horns and their size, these may be the creatures whose remains you saw in the clearing.";
            location[6][6] = location[5][6];
        }

        { // (5,7) The Steppe
            location[5][7] = new Scene("The Steppe", true, false, true, true);
            location[5][7].description = "You cautiously continue toward the raging animals, fully aware they could crush you at any moment.";
            location[5][7].creatures.put("Buffalo", new Creature("Buffalo", 25, 10, 0, 0, new Item("horns", 10, 0 ,0, "The creature's horns are alabaster-white and surprisingly sharp. Maybe they could be used as a weapon?")));
        }

        { // (7, 6) The River
            location[7][6] = new Scene("The River", true, false, false, true, new Item("dagger", 10, 0 ,0, "The dagger is heavier than it appears. Its cold metal stirs beneath the surface, reflecting the light like ripples on a pond."));
            location[7][6].description = "";
        }

    }

}
