/**
 * @Class: CMSC495
 * @Date: 20 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIDominoButtonMouseListener.java
 * @Description: Mouse listener used by GUIDominoButton
 */

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIDominoButtonMouseListener extends MouseAdapter {
	// attributes
	GUIGamePlayPanel guiGamePlayPanel; // target game play panel

	// constructor
	public GUIDominoButtonMouseListener(GUIGamePlayPanel guiGamePlayPanel) {
		this.guiGamePlayPanel = guiGamePlayPanel;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// generate an error if the button is disabled
		if (!((GUIDominoButton) e.getSource()).isEnabled()) {
			new GUIErrorPopupPanel("INVALID ACTION: ", "This button is currently disabeled.",
					guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			return;
		}
		// generate an error if the button can't be used currently
		if (GUIGameController.getPlacingDomino()) {
			new GUIErrorPopupPanel("INVALID ACTION: ", "Please return to playing your domino on your game board"
					+ " using the action buttons below.",
					guiGamePlayPanel.getActionButtons().get(2).getLocationOnScreen(), 1400, 500);
			return;
		}
		
		GUISoundController.playSelectSound(); // play a sound
		
		int selection = 0; // capture the menu selection
		for (int i = GUIGameController.getPlayerList().size(); i < guiGamePlayPanel.getDominoButtons().size(); i++) {
			if (e.getSource() == guiGamePlayPanel.getDominoButtons().get(i)) { // check for the selected button
				if (!guiGamePlayPanel.getDominoButtons().get(i).isEnabled()) {
					return;
				}
				selection = i;
				int selectionOffset = selection-GUIGameController.getPlayerList().size();
				// set the selected domino
				guiGamePlayPanel.setSelectedDomino(guiGamePlayPanel.getCurrentDominoSelection().get(selectionOffset));
				// get the domino list # from a list of dominos for this round
				guiGamePlayPanel.setSelectedDominoNumber(selectionOffset);
				// update domino label to show meeple
				guiGamePlayPanel.getMeepleButtons().get(i)
					.setMeepleImage(GUIImageController.getPlayerMeepleImage(
					guiGamePlayPanel.getCurrentPlayer().getPlayerNumber()-1));
				guiGamePlayPanel.getCurrentPlayer().setNextDomino(guiGamePlayPanel.getSelectedDomino());
			}
		}
		
		// set other domino buttons to not being selected
		for (int k = GUIGameController.getPlayerList().size(); k < guiGamePlayPanel.getDominoButtons().size(); k++) {
			if (guiGamePlayPanel.getDominoButtons().get(k).isEnabled() && k != selection) {
				guiGamePlayPanel.getMeepleButtons().get(k)
				.setMeepleImage(null);
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		for (int i = GUIGameController.getPlayerList().size(); i < guiGamePlayPanel
				.getDominoButtons().size(); i++) {
			if (e.getSource() == guiGamePlayPanel.getDominoButtons().get(i) &&
					guiGamePlayPanel.getDominoButtons().get(i).isEnabled()) { // check for the selected button
				guiGamePlayPanel.getDominoButtons().get(i).setMousedOver(true); // toggle mousedover to true
			}
		}
	}	
	
	@Override
	public void mouseExited(MouseEvent e) {
		for (int i = GUIGameController.getPlayerList().size(); i < guiGamePlayPanel
				.getDominoButtons().size(); i++) {
			if (e.getSource() == guiGamePlayPanel.getDominoButtons().get(i) &&
					guiGamePlayPanel.getDominoButtons().get(i).isEnabled()) { // check for the selected button
				guiGamePlayPanel.getDominoButtons().get(i).setMousedOver(false); // toggle mousedover to false
			}
		}
	}
}
