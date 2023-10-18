/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIComponentController.java
 * @Description: This class controls the list of components for the game
 */

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
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
	private static JButton optionButton; // button that brings up option menu
	private static JButton exitGameButton; // button that exits the game
	private static Dimension screenSize; // size of screen
	private static JFrame mainFrame; // main content frame
	private static JLayeredPane layeredPane; // layered pane to overlay option buttons
	private static JPanel optionPanel; // in-game option menu panel
	private static GUIMenuButton resetGameButton; // reset button for in-game option menu panel
	private static GUIMenuButton endGameButton; // end game button for in-game option menu panel
	private static GUIMenuButton closeOptionPanelButton; // close panel button for in-game option menu panel
	
	static {
		// create component items ins static
		componentList = new ArrayList<JComponent>();
		cardLayout = new CardLayout();
		parentPanel = new JPanel();
		mainFrame = new JFrame();
		layeredPane = new JLayeredPane();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		backButton = new JButton("");
		backButton.setIcon(new ImageIcon(GUIImageController.getBackButtonImage()));
		optionButton = new JButton();
		optionButton.setIcon(new ImageIcon(GUIImageController.getOptionButtonImage()));
		resetGameButton = new GUIMenuButton("Reset Game");
		exitGameButton = new GUIMenuButton("Exit Game");
		endGameButton = new GUIMenuButton("End Game");
		closeOptionPanelButton = new GUIMenuButton("Close Options");
	}
	
	// setters
	public static void setCloseOptionPanelButton(GUIMenuButton newCloseOptionPanelButton) {
		closeOptionPanelButton = newCloseOptionPanelButton;
	}
	
	public static void setEndGameButton(GUIMenuButton newEndGameButton) {
		endGameButton = newEndGameButton;
	}
	
	public static void setResetGameButton(GUIMenuButton newResetGameButton) {
		resetGameButton = newResetGameButton;
	}
	
	public static void setOptionPanel(JPanel newOptionPanel) {
		optionPanel = newOptionPanel;
	}
	
	public static void setComponentList(ArrayList<JComponent> newComponentList) {
		componentList = newComponentList;
	}
		
	public static void setBackButton(JButton newBackButton) {
		backButton = newBackButton;
	}
	
	public static void setOptionButton(JButton newOptionButton) {
		optionButton = newOptionButton;
	}

	public static void setLogoPanel(GUILogoPanel newLogoPanel) {
		logoPanel = newLogoPanel;
	}
	
	public static void setCurrentCardNumber(int newCurrentCardNumber ) {
		currentCardNumber = newCurrentCardNumber;
	}
	
	public static void setDisplayingPanel(JPanel newDisplayingPanel) {
		displayingPanel = newDisplayingPanel;
	}
	
	public static void setParentPanel(JPanel newParentPanel) {
		parentPanel = newParentPanel;
	}
	
	public static void setCardLayout(CardLayout newCardLayout) {
		cardLayout = newCardLayout;
	}
	
	// getters
	public static GUIMenuButton getCloseOptionPanelButton() {
		return closeOptionPanelButton;
	}
	
	public static GUIMenuButton getEndGameButton() {
		return endGameButton;
	}
	
	public static GUIMenuButton getResetGameutton() {
		return resetGameButton;
	}
	
	public static JPanel getOptionPanel() {
		return optionPanel;
	}
	
	public static JFrame getMainFrame() {
		return mainFrame;
	}
	
	public static JLayeredPane getLayeredPane() {
		return layeredPane;
	}
	
	public static Dimension getScreenSize() {
		return screenSize;
	}
	
	public static ArrayList<JComponent> getComponentList() {
		return componentList;
	}
	
	public static JButton getBackButton() {
		return backButton;
	}
	
	public static JButton getOptionButton() {
		return optionButton;
	}
	
	public static JButton getExitGameButton() {
		return exitGameButton;
	}
	
	public static GUILogoPanel getLogoPanel() {
		return logoPanel;
	}
	
	public static int getCurrentCardNumber() {
		return currentCardNumber;
	}
	
	public static CardLayout getCardLayout() {
		return cardLayout;
	}
	
	public static JPanel getParentPanel() {
		return parentPanel;
	}
	
	public static JPanel getDisplayingPanel() {
		return displayingPanel;
	}
}
