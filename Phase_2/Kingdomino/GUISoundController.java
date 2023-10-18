import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIGameSoundController.java
 * Description: This class maintains the sound and music playing logic
 */

public class GUISoundController {
	private static Clip musicClip;
	private static AudioInputStream musicAudioInputStream;
	
	/**
	 * This method plays a sound file
	 * @param fileLocation - file location of sound
	 * @param loop - whether to loop
	 */
	public static void playSound(String fileLocation, boolean loop) {
	    try {
	    	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
	        		GUIGameController.class.getResource(fileLocation));
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();

	        //if (loop) { clip.loop(100); }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	/**
	 * This method plays a sound file as music; can turn on and off so no two music files play at once
	 * @param fileLocation - file location of sound
	 * @param loop - whether to loop
	 */
	public static void playMusic(String fileLocation, boolean loop) {
	    try {
	    	musicAudioInputStream = AudioSystem.getAudioInputStream(
	        		GUIGameController.class.getResource(fileLocation));
	    	musicClip = AudioSystem.getClip();
	    	musicClip.open(musicAudioInputStream);
	    	musicClip.start();

	        //if (loop) { clip.loop(100); }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	   
	
	public static void stopSound() {
		musicClip.stop();
	}
	
	/**
	 * This method just plays the title music for now
	 */
	public static void playTitleMusic() {
		playMusic("sounds/title_long.wav", false);
	}
	

	
	/**
	 * This method plays a button sound when the button is selected
	 */
	public static void playSelectSound() {
		playSound("sounds/new_select.wav", false);
	}
	
	/**
	 * This method plays a sound effect for the back button
	 */
	public static void playBackButtonSound() {
		playSound("sounds/new_deselect.wav", false);
	}
	
	/**
	 * This method plays a sound effect for selecting a tile
	 */
	public static void playSelectTileSound() {
		playSound("sounds/new_select_tile.wav", false);
	}
	
	/**
	 * This method plays a sound effect for placing a tile
	 */
	public static void playPlaceTileSound() {
		playSound("sounds/new_place_tile.wav", false);
	}
	
	/**
	 * This method plays a sound effect for rotating a tile
	 */
	public static void playRotateTileSound() {
		playSound("sounds/new_rotate_tile.wav", false);
	}
	
	/**
	 * This method plays the end game sound effect
	 */
	public static void playWinnerMusic() {
		playSound("sounds/new_winner.wav", false);
	}
	
	/**
	 * This method plays a sound effect for moving a tile
	 */
	public static void playMoveTileSound() {
		playSound("sounds/new_deselect.wav", false);
	}
	
	/**
	 * This method plays the in game music
	 */
	public static void playGamePlayMusic() {
		playMusic("sounds/new_background_music.wav", true);
	}
}
