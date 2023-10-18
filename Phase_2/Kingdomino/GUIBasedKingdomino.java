/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIBasedKingdomino
 * Description: This class maintains the overall logic that will run the Kingdomino game
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class GUIBasedKingdomino {
public static double screenWidth; // width of screen
private static double screenHeight; // height of screen
public static JFrame mainFrame; // main content frame
public static JLayeredPane layeredPane; // layered pane to overlay option buttons

	public static void intialize() {
		// set the game screen size to 90% of user's monitor size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.width*0.9;
		screenHeight = screenSize.height*0.9;
		
		// set up main frame to hold the display panel
		// mainFrame (BorderLayout) <- layeredPane (LayeredPane) <- displayPanel (CardLayout)
		mainFrame = new JFrame();
		mainFrame.setLayout(new BorderLayout());
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, (int)screenWidth, (int)screenHeight);
		layeredPane.setBackground(Color.BLACK);
		
		GUIDisplayPanel displayPanel = new GUIDisplayPanel();
		// BUG: for some reason, panel doesn't display correctly unless 0.99 and 0.95 coefficients are added to size
		displayPanel.setBounds(0, 0, (int)(screenWidth*0.99), (int)(screenHeight*0.95));
		layeredPane.add(displayPanel);
		layeredPane.setLayer(displayPanel, 0);

		// set up the back button that displays at the bottom right corner of the menu pages
		GUIComponentController.getBackButton().setBounds(
				(int)(screenWidth*0.99)-GUIResolutionController.convertScaleWidth(550), 
				(int)(screenHeight*0.95)-GUIResolutionController.convertScaleHeight(550), 
				GUIResolutionController.convertScaleWidth(450), 
				GUIResolutionController.convertScaleHeight(450));
		GUIComponentController.getBackButton().addMouseListener(new BackButtonClicked());
		GUIComponentController.getBackButton().setVisible(false);
		//GUIGameController.getBackButton().setOpaque(false);
		GUIComponentController.getBackButton().setContentAreaFilled(false);
		//GUIGameController.getBackButton().setBackground(new Color(0,0,0,255));
		GUIComponentController.getBackButton().setBorderPainted(false);
		GUIComponentController.getBackButton().setFocusPainted(false);
		GUIComponentController.getBackButton().setForeground(Color.BLACK);
		GUIComponentController.getBackButton().setFont(new Font("Augusta", Font.PLAIN, 
				GUIResolutionController.convertScaleWidth(75)));
		GUIComponentController.getBackButton().setHorizontalTextPosition(SwingConstants.CENTER);
		GUIComponentController.getBackButton().setVerticalTextPosition(SwingConstants.TOP);
		Image newButtonImage = ((ImageIcon) GUIComponentController.getBackButton().getIcon()).getImage().getScaledInstance(
				GUIResolutionController.convertScaleWidth(250), 
    			GUIResolutionController.convertScaleHeight(250), Image.SCALE_SMOOTH);
		GUIComponentController.getBackButton().setIcon(new ImageIcon(newButtonImage));
		layeredPane.add(GUIComponentController.getBackButton());
		layeredPane.setLayer(GUIComponentController.getBackButton(), 2);
		
		mainFrame.add(layeredPane, BorderLayout.CENTER);
		
		// continue setting up main frame
		mainFrame.setTitle("Kingdomino by The Domino Dynasty");
		mainFrame.setSize((int)screenWidth, (int)screenHeight);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(true);
		mainFrame.setVisible(true);
		
		mainFrame.addComponentListener(new ComponentAdapter() {
		    @Override public void componentResized(ComponentEvent e) {
		        Dimension size = mainFrame.getSize(); // get size
		        displayPanel.setSize(size); // push size through
		        mainFrame.revalidate(); // revalidate to see updates
		        mainFrame.repaint(); // "Always invoke repaint after revalidate"
		    }
		});
		GUISoundController.playTitleMusic();
	}

	private static class BackButtonClicked extends MouseAdapter {
    	@Override
        public void mousePressed(MouseEvent e) {
    		GUISoundController.playBackButtonSound();
    		if (GUIComponentController.getCurrentCardNumber() <= 0) {
    			return;
    		} else if (GUIComponentController.getCurrentCardNumber() == 1) {	
    			GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "titleCard");
    			GUIComponentController.setCurrentCardNumber(0);
    		}else if (GUIComponentController.getCurrentCardNumber() == 2 ||
    				GUIComponentController.getCurrentCardNumber() == 3 ) {
    			GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "mainMenuCard");
    			GUIComponentController.setCurrentCardNumber(1);
    		} else if (GUIComponentController.getCurrentCardNumber() == 4) {
    			GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "playerCard");
    			GUIComponentController.setCurrentCardNumber(3);
    		}

    		if (GUIComponentController.getCurrentCardNumber() == 0) {
    			for (JComponent c : GUIComponentController.getComponentList()) {
    				if (c instanceof GUITitlePanel) {
    					//((GUITitlePanel) c).getStartButton().resizeButtonImage();
    				}
    			}
    			GUIComponentController.getLogoPanel().setVisible(false);
    			GUIComponentController.getBackButton().setVisible(false);
    		}
    	}

    	@Override
		public void mouseEntered(MouseEvent e) {
    		int width = GUIComponentController.getBackButton().getWidth();
    		int height = GUIComponentController.getBackButton().getHeight();
    		if (width <= 0 || height <= 0) { return; }
    		Image newButtonImage = ((ImageIcon) GUIComponentController.getBackButton()
    				.getIcon()).getImage().getScaledInstance(GUIResolutionController.convertScaleWidth(300), 
    	        			GUIResolutionController.convertScaleHeight(300), Image.SCALE_SMOOTH);

    		GUIComponentController.getBackButton().setIcon(new ImageIcon(newButtonImage));
    	}
    	
    	public void mouseExited(MouseEvent e) {
    		int width = GUIComponentController.getBackButton().getWidth();
    		int height = GUIComponentController.getBackButton().getHeight();
    		if (width <= 0 || height <= 0) { return; }
    		Image newButtonImage = ((ImageIcon) GUIComponentController.getBackButton()
    				.getIcon()).getImage().getScaledInstance(GUIResolutionController.convertScaleWidth(250), 
    	        			GUIResolutionController.convertScaleHeight(250), Image.SCALE_SMOOTH);

    		GUIComponentController.getBackButton().setIcon(new ImageIcon(newButtonImage));
    	}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	GUIBasedKingdomino.intialize();
	        }
	    });
	}
}
