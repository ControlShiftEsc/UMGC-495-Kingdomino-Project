/**
 * Class: CMSC495 Date: 23 AUG 2023 Creator: Alan Anderson Team Members: Alan Anderson, William
 * Feighner, Michael Wood Jr., Ibadet Mijit, Jenna Seipel, Joseph Lewis File: TextBasedGame.java
 * Description: This java file contains the project information, authors, start date, etc.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class TextBasedKingdomino {

  // attributes
  private final Scanner menuScanner = new Scanner(System.in); // scanner for user input
  private int playerCount; // total number of players
  private ArrayList<Player> playerList; // list of players
  private ArrayList<Domino> gameDominoList = new ArrayList<>(); // list of all dominos in game

  public static void main(String[] args) {
    TextBasedKingdomino kingdomino = new TextBasedKingdomino();
    kingdomino.displayTitle();
  }

  // methods

  // displays the group name and title of the project
  public void displayTitle() {

    String[] title = new String[6];
    // ASCII Text generated from:
    // https://patorjk.com/software/taag/
    title[0] = " ____  __.__                   .___             .__       .__";
    title[1] = "|    |/ _|__| ____    ____   __| _/____   _____ |__| ____ |__| ____ ";
    title[2] = "|      < |  |/    \\  / ___\\ / __ |/  _ \\ /     \\|  |/    \\|  |/  _ \\  ";
    title[3] = "|    |  \\|  |   |  \\/ /_/  > /_/ (  <_> )  Y Y  \\  |   |  \\  (  <_> )";
    title[4] = "|____|__ \\__|___|  /\\___  /\\____ |\\____/|__|_|  /__|___|  /__|\\____/";
    title[5] = "        \\/       \\//_____/      \\/            \\/        \\/     ";

    System.out.println(ProjectInfo.getGroupName() + " presents...");
    for (String s : title) {
      System.out.println(s);
    }
    System.out.println("\n" + ProjectInfo.getTitle() + "\n");

    initializeKingdomino();
  }

  // initializes the game, starts, the ends it
  public void initializeKingdomino() {
    this.promptNumberPlayers();
    this.createPlayers();
    this.displayPlayerNames();
    System.out.println("\nThe player order for this game is: \n"); // white space for readability
    this.randomizePlayerOrder();
    System.out.println(); // white space for readability
    // create master domino list, then remove some based on player count
    this.gameDominoList = StaticGameDominoList.getDominosList();
    StaticGameDominoList.removeDominosFromList(playerCount);

    /*
     * //debug text for showing which dominos are in game
     * for (Domino d : StaticGameDominoList.getDominosList()) {
     * System.out.println(d.toString());
     * }
     */

    this.startGame();
    this.endGame();
  }

  /**
   * Prompts the user for the number of players; sets number of players
   */
  public void promptNumberPlayers() {
    System.out.println("Choose the number of players: 2, 3, or 4");
    while (menuScanner.hasNextLine()) {
      try {
        setPlayerCount(Integer.parseInt(menuScanner.nextLine()));
        if (this.playerCount < 2 || this.playerCount > 4) {
          throw new InvalidPlayerNumberException("invalid player number");
        } else {
          break;
        }
      } catch (NumberFormatException e) {
        System.out.println("Error: invalid number format (must be integer).");
      } catch (InvalidPlayerNumberException e2) {
        System.out.println("Error: invalid player number (must be 2 - 4).");
      }
    }
  }

  public void createPlayers() {
    ArrayList<Player> newPlayerList = new ArrayList<>();

    int currentPlayer = 1;
    while (currentPlayer <= getPlayerCount()) {
      System.out.println("Enter the name for Player #" + currentPlayer + ":");
      while (menuScanner.hasNextLine()) {
        try {
          newPlayerList.add(new Player(menuScanner.nextLine()));
          currentPlayer++;
          break;
        } catch (InvalidPlayerNameException e) {
          System.out.println("Error: player name can't be blank");
        }
      }
    }

    // in a 2 player game, each player controls one board but selects two dominoes
    // each round
    // adds additional "Players" who control the same gameboards as players 1 & 2
    if (getPlayerCount() == 2) {
      try {
        newPlayerList.add(new Player(newPlayerList.get(0).getPlayerName() + "#2"));
        newPlayerList.get(newPlayerList.size() - 1)
            .setPlayerGameBoard(newPlayerList.get(0).getPlayerGameBoard());
        newPlayerList.add(new Player(newPlayerList.get(1).getPlayerName() + "#2"));
        newPlayerList.get(newPlayerList.size() - 1)
            .setPlayerGameBoard(newPlayerList.get(1).getPlayerGameBoard());
      } catch (InvalidPlayerNameException e) {
        System.out.println("Error: player name can't be blank");
      }
    }
    this.setPlayerList(newPlayerList);
  }

  public void displayPlayerNames() {
    System.out.println("The players in this game are:\n");
    System.out.println(getPlayerNames());
  }

  public void randomizePlayerOrder() {
    ArrayList<Integer> order = new ArrayList<>();
    for (int i = 0; i < this.getPlayerList().size(); i++) {
      order.add(i);
    }

    Random rand = new Random();
    for (int k = 0; k < this.getPlayerList().size(); k++) {
      int selection = rand.nextInt(order.size());
      this.getPlayerList().get(k).setCurrentOrder(order.get(selection));
      order.remove(selection);
    }

    System.out.println(this.getPlayerCurrentOrder());
  }

  public void startGame() {

    int round = 1; // current round

    // current dominos to be selected for this round
    ArrayList<Domino> currentDominoSelection = new ArrayList<>();

    Random rand = new Random(); // chooses which dominos are available each round

    // while there are still dominos left to be selected in the game
    while (!this.getDominosList().isEmpty()) {
      System.out.println("////--------ROUND " + round + "--------\\\\\\\\\n");
      // pull 3 random dominoes for 3 players, else 4 dominoes
      if (this.getPlayerList().size() == 3) {
        for (int i = 0; i < this.getPlayerList().size(); i++) {
          int dominoToRemove = rand.nextInt(this.getDominosList().size());
          // get a random domino from the main list of dominos and add it to the currently
          // selectable list
          currentDominoSelection.add(this.getDominosList().get(dominoToRemove));
          this.getDominosList().remove(dominoToRemove); // then remove domino from main list
        }
      } else {
        for (int i = 0; i < 4; i++) {
          int dominoToRemove = rand.nextInt(this.getDominosList().size());
          // get a random domino from the main list of dominos and add it to the currently
          // selectable list
          currentDominoSelection.add(this.getDominosList().get(dominoToRemove));
          this.getDominosList().remove(dominoToRemove); // then remove domino from main list
        }
      }

      currentDominoSelection.sort(
          Comparator.comparing(Domino::getDominoNumber)); // order dominos by number
      // set selection order for each domino, so it determines next round order when
      // player selects
      for (int i = 0; i < currentDominoSelection.size(); i++) {
        currentDominoSelection.get(i).setPlayerOrder(i);
      }

      this.getPlayerList()
          .sort(Comparator.comparing(Player::getCurrentOrder)); // order players by turn order

      // in turn order, players will select a domino and place it on their player
      // boards

      for (Player p : this.getPlayerList()) {
        int[] boardArea = p.getPlayerGameBoard().getPlayArea();
        System.out.println("It's " + p.getPlayerName() + "'s turn!\n");

        // this next portion prints the current game board
        for (int y = boardArea[2]; y <= boardArea[3]; y++) { // for each y
          for (int x = boardArea[0]; x <= boardArea[1]; x++) { // for each x
            System.out.print(
                "  " + p.getPlayerGameBoard().getGameBoardSpace(x, y).toString() + "  ");
          }
          System.out.println("\n");
        }

        System.out.println();

        boolean dominoSelected = false; // flag for selecting domino
        int selection = 0; // domino selected by player from list

        // loop to capture player selecting a domino
        while (!dominoSelected) {
          System.out.println("Choose a tile: ");
          for (int k = 0; k < currentDominoSelection.size(); k++) {
            System.out.println((k + 1) + ": " + currentDominoSelection.get(k));
          }

          // capture the player's choice for their selected domino
          while (menuScanner.hasNextLine()) {
            try {
              selection = Integer.parseInt(menuScanner.nextLine());
              if (selection < 0 || selection > currentDominoSelection.size()) {
                throw new IllegalArgumentException("Error: selection not in range");
              }
              dominoSelected = true; // domino successfully selected
              break;
            } catch (Exception e) {
              System.out.println("Error: invalid input");
            }
          }
        }

        // get domino selection based on list of dominos provided
        Domino selectedDomino = currentDominoSelection.get(selection - 1);
        // set the player's turn order for the next round based on the domino selected
        // this round
        p.setNextOrder(selectedDomino.getPlayerOrder());
        System.out.println("You have selected " + selectedDomino);

        // set location of selected domino on player's game board
        selectedDomino.getSideA().setXLoc(boardArea[0]);
        selectedDomino.getSideA().setYLoc(boardArea[2]);
        selectedDomino.getSideB().setXLoc(boardArea[0] + 1);
        selectedDomino.getSideB().setYLoc(boardArea[2]);

        // capture current location of selected domino
        int sax;
        int say;
        int sbx;
        int sby;

        // capture change in location to be applied to selected domino
        int xChangeA = boardArea[0];
        int yChangeA = boardArea[2];
        int xChangeB = boardArea[0] + 1;
        int yChangeB = boardArea[2];

        int option = 0; // capture user option selection
        boolean dominoPlaced = false; // flag whether the domino has been placed on player game board
        while (!dominoPlaced) { // while domino hasn't been placed
          // capture current location of domino on player game board
          sax = selectedDomino.getSideA().getXLoc();
          say = selectedDomino.getSideA().getYLoc();
          sbx = selectedDomino.getSideB().getXLoc();
          sby = selectedDomino.getSideB().getYLoc();

          // capture player board here to reduce line space in next block of code
          GameBoard.Space[][] playerBoardSpaces = p.getPlayerGameBoard().getGameBoardSpaces();

          System.out.println("Current board state with selected dominio: \n");

          // this next portion prints the current game board and current locatino of
          // domino to be played
          // the domino location is surrounded by brackets []
          // if the domino location is over another domino already placed on the board
          // then the domino has an asterisks * next to it
          for (int y = boardArea[2]; y <= boardArea[3]; y++) { // for each y
            for (int x = boardArea[0]; x <= boardArea[1]; x++) { // for each x
              if (sax == x
                  && say == y) { // if side a of the domino is here, display inside brackets
                System.out.print(" [" + selectedDomino.sideAToString() + "]");
                if (playerBoardSpaces[sax][say].getSType() != LandType.EMPTY) {
                  System.out.print("*");
                } else {
                  System.out.print(" ");
                }
              } else if (sbx == x && sby == y) { // if side b of the dimino is here, use brackets
                System.out.print(" [" + selectedDomino.sideBToString() + "]");
                if (playerBoardSpaces[sbx][sby].getSType() != LandType.EMPTY) {
                  System.out.print("*");
                } else {
                  System.out.print(" ");
                }
              } else {
                // otherwise just print the information that's in this space
                System.out.print("  " + playerBoardSpaces[x][y].toString() + "  ");
              }
              System.out.print("   ");
            }
            System.out.println("\n");
          }
          System.out.println();

          // display domino placement menu
          System.out.println("Select an action for the domino:");
          System.out.println("1: Rotate clockwise");
          System.out.println("2: Rotate counter clockwise");
          System.out.println("3: Move up");
          System.out.println("4: Move down");
          System.out.println("5: Move left");
          System.out.println("6: Move right");
          System.out.println("7: Place tile in current position");
          System.out.println("8: Discard domino");

          // continue loop until correct option is selected
          while (menuScanner.hasNextLine()) {
            try {
              option = Integer.parseInt(menuScanner.nextLine());
              if (option < 1 || option > 8) {
                throw new IllegalArgumentException("Error: selection not in range");
              }
              break;
            } catch (Exception e) {
              System.out.println("Error: invalid input");
            }
          }

          // temp varibles to track domino rotation
          int tempsbx;
          int tempsby;

          switch (option) {
            case 1 -> { // rotate clockwise
              if (say == sby) { // if domino is horizontal
                if (sax < sbx) { // if in original orientation
                  tempsby = sby + 1;
                  tempsbx = sbx - 1;
                } else { // otherwise, if flipped 180 degrees
                  tempsby = sby - 1;
                  tempsbx = sbx + 1;
                }

              } else { // if domino is flipped 90 or 270 degrees
                if (say < sby) {
                  tempsby = sby - 1;
                  tempsbx = sbx - 1;
                } else {
                  tempsby = sby + 1;
                  tempsbx = sbx + 1;
                }
              }

              // if rotation would cause any part of domino to be outside of game board range
              if (tempsby < boardArea[2] || tempsby > boardArea[3] ||
                  tempsbx < boardArea[0] || tempsbx > boardArea[1]) {
                System.out.println(
                    "Error: rotation will cause domino to fall outside of play area");
                break;
              }

              // capture locatoin changes from domino rotation
              xChangeA = sax;
              yChangeA = say;
              xChangeB = tempsbx;
              yChangeB = tempsby;
            }
            case 2 -> { // same as above, but counter clockwise
              if (say == sby) { // if domino is horizontal
                if (sax < sbx) { // if in original orientation
                  tempsby = sby - 1;
                  tempsbx = sbx - 1;
                } else { // otherwise, if flipped 180 degrees
                  tempsby = sby + 1;
                  tempsbx = sbx + 1;
                }

              } else { // if domino is flipped 90 or 270 degrees
                if (say < sby) {
                  tempsby = sby - 1;
                  tempsbx = sbx + 1;
                } else {
                  tempsby = sby + 1;
                  tempsbx = sbx - 1;
                }
              }
              if (tempsby < boardArea[2] || tempsby > boardArea[3] ||
                  tempsbx < boardArea[0] || tempsbx > boardArea[1]) {
                System.out.println(
                    "Error: rotation will cause domino to fall outside of play area");
                break;
              }
              xChangeA = sax;
              yChangeA = say;
              xChangeB = tempsbx;
              yChangeB = tempsby;
            }
            case 3 -> { // move domino up
              // don't move up if doing so moves it outside of game board range
              if (selectedDomino.getSideA().getYLoc() - 1 < boardArea[2] ||
                  selectedDomino.getSideB().getYLoc() - 1 < boardArea[2]) {
                System.out.println("Error: invalid move. Domino will be outside of play area");
              } else {
                xChangeA = selectedDomino.getSideA().getXLoc();
                yChangeA = selectedDomino.getSideA().getYLoc() - 1;
                xChangeB = selectedDomino.getSideB().getXLoc();
                yChangeB = selectedDomino.getSideB().getYLoc() - 1;
              }
            }
            case 4 -> { // move domino down
              // don't do down if moving outside of game board
              System.out.println(boardArea[3] + " " + (selectedDomino.getSideA().getYLoc() + 1));
              if (selectedDomino.getSideA().getYLoc() + 1 > boardArea[3] ||
                  selectedDomino.getSideB().getYLoc() + 1 > boardArea[3]) {
                System.out.println("Error: invalid move. Domino will be outside of play area");
              } else {
                xChangeA = selectedDomino.getSideA().getXLoc();
                yChangeA = selectedDomino.getSideA().getYLoc() + 1;
                xChangeB = selectedDomino.getSideB().getXLoc();
                yChangeB = selectedDomino.getSideB().getYLoc() + 1;
              }
            }
            case 5 -> { // move left
              if (selectedDomino.getSideA().getXLoc() - 1 < boardArea[0] ||
                  selectedDomino.getSideB().getXLoc() - 1 < boardArea[0]) {
                System.out.println("Error: invalid move. Domino will be outside of play area");
              } else {
                xChangeA = selectedDomino.getSideA().getXLoc() - 1;
                yChangeA = selectedDomino.getSideA().getYLoc();
                xChangeB = selectedDomino.getSideB().getXLoc() - 1;
                yChangeB = selectedDomino.getSideB().getYLoc();
              }
            }
            case 6 -> { // move right
              if (selectedDomino.getSideA().getXLoc() + 1 > boardArea[1] ||
                  selectedDomino.getSideB().getXLoc() + 1 > boardArea[1]) {
                System.out.println("Error: invalid move. Domino will be outside of play area");
              } else {
                xChangeA = selectedDomino.getSideA().getXLoc() + 1;
                yChangeA = selectedDomino.getSideA().getYLoc();
                xChangeB = selectedDomino.getSideB().getXLoc() + 1;
                yChangeB = selectedDomino.getSideB().getYLoc();
              }
            }
            case 7 -> { // place domino on board at current location
              // if both sides of domino are over a space location that's empty
              if (playerBoardSpaces[sax][say].getSType() == LandType.EMPTY &&
                  playerBoardSpaces[sbx][sby].getSType() == LandType.EMPTY &&
                  verifyMatchingSide(p.getPlayerGameBoard().getGameBoardSpaces(), sax, say, sbx,
                      sby,
                      selectedDomino)) {
                // place domino on the game board
                int crownsA = selectedDomino.getSideACrowns();
                int crownsB = selectedDomino.getSideBCrowns();
                LandType sideTypeA = selectedDomino.getSideA().getSType();
                LandType sideTypeB = selectedDomino.getSideB().getSType();

                p.getPlayerGameBoard().getGameBoardSpace(sax, say).setNumCrowns(crownsA);
                p.getPlayerGameBoard().getGameBoardSpace(sax, say).setSType(sideTypeA);
                p.getPlayerGameBoard().getGameBoardSpace(sbx, sby).setNumCrowns(crownsB);
                p.getPlayerGameBoard().getGameBoardSpace(sbx, sby).setSType(sideTypeB);

                currentDominoSelection.remove(selection - 1);
                dominoPlaced = true;
              } else {
                if (verifyMatchingSide(p.getPlayerGameBoard().getGameBoardSpaces(), sax, say, sbx,
                    sby,
                    selectedDomino)) {
                  System.out.println("Error: location not empty for domino placement");
                } else {
                  System.out.println(
                      "Error: At least one side of the tile must match an existing tile or castle.");
                }
              }
            }
            case 8 -> {
              // domino is discarded, go to next player
              currentDominoSelection.remove(selection - 1);
              dominoPlaced = true;
            }
          }

          // update domino locations if move or rotated
          selectedDomino.getSideA().setXLoc(xChangeA);
          selectedDomino.getSideA().setYLoc(yChangeA);
          selectedDomino.getSideB().setXLoc(xChangeB);
          selectedDomino.getSideB().setYLoc(yChangeB);
        }
      }

      round++;
      // set player orders for next round based on the selections they made this round
      for (Player p : this.getPlayerList()) {
        p.setCurrentOrder(p.getNextOrder());
      }
    }
  }

  /**
   * @param playerGameBoard Takes player gameboard
   * @param sax             Tile side A intended x coordinate
   * @param say             Tile side A intended y coordinate
   * @param sbx             Tile side B intended x coordinate
   * @param sby             Tile side B intended y coordinate
   * @param selectedDomino  selected domino for placement
   * @return whether selected domino placement has matching side on game board
   * <p>
   * Validates intended domino placement. One side of the selected domino terrains must match an
   * adjacent terrain already on the board or the starting castle.
   * <p>
   * Method takes the playerboard, selected domino and intended placement. Creates lists of
   * surrounding terrains of intended placement. Checks to see if terrain of either side of tile
   * matches at least one surrounding space of that side.
   */
  private boolean verifyMatchingSide(GameBoard.Space[][] playerGameBoard, int sax, int say, int sbx,
      int sby,
      Domino selectedDomino) {
    ArrayList<LandType> sideAOptions = new ArrayList<>();
    ArrayList<LandType> sideBOptions = new ArrayList<>();
    boolean sideAValid = false;
    boolean sideBValid = false;

    gatherSideTypes(playerGameBoard, sax, say, sideAOptions);

    gatherSideTypes(playerGameBoard, sbx, sby, sideBOptions);

    for (LandType sideAOption : sideAOptions) {
      if (sideAOption == selectedDomino.getSideA().getSType() || sideAOption == LandType.CASTLE) {
        sideAValid = true;
        break;
      }
    }

    for (LandType sideBOption : sideBOptions) {
      if (sideBOption == selectedDomino.getSideB().getSType() || sideBOption == LandType.CASTLE) {
        sideBValid = true;
        break;
      }
    }
    return sideAValid || sideBValid;
  }

  private void gatherSideTypes(GameBoard.Space[][] playerGameBoard, int x, int y,
      ArrayList<LandType> sideOptions) {
    if (x >= 1) {
      sideOptions.add(playerGameBoard[x - 1][y].getSType());
    }
    if (x < playerGameBoard.length - 1) {
      sideOptions.add(playerGameBoard[x + 1][y].getSType());
    }
    if (y >= 1) {
      sideOptions.add(playerGameBoard[x][y - 1].getSType());
    }
    if (y < playerGameBoard[0].length - 1) {
      sideOptions.add(playerGameBoard[x][y + 1].getSType());
    }
  }

  public void endGame() {
    System.out.println("\n////--------FINAL SCORING--------\\\\\\\\\n");

    playerList.sort(Comparator.comparing(x -> x.getPlayerGameBoard().getCurrentScore()));

    for (int px = playerList.size() - 1; px >= 0; px--) {
      System.out.println(playerList.get(px).getPlayerName() + "'s final score: "
          + playerList.get(px).getPlayerGameBoard().getCurrentScore());
    }
  }

  public int getPlayerCount() {
    return this.playerCount;
  }

  public void setPlayerCount(int playerCount) {
    this.playerCount = playerCount;
  }

  public ArrayList<Player> getPlayerList() {
    return this.playerList;
  }

  public void setPlayerList(ArrayList<Player> playerList) {
    this.playerList = playerList;
  }

  public ArrayList<Domino> getDominosList() {
    return this.gameDominoList;
  }

  public void setDominosList(ArrayList<Domino> gameDominosList) {
    this.gameDominoList = gameDominosList;
  }

  // method gets player names in a single string
  public String getPlayerNames() {
    StringBuilder playerListString = new StringBuilder();
    int count = 1;
    for (Player p : playerList) {
      if (count != playerList.size()) {
        playerListString.append("Player ").append(count).append(": ").append(p.getPlayerName())
            .append(", ");
        count++;
      } else {
        playerListString.append("Player ").append(count).append(": ").append(p.getPlayerName());
      }
    }
    return playerListString.toString();
  }

  // method gets the current order of players
  public String getPlayerCurrentOrder() {
    StringBuilder playerListString = new StringBuilder();

    for (int i = 0; i < this.getPlayerList().size(); i++) {
      for (Player p : this.getPlayerList()) {
        String orderText = switch (p.getCurrentOrder()) {
          case 0 -> "first";
          case 1 -> "second";
          case 2 -> "third";
          case 3 -> "fourth";
          default -> "";
        };

        if (p.getCurrentOrder() == i) {
          if (p.getCurrentOrder() == this.getPlayerList().size() - 1) {
            playerListString.append("and ");
          }

          playerListString.append(p.getPlayerName()).append(" is the ").append(orderText)
              .append(" player");

          if (p.getCurrentOrder() != this.getPlayerList().size() - 1) {
            playerListString.append(", ");
          } else {
            playerListString.append(".");
          }
        }
      }
    }

    return playerListString.toString();
  }

}
