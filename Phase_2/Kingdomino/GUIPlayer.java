/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIPlayer.java
 * Description: This class is the GUI overlay to the Player class
 */

public class GUIPlayer extends Player{

	private GUIGameBoard guiGameBoard; // player's game board
	
	public GUIPlayer(String name) throws InvalidPlayerNameException {
		super(name);
		this.guiGameBoard = new GUIGameBoard();
	}
	
	/**
	 * This method sets the user's game board
	 * @param guiGameBoard - target game board to apply
	 */
	public void setGUIGameBoard(GUIGameBoard guiGameBoard) {
		this.guiGameBoard = guiGameBoard;
	}
	
	/**
	 * This method returns the user's game board
	 * @return - target game board to return
	 */
	public GUIGameBoard getGUIGameBoard() {
		return this.guiGameBoard;
	}

}
