/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIGameSoundController.java
 * @Description: This class plays sounds and controls the volume for those sounds
 */

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.SwingUtilities;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

// midi sound is currently bugged;
// volume control does not work for midi in current state

public class GUISoundController {
	
	static Sequencer sequencer;
	static Receiver receiver;
	static float masterVolume = 1f;
	static float soundFXVolume = 1f;
	static float musicVolume = 1f;
	
	static {
		try {
			sequencer = MidiSystem.getSequencer();
			receiver = MidiSystem.getReceiver();
		} catch (MidiUnavailableException e) {
			System.out.println("Error: Midi in unavailable in static in GUISoundController.java.");
			e.printStackTrace();
		}
	}

	public static void playSound(String fileLocation, boolean loop) {
		try {
			
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(GUISoundController.class.getResource(fileLocation));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
			float volumeChange;
			
			if (masterVolume < soundFXVolume) {
				volumeChange = masterVolume;
			} else {
				volumeChange = soundFXVolume;
			}

			float maximum = -10f;
			float minimum = -50f;
			float range = maximum - minimum;
			//float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volumeChange) + minimum;

			gainControl.setValue(gain);
			
			clip.start();

			// if (loop) { clip.loop(100); }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void playMusic(String musicLocation) {
		// playSound("sounds/title_long.wav", false);
		// Obtains the default Sequencer connected to a default device.
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	try {
					sequencer.open();

					InputStream is = new BufferedInputStream(
							GUISoundController.class.getResourceAsStream(musicLocation));
					
					sequencer.setSequence(is);
					sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

					// midi volume control code taken from:
					// http://www.java2s.com/Code/Java/Development-Class/SettingtheVolumeofPlayingMidiAudio.htm
					
			    	float volumeChange;
			    	
					if (masterVolume < musicVolume) {
						volumeChange = masterVolume;
					} else {
						volumeChange = musicVolume;
					}	
					
					sequencer.start();

					Sequence sequence = sequencer.getSequence();
					Track[] tracks = sequence.getTracks();			
					
					for (Track t : tracks) {
						for (int i = 0; i < 16; i++) {
							t.add(new MidiEvent(
								    new ShortMessage(ShortMessage.CONTROL_CHANGE, i, 7, (int)(30*volumeChange)),
								    0));
						}
					}

				} catch (MidiUnavailableException e) {
					System.out.println("Error: Midi is unavailable in GUISoundController.java.");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Error: Unable to read midi file in GUISoundController.java.");
					e.printStackTrace();
				} catch (InvalidMidiDataException e) {
					System.out.println("Error: Unable to create ShortMessage in GUISoundController.java.");
					e.printStackTrace();
				}
		    }
		});
	}
	/**
	 * This method just plays the title music for now
	 */
	public static void playTitleMusic() {
		playMusic("sounds/title.mid");
	}
	
	public static void playGamePlayMusic() {
		playMusic("sounds/new_gameplay.mid");

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

	public static double getMasterVolume() {
		return masterVolume;
	}
	
	public static double getSoundFXVolume() {
		return soundFXVolume;
	}
	
	public static void setSoundFXVolume(float newSoundFXVolume) {
		soundFXVolume = newSoundFXVolume;
	}
	
	public static void setMasterVolume(float newMasterVolume) {
		masterVolume = newMasterVolume;
		setMusicVolume(masterVolume);
	}
	
	public static void setMusicVolume(float newMusicVolume) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    
		    	musicVolume = newMusicVolume;
		    	
		    	float volumeChange;
		    	
				if (masterVolume < musicVolume) {
					volumeChange = masterVolume;
				} else {
					volumeChange = musicVolume;
				}				
				
				Sequence sequence = sequencer.getSequence();
				Track[] tracks = sequence.getTracks();
				
				/*
				System.out.print(tracks.length + " XX");
				for (Track t : tracks) {
					System.out.println(t.size());
					for (int e = 0; e < t.size(); e++) {
						MidiMessage message = t.get(e).getMessage();
						if (message instanceof ShortMessage && message.getMessage().length >= 2 
								&& message.getMessage()[1] == 7 && t.get(e).getTick() == 1) {
							System.out.println(e);
							t.remove(t.get(e));
						}
					}
				}
				*/
						
				for (Track t : tracks) {
					for (int i = 0; i < 16; i++) {
						try {
							//	    new ShortMessage(ShortMessage.CONTROL_CHANGE, i, 7, (int)(30*volumeChange)),
							//	    sequencer.getTickPosition()));
							
							t.add(new MidiEvent(
								    new ShortMessage(ShortMessage.CONTROL_CHANGE, i, 7, (int)(30*volumeChange)),
								    0));
		
							
						} catch (InvalidMidiDataException e) {
							System.out.println("Error: Unable to create ShortMessage in GUISoundController.java.");
							e.printStackTrace();
						}  
					}
				}
				
		    }
		});
	}
}
