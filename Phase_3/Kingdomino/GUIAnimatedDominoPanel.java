/**
 * @Class: CMSC495
 * @Date: 20 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIAnimatedDominoPanel.java
 * @Description: This panel houses the falling domino animation used for the start menu and options
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JPanel;

public class GUIAnimatedDominoPanel extends JPanel{
	private static final long serialVersionUID = -9135146838827127162L;

	Random rand = new Random(); // random number generator
	
	// list of animated dominos
	private ArrayList<GUIAnimatedTitleDomino> animatedDominos = new ArrayList<GUIAnimatedTitleDomino>();
	// number of dominos to animate for title screen
	private int numAnimatedDominos = 10; 
	
	public GUIAnimatedDominoPanel() {
		animatedDominos = new ArrayList<GUIAnimatedTitleDomino>(); // create new list
		for (int i = 0; i < numAnimatedDominos; i++) {
			GUIAnimatedTitleDomino d = new GUIAnimatedTitleDomino(this, 100); // create animated dominos
			animatedDominos.add(d); // then add these to a list to animte them in the paint component
		}

		this.setOpaque(true);
		this.setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!GUIGameController.getGameIsRunning()) { // don't draw anything if not in the menu options
			Graphics2D g2 = (Graphics2D) g.create(); // create Graphics2D object
			
			// we override the paintComponent here to draw the animated dominos
			// for each domino in the list of animated dominos
			// sort list first to draw larger objects over small objects
			animatedDominos.sort((Comparator.comparing(x -> ((GUIAnimatedTitleDomino) x)
					.getScale()).reversed()));
			for (GUIAnimatedTitleDomino d : animatedDominos) {
				if (!d.getInitialized()) { // don't draw dominos if they haven't initialized
					continue;
				}
				AffineTransform at2 = new AffineTransform();
				// move domino to location based on animation frame
				double yTranslation = (((double)d.getFrame()/d.getFrameMax())*
						(this.getHeight()+d.getSImage().getHeight(this)) - d.getSImage().getHeight(this));
				at2.translate(d.getStartX(), yTranslation);

				// rotate domino based on current rotation degree
				at2.rotate(Math.toRadians(d.getDegrees()), d.getSImage().getWidth(this)/2,
						d.getSImage().getHeight(this)/2);
				// then draw domino image to panel
				g2.drawImage(d.getSImage(), at2, null);
			}
			g2.dispose();
		}
	}
	
}
