/**
 * Class: CMSC495 Date: 23 AUG 2023 Creator: Alan Anderson Team Members: Alan Anderson, William
 * Feighner, Michael Wood Jr., Ibadet Mijit, Jenna Seipel File: TestBoardGameScoring.java
 * Description: This java file randomly generates a player board in a game of Kingdomino. Random
 * land types and crown amounts are set on each grid space of the board, then calculated for a final
 * score to test the functionality of the GameBoard class.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class TestGameBoardScoring {

  public static void main(String[] args) throws InvalidPlayerNameException {
    Random randPlayers = new Random();
    int randNumPlayers = randPlayers.nextInt(3) + 2;
    ArrayList<Player> playerList = new ArrayList<>(); // generate 2 to 4 players
    //GameBoard[] gb = new GameBoard[randNumPlayers]; // create a blank game board
    Random randType = new Random(); // set up the game board spaces to be randomized
    Random randCrowns = new Random(); // set up the crowns to be randomized
    LandType randTypeChoice = LandType.EMPTY; // set default random space type as empty

    for (int rnp = 1; rnp <= randNumPlayers; rnp++) {
      Player p = new Player("Player # " + rnp);
      playerList.add(p);

      for (int i = 0; i < p.getPlayerGameBoard().getWidth(); i++) { // for each space in the column
        for (int k = 0; k < p.getPlayerGameBoard().getHeight(); k++) { // for each space in the row
          int randPick = randType.nextInt(7); // select a random land type
          if (randPick == 0) {
            randTypeChoice = LandType.WHEATFIELD;
          }
          if (randPick == 1) {
            randTypeChoice = LandType.FOREST;
          }
          if (randPick == 2) {
            randTypeChoice = LandType.LAKE;
          }
          if (randPick == 3) {
            randTypeChoice = LandType.GRASSLAND;
          }
          if (randPick == 4) {
            randTypeChoice = LandType.SWAMP;
          }
          if (randPick == 5) {
            randTypeChoice = LandType.MINE;
          }
          if (randPick == 6) {
            randTypeChoice = LandType.EMPTY;
          }

          // then set the land type to the selection
          p.getPlayerGameBoard().getGameBoardSpace(i, k).setSType(randTypeChoice);
          // randomize number of crowns
          p.getPlayerGameBoard().getGameBoardSpace(i, k).setNumCrowns(randCrowns.nextInt(4));
        }
      }

      // randomly place castle somewhere on player board
      Random castleXY = new Random();
      int castleX = castleXY.nextInt(5);
      int castleY = castleXY.nextInt(5);
      p.getPlayerGameBoard().getGameBoardSpace(castleX, castleY).setSType(LandType.CASTLE);
      p.getPlayerGameBoard().getGameBoardSpace(castleX, castleY).setNumCrowns(0);

      // then print the board
      for (int y = 0; y < p.getPlayerGameBoard().getHeight(); y++) {
        for (int x = 0; x < p.getPlayerGameBoard().getWidth(); x++) {
          System.out.print(p.getPlayerGameBoard().getGameBoardSpaces()[x][y].toString());
          System.out.print("  ");
        }
        System.out.println("\n");
      }
      System.out.println();

      // then print the score for the board and other debug info
      System.out.println("Current Score: " + p.getPlayerGameBoard().getCurrentScoreDebug());

      System.out.println("\n////--------FINAL SCORING--------\\\\\\\\\n");

      playerList.sort(Comparator.comparing(x -> x.getPlayerGameBoard().getCurrentScore()));

      for (int px = playerList.size() - 1; px >= 0; px--) {
        System.out.println(playerList.get(px).getPlayerName() + "'s final score: "
            + playerList.get(px).getPlayerGameBoard().getCurrentScore());
      }

    }
  }
}
