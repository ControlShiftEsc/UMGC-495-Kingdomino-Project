/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIActionButtonMouseListener.java
 * @Description: This is the mouse listener for the GUI action buttons that control dominos played on the game board
 */

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIActionButtonMouseListener extends MouseAdapter {

	private int currentPlayerCounted = -1;
	private GUIGamePlayPanel guiGamePlayPanel;
	private String moveErrorPre = "Moving the domino";
	private String moveErrorPost = "will place it outside of the play area.";
	private String[] errorMsg = new String[] {
			"Please choose and confirm your domino selection above before selecting an action.",
			moveErrorPre + " left " + moveErrorPost, moveErrorPre + " right " + moveErrorPost,
			moveErrorPre + " up " + moveErrorPost, moveErrorPre + " down " + moveErrorPost,
			"Rotating the domino clockwise " + moveErrorPost, "Rotating the domino counter clockwise " + moveErrorPost,
			"Location must be empty in order to place a domino.",
			"Domino placed must touch the Castle space or have at least one side match a space next to it." };

	private String[] errorTypes = new String[] { "INVALID MOVEMENT:\n\n", "INVALID ACTION:\n\n",
			"INVALID PLACEMENT:\n\n" };

	public GUIActionButtonMouseListener(GUIGamePlayPanel guiGamePlayPanel) {
		this.guiGamePlayPanel = guiGamePlayPanel;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// set the current domino
		Domino currentDomino = guiGamePlayPanel.getCurrentPlayer().getCurrentDomino();

		// if currently in the placing domino phase of a round, show error and do nothing
		if (!GUIGameController.getPlacingDomino()) {
			new GUIErrorPopupPanel(errorTypes[1], errorMsg[0],
					guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			return;
		}

		// show error and do no action if no domino has been selected
		if (currentDomino == null) {
			new GUIErrorPopupPanel(errorTypes[1], errorMsg[0],
					guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			return;
		}

		// first, get the virtual location of the currently selected domino to compare
		// against the current player's board
		int sax = currentDomino.getSideA().getXLoc(); // side A x location
		int say = currentDomino.getSideA().getYLoc(); // side A y location
		int sbx = currentDomino.getSideB().getXLoc(); // side B x location
		int sby = currentDomino.getSideB().getYLoc(); // side B y location

		int[] boardArea = { 0, 0, 0, 0 }; // initialize playable bounds on player board

		// TODO early rough implementation to find current player board that can likely
		// be made better
		// below finds the play area of the current player's game board
		currentPlayerCounted = -1;
		for (int k = 0; k < GUIGameController.getPlayerList().size(); k++) {
			currentPlayerCounted++;
			if (GUIGameController.getPlayerList().get(k) == guiGamePlayPanel.getCurrentPlayer()) {
				boardArea = GUIGameController.getPlayerList().get(k).getGUIGameBoard().getGameBoard().getPlayArea();
				break;
			}
		}

		// set the current game board
		GUIGameBoard currentGameBoard = GUIGameController.getPlayerList().get(currentPlayerCounted).getGUIGameBoard();
		boolean movementOption = false;

		// move up button action
		if (e.getSource() == guiGamePlayPanel.getActionButtons().get(0)) {
			movementOption = true;
			// don't do up if moving outside of game board
			if (currentDomino.getSideA().getYLoc() - 1 < boardArea[2]
					|| currentDomino.getSideB().getYLoc() - 1 < boardArea[2]) {
				new GUIErrorPopupPanel(errorTypes[1], errorMsg[1],
						guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			} else {
				// move virtual location of currently selected domino upward on player board
				GUISoundController.playMoveTileSound();
				currentDomino.getSideA().setYLoc(currentDomino.getSideA().getYLoc() - 1);
				currentDomino.getSideB().setYLoc(currentDomino.getSideB().getYLoc() - 1);
			}

		// move down button action
		} else if (e.getSource() == guiGamePlayPanel.getActionButtons().get(1)) {
			movementOption = true;
			// don't move down if moving down moves the domino outside of the game board
			if (currentDomino.getSideA().getYLoc() + 1 > boardArea[3]
					|| currentDomino.getSideB().getYLoc() + 1 > boardArea[3]) {
				new GUIErrorPopupPanel(errorTypes[0], errorMsg[2],
						guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
				// otherwise do the movement
			} else {
				GUISoundController.playMoveTileSound();
				currentDomino.getSideA().setYLoc(currentDomino.getSideA().getYLoc() + 1);
				currentDomino.getSideB().setYLoc(currentDomino.getSideB().getYLoc() + 1);
			}
		// move left button action
		} else if (e.getSource() == guiGamePlayPanel.getActionButtons().get(2)) {
			movementOption = true;
			if (currentDomino.getSideA().getXLoc() - 1 < boardArea[0]
					|| currentDomino.getSideB().getXLoc() - 1 < boardArea[0]) {
				new GUIErrorPopupPanel(errorTypes[0], errorMsg[3],
						guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);

			} else {
				GUISoundController.playMoveTileSound();
				currentDomino.getSideA().setXLoc(currentDomino.getSideA().getXLoc() - 1);
				currentDomino.getSideB().setXLoc(currentDomino.getSideB().getXLoc() - 1);
			}
		// move right button action
		} else if (e.getSource() == guiGamePlayPanel.getActionButtons().get(3)) {
			movementOption = true;
			if (currentDomino.getSideA().getXLoc() + 1 > boardArea[1]
					|| currentDomino.getSideB().getXLoc() + 1 > boardArea[1]) {
				new GUIErrorPopupPanel(errorTypes[0], errorMsg[4],
						guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			} else {
				GUISoundController.playMoveTileSound();
				currentDomino.getSideA().setXLoc(currentDomino.getSideA().getXLoc() + 1);
				currentDomino.getSideB().setXLoc(currentDomino.getSideB().getXLoc() + 1);
			}
		// rotate Clockwise button action
		} else if (e.getSource() == guiGamePlayPanel.getActionButtons().get(4)) {
			movementOption = true;
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
			if (tempsby < boardArea[2] || tempsby > boardArea[3] || tempsbx < boardArea[0] || tempsbx > boardArea[1]) {
				new GUIErrorPopupPanel(errorTypes[0], errorMsg[5],
						guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			} else {
				GUISoundController.playRotateTileSound();
				currentDomino.getSideB().setXLoc(tempsbx);
				currentDomino.getSideB().setYLoc(tempsby);
				// currentGameBoard.savePlayLayerImage(currentDomino, null);
			}
		// rotate Counterclockwise button action
		} else if (e.getSource() == guiGamePlayPanel.getActionButtons().get(5)) {
			movementOption = true;
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

			if (tempsby < boardArea[2] || tempsby > boardArea[3] || tempsbx < boardArea[0] || tempsbx > boardArea[1]) {
				new GUIErrorPopupPanel(errorTypes[0], errorMsg[6],
						guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			} else {
				GUISoundController.playRotateTileSound();
				currentDomino.getSideB().setXLoc(tempsbx);
				currentDomino.getSideB().setYLoc(tempsby);
			}
		}

		// if a movement option was done, rest the gameboard and show the playable area
		if (movementOption) {
			highlightDominoIfPlayable(currentGameBoard, currentDomino.getSideA().getXLoc(),
					currentDomino.getSideA().getYLoc(), currentDomino.getSideB().getXLoc(),
					currentDomino.getSideB().getYLoc());
			return;
		}

		// play domino at current location action
		if (e.getSource() == guiGamePlayPanel.getActionButtons().get(6)) {
			// toggle mouseover of the selected button to false
			guiGamePlayPanel.getActionButtons().get(6).setMousedOver(false);

			// get the game board space locations of where the domino is to be placed
			GameBoard.Space SideA = currentGameBoard.getGameBoard().getGameBoardSpace(sax, say);
			GameBoard.Space SideB = currentGameBoard.getGameBoard().getGameBoardSpace(sbx, sby);
			
			// if the domino is playable at the location
			if (SideA.getSType() == LandType.EMPTY && SideB.getSType() == LandType.EMPTY
					&& guiGamePlayPanel.verifyMatchingSide(currentGameBoard.getGameBoard().getGameBoardSpaces(), sax,
							say, sbx, sby, currentDomino)) {

				// place domino on the game board
				currentGameBoard.saveOverlayImage(currentDomino);
				currentGameBoard.resetBoardImagesUnderlay();
				currentGameBoard.repaint();
				// currentDominoSelection.remove(currentDomino);

				GUISoundController.playPlaceTileSound(); // play placement sound

				guiGamePlayPanel.setSelectedDomino(null); // set the selected domino to null

				GUIGameController.setPlacingDomino(false); // turn off the placing domino phase of the game

				guiGamePlayPanel.getCurrentPlayer().setCurrentDomino(null); // null the current player's current domino

				// if the final round has already started, do special actions since this isn't a normal round
				if (guiGamePlayPanel.getFinalRoundStarted()) {
					guiGamePlayPanel.setDominosPlayedThisRound(guiGamePlayPanel.getDominosPlayedThisRound() + 1);
					currentGameBoard.resetPlayLayerImageAndStopTimer();
				}

				guiGamePlayPanel.updatePlayerButtonText(); // update the text for player buttons
				guiGamePlayPanel.getMeepleButtons().get(guiGamePlayPanel.getDominosPlayedThisRound() - 1)
						.setMeepleImage(null); // set the meeple image next to the button to the player's meeple to null
				guiGamePlayPanel.getDominoButtons().get(guiGamePlayPanel.getDominosPlayedThisRound() - 1)
						.setDominoImage(null); // also set the domino image to null
				guiGamePlayPanel.getNextPlayer();
			
			// otherwise generate an error describing why the domino can't be played at the location
			} else {
				if (guiGamePlayPanel.verifyMatchingSide(currentGameBoard.getGameBoard().getGameBoardSpaces(), sax, say,
						sbx, sby, currentDomino)) {
					new GUIErrorPopupPanel(errorTypes[2], errorMsg[7],
							guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
				} else {
					new GUIErrorPopupPanel(errorTypes[2], errorMsg[8],
							guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
				}
			}
			// discard selected domino and pass turn option
		} else if (e.getSource() == guiGamePlayPanel.getActionButtons().get(7)) {
			guiGamePlayPanel.getActionButtons().get(7).setMousedOver(false);

			currentGameBoard.resetBoardImagesUnderlay();
			currentGameBoard.resetPlayLayerImageAndStopTimer();
			currentGameBoard.repaint();

			GUISoundController.playPlaceTileSound();

			guiGamePlayPanel.setSelectedDomino(null);

			GUIGameController.setPlacingDomino(false);

			guiGamePlayPanel.getCurrentPlayer().setCurrentDomino(null);

			if (guiGamePlayPanel.getFinalRoundStarted()) {
				guiGamePlayPanel.setDominosPlayedThisRound(guiGamePlayPanel.getDominosPlayedThisRound() + 1);
				currentGameBoard.resetPlayLayerImageAndStopTimer();
			}

			guiGamePlayPanel.getMeepleButtons().get(guiGamePlayPanel.getDominosPlayedThisRound() - 1)
					.setMeepleImage(null);
			guiGamePlayPanel.getDominoButtons().get(guiGamePlayPanel.getDominosPlayedThisRound() - 1)
					.setDominoImage(null);

			guiGamePlayPanel.getNextPlayer();
		}
	}

	/**
	 * This method highlights the domino in play if it's not in a playable area
	 * @param currentGameBoard - game board of current player 
	 * @param sax - Side A x location of domino
	 * @param say - Side A y location of domino
	 * @param sbx - Side B x location of domino
	 * @param sby - Side B y location of domino
	 */
	public void highlightDominoIfPlayable(GUIGameBoard currentGameBoard, int sax, int say, int sbx, int sby) {
		// get the current domino in play
		Domino currentDomino = guiGamePlayPanel.getCurrentPlayer().getCurrentDomino();

		// get the x and y locations of the current spaces in play
		GameBoard.Space sideA = currentGameBoard.getGameBoard().getGameBoardSpace(sax, say);
		GameBoard.Space sideB = currentGameBoard.getGameBoard().getGameBoardSpace(sbx, sby);

		// if the space is empty and also a valid playable space
		if (sideA.getSType() == LandType.EMPTY && sideB.getSType() == LandType.EMPTY
				&& guiGamePlayPanel.verifyMatchingSide(currentGameBoard.getGameBoard().getGameBoardSpaces(), sax, say,
						sbx, sby, currentDomino)) {

			// show the domino on the game board
			currentGameBoard.savePlayLayerImage(currentDomino, null, 0);

		// otherwise
		} else { // make the domino pulsate with a red color
			currentGameBoard.savePlayLayerImage(currentDomino, new Color(255, 0, 0), 10);
		}
	}

	public void mouseEntered(MouseEvent e) {
		for (int i = 0; i < guiGamePlayPanel.getActionButtons().size(); i++) {
			if (e.getSource() == guiGamePlayPanel.getActionButtons().get(i) && GUIGameController.getPlacingDomino()) {
				// check for the selected button
				guiGamePlayPanel.getActionButtons().get(i).setMousedOver(true); // toggle mouse over
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (int i = 0; i < guiGamePlayPanel.getActionButtons().size(); i++) {
			// check for the selected button
			if (e.getSource() == guiGamePlayPanel.getActionButtons().get(i) && GUIGameController.getPlacingDomino()) {
				guiGamePlayPanel.getActionButtons().get(i).setMousedOver(false); // toggle mouse over
			}
		}
	}
}
