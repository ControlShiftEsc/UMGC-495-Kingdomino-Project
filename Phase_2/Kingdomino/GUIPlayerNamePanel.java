/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIPlayerNamePanel.java
 * Description: The display for the panel to input player names
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class GUIPlayerNamePanel extends JPanel{
	// attributes
	private static final long serialVersionUID = 1L;
	private JTextField[] playerTextField; // text array to hold each player name
	private ArrayList<JLabel> labelList;
	private GUIMenuButton startButton; // start game button
	
	private class MouseListenerClicked extends MouseAdapter {
    	@Override
    	
    	// functionality for each of the buttons
        public void mousePressed(MouseEvent e) {
    		// once the "Continue" button is clicked, play game music
    		//BUGGED: Sound distorted, need to look into it //GUISoundController.playGamePlayMusic();
    		// then make the "Back" button inaccessible
    		GUIComponentController.getBackButton().setVisible(false);
			
    		// then set up the player names; if the player name is blank, set it to "Player #"
			for (int i = 0; i < GUIGameController.getPlayerCount(); i++) {
				if ((playerTextField[i].getText().isEmpty() || playerTextField[i].getText().trim() == ""||
						playerTextField[i].getText() == null)) {
					GUIGameController.getPlayerList().get(i).setPlayerName("Player " + i);
				} else {
					GUIGameController.getPlayerList().get(i).setPlayerName(playerTextField[i].getText());
				}
			}
			
			// show the game play panel
			GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "gameplayCard");	
			
			// initialize the game panel
			((GUIGamePlayPanel)GUIComponentController.getComponentList().get(4)).initialize();
        }
	};
	
	public GUIPlayerNamePanel(){	
		this.setLayout(new GridBagLayout()); // set this panel's layout
		this.setBackground(Color.YELLOW); // set background color for this panel
		updateLayout();	// this is called to update the layout based on the nubmer of players selected previously
	}
	
	public void updateLayout() {
		this.removeAll(); // first, remove everything in the panel
		this.setLayout(new GridBagLayout()); // reset the layout
		this.setBackground(Color.RED); // set the background
		GridBagConstraints c = new GridBagConstraints(); // new GBC
		
		// set up the title at the top of the page
		c.gridy = 0;
        c.weighty = 0.1;
        c.weightx = 1;
        c.gridheight = 1;
        c.gridwidth = 3;
        c.insets = new Insets(GUIResolutionController.convertScaleHeight(50), 0, 
        		GUIResolutionController.convertScaleHeight(50), 0);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTH;
        
        JLabel pageTitle = new JLabel("Enter your Players' Names");
        pageTitle.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(2400), GUIResolutionController.convertScaleHeight(225))); // set the size
        pageTitle.setMinimumSize(pageTitle.getPreferredSize());
        pageTitle.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(180)));
        pageTitle.setBackground(new Color(0,0,0, 120));
        pageTitle.setForeground(Color.WHITE);
        pageTitle.setOpaque(true);
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(pageTitle, c);
        
        // set up player name text fields and labels
        c.gridwidth = 1;
        c.ipady = 0;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 1; // set element to be placed at gridx location 0
		c.fill = GridBagConstraints.HORIZONTAL; // make element to be placed fill horizontal space
		
		playerTextField = new JTextField[GUIGameController.getPlayerList().size()]; // create textfields = to player number
		Font labelFont = new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(120)); // create font
		
		labelList = new ArrayList<JLabel>();
		for (int i = 0; i < playerTextField.length; i++) {
			c.gridy = i+1;
			
			// create a JPanel for each player
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout());			
			panel.setBackground(GUIGameController.getPlayerColors(i));
			panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			// add a label with the player name prompt
			JLabel nameLabel = new JLabel("Player " + (i+1) + "'s Name:");
			nameLabel.setFont(labelFont);
			labelList.add(nameLabel);
			panel.add(nameLabel);
			
			// create a textbox with a max entry length of 10
			MaxLengthTextDocument maxLength = new MaxLengthTextDocument();
			maxLength.setMaxChars(10);
			playerTextField[i] = new JTextField(10);
			playerTextField[i].setDocument(maxLength);
			playerTextField[i].setText(GUIGameController.getPlayerList().get(i).getPlayerName());
			playerTextField[i].setFont(labelFont);
			this.add(playerTextField[i], c);
			panel.add(playerTextField[i]);
			
			// add an image of the meeple the player will use
			JLabel meepleLabel = new JLabel();
			Image newButtonImage = GUIImageController.getPlayerMeepleImage(i)
					.getScaledInstance(GUIResolutionController.convertScaleWidth(200), 
							GUIResolutionController.convertScaleHeight(200), Image.SCALE_SMOOTH);
			
			meepleLabel.setIcon(new ImageIcon(newButtonImage));
			panel.add(meepleLabel);
			this.add(panel, c);
		}
		
		// finally, configure the start button
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy++;;
		c.gridwidth = 3;
		c.weighty = 10;
		c.insets = new Insets(GUIResolutionController.convertScaleHeight(60), 0, 0, 0); // 20 pixel space at top between elements
		
		startButton = new GUIMenuButton("Start Game");
		startButton.addMouseListener(new MouseListenerClicked());
		startButton.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(150)));

		startButton.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(1000), 
				GUIResolutionController.convertScaleHeight(225)));
		startButton.setMinimumSize(startButton.getPreferredSize()); // stops this from flickering on resize
		Image newButtonImage = ((ImageIcon) startButton.getIcon()).getImage().getScaledInstance(
				GUIResolutionController.convertScaleWidth(1000), 
				GUIResolutionController.convertScaleHeight(225), Image.SCALE_SMOOTH);
		startButton.setIcon(new ImageIcon(newButtonImage));
		startButton.addMouseListener(new MouseListenerClicked());	

		this.add(startButton, c);
		
		// automatically resize components if change in window size is detected
		this.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(final ComponentEvent e) {
		    	// calculations for size based on 1728 x 972 resolution (90% 1980 x 1080)
		        super.componentResized(e);
		    	int width = getWidth();
		    	int height = getHeight();
                
		    	pageTitle.setPreferredSize(new Dimension((int)(0.694444*width), (int)(0.07716049*height)));
		        pageTitle.setMinimumSize(new Dimension((int)(0.3472222*width), (int)(0.07716049*height)));
		        pageTitle.setFont(new Font("Augusta", Font.PLAIN, (int)(width*0.05208333)));
		        
		        startButton.setSize(new Dimension((int)(width*0.260416666), (int)(height*0.1157407)));
                startButton.setFont(new Font("Augusta", Font.PLAIN, (int)(width*0.04)));
                Image newButtonImage = ((ImageIcon) startButton.getIcon()).getImage().getScaledInstance(
                		startButton.getIcon().getIconWidth(), startButton.getIcon().getIconHeight(), Image.SCALE_SMOOTH);
                startButton.setIcon(new ImageIcon(newButtonImage));
                                
		    }
		});
	}
	
	// this solution to limit length in a JTextField was taken from:
	// https://stackoverflow.com/questions/30027582/limit-the-number-of-characters-of-a-jtextfield
	// By MChaker, 2015 May 4
	
	public class MaxLengthTextDocument extends PlainDocument {
		private static final long serialVersionUID = 1L;
		//Store maximum characters permitted
	    private int maxChars;

	    @Override
	    public void insertString(int offs, String str, AttributeSet a)
	            throws BadLocationException {
	        // the length of string that will be created is getLength() + str.length()
	        if(str != null && (getLength() + str.length() <= maxChars)){
	            super.insertString(offs, str, a);
	        }
	    }
	    
	    public void setMaxChars(int maxChars) {
	    	this.maxChars = maxChars;
	    }
	}
	
	@Override
	// draw custom image on background
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    	Image newImage = GUIImageController.getBackGroundImage(5).getScaledInstance(
	    			this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
	        g.drawImage(newImage, 0, 0, null);
	}
	
}
