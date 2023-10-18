/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIPlayer.java
 * @Description: This class is the GUI overlay to the Player class
 */

public class GUIPlayer extends Player{

	private GUIGameBoard guiGameBoard; // player's game board
	private Domino nextDomino;
	private Domino secondNextDomino;
	private Domino currentDomino;
	private Domino secondCurrentDomino;
	private int playerNumber;
	
	public GUIPlayer(String name, int playerNumber) throws InvalidPlayerNameException {
		super(name);
		this.guiGameBoard = new GUIGameBoard();
		this.playerNumber = playerNumber;
	}
	
	/**
	 * This method sets the user's game board
	 * @param guiGameBoard - target game board to apply
	 */
	public void setGUIGameBoard(GUIGameBoard guiGameBoard) {
		this.guiGameBoard = guiGameBoard;
	}
	
	public void setNextDomino(Domino nextDomino) {
		this.nextDomino = nextDomino;
	}
	
	public void setSecondNextDomino(Domino secondNextDomino) {
		this.secondNextDomino = secondNextDomino;
	}
	
	public void setCurrentDomino(Domino currentDomino) {
		this.currentDomino = currentDomino;
	}
	
	public void setSecondCurrentDomino(Domino secondCurrentDomino) {
		this.secondCurrentDomino = secondCurrentDomino;
	}
	
	/**
	 * This method returns the user's game board
	 * @return - target game board to return
	 */
	public GUIGameBoard getGUIGameBoard() {
		return this.guiGameBoard;
	}

	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	public Domino getNextDomino() {
		return this.nextDomino;
	}
	
	public Domino getSecondNextDomino() {
		return this.secondNextDomino;
	}
	
	public Domino getCurrentDomino() {
		return this.currentDomino;
	}
	
	public Domino getSecondCurrentDomino() {
		return this.secondCurrentDomino;
	}
}
