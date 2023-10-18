/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIMenuButton.java
 * @Description: This class holds the menu button that can be used for all in game menus
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GUIMenuButton extends JButton{
	// attributes
	private static final long serialVersionUID = 1L;
	private boolean buttonSelected = false; // flag whether button has been selected
	
	// constructor
	public GUIMenuButton(String text) {
		super(text);
		// set up parameters for new button
		this.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(150)));
		this.setOpaque(false); // make it see-through
		this.setContentAreaFilled(false); // don't fill button with a solid color
		this.setBorderPainted(false); // don't draw the border
		this.setHorizontalTextPosition(JButton.CENTER); // center text horizontally
		this.setVerticalTextPosition(JButton.CENTER);
		this.setFocusPainted(false); // don't outline button when it has focus
		this.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(1000), 
				GUIResolutionController.convertScaleWidth(225)));
		this.setMinimumSize(this.getPreferredSize()); // stops this from flickering on resize
		Image newButtonImage = GUIImageController.getMenuButtonImage(1).getScaledInstance(
				GUIResolutionController.convertScaleWidth(1000), 
				GUIResolutionController.convertScaleWidth(225), Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(newButtonImage));
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
	 * This method resets the button's image
	 */
	public void resetButtonImage() {
		if (this.getWidth() == 0 || this.getHeight() == 0) { return; }
		Image newButtonImage = GUIImageController.getMenuButtonImage(1).getScaledInstance(this.getIcon().getIconWidth(),
				this.getIcon().getIconHeight(), Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(newButtonImage));
	}
				
	// setters
	public void setButtonSelected(boolean buttonSelected) {
		this.buttonSelected = buttonSelected;
	}
		
	// custom MouseAdapter class
	private class ButtonMouseListener extends MouseAdapter {
		// change images upon mouse action
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
