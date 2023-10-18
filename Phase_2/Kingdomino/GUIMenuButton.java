/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIMenuButton.java
 * Description: This class holds the menu button that can be used for all in game menus
 */

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GUIMenuButton extends JButton{
	private static final long serialVersionUID = 1L;
	private boolean buttonSelected = false; // flag whether button has been selected
	
	// constructor
	public GUIMenuButton(String text) {
		super(text);
		// set up parameters for new button
		this.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(50)));
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.CENTER);
		this.setFocusPainted(false);
		this.setIcon(new ImageIcon(GUIImageController.getMenuButtonImage(1)));
		this.addMouseListener(new ButtonMouseListener());	
	}
	
	/**
	 * This method updates the button using the image supplied in the parameter
	 * @param image - image used to apply to the button
	 */
	public void updateButtonImage(Image image) {
		if (this.getWidth() == 0 || this.getHeight() == 0) { return; }
		Image newButtonImage = image.getScaledInstance(this.getIcon().getIconWidth(),
				this.getIcon().getIconHeight(), Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(newButtonImage));
	}
	
	/**
	 * This method resets the button back to the default image
	 * @param image - image used to apply to the button
	 */
	public void updateButtonImage() {
		if (this.getWidth() == 0 || this.getHeight() == 0) { return; }
		Image newButtonImage = GUIImageController.getMenuButtonImage(1).getScaledInstance(this.getIcon().getIconWidth(),
				this.getIcon().getIconHeight(), Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(newButtonImage));
	}
				
	// setters
	
	/**
	 * This method sets the buttons selected flag to true or false
	 * @param buttonSelected - selected (true) or not selected (false)
	 */
	public void setButtonSelected(boolean buttonSelected) {
		this.buttonSelected = buttonSelected;
	}
		
	// custom MouseAdapter class
	private class ButtonMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			GUISoundController.playSelectSound();
			setButtonSelected(false);
			updateButtonImage(GUIImageController.getMenuButtonImage(3));
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (!buttonSelected) {
				updateButtonImage(GUIImageController.getMenuButtonImage(2));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (!buttonSelected) {
				updateButtonImage(GUIImageController.getMenuButtonImage(1));
			}
		}
	};
}
