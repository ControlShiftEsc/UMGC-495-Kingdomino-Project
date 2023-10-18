/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Michael Wood Jr.
 * @Editor: William Feighner, Alan Anderson
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUITitleJLabel.java
 * @Description: This class controls what is displayed within the team logo is displayed at the top of the screen
 */

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GUITitleJLabel extends JLabel {
	private static final long serialVersionUID = 406565401340327377L;
	private Image backgroundImage;
	
	public GUITitleJLabel(String text, Image newBackgroundImage) {
		super(text);
		this.backgroundImage = newBackgroundImage;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				backgroundImage = backgroundImage.getScaledInstance((int) (getWidth()),
						(int) (getHeight()), Image.SCALE_SMOOTH);
			}
		});
		
	}

	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(backgroundImage, 0, 0, null);
		super.paintComponent(g);
	}
}
