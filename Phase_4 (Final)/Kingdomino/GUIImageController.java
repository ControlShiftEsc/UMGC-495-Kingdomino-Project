/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIImageController.java
 * @Description: This class maintains the logic for loading and displaying images.
 */

/**
 * Class: CMSC495 Date: 2 SEP 2023 Creator: Alan Anderson Editor/Revisor: William Feighner Team
 * Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet Mijit, Jenna Seipel, Joseph
 * Lewis File: GUIImageController.java Description: This class maintains the logic for loading and
 * displaying images.
 */

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class GUIImageController {

	private static Image backButtonImage; // back button image
	private static Image optionButtonImage; // in game option button image

	// meeples for each of the players; different colors per player
	private static Image player1Meeple;
	private static Image player2Meeple;
	private static Image player3Meeple;
	private static Image player4Meeple;

	// images for the domino movement/placement options in the GUI
	private static ArrayList<Image> actionButtonImages;
	private static Image moveTileDown;
	private static Image moveTileUp;
	private static Image moveTileLeft;
	private static Image moveTileRight;
	private static Image rotateTileCounterClockwise;
	private static Image rotateTileClockwise;
	private static Image placeTile;
	private static Image discardTile;

	// panel background images
	private static Image bookImage;
	private static Image leftGamePlayPanelImage;
	private static Image rightGamePlayPanelImage;
	private static Image kingdominoBoxArtImage;
	private static Image queendominoBoxArtImage;
	private static Image giantdominoBoxArtImage;
	private static Image errorImage; // mouse pressed button image
	private static Image sectionBackground;
	private static Image sectionBackground2;
	private static Image sectionBackground3;

	// how to play panel images
	private static Image howToPlayImage1;
	private static Image howToPlayImage2;
	private static Image howToPlayImage3;
	private static Image howToPlayImage4;
	private static Image howToPlayImage5;
	private static Image howToPlayImage6;
	private static Image howToPlayImage7;
	private static Image howToPlayImage8;
	private static Image howToPlayImage9;
	
	// mouse button images for GUIMenuBotton class
	private static Image buttonImage; // normal button image
	private static Image mouseOverButtonImage; // mouse over button image
	private static Image mousePressedButtonImage; // mouse pressed button image

	private static ArrayList<Color> coloredScoreGroups;
	private static Random rand;
	
	static {

		try {

			coloredScoreGroups = new ArrayList<Color>();

			rand = new Random();

			for (int i = 0; i < 25; i++) {
				int r = rand.nextInt(256);
				int g = rand.nextInt(256);
				int b = rand.nextInt(256);

				if (inRange(r, g - 40, g + 40) || inRange(r, b - 40, b + 40) || inRange(g, b - 40, b + 40)) {
					i--;
					continue;
				}

				for (Color c : coloredScoreGroups) {
					if (inRange(r, c.getRed() - 20, c.getRed() + 20) ||
						inRange(g, c.getGreen() - 20, c.getGreen() + 20) ||
						inRange(b, c.getBlue() - 20, c.getBlue() + 20)) {
					}
				}
				
				coloredScoreGroups.add(new Color(r, g, b, 255));
			}

			optionButtonImage = ImageIO.read(GUIGameController.class.getResource("images/menu/optionButton.png"));
			backButtonImage = ImageIO.read(GUIGameController.class.getResource("images/menu/backButton.png"));
			player1Meeple = ImageIO.read(GUIGameController.class.getResource("images/Meeples/pinkmeeple.png"));
			player2Meeple = ImageIO.read(GUIGameController.class.getResource("images/Meeples/bluemeeple.png"));
			player3Meeple = ImageIO.read(GUIGameController.class.getResource("images/Meeples/greenmeeple.png"));
			player4Meeple = ImageIO.read(GUIGameController.class.getResource("images/Meeples/yellowmeeple.png"));

			moveTileUp = ImageIO.read(GUIGameController.class.getResource("images/menu/MoveTileUp.png"));
			moveTileDown = ImageIO.read(GUIGameController.class.getResource("images/menu/MoveTileDown.png"));
			moveTileLeft = ImageIO.read(GUIGameController.class.getResource("images/menu/MoveTileLeft.png"));
			moveTileRight = ImageIO.read(GUIGameController.class.getResource("images/menu/MoveTileRight.png"));
			rotateTileCounterClockwise = ImageIO
					.read(GUIGameController.class.getResource("images/menu/RotateTileCounterClockwise.png"));
			rotateTileClockwise = ImageIO
					.read(GUIGameController.class.getResource("images/menu/RotateTileClockwise.png"));
			placeTile = ImageIO.read(GUIGameController.class.getResource("images/menu/PlaceTile3.png"));
			discardTile = ImageIO.read(GUIGameController.class.getResource("images/menu/DiscardTile3.png"));

			actionButtonImages = new ArrayList<Image>();
			actionButtonImages.add(moveTileUp); // 0
			actionButtonImages.add(moveTileDown); // 1
			actionButtonImages.add(moveTileLeft); // 2
			actionButtonImages.add(moveTileRight); // 3
			actionButtonImages.add(rotateTileClockwise); // 4
			actionButtonImages.add(rotateTileCounterClockwise); // 5
			actionButtonImages.add(placeTile); // 6
			actionButtonImages.add(discardTile);// 7

			// how to play panel images
			howToPlayImage1 = ImageIO.read(GUIGameController.class.getResource("images/menu/howtoplayimage1.png"));
			howToPlayImage2 = ImageIO.read(GUIGameController.class.getResource("images/menu/howtoplayimage2.png"));
			howToPlayImage3 = ImageIO.read(GUIGameController.class.getResource("images/menu/howtoplayimage3.png"));
			howToPlayImage4 = ImageIO.read(GUIGameController.class.getResource("images/menu/howtoplayimage1.png"));
			howToPlayImage5 = ImageIO.read(GUIGameController.class.getResource("images/menu/howtoplayimage1.png"));
			howToPlayImage6 = ImageIO.read(GUIGameController.class.getResource("images/menu/howtoplayimage1.png"));
			howToPlayImage7 = ImageIO.read(GUIGameController.class.getResource("images/menu/howtoplayimage1.png"));
			howToPlayImage8 = ImageIO.read(GUIGameController.class.getResource("images/menu/howtoplayimage1.png"));
			howToPlayImage9 = ImageIO.read(GUIGameController.class.getResource("images/menu/howtoplayimage1.png"));
			
			// Kingdomino box art image from Blue Orange Games
			// https://blueorangegames.eu/en/games/kingdomino/
			kingdominoBoxArtImage = ImageIO.read(GUIGameController.class.getResource("images/menu/boxart.png"));

			// Queendomino box art image from Blue Orange Games
			// https://blueorangegames.eu/en/games/queendomino/
			queendominoBoxArtImage = ImageIO.read(GUIGameController.class.getResource("images/menu/boxart2.png"));

			// Kingdomino: Age of Giants box art image from Blue Orange Games
			// https://boardgamegeek.com/image/4177705/kingdomino-age-giants
			giantdominoBoxArtImage = ImageIO.read(GUIGameController.class.getResource("images/menu/boxart3.png"));

			errorImage = ImageIO.read(GUIGameController.class.getResource("images/menu/errorButton.png"));

			BufferedImage newImage = ImageIO
					.read(GUIGameController.class.getResource("images/menu/sectionBackground.png"));

			sectionBackground = dye(newImage, new Color(194, 24, 7));

			sectionBackground2 = dye(newImage, new Color(8, 37, 103));

			sectionBackground3 = dye(newImage, new Color (115, 112, 0));

			// button images //
			buttonImage = ImageIO.read(GUIGameController.class.getResource("/images/menu/button.png"));
			mouseOverButtonImage = ImageIO.read(GUIGameController.class.getResource("/images/menu/enteredButton.png"));
			mousePressedButtonImage = ImageIO
					.read(GUIGameController.class.getResource("/images/menu/pressedButton.png"));
			;

		} catch (IOException e) {
			System.out.println("Error loading button images in GUIMenuButton.java");
			e.printStackTrace();
		}
	}

	public static boolean inRange(int original, int min, int max) {
		if (original >= min && original <= max) {
			return true;
		}
		return false;
	}
	
	public static void colorBoardImagesOverlay(GUIGameBoard gameboard) {
		gameboard.getGameBoard().getCurrentScore();

		int count = -1;
		for (ArrayList<GameBoard.Space> arrays : gameboard.getGameBoard().getGameBoardScoreGroups()) {
			if (arrays.size() == 1 && arrays.get(0).getSType() == LandType.EMPTY
					|| arrays.get(0).getSType() == LandType.CASTLE) {
				continue;
			}
			count++;
			for (GameBoard.Space space : arrays) {
				int x = space.getXLoc();
				int y = space.getYLoc();
				if (gameboard.getImageOverlayLayer()[x][y] != null) {
					gameboard.getGameBoardImagesGameOverLayer()[x][y] = 
							GUIImageController.dye((BufferedImage) gameboard.getImageOverlayLayer()[x][y],
							coloredScoreGroups.get(count-1));
				}
			}
		}
	}
	
	public static void resetColorBoardImagesOverlay(GUIGameBoard gameboard) {
		for (int y = 0; y < gameboard.getGameBoardImagesGameOverLayer().length; y++) {
			for (int x = 0; x < gameboard.getGameBoardImagesGameOverLayer()[y].length; x++) {
				gameboard.getGameBoardImagesGameOverLayer()[x][y] = null;
			}
		}
	}
	
	// dye method taken from:
	// https://stackoverflow.com/questions/21382966/colorize-a-picture-in-java
	public static BufferedImage dye(BufferedImage image, Color color) {
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dyed.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.setComposite(AlphaComposite.SrcAtop);
		g.setColor(color);
		g.fillRect(0, 0, w, h);
		g.dispose();
		return dyed;
	}

	public static BufferedImage drawBorder(BufferedImage image, Color color, int border) {
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage newImage = new BufferedImage(w, h, image.getType());
		Graphics2D g = (Graphics2D) newImage.getGraphics();
		// g.setStroke(ne w BasicStroke(border));
		g.setColor(color);
		g.setStroke(new BasicStroke(border));
		g.drawImage(image, border, border, null);
		g.drawRect(0, 0, w, h);
		g.drawLine(0, h, w, 0);
		g.dispose();
		return newImage;
	}

	// getters

	/**
	 * This method returns images for the GUIMenuButton class
	 * 
	 * @param image - image to be returned: 1: default button image 2: mouse over
	 *              button image 3: mouse pressed/selected button image
	 * @return - selected image
	 */
	public static Image getMenuButtonImage(int image) {
		if (image == 1) {
			return buttonImage;
		}
		if (image == 2) {
			return mouseOverButtonImage;
		}
		if (image == 3) {
			return mousePressedButtonImage;
		}
		return null;
	}

	/**
	 * This method returns the image for the back button
	 * 
	 * @return - returns back buton image
	 */
	public static Image getBackButtonImage() {
		return backButtonImage;
	}

	public static Image getOptionButtonImage() {
		return optionButtonImage;
	}

	public static Image getHowToPlayImage(int image) {
		if (image == 1) { return howToPlayImage1; }
		else if (image == 2) { return howToPlayImage2; } 
		else if (image == 3) { return howToPlayImage3; } 
		
		else if (image == 2) { return howToPlayImage2; } 
		else if (image == 2) { return howToPlayImage2; } 
		else if (image == 2) { return howToPlayImage2; } 
		else if (image == 2) { return howToPlayImage2; } 
		else if (image == 2) { return howToPlayImage2; } 
		else if (image == 2) { return howToPlayImage2; } 
		return howToPlayImage1;
	}
	/**
	 * This method returns a background image based on the integer provided in the
	 * parameter
	 * 
	 * @param image - integer provided to choose the background image 1: left pane
	 *              background 2: image for irght pane background 3: kingdomino box
	 *              art 4: queendomino box art 5: giants expansion for kingdomino
	 *              box art 6: book image
	 * @return - returns the image selected
	 */
	public static Image getBackGroundImage(int image) {
		if (image == 1) {
			return leftGamePlayPanelImage;
		}
		if (image == 2) {
			return rightGamePlayPanelImage;
		}
		if (image == 3) {
			return kingdominoBoxArtImage;
		}
		if (image == 4) {
			return queendominoBoxArtImage;
		}
		if (image == 5) {
			return giantdominoBoxArtImage;
		}
		if (image == 6) {
			return bookImage;
		}
		if (image == 7) {
			return errorImage;
		}
		if (image == 8) {
			return sectionBackground;
		}
		if (image == 9) {
			return sectionBackground2;
		}

		if (image == 10) {
			return sectionBackground3;
		}
		return null;
	}

	/**
	 * This method returns an image from the array of action button images
	 * 
	 * @param button - integer that determines the image returned: 1: move up image
	 *               2: move down image 3: move left image 4: move right image 5:
	 *               rotate left image 6: rotate right image 7: place domino image
	 *               8: discard domino image
	 * @return - the image returned
	 */
	public static Image getActionButtonImage(int button) {
		return actionButtonImages.get(button);
	}

	/**
	 * This method returns the image for a player's meeple
	 * 
	 * @param player - integer that determins the image returned: 1: player 1 meeple
	 *               2: player 2 meeple 3: player 3 meeple 4: player 4 meeple
	 * @return - the meeple image returned
	 */
	public static Image getPlayerMeepleImage(int player) {
		if (player == 0) {
			return player1Meeple;
		}
		if (player == 1) {
			return player2Meeple;
		}
		if (player == 2) {
			return player3Meeple;
		}
		if (player == 3) {
			return player4Meeple;
		}
		return null;
	}
	
	public static ArrayList<Color> getColoredScoreGroups() {
		return coloredScoreGroups;
	}
	
	public static void setTwoPlayerMeepleImages() {
		player3Meeple = player1Meeple;
		player4Meeple = player2Meeple;
	}
	
	// code taken from:
	// https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
	public static BufferedImage toBufferedImage(Image img) {
	    if (img instanceof BufferedImage)  {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), 
	    		img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
