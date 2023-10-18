/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIGameController.java
 * @Description: This class controls compnenets that are shared across classes during the game play
 */

import java.awt.Color;
import java.util.ArrayList;

public class GUIGameController {
	private static ArrayList<GUIPlayer> playerList; // list of players in game
	private static int playerCount; // number of players in game
	private static Color[] playerColors; // player colors based on meeple color
	private static boolean debugMode = false; // whether game is in debug mode; not enough time to implement
	private static boolean gameRunning = false; // flag whether game has started or not
	private static boolean placingDomino = false; // whether a player is currently placing a domino in game
	
	static {
		
		playerColors = new Color[]{ // create colors for each player based on meeple color from board game
				new Color(255,105,180), 
				new Color(0,195,255), 	
				new Color(50,205,50), 
				new Color(255,255,0) };
		playerList = new ArrayList<GUIPlayer>(); // create new list of GUIPlayer players
	}
		
	// setters
	public static void setIsGameRunning(boolean newGameRunning) {
		gameRunning = newGameRunning;
	}

	public static void setPlayerCount(int newPlayerCount ) {
		playerCount = newPlayerCount;
	}
	
	public static void setPlayerList(ArrayList<GUIPlayer> newPlayerList) {
		playerList = newPlayerList;
	}

	public static void setPlacingDomino(boolean newPlacingDomino) {
		placingDomino = newPlacingDomino;
		
		// stop animation syncher if placing a domino; otherwise, start it
		if (placingDomino) {
			GUIAnimatedButtonSynchronizer.getTimer().stop();
		} else {
			GUIAnimatedButtonSynchronizer.getTimer().start();
		}
	}
	
	// getters
	
	public static boolean getPlacingDomino() {
		return placingDomino;
	}

	public static Color getPlayerColors(int player) {
		if (player == 0) { return playerColors[0]; }
		if (player == 1) { return playerColors[1]; }
		if (player == 2) { return playerColors[2]; }
		if (player == 3) { return playerColors[3]; }
		return null;
	}
		
	public static ArrayList<GUIPlayer> getPlayerList() {
		return playerList;
	}
		
	public static int getPlayerCount() {
		return playerCount;
	}
	
	public static boolean getDebugMode() {
		return debugMode;
	}
	
	public static boolean getGameIsRunning() {
		return gameRunning;
	}
}
