/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Michael Wood Jr.
 * @Editor: Alan Anderson & William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIDisplayPanel.java
 * @Description: This class holds an animated image that can be drawn by a JPanel.
 */

import java.awt.*;
import javax.swing.*;

public class GUIDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GUITitlePanel titlePanel; // panel that holds the title screen
	private GUIMainMenuPanel mainMenuPanel; // panel that holds the main game menu
	private GUIRulesPanel rulesPanel; // panel that holds the rules
	private GUICreditsPanel creditsPanel; // panel that holds the main game menu
    private GUIPlayerNumberPanel playerPanel; // panel that holds the player selection menu
    private GUIPlayerNamePanel playerNamePanel; // player name entry
    private GUIGamePlayPanel gameplayPanel; // the meat and potatoes; holds the game gui and logic
    private GUIScoringPanel scoringPanel; // displays final score
    //private GUILogoPanel logoPanel; // displays the logo overlay at the top of screen
    
    public GUIDisplayPanel() {	
		this.setOpaque(false); // background of parent will be painted first
		this.setBackground( new Color(0, 0, 0, 0) );
		
    	// set the layout for the game controller; this will be used by the other panels
    	GUIComponentController.getParentPanel().setLayout(GUIComponentController.getCardLayout());
    	
    	// add title panel
		titlePanel = new GUITitlePanel() {
			private static final long serialVersionUID = -2799035584882105027L;

			@Override
			public void paintComponent(Graphics g) {
				// this ensures background is transparent so that animated dominos can be seen
				super.paintComponent(g);
				g.setColor( this.getBackground() );
				g.fillRect(0, 0, getWidth(), getHeight());
				this.getRootPane().repaint();
			}
		};
		
		this.setOpaque(false);
		titlePanel.setBackground(new Color(0,0,0,0));
		GUIComponentController.getComponentList().add(titlePanel);
		GUIComponentController.getParentPanel().add(titlePanel, "titleCard");
		
		
		// add menu panel
    	mainMenuPanel = new GUIMainMenuPanel();
    	mainMenuPanel.setBackground(new Color(0,0,0,0));
    	GUIComponentController.getComponentList().add(mainMenuPanel);
    	GUIComponentController.getParentPanel().add(mainMenuPanel, "mainMenuCard");

	  	// add rules panel
    	rulesPanel = new GUIRulesPanel();
    	rulesPanel.setBackground(new Color(0,0,0,0));
    	GUIComponentController.getComponentList().add(rulesPanel);
    	GUIComponentController.getParentPanel().add(rulesPanel, "rulesCard");
    	
    	// add credits panel
    	creditsPanel = new GUICreditsPanel();
    	creditsPanel.setBackground(new Color(0,0,0,0));
    	GUIComponentController.getComponentList().add(creditsPanel);
    	GUIComponentController.getParentPanel().add(creditsPanel, "creditsCard");
    	
    	// add player name panel
    	playerNamePanel = new GUIPlayerNamePanel();
    	playerNamePanel.setBackground(new Color(0,0,0,0));
    	GUIComponentController.getComponentList().add(playerNamePanel);
    	GUIComponentController.getParentPanel().add(playerNamePanel, "playerNameCard");
    	
    	// add player number panel
    	playerPanel = new GUIPlayerNumberPanel();
    	playerPanel.setBackground(new Color(0,0,0,0));
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
    
    @Override
	public void paintComponent(Graphics g) {
    	super.paintComponent(g);
		g.setColor( this.getBackground() );
		g.fillRect(0, 0, getWidth(), getHeight());
		repaint();
	}
}
