/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Michael Wood Jr.
 * Editor/Revisor: Alan Anderson & William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIDisplayPanel.java
 * Description: This class holds an animated image that can be drawn by a JPanel.
 */

import java.awt.*;
import javax.swing.*;

public class GUIDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GUITitlePanel titlePanel; // panel that holds the title screen
	private GUIMainMenuPanel mainMenuPanel; // panel that holds the main game menu
    private GUIPlayerNumberPanel playerPanel; // panel that holds the player selection menu
    private GUIPlayerNamePanel playerNamePanel; // player name entry
    private GUIGamePlayPanel gameplayPanel; // the meat and potatoes; holds the game gui and logic
    private GUIScoringPanel scoringPanel; // displays final score
    //private GUILogoPanel logoPanel; // displays the logo overlay at the top of screen
    
    public GUIDisplayPanel() {
    	// set the layout for the game controller; this will be used by the other panels
    	GUIComponentController.getParentPanel().setLayout(GUIComponentController.getCardLayout());
    	
    	// add title panel
		titlePanel = new GUITitlePanel();
		titlePanel.setBackground(new Color(0,0,0));
		GUIComponentController.getComponentList().add(titlePanel);
		GUIComponentController.getParentPanel().add(titlePanel, "titleCard");
		
		// add menu panel
    	mainMenuPanel = new GUIMainMenuPanel();
    	GUIComponentController.getComponentList().add(mainMenuPanel);
    	GUIComponentController.getParentPanel().add(mainMenuPanel, "mainMenuCard");
    	
    	// add player name panel
    	playerNamePanel = new GUIPlayerNamePanel();
    	GUIComponentController.getComponentList().add(playerNamePanel);
    	GUIComponentController.getParentPanel().add(playerNamePanel, "playerNameCard");
    	
    	// add player number panel
    	playerPanel = new GUIPlayerNumberPanel();
    	GUIComponentController.getComponentList().add(playerPanel);
    	GUIComponentController.getParentPanel().add(playerPanel, "playerCard");
    	
    	// add game play panel
    	gameplayPanel = new GUIGamePlayPanel();
    	GUIComponentController.getComponentList().add(gameplayPanel);
    	GUIComponentController.getParentPanel().add(gameplayPanel, "gameplayCard");
    	
    	// add scoring panel
    	scoringPanel = new GUIScoringPanel();
    	GUIComponentController.getComponentList().add(scoringPanel);
    	GUIComponentController.getParentPanel().add(scoringPanel, "scoringPanel");
    	
    	// now show the title screen
    	GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "titleCard");
    	
		// add the logo panel and display to the main screen
    	this.setLayout(new BorderLayout());
    	GUIComponentController.setLogoPanel(new GUILogoPanel());
    	GUIComponentController.getLogoPanel().setVisible(false);
    	this.add(GUIComponentController.getLogoPanel(), BorderLayout.PAGE_START);
    	this.add(GUIComponentController.getParentPanel(), BorderLayout.CENTER);
    }
}
