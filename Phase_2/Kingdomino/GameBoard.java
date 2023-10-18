/**
 * Class: CMSC495 Date: 23 AUG 2023 Creator: Alan Anderson Team Members: Alan Anderson, William
 * Feighner, Michael Wood Jr., Ibadet Mijit, Jenna Seipel File: GameBoard.java Description: This
 * java file contains the GameBoard class that represents the players game board in a game of
 * Kingdomino. The board is laid out in a grid upon which domino tiles will be placed. The game
 * board is scored at the end of the game determine the game's winner.
 */

import java.util.ArrayList;

public class GameBoard {

  private final int MAX_WIDTH = 9; // initial width of game board
  private final int MAX_HEIGHT = 9; // initial height of game board
  private final Space[][] spaces; // representation of grid spaces on game board
  private ArrayList<ArrayList<Space>> scoredSpaces; // array list of spaces scored
  private int scoredSpacesCurrentLevel =
      0; // current scoring level; used to group spaces for scoring
  private int currentScore = 0; // current score for the game board

  // constructor
  public GameBoard() {
    spaces =
        new Space[MAX_WIDTH][MAX_HEIGHT]; // create matrix of spaces based on max height and width
    for (int i = 0; i < MAX_WIDTH; i++) {
      for (int k = 0; k < MAX_HEIGHT; k++) {
        spaces[i][k] = new Space(LandType.EMPTY, i, k); // set each space on board to empty type
      }
    }
    spaces[4][4].setSType(LandType.CASTLE); // set castle in middle of game board
  }

  // methods

  // resets scored flag of each game board space to false; called after checking
  // scoring
  public void resetSpacesScoredBooleanCheck() {
    for (int i = 0; i < MAX_WIDTH; i++) {
      for (int k = 0; k < MAX_HEIGHT; k++) {
        spaces[i][k].setScored(false);
      }
    }
  }

/*
  public int[] getPlayArea() {
    int minY = 10;
    int maxY = -1;
    int minX = 10;
    int maxX = -1;


    for (int x = 0; x < MAX_WIDTH; x++) {
      for (int y = 0; y < MAX_HEIGHT; y++) {
        if (spaces[x][y].getSType() != LandType.EMPTY) { // if space is occupied
          if (x < minX) {
            minX = x;
          }
          if (x > maxX) {
            maxX = x;
          }
          if (y < minY) {
            minY = y;
          }
          if (y > maxY) {
            maxY = y;
          }
        }
      }
    }

    int newMinX = maxX - 4;
    int newMaxX = minX + 4;
    int newMinY = maxY - 4;
    int newMaxY = minY + 4;

    return new int[]{newMinX, newMaxX, newMinY, newMaxY};
  }
*/

  public int[] getPlayArea() {
    int minY = 4;
    int maxY = 4;
    int minX = 4;
    int maxX = 4;

    for (int xIndex = 0; xIndex < MAX_WIDTH; xIndex++) {
      for (int yIndex = 0; yIndex < MAX_HEIGHT; yIndex++) {
        if (spaces[xIndex][yIndex].getSType() != LandType.EMPTY) {
          minX = Math.min(minX, xIndex);
          maxX = Math.max(maxX, xIndex);
          minY = Math.min(minY, yIndex);
          maxY = Math.max(maxY, yIndex);
        }
      }
    }
    int newMinX, newMaxX, newMinY, newMaxY;
    if (maxX - minX <= 2) {
      newMinX = minX - 2;
      newMaxX = maxX + 2;
    } else if (maxX - minX == 3) {
      newMinX = minX - 1;
      newMaxX = maxX + 1;
    } else {
      newMinX = minX;
      newMaxX = maxX;
    }
    if (maxY - minY <= 2) {
      newMinY = minY - 2;
      newMaxY = maxY + 2;
    } else if (maxY - minY == 3) {
      newMinY = minY - 1;
      newMaxY = maxY + 1;
    } else {
      newMinY = minY;
      newMaxY = maxY;
    }
    return new int[]{newMinX, newMaxX, newMinY, newMaxY};
  }

  // calculates current game board score through recursively calling scoreAll(x,
  // y); returns score
  public void calculateCurrentScore(boolean debug) {
    scoredSpaces = new ArrayList<>(); // reset array list
    scoredSpacesCurrentLevel = 0; // reset scoring level
    for (int k = 0; k < MAX_WIDTH; k++) {
      for (int i = 0; i < MAX_HEIGHT; i++) {
        if (!this.getGameBoardSpace(i, k).getScored()) {
          scoredSpaces.add(new ArrayList<>());
          // System.out.println("Size: " + scoredSpaces.size());
          scoreAll(i, k);
          scoredSpacesCurrentLevel++;
        }
      }
    }

    if (debug) {
      this.debugGameBoardPrint(); // print game board tile info if in debug mode
    }

    this.currentScore = getTotalScore(); // set this game board's score as the calculated score
    this.resetSpacesScoredBooleanCheck(); // reset "scored" flag on each game board space
  }

  private int getTotalScore() {
    int totalScore = 0; // capture total score
    for (ArrayList<Space> a :
        scoredSpaces) { // for each group of tiles grouped together in a score group
      int totalCrowns = 0; // prepare to capture the total number of crowns
      int totalSpaces = a.size(); // capture the total number of spaces in this group
      for (Space s : a) {
        totalCrowns += s.getNumCrowns(); // get all crowns from each space in this score group
      }
      totalScore +=
          totalCrowns * totalSpaces; // multiply crowns and spaces in group; add to total score
    }
    return totalScore;
  }

  // returns the current score of this game board
  public int getCurrentScore() {
    calculateCurrentScore(false);
    return this.currentScore;
  }

  // returns the current score of this game board and extra debug information
  public int getCurrentScoreDebug() {
    calculateCurrentScore(true);
    return this.currentScore;
  }

  // debug printing to show game board tile groupings
  public void debugGameBoardPrint() {
    int groupNumber = 1; // current tile score group #
    for (ArrayList<Space> a : scoredSpaces) { // for each group of score group tiles
      int totalCrowns = 0; // prepare to capture the total crowns
      int totalSpaces = a.size(); // capture the total number of spaces in this score group
      System.out.println("Group # " + groupNumber); // print debug info
      for (Space s : a) { // then for each space in this score group
        System.out.println(s.toString() + "(" + s.getXLoc() + "," + s.getYLoc() + ")");
        totalCrowns += s.getNumCrowns(); // print info and add crowns to total crowns
      }
      System.out.println("Group Score: " + (totalCrowns * totalSpaces));
      System.out.println();
      groupNumber++; // increase group number for print purposes
    }
  }

  public void scoreAll(int startX, int startY) {
    // if space to be scored is outside of game board, don't check it
    if (startX > MAX_WIDTH - 1 || startX < 0 || startY > MAX_HEIGHT - 1 || startY < 0) {
      return;
    }

    // if space is already scored, don't check it
    if (this.getGameBoardSpace(startX, startY).getScored()) {
      return;
    }

    // if this space type is different than other space types in this score group,
    // don't check it
    for (Space s : scoredSpaces.get(scoredSpacesCurrentLevel)) {
      if (!scoredSpaces.get(scoredSpacesCurrentLevel).isEmpty()
          && this.getGameBoardSpace(startX, startY).getSType() != s.getSType()) {
        return;
      }
    }

    this.getGameBoardSpace(startX, startY).setScored(true); // flag this space as scored
    // add this space to the current score group
    this.scoredSpaces.get(scoredSpacesCurrentLevel).add(this.getGameBoardSpace(startX, startY));
    // then spread out and check each space above, below, to the left and right of
    // this space
    this.scoreAll(startX + 1, startY);
    this.scoreAll(startX, startY + 1);
    this.scoreAll(startX - 1, startY);
    this.scoreAll(startX, startY - 1);
  }

  // getters
  public Space[][] getGameBoardSpaces() {
    return spaces;
  }

  public Space getGameBoardSpace(int xLoc, int yLoc) {
    return spaces[xLoc][yLoc];
  }

  public int getWidth() {
    // attributes
    // max width of game board
    return 5;
  }

  public int getHeight() {
    // max height of game board
    return 5;
  }

  // subclasses
  static class Space {

    private LandType sType; // type of land on this space
    private int xLoc; // x location of this space on game board
    private int yLoc; // y location of this sapce on game board
    private int numCrowns; // number of crowns this space has
    private boolean scored; // whether this space has been scored

    public Space(LandType sType, int xLoc, int yLoc) {
      this.setXLoc(xLoc);
      this.setYLoc(yLoc);
      this.setSType(sType);
      this.setNumCrowns(0);
      this.setScored(false);
    }

    // getters
    public int getXLoc() {
      return this.xLoc;
    }

    // setters
    public void setXLoc(int xLoc) {
      this.xLoc = xLoc;
    }

    public int getYLoc() {
      return this.yLoc;
    }

    public void setYLoc(int yLoc) {
      this.yLoc = yLoc;
    }

    public LandType getSType() {
      return sType;
    }

    public void setSType(LandType sType) {
      this.sType = sType;
    }

    public int getNumCrowns() {
      return this.numCrowns;
    }

    public void setNumCrowns(int numCrowns) {
      this.numCrowns = numCrowns;
    }

    public boolean getScored() {
      return this.scored;
    }

    public void setScored(boolean scored) {
      this.scored = scored;
    }

    @Override
    public String toString() {
      return this.getSType().getInitialName() + this.getNumCrowns() + "C";
    }
  }
}
