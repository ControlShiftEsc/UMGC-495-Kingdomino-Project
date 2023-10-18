
/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIAnimatedTitleLogo
 * Description: This class holds an animated image that can be drawn by a JPanel.
 */

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.Timer;

public class GUIAnimatedImage {
	// attributes
	boolean initialized;// flag whether domino animation has been initialized
	private int frame; // frame animation counter
	private int frameMax; // max frames in animation
	private int degrees; // degrees the animation is rotated
	private boolean rotationClockwise; // flag on whether domino rotates clockwise or counter
	private int startX; // x location offset of animation
	private Timer timer; // animation timer
	private BufferedImage bImage; // animation image
	private Image sImage; // scaled animation image
	private Random rand; // random number generator
	
	// constructor
	public GUIAnimatedImage() {
		rand = new Random();
	}
	
	// getters
	
	/**
	 * Returns whether the animation has been initialized or not
	 * @return - initialized flag
	 */
	public boolean getInitialized() {
		return this.initialized;
	}
	
	/**
	 * Returns the current frame of the animation
	 * @return - current frame
	 */
	public int getFrame() {
		return this.frame;
	}
	
	/**
	 * Returns the maximum number of frames in an animation
	 * @return - max number of frames
	 */
	public int getFrameMax() {
		return this.frameMax;
	}
	
	/**
	 * Returns the current degree rotation of an animation
	 * @return - current rotation in degrees
	 */
	public int getDegrees() {
		return this.degrees;
	}
	
	/**
	 *Returns whether the animation is rotating clockwise or counter-clockwise
	 * @return - clockwise or counterclockwise rotation flag
	 */
	public boolean isRotationClockwise() {
		return rotationClockwise;
	}
	
	/**
	 * Returns the starting x location of the animaation
	 * @return - starting x location
	 */
	public int getStartX() {
		return this.startX;
	}
	
	/**
	 * Returns the timer for the animation
	 * @return - animation's timer
	 */
	public Timer getTimer() {
		return this.timer;
	}
	
	/**
	 * Returns the image of the animation
	 * @return - animation's image
	 */
	public BufferedImage getBImage() {
		return this.bImage;
	}
	
	/**
	 * Returns the scaled image of the animation
	 * @return - scaled image
	 */
	public Image getSImage() {
		return this.sImage;
	}
	
	/**
	 * Returns the animation's random number generator
	 * @return - randon number generator of animation
	 */
	public Random getRandom() {
		return this.rand;
	}
	
	// setters
	/**
	 *  Sets the animation's initialized flag
	 * @param initialized - whether animation has been initialized
	 */
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	/**
	 * Sets the frame of the animation
	 * @param frame - animation's current frame
	 */
	public void setFrame(int frame) {
		this.frame = frame;
	}
	
	/**
	 * Sets the maximum number of frames in the animation
	 * @param frameMax - animation's maximum number of frames
	 */
	public void setFrameMax(int frameMax) {
		this.frameMax = frameMax;
	}
	
	/**
	 * Sets animation's current rotation in degrees
	 * @param degrees - animation's rotation in degrees
	 */
	public void setDegrees(int degrees) {
		this.degrees = degrees;
	}
	
	/**
	 * Sets whether the animation rotates clockwise or counter-clockwise
	 * @param rotationClockwise - true (clockwise) or false (counterclockwise)
	 */
	public void setRotationClockwise(boolean rotationClockwise) {
		this.rotationClockwise = rotationClockwise;
	}
	
	/**
	 * Sets the animation's starting x location
	 * @param startX - starting x location
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}
	
	/**
	 * Sets the animation's timer
	 * @param timer - animation timer
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	/**
	 * Sets the animation's image
	 * @param bImage - animation image
	 */
	public void setBImage(BufferedImage bImage) {
		this.bImage = bImage;
	}
	
	/**
	 * Sets the animation's scaled image
	 * @param sImage - scaled image
	 */
	public void setSImage(Image sImage) {
		this.sImage = sImage;
	}
	
	/**
	 * Sets the animation's random number generator
	 * @param rand - random number generator
	 */
	public void setRandom(Random rand) {
		this.rand = rand;
	}
}
