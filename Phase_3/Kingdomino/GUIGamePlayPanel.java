/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson & William Feighner
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIGamePlayPanel.java
 * @Description: This class maintains the gampe play logic and display layout for the main game
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GUIGamePlayPanel extends JPanel {

	// attributes
	private static final long serialVersionUID = 1L;

	// game play attributes
	boolean initialized = false; // flag whether this JPanel has been initialized
	GUIViewBoardMouseListener vbml;
	private ArrayList<Domino> dominoList; // list of dominos in game
	private ArrayList<GUIPlayer> playerOrderList; // player order list
	private ArrayList<Domino> currentDominoSelection;
	private ArrayList<Domino> nextDominoSelection;
	private Domino selectedDomino; // the current domino selected
	private int currentPlayerNumber; // current player number; corresponds to playerList
	private GUIPlayer currentPlayer; // current player as a player object
	private Random rand; // random number generator
	private int dominosPlayedThisRound = 0; // number of dominos played in the current round
	private int round = 1; // the current round number
	private boolean gameEnded = false; // flag whether the game has ended
	private Color headerColor = Color.white; // color for header text
	private Color subHeaderColor = Color.white; // color for sub-header text
		
	// display set up - below are a list of panels and their corresponding sub
	// top middle
	private JPanel statusPanel; // sits at top of screen under logo; shows the current status of the round
	private JLabel statusLabel; // text to display in status panel
	private ArrayList<String> statusText; // array of messages for status panel
	
	// left side panel
	private JLabel viewPlayerBoardLabel; // holds buttons to switch between player boards; housed in
	private ArrayList<JButton> viewBoardButtons; // list of buttons to view other player boards
	
	// bottom left side panel
	private JPanel boardPanelDisplay; // holds all game boards in card layout format; contained in
	private CardLayout boardPanelDisplayCard; // card laoyout for boardPanelDisplay
	
	// right top side panel
	private JLabel dominoPanelLabel; // label for domino buttons
	private ArrayList<GUIDominoButton> dominoButtons; // list of dominos that will be selected during game play, as
	private ArrayList<GUIMeepleButton> meepleButtons; // labels for the domino buttons
	private boolean[] dominoButtonSelected; // flag whether domino button has been selected
	private boolean[] dominoButtonPlayed; // flag whether domino button has been played in the current round
	private int selectedDominoNumber; // the number of the domino selected; corresponds to dominoButtons ArrayList
	
	// right bottom side panel
	private JLabel actionPanelLabel; // label for action buttons
	private ArrayList<GUIActionButton> actionButtons; // list of actions available to the player during game play
	private boolean finalRoundStarted = false;

	/**
	 * This method initializes the panel
	 */
	public void initialize() {
		if (initialized) {
			return;
		} // don't initialize if already initialized
		initialized = true; // set initialized to true
		this.removeAll(); // remove all sub components

		this.playerOrderList = new ArrayList<GUIPlayer>(); // create new player order list
		this.dominoList = new ArrayList<Domino>(StaticGameDominoList.getDominosList()); // game domino list

		rand = new Random(); // random number generator

		this.setOpaque(false);
		this.setLayout(new GridBagLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setOpaque(true);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout(new GridLayout(0, 2));
		// set up the left and right panels that will hold the other sub panels
		// colors are for debugging purposes
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridBagLayout());
		leftPanel.setOpaque(false);
		leftPanel.setBackground(Color.BLACK);
		mainPanel.add(leftPanel);

		GridBagConstraints lp = new GridBagConstraints();

		JPanel leftTopPanel = new JPanel();
		leftTopPanel.setLayout(new GridBagLayout());
		leftTopPanel.setBackground(Color.BLACK);
		GridBagConstraints ltp = new GridBagConstraints();

		ltp.gridx = 0;
		ltp.gridy = 0;
		ltp.weighty = 0;
		ltp.weightx = 1;
		ltp.insets = new Insets(GUIResolutionController.convertScaleHeight(50),
				GUIResolutionController.convertScaleWidth(200), 0, GUIResolutionController.convertScaleWidth(200));
		ltp.fill = GridBagConstraints.HORIZONTAL;
		// setup for player board view buttons at top left
		viewPlayerBoardLabel = new GUITitleJLabel("Player Boards", GUIImageController.getBackGroundImage(8));
		viewPlayerBoardLabel.setForeground(headerColor);
		viewPlayerBoardLabel.setOpaque(false);
		viewPlayerBoardLabel.setHorizontalAlignment(SwingConstants.CENTER);
		viewPlayerBoardLabel
				.setFont(new Font("Arial Black", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));

		leftTopPanel.add(viewPlayerBoardLabel, ltp);

		JPanel leftTopSubPanel1 = new JPanel();
		leftTopSubPanel1.setLayout(new GridBagLayout());
		leftTopSubPanel1.setBackground(Color.BLACK);

		GridBagConstraints ltsp1 = new GridBagConstraints();
		ltsp1.gridx = 0;
		ltsp1.gridy = 0;
		ltsp1.weightx = 1;
		ltsp1.fill = GridBagConstraints.HORIZONTAL;
		ltsp1.anchor = GridBagConstraints.SOUTH;
		ltsp1.insets = new Insets(0, GUIResolutionController.convertScaleWidth(200), 0, 0);
		// creatE each button, add to list, and position on sub panel

		viewBoardButtons = new ArrayList<JButton>();
		vbml = new GUIViewBoardMouseListener(this);

		String preHTML = "<html>";
		String newLine = "<p style='margin-top:-5'>";
		String postHTML = "</p></html>";

		for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
			if (i == GUIGameController.getPlayerList().size() - 1) {
				ltsp1.insets = new Insets(0, 0, 0, GUIResolutionController.convertScaleWidth(200));
			}
			JButton button = new JButton(preHTML + GUIGameController.getPlayerList().get(i).getPlayerName() + newLine
					+ " (0pts)" + postHTML);
			GUINoPressedHighlightButtonModel bmodel = new GUINoPressedHighlightButtonModel();
			button.setModel(bmodel);

			button.setBackground(GUIGameController.getPlayerColors(i));
			button.addMouseListener(vbml);
			// button.setBorderPainted(false);
			button.setHorizontalAlignment(SwingConstants.CENTER);

			button.setFocusPainted(false);
			button.setBorder(new LineBorder(Color.BLACK, 1));
			button.setToolTipText(
					"Click to display " + GUIGameController.getPlayerList().get(i).getPlayerName() + "'s board");
			button.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(40)));
			Image newButtonImage = GUIImageController.getPlayerMeepleImage(i).getScaledInstance(
					GUIResolutionController.convertScaleWidth(80), GUIResolutionController.convertScaleWidth(80),
					Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(newButtonImage));
			viewBoardButtons.add(button);
			leftTopSubPanel1.add(button, ltsp1);
			ltsp1.gridx += 1;
			ltsp1.insets = new Insets(0, 0, 0, 0);
		}

		ltp.gridx = 0;
		ltp.gridy += 1;
		ltp.weighty = 0;
		ltp.weightx = 1;
		ltp.insets = new Insets(0, 0, 0, 0);
		ltp.fill = GridBagConstraints.HORIZONTAL;
		leftTopPanel.add(leftTopSubPanel1, ltp);

		lp.gridx = 0;
		lp.gridy = 0;
		lp.weightx = 0.2;
		lp.weighty = 0;
		lp.fill = GridBagConstraints.HORIZONTAL;
		lp.anchor = GridBagConstraints.NORTH;

		leftPanel.add(leftTopPanel, lp);

		JPanel leftBottomPanel = new JPanel(new BorderLayout());
		leftBottomPanel.setBackground(Color.BLACK);
		lp.gridy += 1;
		lp.weighty = 1;
		lp.fill = GridBagConstraints.BOTH;
		lp.anchor = GridBagConstraints.NORTH;
		lp.insets = new Insets(0, GUIResolutionController.convertScaleWidth(250), 0,
				GUIResolutionController.convertScaleWidth(250));
		leftPanel.add(leftBottomPanel, lp);

		boardPanelDisplay = new JPanel();
		// boardPanelDisplay.setBackground(Color.BLACK);
		boardPanelDisplayCard = new CardLayout();
		boardPanelDisplay.setLayout(boardPanelDisplayCard);

		// create a GUI game board for each player in the game
		for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
			GUIGameController.getPlayerList().get(i).setGUIGameBoard(new GUIGameBoard());
			boardPanelDisplay.add(GUIGameController.getPlayerList().get(i).getGUIGameBoard(), "" + i);
			GUIGameController.getPlayerList().get(i).getGUIGameBoard().setBackground(new Color(0, 0, 0));
			// TODO
			GUIGameController.getPlayerList().get(i).getGUIGameBoard()
					.setBoardColor(GUIGameController.getPlayerColors(i));
			GUIGameController.getPlayerList().get(i).getGUIGameBoard().setCastleImage(i);
		}

		// if there are two players, adjust to the following setup
		if (GUIGameController.getPlayerList().size() == 2) {
			GUIImageController.setTwoPlayerMeepleImages();
			// set player 3 and 4's gameboard to be the same as player 1 and 2's
			// respectively
			try {
				GUIGameController.getPlayerList().add(new GUIPlayer("x", 3));
				GUIGameController.getPlayerList().add(new GUIPlayer("x", 4));
			} catch (InvalidPlayerNameException e1) {
				System.out.println("Error: Invalid player name in GUIGamePlayPanel.java");
				e1.printStackTrace();
			}

			GUIGameController.getPlayerList().get(2)
					.setGUIGameBoard(GUIGameController.getPlayerList().get(0).getGUIGameBoard());
			GUIGameController.getPlayerList().get(3)
					.setGUIGameBoard(GUIGameController.getPlayerList().get(1).getGUIGameBoard());

			// set players 1 and 3 to be equal, and 2 and 4 to be equal
			// GUIGameController.getPlayerList().set(3,
			// GUIGameController.getPlayerList().get(1));
		}

		leftBottomPanel.add(boardPanelDisplay);

		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.BLACK);
		rightPanel.setLayout(new GridBagLayout());
		mainPanel.add(rightPanel);

		GridBagConstraints rp = new GridBagConstraints();

		rp.gridx = 0;
		rp.gridy = 0;
		rp.weightx = 1;
		rp.weighty = 1;
		rp.fill = GridBagConstraints.BOTH;
		rp.insets = new Insets(0, 0, 20, 0);

		JPanel topRightPanel = new JPanel();
		topRightPanel.setLayout(new GridBagLayout());
		GridBagConstraints trp = new GridBagConstraints();
		topRightPanel.setBackground(Color.BLACK);

		dominoButtons = new ArrayList<GUIDominoButton>();
		meepleButtons = new ArrayList<GUIMeepleButton>();
		dominoButtonSelected = new boolean[GUIGameController.getPlayerList().size()];
		dominoButtonPlayed = new boolean[GUIGameController.getPlayerList().size()];

		trp.gridy = 0;
		trp.gridx = 0;
		trp.weightx = 1;
		trp.weighty = 0;
		trp.gridwidth = 4;
		trp.anchor = GridBagConstraints.NORTH;
		trp.fill = GridBagConstraints.HORIZONTAL;
		trp.insets = new Insets(GUIResolutionController.convertScaleHeight(50), 0, 0,
				GUIResolutionController.convertScaleWidth(200));

		dominoPanelLabel = new GUITitleJLabel("Domino Choices", GUIImageController.getBackGroundImage(9));

		dominoPanelLabel.setForeground(headerColor);
		dominoPanelLabel.setFont(new Font("Arial Black", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
		// dominoPanelLabel.setBackground(Color.MAGENTA);
		dominoPanelLabel.setOpaque(false);
		dominoPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topRightPanel.add(dominoPanelLabel, trp);

		trp.gridy = 1;
		trp.gridx = 0;
		trp.gridwidth = 1;
		trp.insets = new Insets(0, 0, 0, 0);

		GUIDominoButtonMouseListener dominoMouseListener = new GUIDominoButtonMouseListener(this);
		GUIMeepleButtonMouseListener meepleMouseListener = new GUIMeepleButtonMouseListener(this);

		int insetsTop = 40;
		int insetsBottom = 0;
		int insetsLeft = 20;
		int insetsRight = 100;
		int insetsTop2 = 40;
		int insetsBottom2 = 0;
		int insetsLeft2 = 0;
		int insetsRight2 = 100;

		for (int i = 0; i < GUIGameController.getPlayerList().size() * 2; i++) {
			if (trp.gridy > GUIGameController.getPlayerList().size() * 2) {
				trp.gridx += 2;
				trp.gridy = 1;
				insetsRight2 = 200;
				// trp.insets = new Insets(GUIResolutionController.convertScaleHeight(2),
				// GUIResolutionController.convertScaleWidth(100),
				// GUIResolutionController.convertScaleHeight(25),
				// GUIResolutionController.convertScaleWidth(300));
			}

			trp.gridy += 1;
			trp.weightx = 0.1;
			trp.weighty = 0.1;
			trp.ipadx = 50;
			trp.ipady = GUIResolutionController.convertScaleHeight(150);
			trp.fill = GridBagConstraints.BOTH;
			trp.insets = new Insets(GUIResolutionController.convertScaleHeight(insetsTop),
					GUIResolutionController.convertScaleWidth(insetsLeft),
					GUIResolutionController.convertScaleHeight(insetsBottom),
					GUIResolutionController.convertScaleWidth(insetsRight));
			GUIMeepleButton l = new GUIMeepleButton(null);
			l.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(40)));
			l.setForeground(subHeaderColor);
			// l.setBorder(new LineBorder(Color.BLACK, 2));
			l.setHorizontalAlignment(SwingConstants.CENTER);
			l.addMouseListener(meepleMouseListener);
			meepleButtons.add(l);
			topRightPanel.add(meepleButtons.get(i), trp);

			trp.gridx += 1;
			trp.weightx = 1;
			trp.weighty = 1;
			trp.ipadx = 0;
			// trp.ipady = GUIResolutionController.convertScaleWidth(150);
			trp.fill = GridBagConstraints.BOTH;
			trp.insets = new Insets(GUIResolutionController.convertScaleHeight(insetsTop2),
					GUIResolutionController.convertScaleWidth(insetsLeft2),
					GUIResolutionController.convertScaleHeight(insetsBottom2),
					GUIResolutionController.convertScaleWidth(insetsRight2));
			GUIDominoButton b = new GUIDominoButton(null) {
				private static final long serialVersionUID = -557996320688110473L;

				@Override
				public Dimension getPreferredSize() {
					return new Dimension();
				}
			};

			b.setBorder(null);
			b.setContentAreaFilled(false);
			b.setMargin(new Insets(0, 0, 0, 0));
			b.addMouseListener(dominoMouseListener);
			b.setBorder(new LineBorder(Color.BLACK, 1));
			dominoButtons.add(b);

			topRightPanel.add(dominoButtons.get(i), trp);
			trp.gridy += 1;
			trp.gridx -= 1;
			if (i < GUIGameController.getPlayerList().size()) {
				dominoButtons.get(i).setEnabled(false);
				meepleButtons.get(i).setEnabled(false);
			}
		}

		rightPanel.add(topRightPanel, rp);

		JPanel bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new GridBagLayout());
		GridBagConstraints brp = new GridBagConstraints();
		bottomRightPanel.setBackground(Color.BLACK);

		// BEGIN action panel setup
		actionButtons = new ArrayList<>();

		brp.gridy = 0;
		brp.gridx = 0;
		brp.weightx = 1;
		brp.weighty = 0;
		brp.ipady = 4;
		brp.gridwidth = 4;
		brp.anchor = GridBagConstraints.NORTH;
		brp.fill = GridBagConstraints.HORIZONTAL;
		brp.insets = new Insets(0, 0, 0, GUIResolutionController.convertScaleWidth(200));
		actionPanelLabel = new GUITitleJLabel("Action Menu", GUIImageController.getBackGroundImage(10));
		actionPanelLabel.setForeground(headerColor);

		actionPanelLabel.setFont(new Font("Arial Black", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
		actionPanelLabel.setOpaque(false);
		actionPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomRightPanel.add(actionPanelLabel, brp);

		brp.gridx = 0;
		brp.ipady = 0;
		brp.gridy = 1;
		brp.weightx = 1;
		brp.weighty = 1;
		brp.gridwidth = 1;
		brp.ipady = GUIResolutionController.convertScaleHeight(150);
		brp.insets = new Insets(0, 0, 0, 0);
		;

		String[] actionButtonString = { "Move Up", "Move Down", "Move Left", "Move Right", "Rotate Right",
				"Rotate Left", "Play", "Discard" };

		String[] actionButtonToolTip = { "Move the domino up on the player board",
				"Move the domino down on the player board", "Move the domino left on the player board",
				"Move the domino right on the player board", "Rotate the domino clockwise on the player board",
				"Rotate the domino counter-clockwise on the player board",
				"Play the domino at its current place on the player board",
				"Discard the domino and pass until the next turn" };

		GUIActionButtonKeyListener actionButtonKeyListener = new GUIActionButtonKeyListener(this);

		GUIActionButtonMouseListener actionMouseListener = new GUIActionButtonMouseListener(this);
		int rowCount = 0;
		for (int i = 0; i < 8; i++) {
			if (rowCount > 3) {
				brp.gridx = 0;
				brp.gridy += 1;
				rowCount = 0;
			}

			// add space to the left
			if (brp.gridx == 0) {
				brp.insets = new Insets(0, GUIResolutionController.convertScaleWidth(100), 0,
						GUIResolutionController.convertScaleWidth(40));
				// add space to the right side of the end of the button group to squeeze it to
				// the middle
			} else if ((brp.gridx + 1) % 4 == 0) {
				brp.insets = new Insets(0, 0, 0, GUIResolutionController.convertScaleWidth(300));
				// add space under bottom row of buttons to push the entire button group upwards
				// on the paenl
			} else if ((brp.gridy) % 2 == 0) {
				brp.insets = new Insets(0, 0, GUIResolutionController.convertScaleHeight(200),
						GUIResolutionController.convertScaleWidth(40));
			} else {
				brp.insets = new Insets(0, 0, 0, GUIResolutionController.convertScaleWidth(40));
			}

			GUIActionButton b = new GUIActionButton(GUIImageController.getActionButtonImage(i), this);
			actionButtons.add(b);
			b.addMouseListener(actionMouseListener);
			b.addKeyListener(actionButtonKeyListener);
			// b.setBorderPainted(false);
			b.setContentAreaFilled(false);
			b.setFocusPainted(false);
			b.setOpaque(false);

			TitledBorder title = BorderFactory.createTitledBorder(actionButtonString[i]);
			title.setTitleFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(40)));
			title.setTitleColor(headerColor);
			title.setTitleJustification(TitledBorder.CENTER);
			title.setTitlePosition(TitledBorder.BOTTOM);

			b.setBorder(title);
			b.setToolTipText(actionButtonToolTip[i]);
			bottomRightPanel.add(actionButtons.get(i), brp);
			brp.gridx++;
			rowCount++;
			brp.insets = new Insets(0, 0, 0, 0);
		}

		rp.gridx = 0;
		rp.gridy = 1;
		rp.weightx = 1;
		rp.weighty = 0.5;
		rp.fill = GridBagConstraints.BOTH;
		rp.insets = new Insets(0, 0, 0, 0);
		rightPanel.add(bottomRightPanel, rp);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;

		statusText = new ArrayList<String>();
		statusText.add("Round"); // 0 current round
		statusText.add("Player Turn"); // 1 current player's turn
		statusText.add("Current Action"); // 2 current action
		statusText.add(""); // 3 error message

		statusPanel = new JPanel();
		statusPanel.setBackground(Color.WHITE);
		statusPanel.setLayout(new BorderLayout());
		statusLabel = new JLabel("Status updates here");
		statusLabel.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusPanel.add(statusLabel, BorderLayout.CENTER);
		statusPanel.setToolTipText("Displays the current status of the game");

		this.add(statusPanel, c);

		c.gridx = 0;
		c.gridy += 1;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;

		this.add(mainPanel, c);

		c.gridx = 0;
		c.gridy += 1;
		c.weightx = 1;
		c.weighty = 2;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;

		JPanel blankPanel = new JPanel();
		blankPanel.setBackground(Color.BLACK);
		// this.add(blankPanel, c);

		startGame();
	}

	/**
	 * This method updates the status text info displayed at the top of the page
	 *
	 * @param -       type the text parameter to update: 0 current round 1 current
	 *                player's turn 2 current action 3 error message
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

		GUIGameController.setIsGameRunning(true);

		playerOrderList = new ArrayList<GUIPlayer>(GUIGameController.getPlayerList());

		// current dominos to be selected for this round
		currentDominoSelection = new ArrayList<Domino>();
		nextDominoSelection = new ArrayList<Domino>();

		setUpGameDominos(); // setup dominos for the entire game based on number of players

		randomizePlayerOrder(); // randomize player order

		round = 0; // set current round to round 1

		updateStatusText(0, "Round " + round + ":"); // update status label to show current round

		updateDominosForCurrentRound(); // update current dominos for selection and display

		playerOrderList.sort(Comparator.comparing(Player::getCurrentOrder)); // order players by turn order

		getNextPlayer(); // gets first player

		updateDominoButtonInfo(); // update information for dominos that can be selected in domino buttons
		// displayed
		GUISoundController.playGamePlayMusic();
	}

	/**
	 * This method restarts the game
	 */
	public void restartGame() {
		if (GUIGameController.getPlayerCount() == 2) {
			GUIGameController.getPlayerList().remove(3);
			GUIGameController.getPlayerList().remove(2);
		}

		finalRoundStarted = false;

		GUIGameController.setPlacingDomino(false);

		this.initialized = false;
		this.initialize();

		selectedDomino = null;

		dominosPlayedThisRound = 0;

		for (GUIPlayer p : GUIGameController.getPlayerList()) {
			p.setNextDomino(null);
			p.setCurrentDomino(null);
		}

		updateAllPlayerButtonText();

		resetDominoButtonClickability(); // makes domino buttons clickable again

		resetDominoAndMeepleImagesRight();

		GUIGameController.setIsGameRunning(true);

		playerOrderList = new ArrayList<GUIPlayer>(GUIGameController.getPlayerList());

		// current dominos to be selected for this round
		currentDominoSelection = new ArrayList<Domino>();

		this.dominoList = new ArrayList<Domino>(); // game domino list

		for (Domino d : StaticGameDominoList.getDominosList()) {
			dominoList.add(d);
		}

		setUpGameDominos(); // setup dominos for the entire game based on number of players

		randomizePlayerOrder(); // randomize player order

		round = 0; // set current round to round 1

		updateStatusText(0, "Round " + round + ":"); // update status label to show current round

		updateDominosForCurrentRound(); // update current dominos for selection and display

		playerOrderList.sort(Comparator.comparing(Player::getCurrentOrder)); // order players by turn order

		getNextPlayer(); // gets first player

		updateDominoButtonInfo(); // update information for dominos that can be selected in domino buttons
		// displayed
		GUISoundController.playGamePlayMusic();
	}

	/**
	 * This method ends the game
	 */
	public static void endGame() {
		for (GUIPlayer p : GUIGameController.getPlayerList()) {
			p.getGUIGameBoard().resetPlayLayerImageAndStopTimer();
		}
		GUIGameController.setIsGameRunning(false);
		GUISoundController.playWinnerMusic(); // play end game music
		// then initialize score display panel and switch to it
		((GUIScoringPanel) GUIComponentController.getComponentList().get(7)).initialize();
		GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "scoringPanel");
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

		// go through and randomly remove the number of dominos needed from the list of
		// all dominos
		// after removal, the dominos remaining will be the dominos playable for the
		// game
		for (int i = 0; i < dominosToRemove; i++) {
			int max = dominoList.size();
			dominoList.remove(rand.nextInt(max));
		}
	}

	/**
	 * This method moves the images on domino and meeple buttons from the right ot the left
	 */
	public void moveDominoAndMeepleImagesLeft() {
		int playerNumber = GUIGameController.getPlayerList().size();
		// for each domino and meeple image on the right, move it to the left
		for (int i = playerNumber; i < getDominoButtons().size(); i++) {
			getDominoButtons().get(i - playerNumber).setDominoImage(getDominoButtons().get(i).getDominoImage());
			getMeepleButtons().get(i - playerNumber).setMeepleImage(getMeepleButtons().get(i).getMeepleImage());
			getMeepleButtons().get(i - playerNumber).selectMeeple(true);
			getMeepleButtons().get(i).selectMeeple(false);
		}
	}

	/**
	 * This method resets domino and meeple button images on the right
	 */
	public void resetDominoAndMeepleImagesRight() {
		int playerNumber = GUIGameController.getPlayerList().size();
		for (int i = playerNumber; i < getDominoButtons().size(); i++) {
			// make each domino and meeple image on the right null, then reset the meeple mouseover toggle
			getDominoButtons().get(i).setDominoImage(null);
			getMeepleButtons().get(i).setMeepleImage(null);
			getMeepleButtons().get(i).setMousedOver(false);
		}
	}

	/**
	 * This method initiates the final round
	 */
	public void finalRound() {
		getMeepleButtons().get(getDominosPlayedThisRound()).selectMeeple(false);
		currentPlayer = playerOrderList.get(dominosPlayedThisRound); // get current player
		updateStatusText(1, currentPlayer.getPlayerName().toUpperCase() + ", "); // update player status text
		updateStatusText(2, "PLAY YOUR LAST DOMINO!"); // update current action status text
		displayCurrentPlayerGameBoard(); // display current player's game board

		GUIGameController.setPlacingDomino(true);

		updateStatusText(2, "PLAY YOUR DOMINO! Move, rotate, place, discard, or re-choose your domino.");

		for (int k = 0; k < getPlayerOrderList().size(); k++) {
			if (GUIGameController.getPlayerList().get(k) == getCurrentPlayer()) {
				playableBoardSpaceHighlight(getCurrentPlayer().getGUIGameBoard(),
						getCurrentPlayer().getCurrentDomino());
				setCurrentPlayerNumber(k); // find the current player and get order number
				// in list
				int[] boardArea = GUIGameController.getPlayerList().get(k).getGUIGameBoard().getGameBoard()
						.getPlayArea(); // get playable board area
				// then get virtual location of selected domino on player board

				Domino currentDomino = getCurrentPlayer().getCurrentDomino();

				currentDomino.getSideA().setXLoc(boardArea[0]);
				currentDomino.getSideA().setYLoc(boardArea[2]);
				currentDomino.getSideB().setXLoc(boardArea[0] + 1);
				currentDomino.getSideB().setYLoc(boardArea[2]);

				int sax = currentDomino.getSideA().getXLoc(); // side A x location
				int say = currentDomino.getSideA().getYLoc(); // side A y location
				int sbx = currentDomino.getSideB().getXLoc(); // side B x location
				int sby = currentDomino.getSideB().getYLoc(); // side B y location

				GameBoard.Space sideA = getCurrentPlayer().getGUIGameBoard().getGameBoard().getGameBoardSpace(sax, say);
				GameBoard.Space sideB = getCurrentPlayer().getGUIGameBoard().getGameBoard().getGameBoardSpace(sbx, sby);

				// show the image of the domino over the player's board
				if (sideA.getSType() == LandType.EMPTY && sideB.getSType() == LandType.EMPTY
						&& verifyMatchingSide(getCurrentPlayer().getGUIGameBoard().getGameBoard().getGameBoardSpaces(),
								sax, say, sbx, sby, currentDomino)) {

					// place domino on the game board

					GUIGameController.getPlayerList().get(k).getGUIGameBoard().savePlayLayerImage(currentDomino, null,
							0);
					return;
					// currentDominoSelection.remove(guiGamePlayPanel.getSelectedDomino());
				} else {
					GUIGameController.getPlayerList().get(k).getGUIGameBoard().savePlayLayerImage(currentDomino,
							new Color(255, 0, 0), 0);
					return;
				}

			} // skip player in list if not the current player

		}
		GUISoundController.playSelectTileSound(); // play selection sound
	}

	/**
	 * This method gets the next player in turn
	 */
	public void getNextPlayer() {
		// if all dominos have been played this round, set up the next round
		if (dominosPlayedThisRound == playerOrderList.size()) {
			if (finalRoundStarted) {
				endGame();
				return;
			}
			dominosPlayedThisRound = 0; // reset number of dominos played
			round++; // advance to next round
			updateStatusText(0, "ROUND " + round + "! "); // update round status text at top of screen

			// get player order for next round; determined by dominos they selected last
			// round
			for (GUIPlayer p : playerOrderList) {
				p.setCurrentOrder(p.getNextOrder());
				p.setCurrentDomino(p.getNextDomino());
			}

			playerOrderList.sort(Comparator.comparing(Player::getCurrentOrder)); // order players by turn order
			currentPlayer = playerOrderList.get(0); // set first player as the current player
			// update current action status
			updateStatusText(1, currentPlayer.getPlayerName().toUpperCase() + ", "); // update current player status
			updateStatusText(2, "CHOOSE A DOMINO! Click a domino then confirm by clicking your meeple to its left.");

			displayCurrentPlayerGameBoard(); // display current player's game board
			moveDominoAndMeepleImagesLeft(); // move domino and meeple images from right to left
			resetDominoAndMeepleImagesRight(); // reset the images on the right

			if (this.dominoList.size() > 0) {
				updateDominosForCurrentRound(); // randomly generate dominos for the current round
				updateDominoButtonInfo(); // update the domino buttons to display the newly generated dominos
				// resetDominoButtonClickability(); // makes domino buttons clickable again
			} else {
				finalRoundStarted = true;
				finalRound();
			}

		} else {
			currentPlayer = playerOrderList.get(dominosPlayedThisRound); // get current player
			updateStatusText(1, currentPlayer.getPlayerName().toUpperCase() + ", "); // update current player status
			updateStatusText(2, "CHOOSE A DOMINO! Click a domino then confirm by clicking your meeple to its left.");
			displayCurrentPlayerGameBoard(); // display current player's game board
			
			if (finalRoundStarted) {
				finalRound();
			}
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
					vbml.setSelectedNum(i - 2);
				} else {
					boardPanelDisplayCard.show(boardPanelDisplay, String.valueOf(i));
					vbml.setSelectedNum(i);
				}
				// darken the tabs of the other players not being viewed
				switchViewBoardButtonColorsOnSelect(i);
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

					playerListString = playerListString.append(p.getPlayerName() + " is the " + orderText + " player");

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

	public void playableBoardSpaceHighlight(GUIGameBoard gameboard, Domino domino) {
		gameboard.resetBoardImagesUnderlay();
		ArrayList<int[]> playOptions = new ArrayList<>();

		// int[] castle = new int[] { 4, 4 };
		int[] playArea = gameboard.getGameBoard().getPlayArea();

		for (int y = playArea[2]; y <= playArea[3]; y++) {
			for (int x = playArea[0]; x < playArea[1]; x++) {
				LandType compareType = gameboard.getGameBoard().getGameBoardSpaces()[x][y].getSType();
				if (domino.getSideA().getSType() == compareType || domino.getSideB().getSType() == compareType
						|| compareType == LandType.CASTLE) {
					if (x >= playArea[0]) {
						if (gameboard.getGameBoard().getGameBoardSpace(Math.max(x - 1, playArea[0]), y)
								.getSType() == LandType.EMPTY) {
							playOptions.add(new int[] { Math.max(x - 1, playArea[0]), y });
							playOptions.add(new int[] { Math.max(x - 2, playArea[0]), y });
							playOptions.add(new int[] { x, y });
							playOptions.add(new int[] { Math.max(x - 1, playArea[0]), Math.min(y + 1, playArea[3]) });
							playOptions.add(new int[] { Math.max(x - 1, playArea[0]), Math.max(y - 1, playArea[2]) });
						}
					}
					// get the space type to the right side of the domino

					if (x <= playArea[1]) {
						if (gameboard.getGameBoard().getGameBoardSpace(Math.min(x + 1, playArea[1]), y)
								.getSType() == LandType.EMPTY) {
							playOptions.add(new int[] { Math.min(x + 1, playArea[1]), y });
							playOptions.add(new int[] { Math.min(x + 2, playArea[1]), y });
							playOptions.add(new int[] { x, y });
							playOptions.add(new int[] { Math.min(x + 1, playArea[1]), Math.min(y + 1, playArea[3]) });
							playOptions.add(new int[] { Math.min(x + 1, playArea[1]), Math.max(y - 1, playArea[2]) });
						}

					}

					// get the space type below the domino
					if (y >= playArea[2]) {
						if (gameboard.getGameBoard().getGameBoardSpace(x, Math.max(y - 1, playArea[2]))
								.getSType() == LandType.EMPTY) {
							playOptions.add(new int[] { x, Math.max(y - 1, playArea[2]) });
							playOptions.add(new int[] { Math.min(x + 1, playArea[1]), Math.max(y - 1, playArea[2]) });
							playOptions.add(new int[] { Math.max(x - 1, playArea[0]), Math.max(y - 1, playArea[2]) });
							playOptions.add(new int[] { x, y });
							playOptions.add(new int[] { x, Math.max(y - 2, playArea[2]) });
						}
					}

					// get the space type above the domino
					if (y <= playArea[3]) {
						if (gameboard.getGameBoard().getGameBoardSpace(x, Math.min(y + 1, playArea[3]))
								.getSType() == LandType.EMPTY) {
							playOptions.add(new int[] { x, Math.min(y + 1, playArea[3]) });
							playOptions.add(new int[] { Math.min(x + 1, playArea[1]), Math.min(y + 1, playArea[3]) });
							playOptions.add(new int[] { Math.max(x - 1, playArea[0]), Math.min(y + 1, playArea[3]) });
							playOptions.add(new int[] { x, Math.min(y + 2, playArea[3]) });
							playOptions.add(new int[] { x, y });
						}
					}
				}
			}
		}
		gameboard.showPlayableAreaImagesUnderlay(playOptions);
	}

	/**
	 * This method verifies whether the sides of a domino match the board area at
	 * which it's being placed
	 *
	 * @param playerGameBoard - player's game board
	 * @param sax             - domino's A side x location
	 * @param say             - domino's A side y location
	 * @param sbx             - domino's B side x location
	 * @param sby             - domino's B side y location
	 * @param selectedDomino  - the selected domino to be compared
	 * @return playable (true) or not playable (false)
	 */
	public boolean verifyMatchingSide(GameBoard.Space[][] playerGameBoard, int sax, int say, int sbx, int sby,
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
	 * This method gathers a list of side types that the current domino selected can
	 * be played against
	 *
	 * @param playerGameBoard - player's game board
	 * @param x               - x location on game board
	 * @param y               - y location on game board
	 * @param sideOptions     - list of side options
	 */
	private void gatherSideTypes(GameBoard.Space[][] playerGameBoard, int x, int y, ArrayList<LandType> sideOptions) {
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
	 * This method updates the domino button display image, info label, and tool
	 * tips
	 */
	public void updateDominoButtonInfo() {
		for (int x = GUIGameController.getPlayerList().size(); x < dominoButtons.size(); x++) {
			dominoButtons.get(x).setEnabled(false);
			meepleButtons.get(x).setEnabled(false);
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int delay = 500;
				new Timer(delay, new ActionListener() {
					private int i = 0;
					private int k = 0;

					@Override
					public void actionPerformed(ActionEvent e) {
						if (k < currentDominoSelection.size()) {
							GUISoundController.playRotateTileSound();
							dominoButtons.get(k + GUIGameController.getPlayerList().size())
									.setDominoImage(GUIStaticGameDominoList.dominoImagesBack
											.get(currentDominoSelection.get(k).getDominoNumber() - 1));
							k++;
						} else if (i < currentDominoSelection.size()) {
							GUISoundController.playPlaceTileSound();
							dominoButtons.get(i + GUIGameController.getPlayerList().size())
									.setDominoImage(GUIStaticGameDominoList.dominoImagesFront
											.get(currentDominoSelection.get(i).getDominoNumber() - 1));
							i++;
						} else {
							((Timer) e.getSource()).stop();
							for (int x = GUIGameController.getPlayerList().size(); x < dominoButtons.size(); x++) {
								dominoButtons.get(x).setEnabled(true);
								meepleButtons.get(x).setEnabled(true);
							}
						}
					}
				}).start();
			}
		});
	}

	/**
	 * This method resets domino button clickability, making them clickable again
	 */
	public void resetDominoButtonClickability() {
		for (int i = GUIGameController.getPlayerList().size(); i < getDominoButtons().size(); i++) {
			getDominoButtons().get(i).setEnabled(true);
			getMeepleButtons().get(i).setEnabled(true);
		}
	}

	/**
	 * Update the text on the view tabs for player boards to display the current
	 * name and score Should be called each time the player adds a domino to their
	 * board
	 */
	public void updatePlayerButtonText() {
		String preHTML = "<html>";
		String newLine = "<p style='margin-top:-5'>";
		String postHTML = "</p></html>";

		for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
			if (currentPlayer == GUIGameController.getPlayerList().get(i)) {
				int score = GUIGameController.getPlayerList().get(i).getGUIGameBoard().getGameBoard().getCurrentScore();
				String name = GUIGameController.getPlayerList().get(i).getPlayerName();
				if (GUIGameController.getPlayerCount() == 2) {
					if (i > 1) {
						viewBoardButtons.get(i - 2)
								.setText(preHTML + name + newLine + " (" + score + " pts)" + postHTML);
					} else {
						viewBoardButtons.get(i).setText(preHTML + name + newLine + " (" + score + " pts)" + postHTML);
					}
				} else {
					viewBoardButtons.get(i).setText(preHTML + name + newLine + " (" + score + " pts)" + postHTML);
				}
			}
		}
	}

	public void updateAllPlayerButtonText() {
		String preHTML = "<html>";
		String newLine = "<p style='margin-top:-5'>";
		String postHTML = "</p></html>";

		for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
			int score = GUIGameController.getPlayerList().get(i).getGUIGameBoard().getGameBoard().getCurrentScore();
			String name = GUIGameController.getPlayerList().get(i).getPlayerName();
			if (GUIGameController.getPlayerCount() == 2) {
				if (i > 1) {
					viewBoardButtons.get(i - 2).setText(preHTML + name + newLine + " (" + score + " pts)" + postHTML);
				} else {
					viewBoardButtons.get(i).setText(preHTML + name + newLine + " (" + score + " pts)" + postHTML);
				}
			} else {
				viewBoardButtons.get(i).setText(preHTML + name + newLine + " (" + score + " pts)" + postHTML);
			}
		}
	}

	public void updateDominosForNextRound() {
		nextDominoSelection = new ArrayList<Domino>();

		for (int i = 0; i < GUIGameController.getPlayerList().size(); i++) {
			int dominoToRemove = rand.nextInt(this.dominoList.size());
			// get a random domino from the main list of dominos and add it to the currently
			// selectable list
			nextDominoSelection.add(this.dominoList.get(dominoToRemove));
			dominoList.remove(dominoToRemove); // then remove domino from main list
		}

		// order dominos by domino number
		nextDominoSelection.sort(Comparator.comparing(Domino::getDominoNumber));

		// set selection order for each domino, so it determines next round order when
		// player selects
		for (int i = 0; i < nextDominoSelection.size(); i++) {
			nextDominoSelection.get(i).setPlayerOrder(i);
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
				viewBoardButtons.get(i)
						.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(50)));
			} else { // while darkening the others
				viewBoardButtons.get(i).setBackground(Color.GRAY);
				viewBoardButtons.get(i)
						.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(40)));
			}
		}
	}

	public int getCurrentPlayerNumber() {
		return this.currentPlayerNumber;
	}

	public void setCurrentPlayerNumber(int currentPlayerNumber) {
		this.currentPlayerNumber = currentPlayerNumber;
	}

	public int getSelectedDominoNumber() {
		return this.selectedDominoNumber;
	}

	public void setSelectedDominoNumber(int selectedDominoNumber) {
		this.selectedDominoNumber = selectedDominoNumber;
	}

	public ArrayList<GUIDominoButton> getDominoButtons() {
		return this.dominoButtons;
	}

	public boolean getFinalRoundStarted() {
		return this.finalRoundStarted;
	}

	public ArrayList<GUIActionButton> getActionButtons() {
		return this.actionButtons;
	}

	public Domino getSelectedDomino() {
		return this.selectedDomino;
	}

	public void setSelectedDomino(Domino selectedDomino) {
		this.selectedDomino = selectedDomino;
	}

	public ArrayList<GUIMeepleButton> getMeepleButtons() {
		return this.meepleButtons;
	}

	public GUIPlayer getCurrentPlayer() {
		return this.currentPlayer;
	}

	public int getDominosPlayedThisRound() {
		return this.dominosPlayedThisRound;
	}

	public void setDominosPlayedThisRound(int dominosPlayedThisRound) {
		this.dominosPlayedThisRound = dominosPlayedThisRound;
	}

	public boolean[] getDominoButtonSelected() {
		return this.dominoButtonSelected;
	}

	public boolean[] getDominoButtonsPlayed() {
		return this.dominoButtonPlayed;
	}

	public ArrayList<Domino> getDominoList() {
		return dominoList;
	}

	public boolean getGameEnded() {
		return gameEnded;
	}

	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}

	public CardLayout getViewBoardPanelDisplayCard() {
		return boardPanelDisplayCard;
	}

	public JPanel getViewBoardPanl() {
		return boardPanelDisplay;
	}

	public ArrayList<JButton> getViewBoardButtons() {
		return this.viewBoardButtons;
	}

	public ArrayList<GUIPlayer> getPlayerOrderList() {
		return this.playerOrderList;
	}

	public ArrayList<Domino> getCurrentDominoSelection() {
		return this.currentDominoSelection;
	}

	public ArrayList<Domino> getNextCurrentDominoSelection() {
		return this.nextDominoSelection;
	}

	public void setDominoButtonPlayed(int index, boolean flag) {
		dominoButtonPlayed[index] = flag;
	}

	public void setDominoButtonSelected(int index, boolean flag) {
		this.dominoButtonSelected[index] = flag;
	}
}
