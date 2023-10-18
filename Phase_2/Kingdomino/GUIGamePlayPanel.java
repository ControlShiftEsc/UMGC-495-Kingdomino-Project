/**
 * Class: CMSC495 Date: 2 SEP 2023 Creator: Alan Anderson & William Feighner Editor/Revisor: William
 * Feighner Team Members: Alan Anderson, , Michael Wood Jr., Ibadet Mijit, Jenna Seipel, Joseph
 * Lewis File: GUIGamePlayPanel.java Description: This class maintains the gampeplay logic and
 * display layout for the main portion of the game.
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class GUIGamePlayPanel extends JPanel {

  // attributes
  private static final long serialVersionUID = 1L;

  // game play attributes
  boolean initialized = false; // flag whether this JPanel has been initialized
  ArrayList<GUIGameBoard> gameBoardPanel; // array of gui player game boards in game
  ArrayList<Domino> dominoList; // list of dominos in game
  ArrayList<GUIPlayer> playerOrderList; // player order list
  ArrayList<Domino> currentDominoSelection;
  Domino selectedDomino; // the current domino selected
  int currentPlayerNumber; // current player number; corresponds to playerList
  GUIPlayer currentPlayer; // current player as a player object
  Random rand; // random number generator
  int dominosPlayedThisRound = 0; // number of dominos played in the current round
  int round = 1; // the current round number
  // top middle
  JPanel statusPanel; // sits at top of screen under logo; shows the current status of the round

  //display set up - below are a list of panels and their corresponding sub panels
  JLabel statusLabel; // text to display in status panel
  ArrayList<String> statusText; // array of messages for status panel
  // left side panel
  JPanel leftSidePanel; // left side panel houses the player board and buttons to view other player boards
  // top left side panel
  JPanel leftSidePanelSubPanel1; // sits at top of left side panel
  JLabel viewPlayerBoardLabel; // holds buttons to switch between player boards; housed in leftSidePanelSubPanel1
  JLabel playerCurrentViewLabel; // label next to player view buttons showing the curently viewed player
  ArrayList<JButton> viewBoardButtons; // list of buttons to view other player boards
  // bottom left side panel
  JPanel leftSidePanelSubPanel2; // sits at the bottom of the left side panel
  JPanel boardPanelDisplay; // holds all game boards in card layout format; contained in leftSidePanelSubPanel2
  CardLayout boardPanelDisplayCard; // card laoyout for boardPanelDisplay
  // right side panel
  JPanel rightSidePanel;
  // right top side panel
  JPanel rightSidePanelSubPanel1;
  JPanel dominoPanel; // shows dominos to be played; contained in rightSidePanelSubPanel1
  JLabel dominoPanelLabel; // label for domino buttons
  ArrayList<JButton> dominoButtons; // list of dominos that will be selected during game play, as buttons
  ArrayList<JLabel> dominoButtonLabels; // labels for the domino buttons
  boolean[] dominoButtonSelected; // flag whether domino button has been selected
  boolean[] dominoButtonPlayed; // flag whether domino button has been played in the current round
  int selectedDominoNumber; // the number of the domino selected; corresponds to dominoButtons ArrayList
  // right bottom side panel
  JPanel rightSidePanelSubPanel2;
  JPanel actionPanel; // holds buttons for actions; contained in rightSidePanelSubPanel2
  JLabel actionPanelLabel; // label for action buttons
  ArrayList<JButton> actionButtons; // list of actions available to the player during game play
  private boolean gameEnded = false; // flag whether the game has ended

  public void initialize() {
    if (initialized) {
      return;
    } // don't initialize if already initialized
    initialized = true; // set initialized to true
    this.removeAll(); // remove all sub components

    this.playerOrderList = new ArrayList<GUIPlayer>(); // create new player order list
    this.dominoList = new ArrayList<Domino>(
        StaticGameDominoList.getDominosList()); // game domino list
    rand = new Random(); // random number generator

    // set the main panel to grid bag layout
    this.setLayout(new GridBagLayout()); // set layout
    this.setBackground(Color.BLACK);
    GridBagConstraints c = new GridBagConstraints();

    // BEGIN set up status bar text at the top of the screen under the logo
    statusText = new ArrayList<String>();
    statusText.add("Round"); // 0 current round
    statusText.add("Player Turn"); // 1 current player's turn
    statusText.add("Current Action"); // 2 current action
    statusText.add(""); // 3 error message

    statusPanel = new JPanel();
    statusPanel.setBackground(Color.WHITE);
    statusPanel.setLayout(new BorderLayout());
    statusLabel = new JLabel("Status updates here");
    statusLabel.setFont(
        new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
    statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    statusPanel.add(statusLabel, BorderLayout.CENTER);
    statusPanel.setToolTipText("Displays the current status of the game");

    // grid bag constraints for status panel
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1;
    c.weighty = 0;
    c.gridwidth = 2;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.NORTH;
    this.add(statusPanel, c);
    // END status label setup

    // BEGIN set up of left side panel
    // This portion sets up the player view tabs, currently viewed player label, and player boards
    leftSidePanel = new JPanel();
    leftSidePanel.setLayout(new GridBagLayout());
    leftSidePanel.setOpaque(false);
    GridBagConstraints leftSidePanelC = new GridBagConstraints();

    // gbc for top left panel
    leftSidePanelC.gridx = 0;
    leftSidePanelC.gridy = 0;
    leftSidePanelC.weightx = 1;
    leftSidePanelC.weighty = 0;
    leftSidePanelC.insets = new Insets(GUIResolutionController.convertScaleHeight(20),
        GUIResolutionController.convertScaleWidth(50), 0, 0);
    leftSidePanelC.fill = GridBagConstraints.NONE;
    leftSidePanelC.anchor = GridBagConstraints.NORTH;

    leftSidePanelSubPanel1 = new JPanel();
    leftSidePanelSubPanel1.setLayout(new GridBagLayout());
    leftSidePanelSubPanel1.setBackground(Color.GREEN);
    leftSidePanelSubPanel1.setOpaque(false);
    leftSidePanel.add(leftSidePanelSubPanel1, leftSidePanelC);
    GridBagConstraints leftSidePanelSubPanelC = new GridBagConstraints();

    // gbc for top left sub panel
    leftSidePanelSubPanelC.gridx = 0;
    leftSidePanelSubPanelC.gridy = 0;
    leftSidePanelSubPanelC.weightx = 1;
    leftSidePanelSubPanelC.weighty = 0;
    leftSidePanelSubPanelC.gridwidth = 4;
    leftSidePanelSubPanelC.anchor = GridBagConstraints.NORTH;
    leftSidePanelSubPanelC.fill = GridBagConstraints.NONE;
    //leftSidePanelSubPanelC.anchor = GridBagConstraints.NORTH;

    // setup for player board view buttons at top left
    viewPlayerBoardLabel = new JLabel("[Player Boards]");
    //dominoPanelLabel.setBackground(Color.MAGENTA);
    viewPlayerBoardLabel.setOpaque(false);
    viewPlayerBoardLabel.setHorizontalAlignment(SwingConstants.CENTER);
    viewPlayerBoardLabel.setFont(
        new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
    leftSidePanelSubPanel1.add(viewPlayerBoardLabel, leftSidePanelSubPanelC);

    leftSidePanelSubPanelC.gridy++;
    //leftSidePanelSubPanelC.ipady = (int)(40/scale);
    leftSidePanelSubPanelC.gridwidth = 1;

    // creat each button, add to list, and position on sub panel
    viewBoardButtons = new ArrayList<JButton>();
    ViewMouseListener ml = new ViewMouseListener();
    for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
      JButton button = new JButton(
          GUIGameController.getPlayerList().get(i).getPlayerName() + " (0pts)");
      button.setBackground(GUIGameController.getPlayerColors(i));
      button.addMouseListener(ml);
      button.setBorderPainted(false);
      button.setHorizontalTextPosition(JButton.CENTER);
      button.setVerticalTextPosition(JButton.CENTER);
      button.setFocusPainted(false);
      button.setToolTipText("Click to display " + button.getText() + "'s board");
      button.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(40)));
      viewBoardButtons.add(button);
      leftSidePanelSubPanel1.add(button, leftSidePanelSubPanelC);
      leftSidePanelSubPanelC.gridx++;
    }

    leftSidePanelC.gridx = 0;
    leftSidePanelC.gridy = 1;
    leftSidePanelC.weightx = 1;
    leftSidePanelC.weighty = 1;
    leftSidePanelC.insets = new Insets(0, GUIResolutionController.convertScaleWidth(50), 0, 0);
    leftSidePanelC.fill = GridBagConstraints.NONE;
    leftSidePanelC.anchor = GridBagConstraints.NORTH;

    // BEGIN set up board panel sub panel 2
    leftSidePanelSubPanel2 = new JPanel();
    leftSidePanelSubPanel2.setLayout(new BorderLayout());
    //leftSidePanelSubPanel2.setOpaque(false);
    leftSidePanelSubPanel2.setBackground(new Color(255, 0, 0, 0));
    leftSidePanel.add(leftSidePanelSubPanel2, leftSidePanelC);

    boardPanelDisplay = new JPanel();
    boardPanelDisplay.setPreferredSize(
        new Dimension(GUIResolutionController.convertScaleWidth(1500),
            GUIResolutionController.convertScaleWidth(1500)));
    boardPanelDisplay.setMaximumSize(boardPanelDisplay.getPreferredSize());
    boardPanelDisplayCard = new CardLayout();
    boardPanelDisplay.setLayout(boardPanelDisplayCard);

    // create a GUI game board for each player in the game
    for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
      boardPanelDisplay.add(GUIGameController.getPlayerList().get(i).getGUIGameBoard(),
          "" + i);
      GUIGameController.getPlayerList().get(i).getGUIGameBoard().setBackground(
          GUIGameController.getPlayerColors(i).darker());
      GUIGameController.getPlayerList().get(i).getGUIGameBoard().setCastleImage(i);
    }

    // if there are two players, adjust to the following setup
    if (GUIGameController.getPlayerCount() == 2) {
      // set player 3 and 4's gameboard to be the same as player 1 and 2's respectively
      try {
        GUIGameController.getPlayerList().add(new GUIPlayer("x"));
        GUIGameController.getPlayerList().add(new GUIPlayer("x"));
      } catch (InvalidPlayerNameException e1) {
        System.out.println("Error: Invalid player name in GUIGamePlayPanel.java");
        e1.printStackTrace();
      }

      GUIGameController.getPlayerList().get(2).setGUIGameBoard(
          GUIGameController.getPlayerList().get(0).getGUIGameBoard());
      GUIGameController.getPlayerList().get(3).setGUIGameBoard(
          GUIGameController.getPlayerList().get(1).getGUIGameBoard());

      // set players 1 and 3 to be equal, and 2 and 4 to be equal
      GUIGameController.getPlayerList().set(2, GUIGameController.getPlayerList().get(0));
      GUIGameController.getPlayerList().set(3, GUIGameController.getPlayerList().get(1));

    }

    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 10;
    c.weighty = 1;
    c.gridwidth = 1;
    c.gridheight = 1;
    //c.ipadx = 10;
    c.fill = GridBagConstraints.BOTH;
    c.anchor = GridBagConstraints.NORTH;

    leftSidePanelSubPanel2.add(boardPanelDisplay);

    this.add(leftSidePanel, c);

    // start working on the domino panel; create a sub panel to hold domino buttons
    rightSidePanel = new JPanel();
    rightSidePanel.setLayout(new GridBagLayout());
    //rightSidePanel.setBackground(Color.BLACK);
    rightSidePanel.setOpaque(false);
    GridBagConstraints rightSidePanelC = new GridBagConstraints();

    rightSidePanelC.gridx = 0;
    rightSidePanelC.gridy = 0;
    rightSidePanelC.weightx = 0;
    rightSidePanelC.weighty = 0;
    rightSidePanelC.insets = new Insets(GUIResolutionController.convertScaleHeight(30), 0, 0, 0);
    rightSidePanelC.fill = GridBagConstraints.NONE;
    rightSidePanelC.anchor = GridBagConstraints.NORTH;

    dominoPanel = new JPanel();
    dominoPanel.setLayout(new GridBagLayout());
    dominoPanel.setOpaque(false);
    // dominoPanel.setBackground(Color.ORANGE); // debug colors
    GridBagConstraints dominoPanelC = new GridBagConstraints();

    dominoButtons = new ArrayList<JButton>();
    dominoButtonLabels = new ArrayList<JLabel>();
    dominoButtonSelected = new boolean[GUIGameController.getPlayerList().size()];
    dominoButtonPlayed = new boolean[GUIGameController.getPlayerList().size()];

    dominoPanelC.gridy = 0;
    dominoPanelC.gridx = 0;
    dominoPanelC.weightx = 1;
    dominoPanelC.weighty = 1;
    dominoPanelC.gridwidth = 2;
    dominoPanelC.fill = GridBagConstraints.HORIZONTAL;

    dominoPanelLabel = new JLabel("[Domino Choices]");
    dominoPanelLabel.setFont(
        new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
    //dominoPanelLabel.setBackground(Color.MAGENTA);
    dominoPanelLabel.setOpaque(false);
    dominoPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
    dominoPanel.add(dominoPanelLabel, dominoPanelC);

    dominoPanelC.gridy = 1;
    dominoPanelC.gridx = 0;
    dominoPanelC.gridwidth = 1;
    dominoPanelC.weightx = 0;
    dominoPanelC.weighty = 0;
    dominoPanelC.fill = GridBagConstraints.NONE;
    //dominoPanelC.anchor = GridBagConstraints.WEST;

    DominoMouseListener dominoMouseListener = new DominoMouseListener();
    for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
      if (dominoPanelC.gridx > 1) {
        dominoPanelC.gridx = 0;
        dominoPanelC.gridy += 2;
      }

      dominoPanelC.weighty = 0;
      dominoPanelC.fill = GridBagConstraints.BOTH;
      dominoPanelC.insets = new Insets(GUIResolutionController.convertScaleHeight(25),
          GUIResolutionController.convertScaleWidth(50),
          GUIResolutionController.convertScaleHeight(25),
          GUIResolutionController.convertScaleWidth(50));

      JLabel l = new JLabel("Test");
      l.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(40)));
      l.setForeground(Color.BLACK);
      l.setBorder(new LineBorder(Color.BLACK, 2));
      l.setHorizontalAlignment(SwingConstants.CENTER);
      dominoButtonLabels.add(l);
      dominoPanel.add(dominoButtonLabels.get(i), dominoPanelC);

      dominoPanelC.gridy += 1;
      dominoPanelC.weighty = 0;
      //dominoPanelC.insets = new Insets(0, 0, 0, 0);
      JButton b = new JButton();
      b.setBorder(null);
      b.setContentAreaFilled(false);
      b.addMouseListener(dominoMouseListener);
      b.setBorder(new LineBorder(Color.BLACK, 1));
      b.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(512),
          GUIResolutionController.convertScaleHeight(256)));
      b.setMinimumSize(b.getPreferredSize());
      //double dominoWidth = getWidth()*0.15;
      //b.setSize(new Dimension((int)dominoWidth, (int)dominoWidth/2));
      //Image newButtonImage = ((ImageIcon) b.getIcon()).getImage().getScaledInstance(
      //		b.getWidth(), b.getHeight(), Image.SCALE_SMOOTH);
      //b.setIcon(new ImageIcon(newButtonImage));
      dominoButtons.add(b);

      dominoPanel.add(dominoButtons.get(i), dominoPanelC);

      dominoPanelC.gridx++;
      dominoPanelC.gridy -= 1;
    }

    rightSidePanel.add(dominoPanel, rightSidePanelC);

    // BEGIN action panel setup
    actionButtons = new ArrayList<>();

    actionPanel = new JPanel();
    //actionPanel.setBackground(Color.PINK);
    actionPanel.setOpaque(false);
    actionPanel.setLayout(new GridBagLayout());
    GridBagConstraints actionPanelC = new GridBagConstraints();

    actionPanelC.gridy = 0;
    actionPanelC.gridx = 0;
    actionPanelC.weightx = 1;
    actionPanelC.weighty = 1;
    actionPanelC.ipady = 4;
    actionPanelC.gridwidth = 4;
    actionPanelC.fill = GridBagConstraints.HORIZONTAL;

    actionPanelLabel = new JLabel("[Action Choices]");
    //actionPanelLabel.setBackground(Color.MAGENTA);
    actionPanelLabel.setFont(
        new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
    actionPanelLabel.setOpaque(false);
    actionPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
    actionPanel.add(actionPanelLabel, actionPanelC);

    actionPanelC.gridx = 0;
    actionPanelC.ipady = 0;
    actionPanelC.gridy = 1;
    actionPanelC.weightx = 1;
    actionPanelC.weighty = 1;
    actionPanelC.gridwidth = 1;
    //actionPanelC.insets = new Insets(5, 25, 0, 25);
    actionPanelC.fill = GridBagConstraints.NONE;

    String[] actionButtonString = {"Move Up", "Move Down", "Move Left", "Move Right",
        "Rotate Clockwise",
        "Rotate CounterClockwise", "Play", "Discard", "End Game"};

    String[] actionButtonToolTip = {
        "Move the domino up on the player board",
        "Move the domino down on the player board",
        "Move the domino left on the player board",
        "Move the domino right on the player board",
        "Rotate the domino clockwise on the player board",
        "Rotate the domino counter-clockwise on the player board",
        "Play the domino at its current place on the player board",
        "Discard the domino and pass until the next turn",
        "Quit the game and show end game scores"};

    ActionMouseListener actionMouseListener = new ActionMouseListener();
    int rowCount = 0;
    for (int i = 0; i < 8; i++) {
      if (rowCount > 3) {
        actionPanelC.gridx = 0;
        actionPanelC.gridy += 2;
        rowCount = 0;
      }

      JLabel l = new JLabel(actionButtonString[i]);
      //actionPanel.add(l, actionPanelC);
      l.setFont(new Font("Arial", Font.BOLD, 60));
      l.setForeground(Color.BLACK);
      actionPanelC.gridy += 1;

      JButton b = new JButton();
      actionButtons.add(b);
      b.addMouseListener(actionMouseListener);
      Image buttonImage = GUIImageController.getActionButtonImage(i)
          .getScaledInstance(GUIResolutionController.convertScaleWidth(200),
              GUIResolutionController.convertScaleHeight(200), Image.SCALE_SMOOTH);
      b.setIcon(new ImageIcon(buttonImage));
      b.setBorderPainted(false);
      b.setContentAreaFilled(false);
      b.setFocusPainted(false);
      b.setOpaque(false);
      //b.setText(actionButtonString[i]);
      b.setToolTipText(actionButtonToolTip[i]);
      //b.setVerticalTextPosition(SwingConstants.BOTTOM);
      //b.setHorizontalTextPosition(SwingConstants.CENTER);
      actionPanel.add(actionButtons.get(i), actionPanelC);
      actionPanelC.gridx++;
      actionPanelC.gridy -= 1;
      rowCount++;
    }

    // "End Game" button for action panel
    actionPanelC.gridx = 0;
    actionPanelC.gridy += 2;
    actionPanelC.gridwidth = 4;
    actionPanelC.gridheight = 1;
    //actionPanelC.insets = new Insets(20, 5, 0, 0);
    actionPanelC.fill = GridBagConstraints.BOTH;
    JButton b = new JButton(actionButtonString[8]);
    b.setFont(new Font("Arial", Font.BOLD, GUIResolutionController.convertScaleWidth(60)));
    actionButtons.add(b);
    actionButtons.get(8).setToolTipText(actionButtonToolTip[8]);
    b.addMouseListener(actionMouseListener);
    actionPanel.add(actionButtons.get(8), actionPanelC);

    rightSidePanelC.gridx = 0;
    rightSidePanelC.gridy = 1;
    rightSidePanelC.weightx = 1;
    rightSidePanelC.weighty = 1;
    rightSidePanelC.fill = GridBagConstraints.NONE;
    rightSidePanelC.anchor = GridBagConstraints.NORTH;
    rightSidePanel.add(actionPanel, rightSidePanelC);

    c.gridx = 1;
    c.gridy = 1;
    //c.weightx = 1;
    //c.weighty = 0;
    c.weightx = 0.01;
    c.weighty = 1;
    c.gridwidth = 1;
    c.ipadx = GUIResolutionController.convertScaleWidth(400);
    c.insets = new Insets(0, 0, 0, GUIResolutionController.convertScaleWidth(150));
    c.fill = GridBagConstraints.BOTH;
    c.anchor = GridBagConstraints.WEST;

    this.add(rightSidePanel, c);

    // resize each sub component when window is resized
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(final ComponentEvent e) {
        // calculations for size based on 1728 x 972 resolution (90% 1980 x 1080)
        super.componentResized(e);
        int width = getWidth();
        //int height = getHeight();

        // resize each panel based on screen dimensions
        boardPanelDisplay.setSize(
            new Dimension((int) (width * 0.40509259), (int) (width * 0.40509259)));
        boardPanelDisplay.setMinimumSize(
            new Dimension((int) (width * 0.40509259), (int) (width * 0.40509259)));
        boardPanelDisplay.setPreferredSize(
            new Dimension((int) (width * 0.40509259), (int) (width * 0.40509259)));

        viewPlayerBoardLabel.setFont(new Font("Augusta", Font.PLAIN, (int) (width * 0.0173611)));

        dominoPanelLabel.setFont(new Font("Augusta", Font.PLAIN, (int) (width * 0.0173611)));

        statusLabel.setFont(new Font("Arial", Font.PLAIN, (int) (width * 0.0173611)));

        actionPanelLabel.setFont(new Font("Augusta", Font.PLAIN, (int) (width * 0.0173611)));

        for (JButton b : dominoButtons) {
          double dominoWidth = width * 0.15;
          b.setSize(new Dimension((int) dominoWidth, (int) dominoWidth / 2));
          Image newButtonImage = ((ImageIcon) b.getIcon()).getImage().getScaledInstance(
              b.getWidth(), b.getHeight(), Image.SCALE_SMOOTH);
          b.setIcon(new ImageIcon(newButtonImage));
        }

        for (int i = 0; i < actionButtons.size() - 1; i++) {
          actionButtons.get(i)
              .setSize(new Dimension((int) (width * 0.05787037), (int) (width * 0.05787037)));
          actionButtons.get(i).setFont(new Font("Augusta", Font.PLAIN, (int) (width * 0.04)));
          Image newButtonImage = ((ImageIcon) actionButtons.get(i).getIcon()).getImage()
              .getScaledInstance(
                  actionButtons.get(i).getWidth(), actionButtons.get(i).getHeight(),
                  Image.SCALE_SMOOTH);
          actionButtons.get(i).setIcon(new ImageIcon(newButtonImage));
        }
      }
    });

    startGame();
  }

  /**
   * This method updates the status text info displayed at the top of the page
   *
   * @param -       type the text parameter to update: 0 current round 1 current player's turn 2
   *                current action 3 error message
   * @param message - the message to be displayed
   */
  public void updateStatusText(int type, String message) {
    statusText.set(type, message);

    StringBuilder text = new StringBuilder();

    for (String s : statusText) {
      text.append(s + " ");
    }
    text.deleteCharAt(text.length() - 1);
    statusLabel.setText(text.toString());
  }

  /**
   * This method starts the game
   */
  public void startGame() {

    playerOrderList = new ArrayList<GUIPlayer>(GUIGameController.getPlayerList());

    // current dominos to be selected for this round
    currentDominoSelection = new ArrayList<Domino>();

    setUpGameDominos(); // setup dominos for the entire game based on number of players

    randomizePlayerOrder(); // randomize player order

    round = 1; // set current round to round 1

    updateStatusText(0, "Round " + round + ":"); // update status label to show current round

    updateDominosForCurrentRound(); // update current dominos for selection and display

    playerOrderList.sort(
        Comparator.comparing(Player::getCurrentOrder)); // order players by turn order

    getNextPlayer(); // gets first player

    updateDominoButtonInfo(); // update information for dominos that can be selected in domino buttons displayed
    
	GUISoundController.stopSound();
    GUISoundController.playGamePlayMusic();
  }

  /**
   * Create dominos for the game based on the number of players
   */
  public void setUpGameDominos() {
    int dominosToRemove = 0; // if 4 players, don't remove any dominos
    if (GUIGameController.getPlayerCount() == 2) {
      dominosToRemove = 24; // remove 24 if 2 players
    }
    if (GUIGameController.getPlayerCount() == 3) {
      dominosToRemove = 12; // remove 12 if 3 players
    }

    // go through and randomly remove the number of dominos needed from the list of all dominos
    // after removal, the dominos remaining will be the dominos playable for the game
    for (int i = 0; i < dominosToRemove; i++) {
      int max = dominoList.size();
      dominoList.remove(rand.nextInt(max));
    }
  }

  /**
   * This method ends the game
   */
  public void endGame() {
    GUISoundController.playWinnerMusic(); // play end game music
    // then initialize score display panel and switch to it
    ((GUIScoringPanel) GUIComponentController.getComponentList().get(5)).initialize();
    GUIComponentController.getCardLayout()
        .show(GUIComponentController.getParentPanel(), "scoringPanel");
  }

  /**
   * This method proceeds the game to the next player in turn
   */
  public void getNextPlayer() {
    // if all dominos have been played this round, set up the next round
    if (dominosPlayedThisRound == playerOrderList.size()) {
      dominosPlayedThisRound = 0; // reset number of dominos played
      round++; // advance to next round
      updateStatusText(0, "Round " + round + ":"); // update round status text at top of screen
      updateDominosForCurrentRound(); // randomly generate dominos for the current round
      updateDominoButtonInfo(); // update the domino buttons to display the newly generated dominos
      resetDominoButtonClickability(); // makes domino buttons clickable again

      // get player order for next round; determined by dominos they selected last round
      for (Player p : playerOrderList) {
        p.setCurrentOrder(p.getNextOrder());
      }

      playerOrderList.sort(
          Comparator.comparing(Player::getCurrentOrder)); // order players by turn order

      currentPlayer = playerOrderList.get(0); // set first player as the current player
      updateStatusText(1,
          "It's " + currentPlayer.getPlayerName() + "'s turn!"); // update current player status
      updateStatusText(2, "Choose a domino."); // update current action status
      displayCurrentPlayerGameBoard(); // display current player's game board
    } else {
      currentPlayer = playerOrderList.get(dominosPlayedThisRound); // get current player
      updateStatusText(1,
          "It's " + currentPlayer.getPlayerName() + "'s turn!"); // update player status text
      updateStatusText(2, "Choose a domino."); // update current action status text
      displayCurrentPlayerGameBoard(); // display current player's game board
    }
  }

  /**
   * This method displays the game board of the current player
   */
  public void displayCurrentPlayerGameBoard() {
    for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
      if (GUIGameController.getPlayerList().get(i) == currentPlayer) {
        if (GUIGameController.getPlayerCount() == 2 && i > 1) { // if two players
          // make sure to not display the faux boards for the 3rd and 4th players
          boardPanelDisplayCard.show(boardPanelDisplay, String.valueOf(i - 2));
        } else {
          boardPanelDisplayCard.show(boardPanelDisplay, String.valueOf(i));
        }
        switchViewBoardButtonColorsOnSelect(
            i); // darken the tabs of the other players not being viewed
      }
    }
  }

  /**
   * This method randomizes the player order
   */
  public void randomizePlayerOrder() {
    // add numbers 0 - 3 into an array in random order
    ArrayList<Integer> order = new ArrayList<Integer>();
    for (int i = 0; i < this.playerOrderList.size(); i++) {
      order.add(i);
    }

    // then randomly assign each player an order number from the above array
    Random rand = new Random();
    for (int k = 0; k < this.playerOrderList.size(); k++) {
      int selection = rand.nextInt(order.size());
      this.playerOrderList.get(k).setCurrentOrder(order.get(selection));
      order.remove(selection);
    }
  }

  /**
   * This method displays the current player order
   *
   * @return - player order as a string
   */
  public String getPlayerCurrentOrder() {
    StringBuilder playerListString = new StringBuilder();

    // TODO this can probably all be done more efficiently
    for (int i = 0; i < this.playerOrderList.size(); i++) {
      for (Player p : playerOrderList) {
        String orderText = "";
        switch (p.getCurrentOrder()) {
          case 0:
            orderText = "first";
            break;
          case 1:
            orderText = "second";
            break;
          case 2:
            orderText = "third";
            break;
          case 3:
            orderText = "fourth";
            break;
        }

        if (p.getCurrentOrder() == i) {
          if (p.getCurrentOrder() == this.playerOrderList.size() - 1) {
            playerListString = playerListString.append("and ");
          }

          playerListString = playerListString.append(
              p.getPlayerName() + " is the " + orderText + " player");

          if (p.getCurrentOrder() != this.playerOrderList.size() - 1) {
            playerListString = playerListString.append(", ");
          } else {
            playerListString = playerListString.append(".");
          }
        }
      }
    }

    return playerListString.toString();
  }

  /**
   * This method verifies whether the sides of a domino match the board area at which it's being
   * placed
   *
   * @param playerGameBoard - player's game board
   * @param sax             - domino's A side x location
   * @param say             - domino's A side y location
   * @param sbx             - domino's B side x location
   * @param sby             - domino's B side y location
   * @param selectedDomino  - the selected domino to be compared
   * @return playable (true) or not playable (false)
   */
  private boolean verifyMatchingSide(GameBoard.Space[][] playerGameBoard, int sax, int say, int sbx,
      int sby,
      Domino selectedDomino) {
    ArrayList<LandType> sideAOptions = new ArrayList<>(); // list of options next to side A of domino
    ArrayList<LandType> sideBOptions = new ArrayList<>(); // list of options next to side B of domino
    boolean sideAValid = false; // flag on whether side A is at a valid placement option
    boolean sideBValid = false; // same flag for side B

    // get placement options for both sides at their curent locations
    gatherSideTypes(playerGameBoard, sax, say, sideAOptions);
    gatherSideTypes(playerGameBoard, sbx, sby, sideBOptions);

    // if side A has matching options, flag it true
    for (LandType sideAOption : sideAOptions) {
      if (sideAOption == selectedDomino.getSideA().getSType() || sideAOption == LandType.CASTLE) {
        sideAValid = true;
        break;
      }
    }

    // same for side B;
    for (LandType sideBOption : sideBOptions) {
      if (sideBOption == selectedDomino.getSideB().getSType() || sideBOption == LandType.CASTLE) {
        sideBValid = true;
        break;
      }
    }

    // return whether each side can be placed at it's current location
    return sideAValid || sideBValid;
  }

  /**
   * This method gathers a list of side types that the current domino selected can be played
   * against
   *
   * @param playerGameBoard - player's game board
   * @param x               - x location on game board
   * @param y               - y location on game board
   * @param sideOptions     - list of side options
   */
  private void gatherSideTypes(GameBoard.Space[][] playerGameBoard, int x, int y,
      ArrayList<LandType> sideOptions) {
    // get the space type to the left of this side of the domino
    if (x >= 1) {
      sideOptions.add(playerGameBoard[x - 1][y].getSType());
    }
    // get the space type to the right side of the domino
    if (x < playerGameBoard.length - 1) {
      sideOptions.add(playerGameBoard[x + 1][y].getSType());
    }
    // get the space type below the domino
    if (y >= 1) {
      sideOptions.add(playerGameBoard[x][y - 1].getSType());
    }
    // get the space type above the domino
    if (y < playerGameBoard[0].length - 1) {
      sideOptions.add(playerGameBoard[x][y + 1].getSType());
    }
  }

  /**
   * This method updates the domino button display image, info label, and tool tips
   */
  public void updateDominoButtonInfo() {
    for (int i = 0; i < currentDominoSelection.size(); i++) {
      // set the new domino's image into the button
      BufferedImage source = GUIStaticGameDominoList.dominoImagesFront
          .get(currentDominoSelection.get(i).getDominoNumber() - 1);
      Image newButtonImage = source.getScaledInstance(
          GUIResolutionController.convertScaleWidth(512),
          GUIResolutionController.convertScaleHeight(256), Image.SCALE_SMOOTH);
      ImageIcon img = new ImageIcon(newButtonImage);
      dominoButtons.get(i).setIcon(img);

      // create a copy domino to get its information for display
      Domino domino = StaticGameDominoList.getDominosList().
          get(currentDominoSelection.get(i).getDominoNumber() - 1);

      // set the tool tip
      String toolTipText = domino.getSideAFullName() + " (" + domino.getSideACrowns() + "C) "
          + domino.getSideBFullName() + " (" + domino.getSideBCrowns() + "C) ";

      // get the domino's "full name" to display in the label above the domino button
      String fullName = "Domino # " + domino.getDominoNumber();

      dominoButtonLabels.get(i).setText(fullName);
      dominoButtonLabels.get(i)
          .setToolTipText("The domino number selected determines turn order the next round");
      dominoButtons.get(i).setToolTipText(toolTipText);
      dominoButtonLabels.get(i)
          .setSize(new Dimension(GUIResolutionController.convertScaleWidth(512),
              GUIResolutionController.convertScaleHeight(256)));
    }
  }

  /**
   * This method resets domino button clickability, making them clickable again
   */
  public void resetDominoButtonClickability() {
    for (int i = 0; i < dominoButtonPlayed.length; i++) {
      dominoButtonPlayed[i] = false;
    }
  }

  /**
   * Update the text on the view tabs for player boards to display the current name and score Should
   * be called each time the player adds a domino to their board
   */
  public void updatePlayerButtonText() {
    for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
      if (currentPlayer == GUIGameController.getPlayerList().get(i)) {
        int score = GUIGameController.getPlayerList().get(i).getGUIGameBoard().getGameBoard()
            .getCurrentScore();
        String name = GUIGameController.getPlayerList().get(i).getPlayerName();
        if (GUIGameController.getPlayerCount() == 2) {
          if (i > 1) {
            viewBoardButtons.get(i - 2).setText(name + " (" + score + " pts)");
          } else {
            viewBoardButtons.get(i).setText(name + " (" + score + " pts)");
          }
        } else {
          viewBoardButtons.get(i).setText(name + " (" + score + " pts)");
        }
      }
    }
  }

  public void updateDominosForCurrentRound() {
    currentDominoSelection = new ArrayList<Domino>();

    for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
      int dominoToRemove = rand.nextInt(this.dominoList.size());
      // get a random domino from the main list of dominos and add it to the currently
      // selectable list
      currentDominoSelection.add(this.dominoList.get(dominoToRemove));
      dominoList.remove(dominoToRemove); // then remove domino from main list
    }

    // order dominos by domino number
    currentDominoSelection.sort(Comparator.comparing(Domino::getDominoNumber));

    // set selection order for each domino, so it determines next round order when
    // player selects
    for (int i = 0; i < currentDominoSelection.size(); i++) {
      currentDominoSelection.get(i).setPlayerOrder(i);
    }
  }

  public String numberToOrdinal(int number) {
    if (number == 1) {
      return "first";
    }
    if (number == 2) {
      return "second";
    }
    if (number == 3) {
      return "third";
    }
    if (number == 4) {
      return "fourth";
    }
    return null;
  }

  public ArrayList<Domino> getDominoList() {
    return dominoList;
  }

	/*
	public ArrayList<Player> getPlayerList() {
		return this.playerList;
	}
	*/

  /**
   * This method returns whether this panel has been intialized
   *
   * @return - initialized (true) or not initialized (false)
   */
  public boolean getInitialized() {
    return this.initialized;
  }

  /**
   * This method sets the initialized status of this panel
   *
   * @param initialized - initialized (true) or not initialized (false)
   */
  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }

  /**
   * This method resets the border around each domino button
   */
  public void resetDominoButtonBorders() {
    for (JButton b : dominoButtons) {
      b.setBorder(new LineBorder(Color.BLACK, 1)); // give each domino button a thin black border
    }
  }

  /**
   * This method switches the view bord button colors upon selection
   *
   * @param buttonNum - the view board selected
   */
  public void switchViewBoardButtonColorsOnSelect(int buttonNum) {
    for (int i = 0; i < viewBoardButtons.size(); i++) {
      if (i == buttonNum) { // brighten the selected panel
        viewBoardButtons.get(i).setBackground(GUIGameController.getPlayerColors(i).brighter());
      } else { // while darkening the others
        viewBoardButtons.get(i).setBackground(GUIGameController.getPlayerColors(i).darker());
      }
    }
  }

  // this gives the background the book image
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Image newImage = GUIImageController.getBackGroundImage(6).getScaledInstance(
        (int) (this.getWidth() * 0.90), (int) (this.getHeight() * 0.90), Image.SCALE_SMOOTH);
    g.drawImage(newImage, (int) (this.getWidth() * 0.05), (int) (this.getHeight() * 0.04), null);
  }

  /**
   * This is the mouse listener for the GUI board view buttons
   */
  private class ViewMouseListener extends MouseAdapter {

    @Override

    // functionality for each of the buttons
    public void mouseClicked(MouseEvent e) {
      int selectedNum = 0; // number of selected button in array;
      // switch around to different player boards
      if (e.getSource() == viewBoardButtons.get(0)) {
        boardPanelDisplayCard.show(boardPanelDisplay, "0");
        // playerCurrentViewLabel.setText(" Showing: " +
        // GUIGameController.getPlayerList().get(0).getPlayerName());
      } else if (e.getSource() == viewBoardButtons.get(1)) {
        boardPanelDisplayCard.show(boardPanelDisplay, "1");
        // playerCurrentViewLabel.setText(" Showing: " +
        // GUIGameController.getPlayerList().get(1).getPlayerName());
        selectedNum = 1;
      } else if (e.getSource() == viewBoardButtons.get(2)) {
        boardPanelDisplayCard.show(boardPanelDisplay, "2");
        // playerCurrentViewLabel.setText(" Showing: " +
        // GUIGameController.getPlayerList().get(2).getPlayerName());
        selectedNum = 2;
      } else if (e.getSource() == viewBoardButtons.get(3)) {
        boardPanelDisplayCard.show(boardPanelDisplay, "3");
        // playerCurrentViewLabel.setText(" Showing: " +
        // GUIGameController.getPlayerList().get(3).getPlayerName());
        selectedNum = 3;
      }
      switchViewBoardButtonColorsOnSelect(
          selectedNum); // darken the tabs of the other players not being viewed
    }
  }

  /**
   * This is the mouse listener for the GUI domino buttons
   */
  private class DominoMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
      resetDominoButtonBorders(); // first reset all mouse buttons
      for (int i = 0; i < dominoButtons.size(); i++) {
        if (e.getSource() == dominoButtons.get(i)) { // check for the selected button
          // do no action if the domino has been played already
          if (!dominoButtonPlayed[i]) {
            // otherwise do the following
            dominoButtonSelected[i] = true; // set the domino as being selected
            dominoButtons.get(i)
                .setBorder(new LineBorder(Color.GREEN, 4)); // give it a border to signify
            selectedDomino = currentDominoSelection.get(i); // set the selected domino
            selectedDominoNumber = i; // get the domino list # from a list of dominos for this round
            // update the status text
            updateStatusText(2, "Move, rotate, place, discard, or re-choose your domino.");

            for (int k = 0; k < playerOrderList.size(); k++) {
              if (GUIGameController.getPlayerList().get(k) == currentPlayer) {
                currentPlayerNumber = k; // find the current player and get order number in list
                int[] boardArea = GUIGameController.getPlayerList().get(k)
                    .getGUIGameBoard().getGameBoard().getPlayArea(); // get playable board area
                // then get virtual location of selected domino on player board
                selectedDomino.getSideA().setXLoc(boardArea[0]);
                selectedDomino.getSideA().setYLoc(boardArea[2]);
                selectedDomino.getSideB().setXLoc(boardArea[0] + 1);
                selectedDomino.getSideB().setYLoc(boardArea[2]);
                // show the image of the domino over the player's board
                GUIGameController.getPlayerList().get(k).getGUIGameBoard()
                    .savePlayLayerImage(selectedDomino);
                return;
              }  // skip player in list if not the current player

            }
            GUISoundController.playSelectTileSound(); // play selection sound
          }
        } else {
          // otherwise, reset the domino button's border and set the button's selected flag to false
          dominoButtonSelected[i] = false;
          dominoButtons.get(i).setBorder(new LineBorder(Color.BLACK, 1));
        }
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      // create a yellow border around the domino if entered but not selected
      for (int i = 0; i < dominoButtons.size(); i++) {
        if (e.getSource() == dominoButtons.get(i)) {
          if (!dominoButtonSelected[i]) {
            dominoButtons.get(i).setBorder(new LineBorder(Color.YELLOW, 1));
          }
        }
      }
    }

    @Override
    public void mouseExited(MouseEvent e) {
      // reset the border back to black when exiting button but not selected
      for (int i = 0; i < dominoButtons.size(); i++) {
        if (e.getSource() == dominoButtons.get(i)) {
          if (!dominoButtonSelected[i]) {
            dominoButtons.get(i).setBorder(new LineBorder(Color.BLACK, 1));
          }
        }
      }

    }
  }

  /**
   * This is a mouse listener for the action buttons
   */
  private class ActionMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
      // option 9/9 ends the game
      if (e.getSource() == actionButtons.get(8)) {
        endGame();
        return;
        // do no action if no domino has been selected
      } else if (selectedDomino == null) {
        return;
      }

      // first, get the virtual location of the currently selected domino to compare
      // against the current player's board
      int sax = selectedDomino.getSideA().getXLoc(); // side A x location
      int say = selectedDomino.getSideA().getYLoc(); // side A y location
      int sbx = selectedDomino.getSideB().getXLoc(); // side B x location
      int sby = selectedDomino.getSideB().getYLoc(); // side B y location

      int[] boardArea = {0, 0, 0, 0}; // initialize playable bounds on player board

      //TODO early rough implementation to find current player board that can likely be made better
      // below finds the play area of the current player's game board
      int currentPlayerCounted = -1;
      for (int k = 0; k < GUIGameController.getPlayerList().size(); k++) {
        currentPlayerCounted++;
        if (GUIGameController.getPlayerList().get(k) == currentPlayer) {
          boardArea = GUIGameController.getPlayerList().get(k).getGUIGameBoard().getGameBoard()
              .getPlayArea();
          break;
        }
      }

      // set the current game board
      GUIGameBoard currentGameBoard = GUIGameController.getPlayerList()
          .get(currentPlayerCounted).getGUIGameBoard();

      // option 1/9 move up button action
      if (e.getSource() == actionButtons.get(0)) {
        // don't do down if moving outside of game board
        if (selectedDomino.getSideA().getYLoc() - 1 < boardArea[2]
            || selectedDomino.getSideB().getYLoc() - 1 < boardArea[2]) {
          System.out.println("Error: invalid move. Domino will be outside of play area");
        } else {
          // move virtual location of currently selected domino upward on player board
          GUISoundController.playMoveTileSound();
          selectedDomino.getSideA().setYLoc(selectedDomino.getSideA().getYLoc() - 1);
          selectedDomino.getSideB().setYLoc(selectedDomino.getSideB().getYLoc() - 1);
          currentGameBoard.savePlayLayerImage(selectedDomino); // display this image on player board
        }

        // option 2/9 move down button action
      } else if (e.getSource() == actionButtons.get(1)) {
        // don't move down if moving down moves the domino outside of the game board
        if (selectedDomino.getSideA().getYLoc() + 1 > boardArea[3]
            || selectedDomino.getSideB().getYLoc() + 1 > boardArea[3]) {
          // otherwise do the movement
        } else {
          GUISoundController.playMoveTileSound();
          selectedDomino.getSideA().setYLoc(selectedDomino.getSideA().getYLoc() + 1);
          selectedDomino.getSideB().setYLoc(selectedDomino.getSideB().getYLoc() + 1);
          currentGameBoard.savePlayLayerImage(selectedDomino);
        }
        // option 3/9 move left button action
      } else if (e.getSource() == actionButtons.get(2)) {
        if (selectedDomino.getSideA().getXLoc() - 1 < boardArea[0]
            || selectedDomino.getSideB().getXLoc() - 1 < boardArea[0]) {
          System.out.println("Error: invalid move. Domino will be outside of play area");
        } else {
          GUISoundController.playMoveTileSound();
          selectedDomino.getSideA().setXLoc(selectedDomino.getSideA().getXLoc() - 1);
          selectedDomino.getSideB().setXLoc(selectedDomino.getSideB().getXLoc() - 1);
          currentGameBoard.savePlayLayerImage(selectedDomino);
        }
        // option 4/9 move left button action
      } else if (e.getSource() == actionButtons.get(3)) {
        if (selectedDomino.getSideA().getXLoc() + 1 > boardArea[1]
            || selectedDomino.getSideB().getXLoc() + 1 > boardArea[1]) {
          System.out.println("Error: invalid move. Domino will be outside of play area");
        } else {
          GUISoundController.playMoveTileSound();
          selectedDomino.getSideA().setXLoc(selectedDomino.getSideA().getXLoc() + 1);
          selectedDomino.getSideB().setXLoc(selectedDomino.getSideB().getXLoc() + 1);
          currentGameBoard.savePlayLayerImage(selectedDomino);
        }
        // option 5/9 rotate Clockwise button action
      } else if (e.getSource() == actionButtons.get(4)) {
        int tempsbx;
        int tempsby;

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
        if (tempsby < boardArea[2] || tempsby > boardArea[3] || tempsbx < boardArea[0]
            || tempsbx > boardArea[1]) {
          System.out.println(
              "Error: rotation will cause domino to fall outside of play area");
        } else {
          GUISoundController.playRotateTileSound();
          selectedDomino.getSideB().setXLoc(tempsbx);
          selectedDomino.getSideB().setYLoc(tempsby);
          currentGameBoard.savePlayLayerImage(selectedDomino);
        }
        // option 6/9 rotate Counterclockwise button action
      } else if (e.getSource() == actionButtons.get(5)) {
        int tempsbx;
        int tempsby;

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

        if (tempsby < boardArea[2] || tempsby > boardArea[3] || tempsbx < boardArea[0]
            || tempsbx > boardArea[1]) {
          System.out.println("Error: rotation will cause domino to fall outside of play area");
        } else {
          GUISoundController.playRotateTileSound();
          selectedDomino.getSideB().setXLoc(tempsbx);
          selectedDomino.getSideB().setYLoc(tempsby);
          currentGameBoard.savePlayLayerImage(selectedDomino);
        }
        // option 7/9 play domino at current location action
      } else if (e.getSource() == actionButtons.get(6)) {
        GameBoard.Space SideA = currentGameBoard.getGameBoard().getGameBoardSpace(sax, say);
        GameBoard.Space SideB = currentGameBoard.getGameBoard().getGameBoardSpace(sbx, sby);
        if (SideA.getSType() == LandType.EMPTY && SideB.getSType() == LandType.EMPTY
            && verifyMatchingSide(currentGameBoard.getGameBoard().getGameBoardSpaces(), sax, say,
            sbx, sby, selectedDomino)) {

          // place domino on the game board
          currentGameBoard.saveOverlayImage(selectedDomino);
          //currentDominoSelection.remove(selectedDomino);

          GUISoundController.playPlaceTileSound();

          Image newButtonImage = GUIImageController.getPlayerMeepleImage(
              currentPlayerNumber).getScaledInstance(100, 50, Image.SCALE_SMOOTH);
          dominoButtonPlayed[selectedDominoNumber] = true;
          dominoButtons.get(selectedDominoNumber).setIcon(new ImageIcon(newButtonImage));
          dominoButtonLabels.get(selectedDominoNumber).setText(currentPlayer.getPlayerName() +
              ": " + numberToOrdinal(selectedDominoNumber + 1) + " next round");

          dominosPlayedThisRound += 1;
          currentPlayer.setNextOrder(selectedDomino.getDominoNumber());
          selectedDomino = null;
          updatePlayerButtonText();
          resetDominoButtonBorders();

          if (dominoList.size() <= 0 &&
              dominosPlayedThisRound == GUIGameController.getPlayerList().size() && !gameEnded) {
            gameEnded = true;
            endGame();
          } else {
            getNextPlayer();
          }

        } else {
          if (verifyMatchingSide(currentGameBoard.getGameBoard().getGameBoardSpaces(), sax, say,
              sbx, sby, selectedDomino)) {
            System.out.println("Error: location not empty for domino placement");
          } else {
            System.out.println(
                "Error: At least one side of the tile must match an existing tile or castle.");
          }
        }
        // option 8/9 discard selected domino and pass turn option
      } else if (e.getSource() == actionButtons.get(7)) {
        currentGameBoard.resetPlayLayerImage();

        Image newButtonImage = GUIImageController.getPlayerMeepleImage(
            currentPlayerNumber).getScaledInstance(100, 50, Image.SCALE_SMOOTH);

        dominoButtonPlayed[selectedDominoNumber] = true;
        dominoButtons.get(selectedDominoNumber).setIcon(new ImageIcon(newButtonImage));
        dominoButtonLabels.get(selectedDominoNumber).setText(currentPlayer.getPlayerName() +
            ": " + numberToOrdinal(selectedDominoNumber + 1) + " next round");

        dominosPlayedThisRound += 1;
        currentPlayer.setNextOrder(selectedDomino.getDominoNumber());

        // get rid of image of discarded domino over player's board
        currentGameBoard.repaint();
        selectedDomino = null;
        resetDominoButtonBorders();

        if (dominoList.size() <= 0 &&
            dominosPlayedThisRound == GUIGameController.getPlayerList().size() && !gameEnded) {
          gameEnded = true;
          endGame();
        } else {
          getNextPlayer();
        }
      }
    }
  }

}
