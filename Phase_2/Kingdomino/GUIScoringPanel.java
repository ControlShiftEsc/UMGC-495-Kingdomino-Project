/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Michael Wood & Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIPlayerNumberPanel.java
 * Description: Displays the score panel
 */

import java.awt.*;
import java.util.Comparator;

import javax.swing.*;

public class GUIScoringPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public void initialize() {
    	
    	this.removeAll();
    	
    	// remove the two copy players if the game only has 2 players
    	if (GUIGameController.getPlayerCount() == 2) {
    		GUIGameController.getPlayerList().remove(3);
    		GUIGameController.getPlayerList().remove(2);
    	}
    	
    	// sort the panels in order by player with most points
    	GUIGameController.getPlayerList().sort(
    			Comparator.comparing(x -> ((GUIPlayer) x).getGUIGameBoard()
    			.getGameBoard().getCurrentScore()).reversed());

    	// set up grid bag layout
    	this.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	
    	c.gridx = 0;
    	c.gridy = 0;
    	c.fill = GridBagConstraints.BOTH;
    	c.weightx = 1;
    	c.weighty = 1;
    	
    	for (int i = 0; i < GUIGameController.getPlayerCount(); i++) {
    		System.out.println(GUIGameController.getPlayerList().get(i).getPlayerName());
    		if (c.gridx > 1) {
    			c.gridx = 0;
    			c.gridy+=2;
    		}
    		
    		c.weighty = 0;
    		
        	JLabel label = new JLabel(GUIGameController.getPlayerList().get(i).getPlayerName() +
        			" (" + GUIGameController.getPlayerList().get(i).getGUIGameBoard().
        			getGameBoard().getCurrentScore() + " points)");
        	label.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
        	label.setForeground(Color.BLACK);
        	this.add(label, c);
        	
        	c.gridy += 1;
        	c.weighty =1;
        	
    		GUIGameBoard gameboard = GUIGameController.getPlayerList().get(i).getGUIGameBoard();
    		gameboard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        	
        	this.add(gameboard, c);
        	gameboard.repaint();
        	gameboard.setVisible(true);
        	gameboard.resetPlayLayerImage();
        	
        	c.gridy -= 1;
        	c.gridx++;
    	}

    	/*
    	JLabel label = new JLabel("");
    	label.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
    	label.setForeground(Color.BLACK);	
      	this.setBackground(Color.RED);
      	*/
    }
}
