/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIComponenetListController.java
 * Description: This class controls the list of components for the game
 */

import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GUIComponentController {
	/**
	 * componentList contents at index:
	 *  index 0: GUITitlePanel
	 *  index 1: GUIMainMenuPanel
	 *  index 2: GUIPlayerNumberPanel
	 *  index 3: GUIPlayerNamePanel
	 *  index 4: GUIGamePlayPanel
	 *  index 5: GUIScorePanel
	 */
	private static ArrayList<JComponent> componentList; // provides access to components across game
	private static CardLayout cardLayout; // card layout for main display
	private static int currentCardNumber = 0; // current card number; used by back button to locate current panel
	private static JPanel parentPanel; // parent panel controlling card layout
	private static JPanel displayingPanel; // panel to hold all displays
	private static GUILogoPanel logoPanel; // team logo 
	private static JButton backButton; // back button for going back one panel
	
	static {
		componentList = new ArrayList<JComponent>();
		cardLayout = new CardLayout();
		parentPanel = new JPanel();
		backButton = new JButton("");
		backButton.setIcon(new ImageIcon(GUIImageController.getBackButtonImage()));
	}
	
	// setters
	/**
	 * This method sets the component list
	 * @param newComponentList - component list to apply
	 */
	public static void setComponentList(ArrayList<JComponent> newComponentList) {
		componentList = newComponentList;
	}
		
	/**
	 * This method sets the back button for the menu screens
	 * @param newBackButton - JButton to apply
	 */
	public static void setBackButton(JButton newBackButton) {
		backButton = newBackButton;
	}
	
	/**
	 * This method sets the logo panel
	 * @param newLogoPanel - logo panel to apply
	 */
	public static void setLogoPanel(GUILogoPanel newLogoPanel) {
		logoPanel = newLogoPanel;
	}
	
	/**
	 * This method sets the current card number for the main display; back button uses this to identify
	 * which panel is currently displayed
	 * @param newCurrentCardNumber - panel number to set
	 */
	public static void setCurrentCardNumber(int newCurrentCardNumber ) {
		currentCardNumber = newCurrentCardNumber;
	}
	
	/**
	 * This method sets the display panel that contains all other sub display panels
	 * @param newDisplayingPanel - display panel to apply
	 */
	public static void setDisplayingPanel(JPanel newDisplayingPanel) {
		displayingPanel = newDisplayingPanel;
	}
	
	/**
	 * This method sets the parent panel which owns the card layout the other sub panels are displayed in
	 * @param newParentPanel - parent panel to apply
	 */
	public static void setParentPanel(JPanel newParentPanel) {
		parentPanel = newParentPanel;
	}
	
	/**
	 * This method sets the card layout for the parent panel
	 * @param newCardLayout - card layout to apply
	 */
	public static void setCardLayout(CardLayout newCardLayout) {
		cardLayout = newCardLayout;
	}
	
	// getters
	/**
	 * This method gets the component list
	 * @return - returns component list
	 */
	public static ArrayList<JComponent> getComponentList() {
		return componentList;
	}
	
	/**
	 * This method returns the back button
	 * @return - back button
	 */
	public static JButton getBackButton() {
		return backButton;
	}
	
	/**
	 * This method returns the team logo panel
	 * @return - team logo panel
	 */
	public static GUILogoPanel getLogoPanel() {
		return logoPanel;
	}
	
	/**
	 * This method returns the parent display panel's current card number
	 * @return - current card number of parent display
	 */
	public static int getCurrentCardNumber() {
		return currentCardNumber;
	}
	
	/**
	 * This method returns the card layout for the parent panel
	 * @return - parent panel's card layout
	 */
	public static CardLayout getCardLayout() {
		return cardLayout;
	}
	
	/**
	 * This method returns the parent panel
	 * @return - parent panel
	 */
	public static JPanel getParentPanel() {
		return parentPanel;
	}
	
	/**
	 * This method returns the current display panel
	 * @return - display panel
	 */
	public static JPanel getDisplayingPanel() {
		return displayingPanel;
	}
}
