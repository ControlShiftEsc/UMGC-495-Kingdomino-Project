/**
 * Class: CMSC495 Date: 2 SEP 2023
 * Creator: Michael Wood Jr. & Alan Anderson
 * Editor/Revisor: William Feighner 
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet Mijit, Jenna Seipel, Joseph Lewis 
 * File: GUIRulesPanel.java Description: This class is the rules panel
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUIRulesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
    int rulePos = 0;

	public GUIRulesPanel() {
		// List of rules
        ArrayList<ImageIcon> rules = new ArrayList<ImageIcon>();

		// List of rule file locations
		String ruleList[] = {"Introduction.png",
							"Objective.png",
							"StartingRound.gif",
							"StartingRound2.gif",
							"PlayingRound.png",
							"DominoSelection.png",
							"KingdomBuilding.png",
							"PlacingDominoes.gif",
							"EndOfGame.gif",
							"Scoring.png",
							"Adjustments.png"};

        ImageIcon rule;
        Image leftButtonImage, rightButtonImage,
			enteredLeftButtonImage, enteredRightButtonImage,
			pressedLeftButtonImage, pressedRightButtonImage;


		// Loads rules files
		for(String ruleFile : ruleList){
			try {
				URL ruleImg = GUIRulesPanel.class.getResource("/images/Rules/" + ruleFile).toURI().toURL();
				rule = new ImageIcon(ruleImg);
				rule.setImage(rule.getImage().getScaledInstance(GUIResolutionController.convertScaleWidth(2800), GUIResolutionController.convertScaleWidth(1400), Image.SCALE_DEFAULT));
				rules.add(rule);
			} catch (MalformedURLException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Displays rule as an image.
    	JLabel ruleDisplay = new JLabel(rules.get(0));
		ruleDisplay.setSize(new Dimension((int)(GUIResolutionController.convertScaleWidth(00)),
				GUIResolutionController.convertScaleHeight(100)));

		// set this page up as a GridBagLayout
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.orange);
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.weighty = 0.1;
		c.weightx = 1;
		c.gridwidth = 5;
		c.ipady = GUIResolutionController.convertScaleHeight(50);
		c.gridheight = 1;
		c.insets = new Insets(GUIResolutionController.convertScaleHeight(50), 0, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;

		// this sets up the transparent title at the top of the page
		JLabel pageTitle = new JLabel("Rules");
		pageTitle.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(900),
				GUIResolutionController.convertScaleHeight(300))); // set the size
		pageTitle.setMinimumSize(pageTitle.getPreferredSize());
		pageTitle.setFont(new Font("Augusta", Font.PLAIN, (GUIResolutionController.convertScaleWidth(300))));
		pageTitle.setBackground(new Color(255, 165, 0, 120));
		pageTitle.setForeground(Color.WHITE);
		pageTitle.setOpaque(true);
		pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(pageTitle, c);

		// Grid for left button
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.ipady = GUIResolutionController.convertScaleHeight(60);
		c.ipadx = GUIResolutionController.convertScaleWidth(100);
		c.weighty = 5;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		
		// Add left arrow button
        JButton leftButton = new JButton();
		leftButton.setContentAreaFilled(false);
		leftButton.setBorderPainted(false);
		leftButton.setFocusPainted(false);
		leftButton.setForeground(Color.BLACK);
            
		// When left button is clicked displats the previous rule
		leftButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(rulePos > 0){
                    rulePos--;
                    ruleDisplay.setIcon(rules.get(rulePos));
                }
            }
        });
		this.add(leftButton, c); 

		// Grid for rule to be displayed
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTH;
		this.add(ruleDisplay, c);

		// Grid for right button
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;	

		// Adds and formats right arrow button
        JButton rightButton = new JButton();
		rightButton.setContentAreaFilled(false);
		rightButton.setBorderPainted(false);
		rightButton.setFocusPainted(false);
		rightButton.setForeground(Color.BLACK);

		// When right button is clicked displats the next rule
		rightButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(rulePos < rules.size()-1){
                    rulePos++;                    
					ruleDisplay.setIcon(rules.get(rulePos));

                }
            }
        });
		this.add(rightButton, c);
        
		// Sets images for left and right arrow buttons
        try {
            leftButtonImage = (ImageIO.read(GUIRulesPanel.class.getResource("images/menu/leftButton.png")).getScaledInstance(
                    GUIResolutionController.convertScaleWidth(225), GUIResolutionController.convertScaleHeight(225),
                    Image.SCALE_SMOOTH));
            rightButtonImage = (ImageIO.read(GUIRulesPanel.class.getResource("images/menu/rightButton.png")).getScaledInstance(
                    GUIResolutionController.convertScaleWidth(225), GUIResolutionController.convertScaleHeight(225),
                    Image.SCALE_SMOOTH));
            enteredLeftButtonImage = (ImageIO.read(GUIRulesPanel.class.getResource("images/menu/enteredLeftButton.png")).getScaledInstance(
                    GUIResolutionController.convertScaleWidth(225), GUIResolutionController.convertScaleHeight(225),
                    Image.SCALE_SMOOTH));
            enteredRightButtonImage = (ImageIO.read(GUIRulesPanel.class.getResource("images/menu/enteredRightButton.png")).getScaledInstance(
                    GUIResolutionController.convertScaleWidth(225), GUIResolutionController.convertScaleHeight(225),
                    Image.SCALE_SMOOTH));
            pressedLeftButtonImage = (ImageIO.read(GUIRulesPanel.class.getResource("images/menu/pressedLeftButton.png")).getScaledInstance(
                    GUIResolutionController.convertScaleWidth(225), GUIResolutionController.convertScaleHeight(225),
                    Image.SCALE_SMOOTH));
            pressedRightButtonImage = (ImageIO.read(GUIRulesPanel.class.getResource("images/menu/pressedRightButton.png")).getScaledInstance(
                    GUIResolutionController.convertScaleWidth(225), GUIResolutionController.convertScaleHeight(225),
                    Image.SCALE_SMOOTH));
       		leftButton.setIcon(new ImageIcon(leftButtonImage));
        	rightButton.setIcon(new ImageIcon(rightButtonImage));

			// Changes state of left button based on mouse actions
			leftButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
       				leftButton.setIcon(new ImageIcon(pressedLeftButtonImage));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
       				leftButton.setIcon(new ImageIcon(enteredLeftButtonImage));
				}

				@Override
				public void mouseExited(MouseEvent e) {
       				leftButton.setIcon(new ImageIcon(leftButtonImage));
				}
			});
			
			// Changes state of right button based on mouse actions
			rightButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
       				rightButton.setIcon(new ImageIcon(pressedRightButtonImage));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
       				rightButton.setIcon(new ImageIcon(enteredRightButtonImage));
				}

				@Override
				public void mouseExited(MouseEvent e) {
       				rightButton.setIcon(new ImageIcon(rightButtonImage));
				}
			});
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

		//c.gridy++;
		//c.ipady = 0;
		//c.ipadx = 0;
		//c.weighty = 0.1;
		//c.weightx = 0.1;
		//c.insets = new Insets(GUIResolutionController.convertScaleHeight(120), 0, 0, 0);
	}
}
