/**
 * Class: CMSC495 Date: 23 AUG 2023 Creator: Alan Anderson Team Members: Alan Anderson, William
 * Feighner, Michael Wood Jr., Ibadet Mijit, Jenna Seipel, Joseph Lewis File: Player.java
 * Description: The Player in a game of Kingdomino selects a domino each round, then places it on
 * their game board for later scoring. The player class maintains information about the player and
 * player order.
 */

public class Player {

  // attributes
  private String name = ""; // player's name
  private int currentOrder; // current round player turn order
  private int nextOrder; // next round turn order based on domino selection in the current round
  private GameBoard gameboard; // player's game board

  // constructor
  public Player(String name) throws InvalidPlayerNameException {
    if (isInvalidName(name)) {
      throw new InvalidPlayerNameException("invalid player name");
    }
    this.setPlayerName(name); // set player name if not invalid
    this.gameboard = new GameBoard(); // create new game board
  }

  // methods

  // this method checks whether player name is valid
  private boolean isInvalidName(String name) {
    return name.trim().isEmpty() || name.isEmpty();
  }

  // getters
  public String getPlayerName() {
    return this.name;
  }

  // setters
  public void setPlayerName(String name) {
    this.name = name;
  }

  public int getCurrentOrder() {
    return this.currentOrder;
  }

  public void setCurrentOrder(int currentOrder) {
    this.currentOrder = currentOrder;
  }

  public int getNextOrder() {
    return this.nextOrder;
  }

  public void setNextOrder(int nextOrder) {
    this.nextOrder = nextOrder;
  }

  public GameBoard getPlayerGameBoard() {
    return this.gameboard;
  }

  public void setPlayerGameBoard(GameBoard gameboard) {
    this.gameboard = gameboard;
  }
}
