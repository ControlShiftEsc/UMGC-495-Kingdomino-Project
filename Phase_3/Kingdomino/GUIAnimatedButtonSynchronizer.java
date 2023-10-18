/**
 * @Class: CMSC495
 * @Date: 27 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIAnimatedButtonSynchronizer.java
 * @Description: This class hosts a timer for synchronized animations
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GUIAnimatedButtonSynchronizer {
	// attributes
	private static int frame = 1; // current frame
	private static int maxFrame = 10; // max frame count
	private static Timer timer = new Timer(10, new ActionListener() { // animation timer
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (frame >= maxFrame) {
				frame = 0;
			}						
			frame++;
		}
	});
	

	// getters
	public static int getFrame() {
		return frame;
	}
	
	public static int getMaxFrame() {
		return maxFrame;
	}
	
	public static Timer getTimer() {
		return timer;
	}
}
