/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIViewBoardMouseListener.java
 * @Description: This is the mouse listener for the GUI board view buttons.
 */

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIViewBoardMouseListener extends MouseAdapter {
	private GUIGamePlayPanel guiGamePlayPanel;
	private int selectedNum = 0;
	
	public GUIViewBoardMouseListener(GUIGamePlayPanel guiGamePlayPanel) {
		this.guiGamePlayPanel = guiGamePlayPanel;
	}
		
	public void setSelectedNum(int selectedNum) {
		this.selectedNum = selectedNum;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		selectedNum = 0; // reset the selected mouse button umber of selected button in array;
		// switch around to different player boards
		if (e.getSource() == guiGamePlayPanel.getViewBoardButtons().get(0)) {
			guiGamePlayPanel.getViewBoardPanelDisplayCard().show(guiGamePlayPanel.getViewBoardPanl(), "0");
		} else if (e.getSource() == guiGamePlayPanel.getViewBoardButtons().get(1)) {
			guiGamePlayPanel.getViewBoardPanelDisplayCard().show(guiGamePlayPanel.getViewBoardPanl(), "1");
			selectedNum = 1;
		} else if (e.getSource() == guiGamePlayPanel.getViewBoardButtons().get(2)) {
			guiGamePlayPanel.getViewBoardPanelDisplayCard().show(guiGamePlayPanel.getViewBoardPanl(), "2");
			selectedNum = 2;
		} else if (e.getSource() == guiGamePlayPanel.getViewBoardButtons().get(3)) {
			guiGamePlayPanel.getViewBoardPanelDisplayCard().show(guiGamePlayPanel.getViewBoardPanl(), "3");
			selectedNum = 3;
		}
		// darken the tabs of the other players not being viewed
		guiGamePlayPanel.switchViewBoardButtonColorsOnSelect(selectedNum);
	}
	
	@Override
	// when mouse enters, turn button player color unless it's the currently selected button
	public void mouseEntered(MouseEvent e) {
		for (int i = 0; i < guiGamePlayPanel.getViewBoardButtons().size(); i++) {
			if (e.getSource() == guiGamePlayPanel.getViewBoardButtons().get(i)) {
				if (i != selectedNum) {
					guiGamePlayPanel.getViewBoardButtons().get(i)
						.setBackground(GUIGameController.getPlayerColors(i));
				}
			}
		}
	}
	
	@Override
	// when mouse exits, turn button grey unless it's the currently selected button
	public void mouseExited(MouseEvent e) {
		for (int i = 0; i < guiGamePlayPanel.getViewBoardButtons().size(); i++) {
			if (e.getSource() == guiGamePlayPanel.getViewBoardButtons().get(i)) {
				if (i != selectedNum) {
					guiGamePlayPanel.getViewBoardButtons().get(i).setBackground(Color.GRAY);
				}
			}
		}
	}
}