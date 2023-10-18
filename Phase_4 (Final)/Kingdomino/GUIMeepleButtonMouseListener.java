/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIMeepleButtonMouseListener.java
 * @Description: This class contains the mouse listener for the GUIMeepleButton class
 */

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIMeepleButtonMouseListener extends MouseAdapter {
	GUIGamePlayPanel guiGamePlayPanel; // target game play panel
	
	public GUIMeepleButtonMouseListener(GUIGamePlayPanel guiGamePlayPanel) {
		this.guiGamePlayPanel = guiGamePlayPanel;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// show this error if the meeple button is disabled, then do nothing
		if (!((GUIMeepleButton) e.getSource()).isEnabled()) {
			new GUIErrorPopupPanel("INVALID ACTION: ", "This button is currently disabeled.",
					guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			return;
		}
		
		// show this error if no domino has been selected, then do nothing
		if (guiGamePlayPanel.getSelectedDomino() == null) {
			new GUIErrorPopupPanel("INVALID ACTION: ", "Please select a domino on the right first.",
					guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			return;
		}
		
		// show this error if it's not time yet to select a meeple button, then do nothing
		if (GUIGameController.getPlacingDomino()) {
			new GUIErrorPopupPanel("INVALID ACTION: ", "Please return to playing your domino on your game board"
					+ " using the action buttons below.",
					guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			return;
		}

		for (int i = GUIGameController.getPlayerList().size(); i < guiGamePlayPanel
				.getMeepleButtons().size(); i++) {
			if (e.getSource() == guiGamePlayPanel.getMeepleButtons().get(i)) { // check for the selected button
				if (!guiGamePlayPanel.getMeepleButtons().get(i).isEnabled()) { // error if the button is disabled
					new GUIErrorPopupPanel("INVALID ACTION: ", "This button is currently disabeled.",
							guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
					return;
				}
				
				// get offset of selection, which is the meeple button on the opposite side
				int selectionOffset = i-GUIGameController.getPlayerList().size();
				
				if (guiGamePlayPanel.getSelectedDominoNumber() == selectionOffset) {
					GUISoundController.playSelectTileSound();
					// flag that a meeple has been selected, then disable the domino and meeple buttons
					guiGamePlayPanel.getMeepleButtons().get(i).selectMeeple(true);
					guiGamePlayPanel.getDominoButtons().get(i).setEnabled(false);
					guiGamePlayPanel.getMeepleButtons().get(i).setEnabled(false);
					guiGamePlayPanel.setDominosPlayedThisRound(guiGamePlayPanel.getDominosPlayedThisRound()+1);
					// get the domino number from the selected domino
					int dominoNumber = guiGamePlayPanel.getSelectedDomino().getDominoNumber();
					guiGamePlayPanel.getCurrentPlayer().setNextOrder(dominoNumber);
					// if the current player has a domino, then enable play of that domino
					if (guiGamePlayPanel.getCurrentPlayer().getCurrentDomino() != null) {
						GUIGameController.setPlacingDomino(true); // enable domino placing mode
						guiGamePlayPanel.getMeepleButtons() // set the selected meeple button to false
								.get(guiGamePlayPanel.getDominosPlayedThisRound()-1).selectMeeple(false);
						guiGamePlayPanel.updateStatusText(2, "PLAY YOUR DOMINO! " // update status text
											+ "Move, rotate, place, discard, or re-choose your domino.");
						for (int k = 0; k < guiGamePlayPanel.getPlayerOrderList().size(); k++) {
							// get the player, then get the player's game board and current domino
							if (GUIGameController.getPlayerList().get(k) == guiGamePlayPanel.getCurrentPlayer()) {
								guiGamePlayPanel.playableBoardSpaceHighlight(
										guiGamePlayPanel.getCurrentPlayer().getGUIGameBoard(), 
										guiGamePlayPanel.getCurrentPlayer().getCurrentDomino());
								guiGamePlayPanel.setCurrentPlayerNumber(k); // find the current player and get order number
																			// in list
								int[] boardArea = GUIGameController.getPlayerList()
										.get(k).getGUIGameBoard().getGameBoard()
										.getPlayArea(); // get playable board area
								// then get virtual location of selected domino on player board

								Domino currentDomino = guiGamePlayPanel.getCurrentPlayer().getCurrentDomino();
								currentDomino.getSideA().setXLoc(boardArea[0]);
								currentDomino.getSideA().setYLoc(boardArea[2]);
								currentDomino.getSideB().setXLoc(boardArea[0] + 1);
								currentDomino.getSideB().setYLoc(boardArea[2]);
								
								
								int sax = currentDomino.getSideA().getXLoc(); // side A x location
								int say = currentDomino.getSideA().getYLoc(); // side A y location
								int sbx = currentDomino.getSideB().getXLoc(); // side B x location
								int sby = currentDomino.getSideB().getYLoc(); // side B y location
								
								GameBoard.Space sideA = guiGamePlayPanel.getCurrentPlayer().getGUIGameBoard()
										.getGameBoard().getGameBoardSpace(sax, say);
								GameBoard.Space sideB = guiGamePlayPanel.getCurrentPlayer().getGUIGameBoard()
										.getGameBoard().getGameBoardSpace(sbx, sby);
												
								// show the image of the domino over the player's board
								if (sideA.getSType() == LandType.EMPTY && sideB.getSType() == LandType.EMPTY
										&& guiGamePlayPanel.verifyMatchingSide(guiGamePlayPanel.getCurrentPlayer()
												.getGUIGameBoard().getGameBoard()
												.getGameBoardSpaces(), sax, say,
												sbx, sby, guiGamePlayPanel.getSelectedDomino())) {

									// place domino on the game board
								
									GUIGameController.getPlayerList().get(k).getGUIGameBoard()
									.savePlayLayerImage(currentDomino, null, 0);
									return;
									// currentDominoSelection.remove(guiGamePlayPanel.getSelectedDomino());
								} else {
									GUIGameController.getPlayerList().get(k).getGUIGameBoard()
									.savePlayLayerImage(currentDomino, new Color(255, 0, 0), 0);
									return;
								}
							
							} // skip player in list if not the current player

						}
						//GUISoundController.playSelectTileSound(); // play selection sound
					} else {
						guiGamePlayPanel.getNextPlayer();
					}
				} else {
					new GUIErrorPopupPanel("INVALID ACTION: ", "Please select your meeple to the right of your selected "
							+ "domino.",
							guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
				}
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		for (int i = GUIGameController.getPlayerList().size(); i < guiGamePlayPanel
				.getMeepleButtons().size(); i++) {
			if (e.getSource() == guiGamePlayPanel.getMeepleButtons().get(i) &&
					guiGamePlayPanel.getMeepleButtons().get(i).isEnabled()) { // check for the selected button
				guiGamePlayPanel.getMeepleButtons().get(i).setMousedOver(true);
			}
		}
	}	
	
	@Override
	public void mouseExited(MouseEvent e) {
		for (int i = GUIGameController.getPlayerList().size(); i < guiGamePlayPanel
				.getMeepleButtons().size(); i++) {
			if (e.getSource() == guiGamePlayPanel.getMeepleButtons().get(i) &&
					guiGamePlayPanel.getMeepleButtons().get(i).isEnabled()) { // check for the selected button
				guiGamePlayPanel.getMeepleButtons().get(i).setMousedOver(false);
			}
		}
	}
}
