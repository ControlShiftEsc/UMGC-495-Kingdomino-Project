/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIStaticGameDominoList.java
 * @Description: Static list of dominos images for GUI display
 */

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GUIStaticGameDominoList {
	// list of front dominos images
	public static ArrayList<BufferedImage> dominoImagesFront = new ArrayList<BufferedImage>();
	// list of blurry font dominos; want to use for animation purposes
	public static ArrayList<BufferedImage> dominoImagesFrontBlurred = new ArrayList<BufferedImage>();
	// list of back domino images
	public static ArrayList<BufferedImage> dominoImagesBack = new ArrayList<BufferedImage>();
	
	static {
		// load the images into their corresponding array lists
		for (int i = 1; i <= 48; i++) {
			try {
				BufferedImage frontImage = ImageIO.read(GUIStaticGameDominoList.class.getResource(
						"images/Playing Tiles/Front/tile_" + i + ".png"));
				dominoImagesFront.add(frontImage);
				
				BufferedImage frontImageBlurred = ImageIO.read(GUIStaticGameDominoList.class.getResource(
						"images/Playing Tiles/blurred/tile_" + i + ".png"));
				dominoImagesFrontBlurred.add(frontImageBlurred);

				BufferedImage backImage = ImageIO.read(GUIStaticGameDominoList.class.getResource(
						"images/Playing Tiles/Back/back_" + i + ".png"));
				dominoImagesBack.add(backImage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}
