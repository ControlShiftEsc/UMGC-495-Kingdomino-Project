
/**
 * @Class: CMSC495
 * @Date: 20 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIActionButton.java
 * @Description: The GUI action buttons that move, play, or discard a domino on the player's board
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class GUIActionButton extends JButton {
	private static final long serialVersionUID = 1775067191493719788L;
	private Image actionButtonImage; // image to be displayed in the button
	private Image normalImage; // normal image
	private Image mousedOverImage; // moused over image
	private boolean mousedOver; // flag whether button is moused over
	private Color backgroundColor = new Color(0, 106, 0); // button background color

	public GUIActionButton(Image newActionButtonImage, GUIGamePlayPanel newGuiGamePlayPanel) {
		super();
		// here we create the images once so that we don't slow down the program by
		// repeatedly
		// creating them in the overridden paintComponent. InvokeLater so that the
		// button has a size
		actionButtonImage = newActionButtonImage;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mousedOverImage = actionButtonImage.getScaledInstance((int) (getWidth() * 0.8),
						(int) (getHeight() * 0.8), Image.SCALE_SMOOTH);
				normalImage = actionButtonImage.getScaledInstance((int) (getWidth() * 0.6), (int) (getHeight() * 0.6),
						Image.SCALE_SMOOTH);
			}
		});

	}

	// getters
	public Image getActionButtonImage() {
		return this.actionButtonImage;
	}

	public void setActionButtonImage(Image actionButtonImage) {
		this.actionButtonImage = actionButtonImage;
	}

	// setters
	public void setMousedOver(boolean mousedOver) {
		this.mousedOver = mousedOver;
	}

	@Override
	public void paintComponent(Graphics g) {
		// if moused over and in the placing domino phase of the game
		// draw a green background, then make the button image bigger
		if (mousedOver && GUIGameController.getPlacingDomino()) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setColor(backgroundColor);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			g2.drawImage(mousedOverImage, (int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.1), null);
			// g2.setStroke(new BasicStroke(GUIResolutionController.convertScaleWidth(4)));
			g2.dispose();
			// otherwise draw the button image normally
		} else {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.drawImage(normalImage, (int) (this.getWidth() * 0.2), (int) (this.getHeight() * 0.2), null);
			g2.dispose();
		}
		// then draw everything else associated with the button
		super.paintComponent(g);
	}
}
