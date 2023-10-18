/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Michael Wood Jr. & Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUICreditsPanel.java
 * @Description: This panel houses the credits for the game
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUICreditsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<GUIMenuButton> buttonList = new ArrayList<GUIMenuButton>();

	public GUICreditsPanel() {
		// mouse listener for buttons
		TitleMenuListenerClicked ml = new TitleMenuListenerClicked();

		// set this page up as a GridBagLayout
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.orange);
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.weighty = 0.1;
		c.weightx = 1;
		c.gridwidth = 2;
		c.ipady = GUIResolutionController.convertScaleHeight(50);
		c.gridheight = 1;
		c.insets = new Insets(GUIResolutionController.convertScaleHeight(50), 0, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;

		// this sets up the transparent title at the top of the page
		JLabel pageTitle = new JLabel("Credits");
		pageTitle.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(900),
				GUIResolutionController.convertScaleHeight(300))); // set the size
		pageTitle.setMinimumSize(pageTitle.getPreferredSize());
		pageTitle.setFont(new Font("Augusta", Font.PLAIN, (GUIResolutionController.convertScaleWidth(300))));
		pageTitle.setBackground(new Color(255, 165, 0, 120));
		pageTitle.setForeground(Color.WHITE);
		pageTitle.setOpaque(true);
		pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(pageTitle, c);

		// this sets up the credits
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.ipady = GUIResolutionController.convertScaleHeight(60);
		c.ipadx = GUIResolutionController.convertScaleWidth(100);
		c.weighty = 10;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTH;
		
		JLabel credits = new JLabel();
		credits.setForeground(Color.WHITE);
		credits.setHorizontalAlignment(SwingConstants.CENTER);
		credits.setFont(new Font("Arial", Font.PLAIN, (GUIResolutionController.convertScaleWidth(50))));
		credits.setBackground(new Color(255, 165, 0, 175));
		credits.setOpaque(true);
		credits.setText(
				"<HTML>"
				+ "<p style=\"text-align: center\">"
				+ "<b>Course</b>: CMSC 495 6390 Capstone in Computer Science (2238)<br>"
				+ "<b>Professor</b>: Professor Shanna Nevarez<br>"
				+ "<b>Project</b>: Kingdomino - Digital Board Game Edition<br><br>"
				+ "</p>"
				+ "<br>"
				+ "<p style=\"text-align: center\">"
				+ "<b>Resources:</b><br>"
				+ "Domino and meeple graphics taken from Kingdomino board game. "
				+ "</p>"
				+ "<br>"
				+ "<p style=\"text-align: center\">"
				+ "Midi music created with Signal:<br>"
				+ "https://signal.vercel.app/?lang=en"
				+ "</p>"
				+ "</HTML>"
				);
		
		this.add(credits, c);
		
		// this sets up the credits
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = GUIResolutionController.convertScaleHeight(60);
		//c.insets = new Insets(GUIResolutionController.convertScaleHeight(20), GUIResolutionController.convertScaleWidth(50), 0, 
			//	GUIResolutionController.convertScaleWidth(50));
		c.weighty = 10;
		c.weightx = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTH;
		
		JLabel credits2 = new JLabel();
		credits2.setForeground(Color.WHITE);
		credits2.setHorizontalAlignment(SwingConstants.CENTER);
		credits2.setFont(new Font("Arial", Font.PLAIN, (GUIResolutionController.convertScaleWidth(50))));
		credits2.setBackground(new Color(0, 60, 255, 175));
		credits2.setOpaque(true);
		credits2.setText(
				"<HTML>"
				+ "<p style=\"text-align: center\">"
				+ "<b>Project Manager / Framework Design Team</b>:<br> Alan Anderson<br><br>"
				+ "<b>Quality Control / Framework Design Team</b>:<br> William Feighner<br><br>"
				+ "<b>Graphics Design / UI & UX Design Team</b>:<br> Michael Wood Jr.<br><br>"
				+ "<b>Testing / Documentation / Quality Control</b>:<br> Ibadet Mijit<br><br>"
				+ "<b>Sound / UI & UX Design Team</b>:<br> Jenna Seipel<br><br>"
				+ "<b>Timeline Manager / UI & UX Design Team</b>:<br> Joseph Lewis<br><br><br>"
				+ "</p>"
				+ "</HTML>"
				);
		
		this.add(credits2, c);
		// this sets up each of the buttons on the page
		c.gridy++;
		c.ipady = 0;
		c.ipadx = 0;
		c.weighty = 0.1;
		c.weightx = 0.1;
		c.insets = new Insets(GUIResolutionController.convertScaleHeight(120), 0, 0, 0);
		// go through and set up each of the menu buttons
		Font buttonFont = new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(150));
		
		GUIMenuButton doneButton = new GUIMenuButton("Done");


		doneButton.setFont(buttonFont);
		doneButton.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(1000),
				GUIResolutionController.convertScaleHeight(225))); // set the size
		doneButton.setMinimumSize(doneButton.getPreferredSize());
		doneButton.addMouseListener(ml); // add the mouse listener
		// then resize the image of the button based on the size of the button itself
		Image newButtonImage = ((ImageIcon) doneButton.getIcon()).getImage().getScaledInstance(
				GUIResolutionController.convertScaleWidth(1000), GUIResolutionController.convertScaleHeight(225),
				Image.SCALE_SMOOTH);
		doneButton.setIcon(new ImageIcon(newButtonImage));
	}

	private class TitleMenuListenerClicked extends MouseAdapter {

		@Override
		// functionality for each of the buttons
		public void mousePressed(MouseEvent e) {
			// display the player number selection panel if the Play button is pressed
			if (e.getSource() == buttonList.get(0)) {
				GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "playerCard");
				GUIComponentController.setCurrentCardNumber(3);
			} else if (e.getSource() == buttonList.get(1)) {
				// display the how to play panel if the how to play button is pressed
				System.out.println("Under Construction");
				//GUIComponentController.setCurrentCardNumber(2);
				// exit the game if exit button is selected
			} else if (e.getSource() == buttonList.get(2)) {
				GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "creditsCard");
				GUIComponentController.setCurrentCardNumber(5);
			} else if (e.getSource() == buttonList.get(3)) {
				System.exit(0);
			}
		}
	}
}
