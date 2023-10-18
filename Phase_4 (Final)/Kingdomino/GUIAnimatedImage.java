/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIAnimatedImage.java
 * @Description: This class holds an animated image that can be drawn by a JPanel
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
	public boolean getInitialized() {
		return this.initialized;
	}
	
	public int getFrame() {
		return this.frame;
	}
	
	public int getFrameMax() {
		return this.frameMax;
	}
	
	public int getDegrees() {
		return this.degrees;
	}
	
	public boolean isRotationClockwise() {
		return rotationClockwise;
	}
	
	public int getStartX() {
		return this.startX;
	}
	
	public Timer getTimer() {
		return this.timer;
	}
	
	public BufferedImage getBImage() {
		return this.bImage;
	}

	public Image getSImage() {
		return this.sImage;
	}

	public Random getRandom() {
		return this.rand;
	}
	
	// setters
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
	}
	
	public void setFrameMax(int frameMax) {
		this.frameMax = frameMax;
	}
	
	public void setDegrees(int degrees) {
		this.degrees = degrees;
	}
	
	public void setRotationClockwise(boolean rotationClockwise) {
		this.rotationClockwise = rotationClockwise;
	}
	
	public void setStartX(int startX) {
		this.startX = startX;
	}
	
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	
	public void setBImage(BufferedImage bImage) {
		this.bImage = bImage;
	}
	
	public void setSImage(Image sImage) {
		this.sImage = sImage;
	}
	
	public void setRandom(Random rand) {
		this.rand = rand;
	}
}
