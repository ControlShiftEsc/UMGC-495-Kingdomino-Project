/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIAnimatedTitleDomino
 * Description: This class represents an animated domino image and maintains
 * all of the attributes needed for a JPanel to draw this object's image.
 */

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUIAnimatedTitleDomino extends GUIAnimatedImage {


	/**
	 * Generates an animated domino image
	 * @param panel - panel to generate size parameters from
	 * @param frameMax = animation's maximum number of frames. More frames for slower animation
	 */
	public GUIAnimatedTitleDomino(JPanel panel, int frameMax) {
		this.setFrameMax(frameMax);
		int imagerand = getRandom().nextInt(48); // generate a random number
		// then get one of the domino images based on the number generated
		
		setBImage(GUIStaticGameDominoList.dominoImagesFront.get(imagerand));
		
		setFrame(0); // offset frame number so animations don't start and end on same frame

		// create a timer object that runs the animations; random time for random animation length
		Timer timer = new Timer(getRandom().nextInt(90) + 30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// flag that stops dominos from being rendered before JPnal tries to draw them
				setInitialized(true); 
				
				// ensures that the panel this is inside has initialized and has a width/height
				if (panel.getWidth() == 0 || panel.getHeight() == 0) { return; }

				// reset frame number if max frame number reached
				if (getFrame() > getFrameMax()) { setFrame(0); }
				
				// on the first frame, determine whether domino rotates clockwise or counterclockwise
				// then get its image and scale it according to the user's screen dimensions
				// then give it a random starting x location ont he screen to animate from
				if (getFrame() == 0) {
					if (getRandom().nextInt(2) == 0) { setRotationClockwise(false); }
					else { setRotationClockwise(true); }
					setBImage(GUIStaticGameDominoList.dominoImagesFront.get(getRandom().nextInt(48)));
					int scale = getRandom().nextInt(4) + 1;
					int newWidth = (int) (panel.getWidth()*0.4/scale);
					int newHeight = (int) (panel.getHeight()*0.4 /scale);
										
					setSImage(getBImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
					setStartX(getRandom().nextInt(panel.getWidth()));
				}
												
				setDegrees(360 * getFrame() / getFrameMax()); // make degree rotation fraction of total max frames
				if (!isRotationClockwise()) { setDegrees(getDegrees() * -1); }
				panel.repaint(); // request to repaint
				setFrame(getFrame()+1); // increment frame number
			}
		});
		
		setTimer(timer); // set the animation's time to the timer above
		int randomDelay = getRandom().nextInt(5000); // create random delay to animate dominos at different times
		timer.setInitialDelay(randomDelay); // give initial delay to timer
		timer.start(); // start timer
	}
}
