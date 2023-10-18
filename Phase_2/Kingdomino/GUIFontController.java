/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIStaticFont.java
 * Description: This class holds the main font used; need to assess whether this is actually needed
 */

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public class GUIFontController {

	private static Font gameFont; // game font for some menu items
	
	static {

		try {
			InputStream stream = GUIFontController.class.getResource("images/Font/Augusta.ttf")
	                .openStream();
	        
			gameFont = Font.createFont(Font.TRUETYPE_FONT, stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(gameFont);
	}
	
	/**
	 * This method sets the game font for some of the menu components
	 * @param newGameFont - new game font to use
	 */
	public static void setGameFont(Font newGameFont) {
		gameFont = newGameFont;
	}
	
	/**
	 * This method returns the game's font for some menu items
	 * @return - menu item game font
	 */
	public static Font getGameFont() {
		return gameFont;
	}
	
}
