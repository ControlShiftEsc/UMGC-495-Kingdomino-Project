/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIPlayerNumberPanel.java
 * @Description: This class houses the methods that control resolution output for the game. The resolution output
 * will also automatically format and change the size of components displayed on screen.
 */

import java.awt.Dimension;
import java.awt.Toolkit;

public class GUIResolutionController {
	// attributes
	private static double screenWidth; // display width
	private static double screenHeight; // display height
	private static double percentageReduced; // precentage to reduce so that window doesn't stretch across full screen
	static {	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // get display size
		screenWidth = screenSize.getWidth(); // capture display width
		screenHeight = screenSize.getHeight(); // capture display height
		percentageReduced = 0.9; // reduce display size to 90%
	}
	
	// getters
	public static double getScreenWidth() {
		return screenWidth;
	}
	
	public static double getScreenHeight() {
		return screenHeight;
	}
	
	public static int convertScaleWidth(double target) {
		return (int)(target*screenWidth/3840*percentageReduced);
	}
	
	public static int convertScaleHeight(double target) {
		return (int)(target*screenHeight/2160*percentageReduced);
	}
}
