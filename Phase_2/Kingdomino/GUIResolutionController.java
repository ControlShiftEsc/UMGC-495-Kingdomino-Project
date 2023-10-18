/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Michael Wood Jr.
 * Editor/Revisor: Alan Anderson & William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIResolutionController.java
 * Description: This class houses the methods that control resolution output for the game. The resolution output
 *              will also automatically format and change the size of components displayed on screen.
 */

import java.awt.Dimension;
import java.awt.Toolkit;

public class GUIResolutionController {
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
	
	/**
	 * This method returns the screen width of the display
	 * @return - screen width
	 */
	public static double getScreenWidth() {
		return screenWidth;
	}
	
	/**
	 * This method returns the screen height of the display
	 * @return - screen height
	 */
	public static double getScreenHeight() {
		return screenHeight;
	}
	
	/**
	 * This method returns a converted width for components based on resolution and DPI scale
	 * @param target - target size
	 * @return - converted size
	 */
	public static int convertScaleWidth(double target) {
		return (int)(target*screenWidth/3840*percentageReduced);
	}
	
	/**
	 * This method returns a converted height for components based on resolution and DPI scale
	 * @param target - target size
	 * @return - converted size
	 */
	public static int convertScaleHeight(double target) {
		return (int)(target*screenHeight/2160*percentageReduced);
	}
}
