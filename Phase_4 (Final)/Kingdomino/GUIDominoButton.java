/**
 * @Class: CMSC495
 * @Date: 20 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIDominoButton.java
 * @Description: This class holds the architecture for the domino button
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JButton;

public class GUIDominoButton extends JButton {
	// attributes
	private static final long serialVersionUID = 4622502725143969206L;
	private Image dominoImage; // image to display in button
	private boolean mousedOver; // flag whether mouse is over button
	private Color backgroundColor = new Color(211, 211, 211); // normal background color
	private Color mouseOverBackgroundColor = new Color(190, 180, 2); // normal mouseover background color
	
	// constructor
	public GUIDominoButton(Image dominoImage) {
		super();
		this.dominoImage = dominoImage;
	}
	
	// getters
	public Image getDominoImage() {
		return this.dominoImage;
	}
	
	// setters
	public void setDominoImage(Image dominoImage) {
		this.dominoImage = dominoImage;
	}
	
	public void setMousedOver(boolean mousedOver) {
		this.mousedOver = mousedOver;
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Color newBackgroundColor = null;
		
		// change color depending on state of mouse button
		if (mousedOver && isEnabled()) {
			newBackgroundColor = mouseOverBackgroundColor;
		} else {
			newBackgroundColor = backgroundColor;
		}
		
		// if mouse button image isn't null, draw the following
		if (dominoImage != null) {
			Graphics2D g2 = (Graphics2D) g.create();
			Image newImage = dominoImage.getScaledInstance((int) (this.getWidth()),
					(int) (this.getHeight()), Image.SCALE_SMOOTH);
			g2.drawImage(newImage, 0, 0, null);
			g2.setColor(newBackgroundColor);
			g2.setStroke(new BasicStroke(GUIResolutionController.convertScaleWidth(4)));
			g2.drawRect((int)(this.getWidth()*0.02), (int)(this.getHeight()*0.03),
					(int)(this.getWidth()*0.96), (int)(this.getHeight()*0.94));
			g2.dispose();
		// otherwise, draw this
		} else {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setColor(Color.BLACK);
			g2.fillRect(0,  0,  this.getWidth(), this.getHeight());
			g2.setColor(Color.WHITE);
			g2.drawRect((int)(this.getWidth()*0.02), (int)(this.getHeight()*0.03),
					(int)(this.getWidth()*0.96), (int)(this.getHeight()*0.94));
			g2.dispose();
		}		
	}
}
