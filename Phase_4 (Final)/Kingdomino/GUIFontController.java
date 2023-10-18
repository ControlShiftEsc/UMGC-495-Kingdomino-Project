/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIFontController.java
 * @Description: This class holds the main font used; need to assess whether this is actually needed
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
			System.out.println("Error: Problem loading font file in GUIFontController.java");
			e.printStackTrace();
		} catch (FontFormatException e) {
			System.out.println("Error: Problem creating font in GUIFontController.java");
			e.printStackTrace();
		}
        		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(gameFont);
	}
	
	// setter
	public static void setGameFont(Font newGameFont) {
		gameFont = newGameFont;
	}
	
	// getter
	public static Font getGameFont() {
		return gameFont;
	}
	
}
