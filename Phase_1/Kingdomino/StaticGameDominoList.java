import java.util.ArrayList;
import java.util.Random;

public class StaticGameDominoList {

  static public ArrayList<Domino> gameDominoList;// list of all dominos in game

  static {
    gameDominoList = new ArrayList<>();

    // then create dominos for the game and add them to the ArrayList
    gameDominoList.add(new Domino(1, LandType.WHEATFIELD, 0, LandType.WHEATFIELD, 0));
    gameDominoList.add(new Domino(2, LandType.WHEATFIELD, 0, LandType.WHEATFIELD, 0));
    gameDominoList.add(new Domino(3, LandType.FOREST, 0, LandType.FOREST, 0));
    gameDominoList.add(new Domino(4, LandType.FOREST, 0, LandType.FOREST, 0));
    gameDominoList.add(new Domino(5, LandType.FOREST, 0, LandType.FOREST, 0));
    gameDominoList.add(new Domino(6, LandType.FOREST, 0, LandType.FOREST, 0));
    gameDominoList.add(new Domino(7, LandType.LAKE, 0, LandType.LAKE, 0));
    gameDominoList.add(new Domino(8, LandType.LAKE, 0, LandType.LAKE, 0));
    gameDominoList.add(new Domino(9, LandType.LAKE, 0, LandType.LAKE, 0));
    gameDominoList.add(new Domino(10, LandType.GRASSLAND, 0, LandType.GRASSLAND, 0));

    gameDominoList.add(new Domino(11, LandType.GRASSLAND, 0, LandType.GRASSLAND, 0));
    gameDominoList.add(new Domino(12, LandType.SWAMP, 0, LandType.SWAMP, 0));
    gameDominoList.add(new Domino(13, LandType.WHEATFIELD, 0, LandType.FOREST, 0));
    gameDominoList.add(new Domino(14, LandType.WHEATFIELD, 0, LandType.LAKE, 0));
    gameDominoList.add(new Domino(15, LandType.WHEATFIELD, 0, LandType.GRASSLAND, 0));
    gameDominoList.add(new Domino(16, LandType.WHEATFIELD, 0, LandType.SWAMP, 0));
    gameDominoList.add(new Domino(17, LandType.FOREST, 0, LandType.LAKE, 0));
    gameDominoList.add(new Domino(18, LandType.FOREST, 0, LandType.GRASSLAND, 0));
    gameDominoList.add(new Domino(19, LandType.WHEATFIELD, 1, LandType.FOREST, 0));
    gameDominoList.add(new Domino(20, LandType.WHEATFIELD, 1, LandType.LAKE, 0));

    gameDominoList.add(new Domino(21, LandType.WHEATFIELD, 1, LandType.GRASSLAND, 0));
    gameDominoList.add(new Domino(22, LandType.WHEATFIELD, 1, LandType.SWAMP, 0));
    gameDominoList.add(new Domino(23, LandType.WHEATFIELD, 1, LandType.MINE, 0));
    gameDominoList.add(new Domino(24, LandType.FOREST, 1, LandType.WHEATFIELD, 0));
    gameDominoList.add(new Domino(25, LandType.FOREST, 1, LandType.WHEATFIELD, 0));
    gameDominoList.add(new Domino(26, LandType.FOREST, 1, LandType.WHEATFIELD, 0));
    gameDominoList.add(new Domino(27, LandType.FOREST, 1, LandType.WHEATFIELD, 0));
    gameDominoList.add(new Domino(28, LandType.FOREST, 1, LandType.LAKE, 0));
    gameDominoList.add(new Domino(29, LandType.FOREST, 1, LandType.GRASSLAND, 0));
    gameDominoList.add(new Domino(30, LandType.LAKE, 1, LandType.WHEATFIELD, 0));

    gameDominoList.add(new Domino(31, LandType.LAKE, 1, LandType.WHEATFIELD, 0));
    gameDominoList.add(new Domino(32, LandType.LAKE, 1, LandType.FOREST, 0));
    gameDominoList.add(new Domino(33, LandType.LAKE, 1, LandType.FOREST, 0));
    gameDominoList.add(new Domino(34, LandType.LAKE, 1, LandType.FOREST, 0));
    gameDominoList.add(new Domino(35, LandType.LAKE, 1, LandType.FOREST, 0));
    gameDominoList.add(new Domino(36, LandType.WHEATFIELD, 0, LandType.GRASSLAND, 1));
    gameDominoList.add(new Domino(37, LandType.LAKE, 0, LandType.GRASSLAND, 1));
    gameDominoList.add(new Domino(38, LandType.WHEATFIELD, 0, LandType.SWAMP, 1));
    gameDominoList.add(new Domino(39, LandType.GRASSLAND, 0, LandType.SWAMP, 1));
    gameDominoList.add(new Domino(40, LandType.MINE, 1, LandType.WHEATFIELD, 0));

    gameDominoList.add(new Domino(41, LandType.WHEATFIELD, 0, LandType.GRASSLAND, 2));
    gameDominoList.add(new Domino(42, LandType.LAKE, 0, LandType.GRASSLAND, 2));
    gameDominoList.add(new Domino(43, LandType.WHEATFIELD, 0, LandType.SWAMP, 2));
    gameDominoList.add(new Domino(44, LandType.GRASSLAND, 0, LandType.SWAMP, 2));
    gameDominoList.add(new Domino(45, LandType.MINE, 2, LandType.WHEATFIELD, 0));
    gameDominoList.add(new Domino(46, LandType.SWAMP, 0, LandType.MINE, 2));
    gameDominoList.add(new Domino(47, LandType.SWAMP, 0, LandType.MINE, 2));
    gameDominoList.add(new Domino(48, LandType.WHEATFIELD, 0, LandType.MINE, 3));
  }

  // this method removes dominos based on the number of players according to Kingdomino rules
  public static void removeDominosFromList(int playerCount) {
    int dominosToRemove; // number of dominos to remove; initialized at 0
    int removeCount = 0; // current number of dominos removed
    Random rand = new Random(); // random domino to remove from ArrayList

    switch (playerCount) {
      case 2 -> { // if two players
        dominosToRemove = 24; // remove 24 dominos for 2 players
        while (removeCount < dominosToRemove) { // keep removing dominos until 24 have been removed
          int randomRemove = rand.nextInt(gameDominoList.size());
          gameDominoList.remove(randomRemove);
          removeCount++;
        }
      }
      case 3 -> { // if three players
        dominosToRemove = 12; // remove 12 dominos for 2 players
        while (removeCount < dominosToRemove) { // keep removing dominos until 12 have been removed
          int randomRemove = rand.nextInt(gameDominoList.size());
          gameDominoList.remove(randomRemove);
          removeCount++;
        }
      }
    }
  }

  public static ArrayList<Domino> getDominosList() {
    return gameDominoList;
  }
}
