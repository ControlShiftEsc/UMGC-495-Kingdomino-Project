/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIGameBoard.java
 * @Description: This class controls the logic for displaying the game board as a GUI component
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUIGameBoard extends JPanel {
	// attributes
	private static final long serialVersionUID = 1L;
	// load textures for empty spaces and castle
	// castle will need a redo so that player colors are represented; should be an
	// easy implementation
	BufferedImage emptyTexture; // empty space texture
	BufferedImage emptyPlayableTexture;
	BufferedImage castleTexture; // castle texture
	// player castle images
	BufferedImage castlePink; // pink castle texture
	BufferedImage castleBlue; // blue castle texture
	BufferedImage castleGreen; // green castle texture
	BufferedImage castleYellow; // yellow castle texture
	private GameBoard gameboard; // game board
	private Image[][] gameBoardImagesUnderlay; // matrix of images to represent game pieces on board
	private Image[][] gameBoardImagesOverlay; // matrix of images to represent game pieces on board
	private Image[][] gameBoardImagesPlayLayer; // matrix of images representing player's domino in play
	private Image[][] gameBoardImagesGameOverLayer; // matrix of images representing scored groups at game end
	
	// represents playable area on player board
	private int[] playArea;
	Timer timer;
	Color boardColor;

	// size of JPanel based on the maximum size of each row or column; used to
	// resize panel
	private int totalSpaceSizeW;
	private int totalSpaceSizeH;

	// constructor
	public GUIGameBoard() {
		// create the images for the different color player castles
		try {
			castlePink = ImageIO.read(GUIGameBoard.class.getResource("images/Starter Tiles/starter_tile_pink.png"));
			castleBlue = ImageIO.read(GUIGameBoard.class.getResource("images/Starter Tiles/starter_tile_blue.png"));
			castleGreen = ImageIO.read(GUIGameBoard.class.getResource("images/Starter Tiles/starter_tile_green.png"));
			castleYellow = ImageIO.read(GUIGameBoard.class.getResource("images/Starter Tiles/starter_tile_yellow.png"));
		} catch (IOException e) {
			System.out.println("Error: Unable to read player castle file. GUIGameBoard.java");
			e.printStackTrace();
		}

		// create the game board and generate the playable area
		gameboard = new GameBoard();
		playArea = gameboard.getPlayArea();

		// create the array of images that will generate the player's game board
		gameBoardImagesUnderlay = new Image[gameboard.getMaxWidth()][gameboard.getMaxHeight()];
		gameBoardImagesOverlay = new Image[gameboard.getMaxWidth()][gameboard.getMaxHeight()];
		gameBoardImagesPlayLayer = new Image[gameboard.getMaxWidth()][gameboard.getMaxHeight()];
		gameBoardImagesGameOverLayer = new Image[gameboard.getMaxWidth()][gameboard.getMaxHeight()];
		
		// create the empty land and castle images on the game board
		try {
			// get empty land texture
			emptyTexture = ImageIO.read(GUIGameBoard.class.getResource("images/Playing Tiles/Front/empty4.png"));
			emptyPlayableTexture = GUIImageController.dye(emptyTexture, new Color(0, 255, 0, 50));
			// then cover each location of the player board image underlay with it
			resetBoardImagesUnderlay();
			// afterwards drop a castle in the middle
			castleTexture = ImageIO.read(GUIGameBoard.class.getResource("images/Starter Tiles/starter_tile_blue.png"));
			gameBoardImagesUnderlay[4][4] = castleTexture;
		} catch (IOException e) {
			System.out.println("Failure to read file in GUIGameBoard.java");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method resets the images underlay layer of the game board
	 */
	public void resetBoardImagesUnderlay() {
		// get the play area for the board, then reset each of its images to the empty texture image
		playArea = this.getGameBoard().getPlayArea();
		for (int y = 0; y < gameBoardImagesUnderlay.length; y++) {
			for (int x = 0; x < gameBoardImagesUnderlay.length; x++) {
				// don't place a texture where the castle should be; in the middle
				if (x != 4 || y != 4 && x >= playArea[0] && x <= playArea[1] && y >= playArea[2] && y <= playArea[3]) {
					gameBoardImagesUnderlay[x][y] = emptyTexture;
				}
			}
		}
	}

	/**
	 * This method shows the playable area on a player's game board
	 * @param playOptions - list of playable spaces on a game board
	 */
	public void showPlayableAreaImagesUnderlay(ArrayList<int[]> playOptions) {
		// get play area then reduce it if any vector is above the max for that vector
		playArea = this.getGameBoard().getPlayArea();
		playArea[0] = Math.max(playArea[0], 0);
		playArea[1] = Math.min(playArea[1], this.getGameBoard().getMaxWidth());
		playArea[2] = Math.max(playArea[2], 0);
		playArea[3] = Math.min(playArea[3], this.getGameBoard().getMaxHeight());

		// indicate to player that space is playable by changing image of space if it is in list of playable spaces
		for (int[] i : playOptions) {
			int x = i[0];
			int y = i[1];
			if (x != 4 || y != 4 && x >= playArea[0] && x <= playArea[1] && y >= playArea[2] && y <= playArea[3]) {
				gameBoardImagesUnderlay[x][y] = emptyPlayableTexture;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		AffineTransform at = new AffineTransform();

		int border = 1; // distance between each square image on board; higher number is more distance
		int[] playArea = gameboard.getPlayArea(); // playable area as an int array
		int minX = playArea[0]; // minimum x location a domino can be
		int maxX = playArea[1]; // maximum x
		int minY = playArea[2]; // minimum y
		int maxY = playArea[3]; // max y

		int mainPanelWidth = getWidth() - border; // max width of panel
		int mainPanelHeight = getHeight() - border; // max height of panel
		int maxBoardColumns = maxX - minX + 1; // number of playable columns
		int maxBoardRows = maxY - minY + 1; // number of playable rows on game board

		// in order to create a square, take the smaller of the two values to make all
		// sides that value later
		double spaceSizeDub = Math.min(mainPanelWidth / maxBoardColumns,
				mainPanelHeight / maxBoardRows);
		int spaceSize = (int) (spaceSizeDub - border);
		totalSpaceSizeW = (spaceSize + border) * maxBoardColumns + border; // total calculated width of all columns
		int startX = (mainPanelWidth - totalSpaceSizeW) / 2 - border; // this value centers the board horizontally
		totalSpaceSizeH = (spaceSize + border) * maxBoardRows + border; // total calculated height
		int startY = (mainPanelHeight - totalSpaceSizeH) / 2 - border; // centers the board vertically

		// draw all image overlays
		at.translate(border, border);
		g2.setColor(Color.BLACK);
		startX = 0;
		startY = 0;
		
		g2.setColor(this.boardColor);
		g2.fillRect(startX, startY,  totalSpaceSizeW, 
				totalSpaceSizeH);

		drawOnImageLayer(gameBoardImagesUnderlay, at, g2, minX, minY, maxX, 
				maxY, startX, startY, spaceSize, border);
		drawOnImageLayer(gameBoardImagesOverlay, at, g2, minX, minY, maxX,
				maxY, startX, startY, spaceSize, border);
		drawOnImageLayer(gameBoardImagesPlayLayer, at, g2, minX, minY, maxX, 
				maxY, startX, startY, spaceSize, border);
		drawOnImageLayer(gameBoardImagesGameOverLayer, at, g2, minX, minY, maxX, 
				maxY, startX, startY, spaceSize, border);
	}

	/**
	 * This method paints the layers of images onto the player game board
	 * 
	 * @param layer     - chosen layer to paint on; 2D Image array
	 * @param at        - the AffineTransform object to use
	 * @param g2        - the Graphics2D object to use
	 * @param minX      - minimum x of play area
	 * @param minY      - minimum x of play area
	 * @param maxX      - maximum x of play area
	 * @param maxY      - maximum y of play area
	 * @param startX    - starting x location of layer mapping
	 * @param startY    - starting y location of layer mapping
	 * @param spaceSize - size of each square in the map
	 * @param border    - border size; size of gap space between squares
	 */
	public void drawOnImageLayer(Image[][] layer, AffineTransform at, Graphics2D g2, int minX, int minY, int maxX,
			int maxY, int startX, int startY, int spaceSize, int border) {
		if (spaceSize <= 0) {
			return;
		} // if the size of the space is less than 0, draw nothing

		at.translate(startX, startY); // move the "pen" to the start location to center everything
		for (int y = minY; y <= maxY; y++) { // for each y
			for (int x = minX; x <= maxX; x++) { // for each x
				if (layer[x][y] == null) { // if this image space is empty, don't draw it
					continue;
				}

				at.translate(x * (spaceSize + border) - minX * (spaceSize + border),
						// adjust again to draw current square
						y * (spaceSize + border) - minY * (spaceSize + border));

				Image newImage = layer[x][y].getScaledInstance(spaceSize, // create image
						spaceSize, Image.SCALE_SMOOTH);
				g2.drawImage(newImage, at, null); // draw the image
				// then move the pen back in position
				at.translate(-(x * (spaceSize + border) - minX * (spaceSize + border)),
						-(y * (spaceSize + border) - minY * (spaceSize + border)));
			}
		}

		at.translate(-startX, -startY); // move the pen back to the start location
	}

	/**
	 * This method saves a domino to the overlay image layer (2nd of 3 layers).
	 * These are the images of dominos that have been played on the player's game
	 * board.
	 * 
	 * @param domino - the domino to be drawn
	 */
	public void saveOverlayImage(Domino domino) {

		BufferedImage source = GUIStaticGameDominoList.dominoImagesFront.get(domino.getDominoNumber() - 1);

		// get the virtual location of the domino on the player's game board
		int xLocA = domino.getSideA().getXLoc();
		int yLocA = domino.getSideA().getYLoc();
		int xLocB = domino.getSideB().getXLoc();
		int yLocB = domino.getSideB().getYLoc();

		// these next attributes will determine how the 2x1 domino image will be slided
		// needs to change based on whether the domino is right-side up, turned
		// counter-clockwise, etc.
		// these settings are for when a domino is right-side up
		int topLeftX = 0; // top left edge x location of Side A of domino
		int topLeftY = 0; // top left edge y location of SIde A of domino
		int topLeftX2 = source.getWidth() / 2; // // top left edge y location of Side B of domino
		int topLeftY2 = 0; // top left edge y location of Side B of domino
		// domino is twice as wide as it is all, account for it here; turns domino
		// rectangle into 2 squares
		int rectangleSizeX = source.getWidth() / 2;
		int rectangleSizeY = source.getHeight();
		double rads = Math.toRadians(0); // degree angle domino is turned at

		if (yLocB < yLocA) { // domino rotated counter clockwise
			topLeftX = 0;
			topLeftY = source.getHeight();
			topLeftX2 = 0;
			topLeftY2 = 0;
			rectangleSizeX = source.getHeight();
			rectangleSizeY = source.getWidth() / 2;
			rads = Math.toRadians(-90);
		} else if (yLocA < yLocB) { // domino rotated clockwise
			topLeftX2 = 0;
			topLeftY2 = source.getHeight();
			topLeftX = 0;
			topLeftY = 0;
			rectangleSizeX = source.getHeight();
			rectangleSizeY = source.getWidth() / 2;
			rads = Math.toRadians(90);
		} else if (xLocB < xLocA) { // domino is upside down
			topLeftX = source.getWidth() / 2;
			topLeftY = 0;
			topLeftX2 = 0;
			topLeftY2 = 0;
			rads = Math.toRadians(180);
		}

		// this portion taken from:
		// https://blog.idrsolutions.com/image-rotation-in-java/
		final double sin = Math.abs(Math.sin(rads));
		final double cos = Math.abs(Math.cos(rads));
		final int w = (int) Math.floor(source.getWidth() * cos + source.getHeight() * sin);
		final int h = (int) Math.floor(source.getHeight() * cos + source.getWidth() * sin);

		final BufferedImage rotatedImage = new BufferedImage(w, h, source.getType());
		final AffineTransform at = new AffineTransform();

		at.translate(w / 2, h / 2);
		at.rotate(rads, 0, 0);
		at.translate(-source.getWidth() / 2, -source.getHeight() / 2);
		final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		rotateOp.filter(source, rotatedImage);
		// end taken portion

		// save domino halves into two images
		BufferedImage output1 = rotatedImage.getSubimage(topLeftX, topLeftY, rectangleSizeX, rectangleSizeY);

		BufferedImage output2 = rotatedImage.getSubimage(topLeftX2, topLeftY2, source.getWidth() / 2,
				source.getHeight());

		// save the domino images to the GUI game board display
		gameBoardImagesOverlay[xLocA][yLocA] = output1;
		gameBoardImagesOverlay[xLocB][yLocB] = output2;

		// save the domino's information to the game board
		gameboard.getGameBoardSpace(xLocA, yLocA).setSType(domino.getSideA().getSType());
		gameboard.getGameBoardSpace(xLocA, yLocA).setNumCrowns(domino.getSideA().getNumCrowns());
		gameboard.getGameBoardSpace(xLocB, yLocB).setSType(domino.getSideB().getSType());
		gameboard.getGameBoardSpace(xLocB, yLocB).setNumCrowns(domino.getSideB().getNumCrowns());
		repaint();
	}

	/**
	 * This method resets the player / domino playing layer of the image overlays
	 */
	public void resetPlayLayerImage() {
		// if (timer != null && timer.isRunning()) { timer.stop(); };
		gameBoardImagesPlayLayer = new Image[gameboard.getMaxWidth()][gameboard.getMaxHeight()];
		repaint();
	}
	
	public void resetPlayLayerImageAndStopTimer() {
		if (timer != null && timer.isRunning()) { timer.stop(); };
		gameBoardImagesPlayLayer = new Image[gameboard.getMaxWidth()][gameboard.getMaxHeight()];
		repaint();
	}

	/**
	 * This method saves an image to the domino playing layer of the game board.
	 * This will show the player how their domino matches up to dominos already
	 * played on their game board.
	 * 
	 * @param domino - domino to be played
	 */
	public void savePlayLayerImage(Domino domino, Color dyeColor, int borderThickness) {

		BufferedImage source = GUIStaticGameDominoList.dominoImagesFront.get(domino.getDominoNumber() - 1);

		/*
		 * if (dyeColor != null) { source =
		 * GUIImageController.drawBorder(GUIStaticGameDominoList.dominoImagesFront
		 * .get(domino.getDominoNumber() - 1), dyeColor, borderThickness); }
		 */

		int xLocA = domino.getSideA().getXLoc();
		int yLocA = domino.getSideA().getYLoc();
		int xLocB = domino.getSideB().getXLoc();
		int yLocB = domino.getSideB().getYLoc();

		int topLeftX = 0;
		int topLeftY = 0;
		int topLeftX2 = source.getWidth() / 2;
		int topLeftY2 = 0;
		int rectangleSizeX = source.getWidth() / 2;
		int rectangleSizeY = source.getHeight();
		double rads = Math.toRadians(0);

		if (yLocB < yLocA) {
			topLeftX = 0;
			topLeftY = source.getHeight();
			topLeftX2 = 0;
			topLeftY2 = 0;
			rectangleSizeX = source.getHeight();
			rectangleSizeY = source.getWidth() / 2;
			rads = Math.toRadians(-90);
		} else if (yLocA < yLocB) {
			topLeftX2 = 0;
			topLeftY2 = source.getHeight();
			topLeftX = 0;
			topLeftY = 0;
			rectangleSizeX = source.getHeight();
			rectangleSizeY = source.getWidth() / 2;
			rads = Math.toRadians(90);
		} else if (xLocB < xLocA) {
			topLeftX = source.getWidth() / 2;
			topLeftY = 0;
			topLeftX2 = 0;
			topLeftY2 = 0;
			rads = Math.toRadians(180);
		}

		// this portion taken from:
		// https://blog.idrsolutions.com/image-rotation-in-java/
		final double sin = Math.abs(Math.sin(rads));
		final double cos = Math.abs(Math.cos(rads));
		final int w = (int) Math.floor(source.getWidth() * cos + source.getHeight() * sin);
		final int h = (int) Math.floor(source.getHeight() * cos + source.getWidth() * sin);

		final BufferedImage rotatedImage = new BufferedImage(w, h, source.getType());
		final AffineTransform at = new AffineTransform();

		at.translate(w / 2, h / 2);
		at.rotate(rads, 0, 0);
		at.translate(-source.getWidth() / 2, -source.getHeight() / 2);
		final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		rotateOp.filter(source, rotatedImage);

		BufferedImage output1 = rotatedImage.getSubimage(topLeftX, topLeftY, rectangleSizeX, rectangleSizeY);

		BufferedImage output2 = rotatedImage.getSubimage(topLeftX2, topLeftY2, source.getWidth() / 2,
				source.getHeight());

		resetPlayLayerImage();

		gameBoardImagesPlayLayer[xLocA][yLocA] = output1;
		gameBoardImagesPlayLayer[xLocB][yLocB] = output2;		
		
		if (dyeColor != null) {
			
			double frame = 0;
			double maxFrame = 30;
			
			int r = dyeColor.getRed();
			int b = dyeColor.getBlue();
			int g = dyeColor.getGreen();
			
			ArrayList<Image> newOutput1s = new ArrayList<Image>();
			ArrayList<Image> newOutput2s = new ArrayList<Image>();
			
			for (double i = frame; i <= maxFrame; i++) {
				double alpha = Math.sin(i / maxFrame * Math.PI) * 100;
				BufferedImage newOutput1 = GUIImageController.dye(output1, new Color(r, g, b, (int) alpha));
				newOutput1s.add(newOutput1);
				BufferedImage newOutput2  = GUIImageController.dye(output2, new Color(r, g, b, (int) alpha));
				newOutput2s.add(newOutput2);
			}

			if (timer != null) {
				timer.stop();
			}

			timer = new Timer(1, new ActionListener() {
				double frame = 0;
				double maxFrame = 30;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (frame > maxFrame) {
						frame = 0;
					}

					//resetPlayLayerImage();
					gameBoardImagesPlayLayer[xLocA][yLocA] = newOutput1s.get((int)frame);
					gameBoardImagesPlayLayer[xLocB][yLocB] = newOutput2s.get((int)frame);
					frame++;
				}
			});
			timer.start();
		} else {
			timer.stop();
		}
	}

	/**
	 * This method generates dominos randomly placed on the players board. Can be used to test scoring
	 */
	public void generateRandomGameBoardImages() {
		ArrayList<Domino> dominoList = new ArrayList<Domino>(StaticGameDominoList.getDominosList());
		Random rand = new Random();
		int randStartX = rand.nextInt(5);
		int randStartY = rand.nextInt(5);
		int xLocA = 0;
		int yLocA = 0;
		int xLocB = 0;
		int yLocB = 0;

		for (int y = randStartY; y <= randStartY + 4; y++) {
			for (int x = randStartX; x <= randStartX + 4; x++) {
				if (gameBoardImagesOverlay[x][y] == null
						&& gameboard.getGameBoardSpace(x, y).getSType() != LandType.CASTLE) {

					xLocA = x;
					yLocA = y;

					int selection = rand.nextInt(dominoList.size());
					Domino domino = dominoList.get(selection);

					// 0 = counter clockwise, 1 = up right, 2 = clockwise, 3 = up-side-down
					int direction = rand.nextInt(4);

					if (direction == 0) {
						xLocB = xLocA;
						yLocB = yLocA - 1;
					}
					if (direction == 1) {
						xLocB = xLocA + 1;
						yLocB = yLocA;
					}
					if (direction == 2) {
						xLocB = xLocA;
						yLocB = yLocA + 1;
					}
					if (direction == 3) {
						xLocB = xLocA - 1;
						yLocB = yLocA;
					}

					// do nothing if out of bounds
					if (yLocB < randStartY || yLocB > randStartY + 4 || xLocB < randStartX || xLocB > randStartX + 4) {
						continue;
					}
					
					// do nothing on the castle space
					if ((xLocA == 4 && yLocB == 4) || (xLocB == 4 && yLocB == 4)) {
						continue;
					}

					// if the current boards spaces aren't null, put a domino on top of it
					if (gameBoardImagesOverlay[xLocA][yLocA] == null && gameBoardImagesOverlay[xLocB][yLocB] == null) {

						dominoList.remove(selection); // remove the domino from the list

						domino.getSideA().setXLoc(xLocA); // set the domino's location and place it on the game board
						domino.getSideA().setYLoc(yLocA);
						domino.getSideB().setXLoc(xLocB);
						domino.getSideB().setYLoc(yLocB);
						saveOverlayImage(domino);

						// set the board type of the spaces equal to the domino that was placed on it
						gameboard.getGameBoardSpace(xLocA, yLocA).setSType(domino.getSideA().getSType());
						gameboard.getGameBoardSpace(xLocB, yLocB).setSType(domino.getSideB().getSType());
					}
				}
			}
		}
	}

	/**
	 * This method sets the castle image on the player board to one of the player colors
	 * @param player - player choice that determines the castle color of the player board        
	 */
	public void setCastleImage(int player) {
		if (player == 0) {
			gameBoardImagesUnderlay[4][4] = castlePink;
		}
		if (player == 1) {
			gameBoardImagesUnderlay[4][4] = castleBlue;
		}
		if (player == 2) {
			gameBoardImagesUnderlay[4][4] = castleGreen;
		}
		if (player == 3) {
			gameBoardImagesUnderlay[4][4] = castleYellow;
		}
	}

	// getters
	public GameBoard getGameBoard() {
		return this.gameboard;
	}

	public Image[][] getImageOverlayLayer() {
		return this.gameBoardImagesOverlay;
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public Image[][] getGameBoardImagesGameOverLayer() {
		return this.gameBoardImagesGameOverLayer;
	}
	// setters
	public void setGameBoard(GameBoard gameboard) {
		this.gameboard = gameboard;
	}

	public void setBoardColor(Color boardColor) {
		this.boardColor = boardColor;
	}
	
	
}
