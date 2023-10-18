/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIBasedKingdomino.java
 * @Description: This class contains the main method for the Kingdomino game
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUIBasedKingdomino {

	public static void intialize() {
		GUIAnimatedButtonSynchronizer.getTimer().start();
		// play sound before window opens
		GUISoundController.playTitleMusic();
		// set the game screen size to 90% of user's monitor size
		Dimension screenSize = GUIComponentController.getScreenSize();
		double screenWidth = screenSize.width * 0.9;
		double screenHeight = screenSize.height * 0.9;

		// set up main frame to hold the display panel
		// mainFrame (BorderLayout) <- layeredPane (LayeredPane) <- displayPanel <-
		// (CardLayout)
		GUIComponentController.getMainFrame().setLayout(new BorderLayout());

		GUIComponentController.getLayeredPane().setBounds(0, 0, (int) screenWidth, (int) screenHeight);
		GUIComponentController.getLayeredPane().setBackground(Color.BLACK);

		GUIDisplayPanel displayPanel = new GUIDisplayPanel();
		// BUG: for some reason, panel doesn't display correctly unless 0.99 and 0.95
		// coefficients are added to size
		displayPanel.setBounds(0, 0, (int) (screenWidth * 0.99), (int) (screenHeight * 0.95));
		GUIComponentController.getLayeredPane().add(displayPanel);
		GUIComponentController.getLayeredPane().setLayer(displayPanel, 1);

		GUIAnimatedDominoPanel dominoPanel = new GUIAnimatedDominoPanel();
		// BUG: for some reason, panel doesn't display correctly unless 0.99 and 0.95
		// coefficients are added to size
		dominoPanel.setBounds(0, 0, (int) (screenWidth * 0.99), (int) (screenHeight * 0.95));
		GUIComponentController.getLayeredPane().add(dominoPanel);
		GUIComponentController.getLayeredPane().setLayer(dominoPanel, 0);

		// IN-GAME OPTIONS MENU SETUP
		// set up a panel that displays when the options button is clicked
		GUIComponentController.setOptionPanel(new JPanel());
		GUIComponentController.getOptionPanel().setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		GUIComponentController.getOptionPanel().setLayout(new GridBagLayout());
		GUIComponentController.getOptionPanel().setVisible(false);
		GUIComponentController.getOptionPanel().setBackground(Color.ORANGE);
		GUIComponentController.getOptionPanel().setBounds((int) (screenWidth * 0.99) / 4,
				(int) (screenHeight * 0.95) / 8, (int) (screenWidth * 0.99) / 2, (int) (screenHeight * 0.95) * 3 / 4);

		GridBagConstraints c = new GridBagConstraints();

		// JLabel title at the top of the JPanel
		JLabel optionsTitle = new JLabel("Game Options");
		optionsTitle.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(300)));
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.NORTH;
		GUIComponentController.getOptionPanel().add(optionsTitle, c);

		Font subLabelFont = new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(100));
		JLabel masterSoundLabel = new JLabel("Master Sound Level");
		masterSoundLabel.setFont(subLabelFont);
		c.gridwidth = 1;
		c.gridy++;
		GUIComponentController.getOptionPanel().add(masterSoundLabel, c);

		// JSlider that will control the master volume
		JSlider masterSoundSlider = new JSlider();
		masterSoundSlider.setValue(100); // starting slider value
		masterSoundSlider.setMinimum(0); // min slider value
		masterSoundSlider.setMaximum(100); // max slider value
		masterSoundSlider.setMajorTickSpacing(1); // minimum tick movement
		masterSoundSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) { // change master volume when slider changes
				float masterVolume = (float) masterSoundSlider.getValue() / 100;
				GUISoundController.setMasterVolume(masterVolume);
			}
		});

		c.gridy++;
		GUIComponentController.getOptionPanel().add(masterSoundSlider, c);

		// JSlider that will control the soundFX volume
		JLabel soundFXLabel = new JLabel("Sound FX Level");
		soundFXLabel.setFont(subLabelFont);
		c.gridy++;
		GUIComponentController.getOptionPanel().add(soundFXLabel, c);

		JSlider soundFXSlider = new JSlider();
		soundFXSlider.setValue(100);
		soundFXSlider.setMinimum(0);
		soundFXSlider.setMaximum(100);
		soundFXSlider.setMajorTickSpacing(1);
		soundFXSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				float soundFXVolume = (float) soundFXSlider.getValue() / 100f;
				GUISoundController.setSoundFXVolume(soundFXVolume);
				System.out.println(soundFXSlider.getValue() + " " + soundFXVolume);
			}
		});

		c.gridy++;
		GUIComponentController.getOptionPanel().add(soundFXSlider, c);

		JLabel musicLabel = new JLabel("Music Level");
		musicLabel.setFont(subLabelFont);
		c.gridy++;
		GUIComponentController.getOptionPanel().add(musicLabel, c);

		// JSlider that will control the music volume
		JSlider musicSlider = new JSlider();
		musicSlider.setValue(100);
		musicSlider.setMinimum(0);
		musicSlider.setMaximum(100);
		musicSlider.setMajorTickSpacing(1);
		musicSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				float musicVolume = (float) musicSlider.getValue() / 100;
				GUISoundController.setMusicVolume(musicVolume);
			}
		});
		c.gridy++;
		GUIComponentController.getOptionPanel().add(musicSlider, c);

		// IN-GAME OPTION MENU BUTTONS
		// reset button setup
		c.gridy = 1;
		c.gridx++;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		GUIComponentController.getResetGameutton().addMouseListener(new OptionButtonClicked());
		GUIComponentController.getOptionPanel().add(GUIComponentController.getResetGameutton(), c);

		// end game button setup
		c.gridy += 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		GUIComponentController.getEndGameButton().addMouseListener(new OptionButtonClicked());
		GUIComponentController.getOptionPanel().add(GUIComponentController.getEndGameButton(), c);

		// exit game button setup
		c.gridy += 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		GUIComponentController.getExitGameButton().addMouseListener(new OptionButtonClicked());
		GUIComponentController.getOptionPanel().add(GUIComponentController.getExitGameButton(), c);

		// close in-game options menu button setup
		c.gridx = 0;
		c.gridy = 8;
		c.gridheight = 2;
		c.gridwidth = 2;
		c.weighty = 10;
		c.insets = new Insets((int) (GUIResolutionController.convertScaleHeight(200)), 0, 0, 0);
		c.fill = GridBagConstraints.NONE;
		GUIComponentController.getCloseOptionPanelButton().addMouseListener(new OptionButtonClicked());
		GUIComponentController.getOptionPanel().add(GUIComponentController.getCloseOptionPanelButton(), c);

		// add the in-game options menu to the layered pane
		GUIComponentController.getLayeredPane().add(GUIComponentController.getOptionPanel());
		GUIComponentController.getLayeredPane().setLayer(GUIComponentController.getOptionPanel(), 9);

		// set up the back button that displays at the bottom right corner of the menu
		// pages
		GUIComponentController.getBackButton().setBounds(
				(int) (screenWidth * 0.99) - GUIResolutionController.convertScaleWidth(400),
				(int) (screenHeight * 0.95) - GUIResolutionController.convertScaleHeight(400),
				GUIResolutionController.convertScaleWidth(450), GUIResolutionController.convertScaleHeight(450));
		GUIComponentController.getBackButton().addMouseListener(new BackButtonClicked());
		GUIComponentController.getBackButton().setVisible(false);
		GUIComponentController.getBackButton().setContentAreaFilled(false);
		GUIComponentController.getBackButton().setBorderPainted(false);
		GUIComponentController.getBackButton().setFocusPainted(false);
		GUIComponentController.getBackButton().setForeground(Color.BLACK);
		GUIComponentController.getBackButton()
				.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(75)));
		GUIComponentController.getBackButton().setHorizontalTextPosition(SwingConstants.CENTER);
		GUIComponentController.getBackButton().setVerticalTextPosition(SwingConstants.TOP);
		Image newButtonImage = ((ImageIcon) GUIComponentController.getBackButton().getIcon()).getImage()
				.getScaledInstance(GUIResolutionController.convertScaleWidth(250),
						GUIResolutionController.convertScaleHeight(250), Image.SCALE_SMOOTH);
		GUIComponentController.getBackButton().setIcon(new ImageIcon(newButtonImage));
		GUIComponentController.getLayeredPane().add(GUIComponentController.getBackButton());
		GUIComponentController.getLayeredPane().setLayer(GUIComponentController.getBackButton(), 2);

		// set up the option button that will display options during game play
		GUIComponentController.getOptionButton().setBounds(
				(int) (screenWidth * 0.99) - GUIResolutionController.convertScaleWidth(300),
				(int) (screenHeight * 0.95) - GUIResolutionController.convertScaleHeight(300),
				GUIResolutionController.convertScaleWidth(300), GUIResolutionController.convertScaleHeight(300));
		GUIComponentController.getOptionButton().addMouseListener(new OptionButtonClicked());
		GUIComponentController.getOptionButton().setVisible(false);
		GUIComponentController.getOptionButton().setContentAreaFilled(false);
		GUIComponentController.getOptionButton().setBorderPainted(false);
		GUIComponentController.getOptionButton().setFocusPainted(false);
		GUIComponentController.getOptionButton().setForeground(Color.BLACK);
		GUIComponentController.getOptionButton()
				.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(75)));
		GUIComponentController.getOptionButton().setHorizontalTextPosition(SwingConstants.CENTER);
		GUIComponentController.getOptionButton().setVerticalTextPosition(SwingConstants.TOP);
		Image newButtonImage2 = ((ImageIcon) GUIComponentController.getOptionButton().getIcon()).getImage()
				.getScaledInstance(GUIResolutionController.convertScaleWidth(250),
						GUIResolutionController.convertScaleHeight(250), Image.SCALE_SMOOTH);
		GUIComponentController.getOptionButton().setIcon(new ImageIcon(newButtonImage2));
		GUIComponentController.getLayeredPane().add(GUIComponentController.getOptionButton());
		GUIComponentController.getLayeredPane().setLayer(GUIComponentController.getOptionButton(), 3);

		// add layered pane to the main frame
		GUIComponentController.getMainFrame().add(GUIComponentController.getLayeredPane(), BorderLayout.CENTER);

		// continue setting up main frame
		GUIComponentController.getMainFrame().setTitle("Kingdomino by The Domino Dynasty");
		GUIComponentController.getMainFrame().setSize((int) screenWidth, (int) screenHeight);
		GUIComponentController.getMainFrame().setLocationRelativeTo(null);
		GUIComponentController.getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUIComponentController.getMainFrame().setResizable(false);
		GUIComponentController.getMainFrame().setVisible(true);
	}

	private static class OptionButtonClicked extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			// if the option button is clicked
			if (e.getSource() == GUIComponentController.getOptionButton()) {
				// make in-game options visible
				GUIComponentController.getOptionPanel().setVisible(true);
				// if the end game button is clicked
			} else if (e.getSource() == GUIComponentController.getEndGameButton()) {
				// end the game if the game is running
				if (GUIGameController.getGameIsRunning()) {
					GUIGamePlayPanel.endGame();
					// otherwise let the user know the game isn't running and thus can't be ended
				} else {
					new GUIErrorPopupPanel("INVALID ACTION: ", "Game has not started yet.",
							GUIComponentController.getEndGameButton().getLocationOnScreen(), 1400, 500);
				}
				// if the reset button is clicked
			} else if (e.getSource() == GUIComponentController.getResetGameutton()) {
				// go to the game play panel
				GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "gameplayCard");
				// then restart the game
				((GUIGamePlayPanel) GUIComponentController.getComponentList().get(6)).restartGame();
			// if the exit button is clicked, simply exit the game
			} else if (e.getSource() == GUIComponentController.getExitGameButton()) {
				System.exit(0);
			// otherwise if the close button is clicked, just close the in-game option menu
			} else if (e.getSource() == GUIComponentController.getCloseOptionPanelButton()) {
				GUIComponentController.getOptionPanel().setVisible(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// make the option button larger on mouse entry
			if (e.getSource() == GUIComponentController.getOptionButton()) {
				int width = GUIComponentController.getOptionButton().getWidth();
				int height = GUIComponentController.getOptionButton().getHeight();
				if (width <= 0 || height <= 0) {
					return;
				}
				Image newButtonImage = GUIImageController.getOptionButtonImage().getScaledInstance(
						GUIResolutionController.convertScaleWidth(300), GUIResolutionController.convertScaleHeight(300),
						Image.SCALE_SMOOTH);

				GUIComponentController.getOptionButton().setIcon(new ImageIcon(newButtonImage));
			}

		}

		public void mouseExited(MouseEvent e) {
			// make the option button smaller on mouse exit
			if (e.getSource() == GUIComponentController.getOptionButton()) {
				int width = GUIComponentController.getOptionButton().getWidth();
				int height = GUIComponentController.getOptionButton().getHeight();
				if (width <= 0 || height <= 0) {
					return;
				}
				Image newButtonImage = GUIImageController.getOptionButtonImage().getScaledInstance(
						GUIResolutionController.convertScaleWidth(250), GUIResolutionController.convertScaleHeight(250),
						Image.SCALE_SMOOTH);

				GUIComponentController.getOptionButton().setIcon(new ImageIcon(newButtonImage));
			}
		}
	}

	private static class BackButtonClicked extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			GUISoundController.playBackButtonSound(); // play back button sound
			// do nothing if at the first display card; can't go back any further
			if (GUIComponentController.getCurrentCardNumber() <= 0) {
				return;
			// otherwise, show the appropriate cards in accordance with the display heirachy
			} else if (GUIComponentController.getCurrentCardNumber() == 1) {
				GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "titleCard");
				GUIComponentController.setCurrentCardNumber(0);
				// back from Play, How to Play, and Credits panels
			} else if (GUIComponentController.getCurrentCardNumber() == 2
					|| GUIComponentController.getCurrentCardNumber() == 3
					|| GUIComponentController.getCurrentCardNumber() == 5) {
				GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "mainMenuCard");
				GUIComponentController.setCurrentCardNumber(1);
				// back from Player Name panel
			} else if (GUIComponentController.getCurrentCardNumber() == 4) {
				GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "playerCard");
				GUIComponentController.setCurrentCardNumber(3);
			}

			// make button invisible if on title page
			if (GUIComponentController.getCurrentCardNumber() == 0) {
				GUIComponentController.getLogoPanel().setVisible(false);
				GUIComponentController.getBackButton().setVisible(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// make the back button larger on mouse entry
			int width = GUIComponentController.getBackButton().getWidth();
			int height = GUIComponentController.getBackButton().getHeight();
			if (width <= 0 || height <= 0) {
				return;
			}
			Image newButtonImage = ((ImageIcon) GUIComponentController.getBackButton().getIcon()).getImage()
					.getScaledInstance(GUIResolutionController.convertScaleWidth(300),
							GUIResolutionController.convertScaleHeight(300), Image.SCALE_SMOOTH);

			GUIComponentController.getBackButton().setIcon(new ImageIcon(newButtonImage));
		}

		public void mouseExited(MouseEvent e) {
			// make the back button smaller on mouse exit
			int width = GUIComponentController.getBackButton().getWidth();
			int height = GUIComponentController.getBackButton().getHeight();
			if (width <= 0 || height <= 0) {
				return;
			}
			Image newButtonImage = ((ImageIcon) GUIComponentController.getBackButton().getIcon()).getImage()
					.getScaledInstance(GUIResolutionController.convertScaleWidth(250),
							GUIResolutionController.convertScaleHeight(250), Image.SCALE_SMOOTH);

			GUIComponentController.getBackButton().setIcon(new ImageIcon(newButtonImage));
		}

	}

	// main method
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUIBasedKingdomino.intialize();
			}
		});
	}
}
