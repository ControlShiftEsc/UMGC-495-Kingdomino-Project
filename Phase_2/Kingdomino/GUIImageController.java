/**
 * Class: CMSC495 Date: 2 SEP 2023 Creator: Alan Anderson Editor/Revisor: William Feighner Team
 * Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet Mijit, Jenna Seipel, Joseph
 * Lewis File: GUIImageController.java Description: This class maintains the logic for loading and
 * displaying images.
 */

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class GUIImageController {

  private static Image backButtonImage; // back button image

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

  // mouse button images for GUIMenuBotton class
  private static Image buttonImage; // normal button image
  private static Image mouseOverButtonImage; // mouse over button image
  private static Image mousePressedButtonImage; // mouse pressed button image

  static {

    try {
      backButtonImage = ImageIO.read(
          GUIGameController.class.getResource("images/menu/backButton.png"));
      player1Meeple = ImageIO.read(
          GUIGameController.class.getResource("images/Meeples/pinkmeeple.png"));
      player2Meeple = ImageIO.read(
          GUIGameController.class.getResource("images/Meeples/bluemeeple.png"));
      player3Meeple = ImageIO.read(
          GUIGameController.class.getResource("images/Meeples/greenmeeple.png"));
      player4Meeple = ImageIO.read(
          GUIGameController.class.getResource("images/Meeples/yellowmeeple.png"));

      moveTileUp = ImageIO.read(GUIGameController.class.getResource("images/menu/MoveTileUp.png"));
      moveTileDown = ImageIO.read(
          GUIGameController.class.getResource("images/menu/MoveTileDown.png"));
      moveTileLeft = ImageIO.read(
          GUIGameController.class.getResource("images/menu/MoveTileLeft.png"));
      moveTileRight = ImageIO.read(
          GUIGameController.class.getResource("images/menu/MoveTileRight.png"));
      rotateTileCounterClockwise = ImageIO.read(
          GUIGameController.class.getResource("images/menu/RotateTileCounterClockwise.png"));
      rotateTileClockwise = ImageIO.read(
          GUIGameController.class.getResource("images/menu/RotateTileClockwise.png"));
      placeTile = ImageIO.read(GUIGameController.class.getResource("images/menu/PlaceTile3.png"));
      discardTile = ImageIO.read(
          GUIGameController.class.getResource("images/menu/DiscardTile3.png"));

      actionButtonImages = new ArrayList<Image>();
      actionButtonImages.add(moveTileUp); // 0
      actionButtonImages.add(moveTileDown); // 1
      actionButtonImages.add(moveTileLeft); // 2
      actionButtonImages.add(moveTileRight); // 3
      actionButtonImages.add(rotateTileClockwise); // 4
      actionButtonImages.add(rotateTileCounterClockwise); // 5
      actionButtonImages.add(placeTile); // 6
      actionButtonImages.add(discardTile);// 7

      // Book images used under Creative Commons License
      // Dougit Design. (2021, November 15). Flickr. https://www.flickr.com/photos/dougitdesign/5613967601
      leftGamePlayPanelImage = ImageIO.read(
          GUIGameController.class.getResource("images/menu/bookLeft.png"));
      rightGamePlayPanelImage = ImageIO.read(
          GUIGameController.class.getResource("images/menu/bookRight.png"));
      bookImage = ImageIO.read(GUIGameController.class.getResource("images/menu/book.png"));
      // Kingdomino box art image from Blue Orange Games
      // https://blueorangegames.eu/en/games/kingdomino/
      kingdominoBoxArtImage = ImageIO.read(
          GUIGameController.class.getResource("images/menu/boxart.png"));

      // Queendomino box art image from Blue Orange Games
      // https://blueorangegames.eu/en/games/queendomino/
      queendominoBoxArtImage = ImageIO.read(
          GUIGameController.class.getResource("images/menu/boxart2.png"));

      // Kingdomino: Age of Giants box art image from Blue Orange Games
      // https://boardgamegeek.com/image/4177705/kingdomino-age-giants
      giantdominoBoxArtImage = ImageIO.read(
          GUIGameController.class.getResource("images/menu/boxart3.png"));

      buttonImage = ImageIO.read(GUIGameController.class.getResource("/images/menu/button.png"));
      mouseOverButtonImage = ImageIO.read(
          GUIGameController.class.getResource("/images/menu/enteredButton.png"));
      mousePressedButtonImage = ImageIO.read(
          GUIGameController.class.getResource("/images/menu/pressedButton.png"));
      ;

    } catch (IOException e) {
      System.out.println("Error loading button images in GUIMenuButton.java");
      e.printStackTrace();
    }
  }

  // getters

  /**
   * This method returns images for the GUIMenuButton class
   * @param image - image to be returned:
   * 					1: default button image
   * 					2: mouse over button image
   * 					3: mouse pressed/selected button image
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
   * @return - returns back buton image
   */
  public static Image getBackButtonImage() {
    return backButtonImage;
  }

  /**
   * This method returns a background image based on the integer provided in the parameter
   * @param image - integer provided to choose the background image
   * 					1: left pane background
   * 					2: image for irght pane background
   * 					3: kingdomino box art
   * 					4: queendomino box art
   * 					5: giants expansion for kingdomino box art
   * 					6: book image
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
    return null;
  }

  /**
   * This method returns an image from the array of action button images
   * @param button - integer that determines the image returned:
   * 					1: move up image
   * 					2: move down image
   * 					3: move left image
   * 					4: move right image
   * 					5: rotate left image
   * 					6: rotate right image
   * 					7: place domino image
   * 					8: discard domino image
   * @return - the image returned
   */
  public static Image getActionButtonImage(int button) {
    return actionButtonImages.get(button);
  }

  /**
   * This method returns the image for a player's meeple
   * @param player - integer that determins the image returned:
   * 					1: player 1 meeple
   * 					2: player 2 meeple
   * 					3: player 3 meeple
   * 					4: player 4 meeple
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
}
