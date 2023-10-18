/**
 * @Class: CMSC495
 * @Date: 20 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIMeepleButton.java
 * @Description: This class holds the structure for the meeple buttons
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JButton;

public class GUIMeepleButton extends JButton {
	// attributes
	private static final long serialVersionUID = -502377916921693272L;
	private Image meepleImage; // button image
	private Color backgroundColor = new Color(211, 211, 211); // button background color group 1
	private Color backgroundColor2 = new Color(211, 211, 211); // button background color group 1
	private Color backgroundColor3 = Color.BLACK; // button background color group 1
	private Color mouseOverBackgroundColor = new Color(190, 180, 2); // button background color group 2
	private Color mouseOverBackgroundColor2 = new Color(255, 242, 0); // button background color group 2 
	private Color mouseOverBackgroundColor3 = new Color(112, 106, 5); // button background color group 2
	private boolean mousedOver = false;
	
	// didn't have time to both implement and test button animations
	//private int animationWidth;
	//private int animationHeight;
	//private int animationDrawStartX;
	//private int animationDrawStartY;
	
	// constructor
	public GUIMeepleButton(Image meepleImage) {
		super();
		this.meepleImage = meepleImage;
		this.setContentAreaFilled(false);
		this.setBorder(null);	
	}
	
	public void selectMeeple(boolean selected) {
		if (selected == true) { // if selected, paint the button the following colors
			backgroundColor = new Color(196, 183, 129);
			backgroundColor2 = new Color(253, 236, 166);
			backgroundColor3 = new Color(98, 91, 62);
		} else { // otherwise, use these colors
			backgroundColor = new Color(211, 211, 211);
			backgroundColor2 = new Color(211, 211, 211);
			backgroundColor3 = Color.BLACK;;
		}
	}
	
	// setters
	public void setMeepleImage(Image meepleImage) {
		this.meepleImage = meepleImage;
	}
	
	public void setMousedOver(boolean mousedOver) {
		this.mousedOver = mousedOver;
	}
	
	// getters
	public Image getMeepleImage() {
		return this.meepleImage;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// if the domino image to the right has been selected, the meeple image won't be null
		// therefore, paint the button in the following way
		if (meepleImage != null) {
			Graphics2D g2 = (Graphics2D) g.create();
			
			Color newBackgroundColor = null;
			Color newBackgroundColor2 = null;
			Color newBackgroundColor3 = null;
			
			if (mousedOver && isEnabled()) { // if the button is moused over, use these colors
				newBackgroundColor = mouseOverBackgroundColor;
				newBackgroundColor2 = mouseOverBackgroundColor2;
				newBackgroundColor3 = mouseOverBackgroundColor3;
			} else { // otherwise, use these colors
				newBackgroundColor = backgroundColor;
				newBackgroundColor2 = backgroundColor2;
				newBackgroundColor3 = backgroundColor3;
			}

			// paint a number of circles to make it look like a raised button
			g2.setColor(newBackgroundColor);
			g2.fillOval(0, 0, this.getWidth(), this.getHeight());
			g2.setColor(newBackgroundColor2);
			g2.fillOval((int)(this.getWidth()*0.10), (int)(this.getHeight()*0.10), 
					(int)(this.getWidth()*0.80), (int)(this.getHeight()*0.80));
			g2.setColor(newBackgroundColor3);
			g2.drawOval((int)(this.getWidth()*0.10), (int)(this.getHeight()*0.10), 
					(int)(this.getWidth()*0.80), (int)(this.getHeight()*0.80));
			
			// then draw the meeple image on top
			Image newImage = meepleImage.getScaledInstance((int) (this.getWidth()/2),
					(int) (this.getHeight()/2), Image.SCALE_SMOOTH);
			g2.drawImage(newImage, this.getWidth()/4, this.getHeight()/4, null);
			g2.dispose();
		} else { // otherwise
			Graphics2D g2 = (Graphics2D) g.create();
			if (isEnabled() && !GUIGameController.getPlacingDomino()) {
				// animation that we didn't have time to implement; above if statement is useless until otherwise
				/*
				int frame = GUIAnimatedButtonSynchronizer.getFrame();
				int maxFrame = GUIAnimatedButtonSynchronizer.getMaxFrame();
				animationWidth = (int)(getWidth()*frame/maxFrame);
				animationHeight = (int)(getHeight()*frame/maxFrame);
				animationDrawStartX = (getWidth()-animationWidth)/2;
				animationDrawStartY = (getHeight()-animationHeight)/2;

				g2.setColor(Color.YELLOW);
				g2.fillOval(animationDrawStartX, animationDrawStartY, animationWidth, animationHeight);
				*/
				g2.setColor(Color.WHITE);
				g2.drawOval(0, 0, this.getWidth(), this.getHeight());	
			} else {
				g2.setColor(Color.WHITE);
				g2.drawOval(0, 0, this.getWidth(), this.getHeight());	
			}
			g2.dispose();
		}		
	}
}
