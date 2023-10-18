/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner Team
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIActionButtonKeyListener.java
 * @Description: This is the keyboard listener for the GUI action buttons.
 */

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUIActionButtonKeyListener implements KeyListener {
	// atrributes
	private final GUIGamePlayPanel guiGamePlayPanel; // target game play panel
	private final String moveErrorPre = "Moving the domino"; // beginning part of movement-related action button error
	private final String moveErrorPost = "will place it outside of the play area."; // end part of movement action error
	private final String[] errorMsg = new String[] { // string array of error messages
			"Please select a domino above before selecting an action.", moveErrorPre + " left " + moveErrorPost,
			moveErrorPre + " right " + moveErrorPost, moveErrorPre + " up " + moveErrorPost,
			moveErrorPre + " down " + moveErrorPost, "Rotating the domino clockwise " + moveErrorPost,
			"Rotating the domino counter clockwise " + moveErrorPost,
			"Location must be empty in order to place a domino.",
			"Domino placed must touch the Castle space or have at least one side match a space next to it." };
	private final String[] errorTypes = new String[] { // string array of error message types
			"Invalid Movement:\n\n", "Invalid Action:\n\n", "Invalid Placement:\n\n" };

	// constructor
	public GUIActionButtonKeyListener(GUIGamePlayPanel guiGamePlayPanel) {
		this.guiGamePlayPanel = guiGamePlayPanel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		Domino currentDomino = guiGamePlayPanel.getCurrentPlayer().getCurrentDomino();

		if (currentDomino == null) {
			new GUIErrorPopupPanel(errorTypes[1], errorMsg[0],
					guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			return;
		}

		if (!GUIGameController.getPlacingDomino()) {
			return;
		}

		int sax = currentDomino.getSideA().getXLoc(); // side A x location
		int say = currentDomino.getSideA().getYLoc(); // side A y location
		int sbx = currentDomino.getSideB().getXLoc(); // side B x location
		int sby = currentDomino.getSideB().getYLoc(); // side B y location

		int[] boardArea = { 0, 0, 0, 0 }; // initialize playable bounds on player board
		int currentPlayerCounted = -1;
		for (int k = 0; k < GUIGameController.getPlayerList().size(); k++) {
			currentPlayerCounted++;
			if (GUIGameController.getPlayerList().get(k) == guiGamePlayPanel.getCurrentPlayer()) {
				boardArea = GUIGameController.getPlayerList().get(k).getGUIGameBoard().getGameBoard().getPlayArea();
				break;
			}
		}

		GUIGameBoard currentGameBoard = GUIGameController.getPlayerList().get(currentPlayerCounted).getGUIGameBoard();
		boolean movementOption = false;
		int tempsbx;
		int tempsby;
		switch (keyCode) {
		case KeyEvent.VK_W:
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
				// currentGameBoard.savePlayLayerImage(currentDomino); // display this image on
				// player board

			}
			break;
		case KeyEvent.VK_S:
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
				// currentGameBoard.savePlayLayerImage(currentDomino, null);
			}
			break;
		case KeyEvent.VK_A:
			movementOption = true;
			if (currentDomino.getSideA().getXLoc() - 1 < boardArea[0]
					|| currentDomino.getSideB().getXLoc() - 1 < boardArea[0]) {
				new GUIErrorPopupPanel(errorTypes[0], errorMsg[3],
						guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);

			} else {
				GUISoundController.playMoveTileSound();
				currentDomino.getSideA().setXLoc(currentDomino.getSideA().getXLoc() - 1);
				currentDomino.getSideB().setXLoc(currentDomino.getSideB().getXLoc() - 1);
				// currentGameBoard.savePlayLayerImage(currentDomino, null);
			}
			break;
		case KeyEvent.VK_D:
			movementOption = true;
			if (currentDomino.getSideA().getXLoc() + 1 > boardArea[1]
					|| currentDomino.getSideB().getXLoc() + 1 > boardArea[1]) {
				new GUIErrorPopupPanel(errorTypes[0], errorMsg[4],
						guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			} else {
				GUISoundController.playMoveTileSound();
				currentDomino.getSideA().setXLoc(currentDomino.getSideA().getXLoc() + 1);
				currentDomino.getSideB().setXLoc(currentDomino.getSideB().getXLoc() + 1);
				// currentGameBoard.savePlayLayerImage(currentDomino, null);
			}
			break;
		case KeyEvent.VK_E:
			movementOption = true;

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
			break;
		case KeyEvent.VK_Q:
			movementOption = true;

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
				// currentGameBoard.savePlayLayerImage(currentDomino, null);
			}
			break;
		case KeyEvent.VK_SPACE:
			guiGamePlayPanel.getActionButtons().get(6).setMousedOver(false);

			GameBoard.Space SideA = currentGameBoard.getGameBoard().getGameBoardSpace(sax, say);
			GameBoard.Space SideB = currentGameBoard.getGameBoard().getGameBoardSpace(sbx, sby);
			if (SideA.getSType() == LandType.EMPTY && SideB.getSType() == LandType.EMPTY
					&& guiGamePlayPanel.verifyMatchingSide(currentGameBoard.getGameBoard().getGameBoardSpaces(), sax,
							say, sbx, sby, currentDomino)) {

				// place domino on the game board
				currentGameBoard.saveOverlayImage(currentDomino);
				currentGameBoard.resetBoardImagesUnderlay();
				currentGameBoard.repaint();
				// currentDominoSelection.remove(currentDomino);

				GUISoundController.playPlaceTileSound();

				guiGamePlayPanel.setSelectedDomino(null);

				GUIGameController.setPlacingDomino(false);

				guiGamePlayPanel.getCurrentPlayer().setCurrentDomino(null);

				if (guiGamePlayPanel.getFinalRoundStarted()) {
					guiGamePlayPanel.setDominosPlayedThisRound(guiGamePlayPanel.getDominosPlayedThisRound() + 1);
					currentGameBoard.resetPlayLayerImageAndStopTimer();
				}

				guiGamePlayPanel.updatePlayerButtonText();
				guiGamePlayPanel.getMeepleButtons().get(guiGamePlayPanel.getDominosPlayedThisRound() - 1)
						.setMeepleImage(null);
				guiGamePlayPanel.getDominoButtons().get(guiGamePlayPanel.getDominosPlayedThisRound() - 1)
						.setDominoImage(null);
				guiGamePlayPanel.getNextPlayer();

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
			break;
		case KeyEvent.VK_X:
			guiGamePlayPanel.getActionButtons().get(7).setMousedOver(false);

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
			break;
		default:
			// Handle other keys if needed
			break;
		}
		if (movementOption) {
			highlightDominoIfPlayable(currentGameBoard, currentDomino.getSideA().getXLoc(),
					currentDomino.getSideA().getYLoc(), currentDomino.getSideB().getXLoc(),
					currentDomino.getSideB().getYLoc());
			return;
		}
	}

	public void highlightDominoIfPlayable(GUIGameBoard currentGameBoard, int sax, int say, int sbx, int sby) {
		Domino currentDomino = guiGamePlayPanel.getCurrentPlayer().getCurrentDomino();

		GameBoard.Space sideA = currentGameBoard.getGameBoard().getGameBoardSpace(sax, say);
		GameBoard.Space sideB = currentGameBoard.getGameBoard().getGameBoardSpace(sbx, sby);

		if (sideA.getSType() == LandType.EMPTY && sideB.getSType() == LandType.EMPTY
				&& guiGamePlayPanel.verifyMatchingSide(currentGameBoard.getGameBoard().getGameBoardSpaces(), sax, say,
						sbx, sby, currentDomino)) {

			// place domino on the game board
			currentGameBoard.savePlayLayerImage(currentDomino, null, 0);

			// currentDominoSelection.remove(currentDomino);
		} else {
			currentGameBoard.savePlayLayerImage(currentDomino, new Color(255, 0, 0), 10);

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Optionally handle key release events, if needed
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Optionally handle key typed events, if needed
	}
}
