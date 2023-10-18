/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIGameController.java
 * Description: This class controls the logic for displaying the game board as a GUI component
 */

import java.awt.Color;
import java.util.ArrayList;

public class GUIGameController {
	private static ArrayList<GUIPlayer> playerList; // list of players in game
	private static int playerCount; // number of players in game
	private static Color[] playerColors; // player colors based on meeple color
	
	static {
		
		playerColors = new Color[]{new Color(255,105,180), new Color(0,195,255), 
							new Color(50,205,50), new Color(255,255,0) };
		
		// create new list of GUIPlayer players
		playerList = new ArrayList<GUIPlayer>();
	}
		
	// setters
		
	/**
	 * This method sets the number of players in the game
	 * @param newPlayerCount - number of players in game
	 */
	public static void setPlayerCount(int newPlayerCount ) {
		playerCount = newPlayerCount;
	}
	
	/**
	 * This method sets the player list for the game
	 * @param newPlayerList - player list to use
	 */
	public static void setPlayerList(ArrayList<GUIPlayer> newPlayerList) {
		playerList = newPlayerList;
	}
		
	// getters
	
	/**
	 * This method returns the color for the player selected
	 * @param player - player selected (0, 1, 2, and 3 for players 1, 2, 3, and 4 respectively)
	 * @return
	 */
	public static Color getPlayerColors(int player) {
		if (player == 0) { return playerColors[0]; }
		if (player == 1) { return playerColors[1]; }
		if (player == 2) { return playerColors[2]; }
		if (player == 3) { return playerColors[3]; }
		return null;
	}
		
	/**
	 * This method returns the player list for the game
	 * @return - player list
	 */
	public static ArrayList<GUIPlayer> getPlayerList() {
		return playerList;
	}
		
	/**
	 * This method gets the number of playrers in the game
	 * @return - number of players
	 */
	public static int getPlayerCount() {
		return playerCount;
	}
}
