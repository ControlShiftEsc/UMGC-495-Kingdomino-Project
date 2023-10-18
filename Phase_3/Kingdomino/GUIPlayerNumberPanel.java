/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIPlayerNumberPanel.java
 * @Description:  Panel for selecting the number of players for the game
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GUIPlayerNumberPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// buttons to select player number
	private ArrayList<GUIMenuButton> buttonList = new ArrayList<GUIMenuButton>();

	// mouse click adaptor for pressing start button
	private class MouseListenerClicked extends MouseAdapter {
		@Override

		// functionality for each of the buttons
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == buttonList.get(0)) {
				createNewPlayerList(2);
				buttonList.get(0).setButtonSelected(true);
				buttonList.get(3).setVisible(true);
				deselectOtherButtons(buttonList.get(0));
			} else if (e.getSource() == buttonList.get(1)) {
				createNewPlayerList(3);
				buttonList.get(1).setButtonSelected(true);
				buttonList.get(3).setVisible(true);
				deselectOtherButtons(buttonList.get(1));
			} else if (e.getSource() == buttonList.get(2)) {
				createNewPlayerList(4);
				buttonList.get(2).setButtonSelected(true);
				buttonList.get(3).setVisible(true);
				deselectOtherButtons(buttonList.get(2));
			} else {
				if (GUIGameController.getPlayerList().size() > 0) {
					for (JComponent j : GUIComponentController.getComponentList()) {
						// show presented by banner at top of screen
						if (j instanceof GUIPlayerNamePanel) {
							((GUIPlayerNamePanel) j).updateLayout();
						}
					}
					GUIGameController.setPlayerCount(GUIGameController.getPlayerList().size());
					GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(),
							"playerNameCard");
					GUIComponentController.setCurrentCardNumber(4);
				}
			}
		}
	}

	public GUIPlayerNumberPanel() {
		// set this panel's layout to gridbag
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridy = 0;
		c.weighty = 0.1;
		c.weightx = 1;
		c.ipady = GUIResolutionController.convertScaleHeight(50);
		c.gridheight = 1;
		c.insets = new Insets(GUIResolutionController.convertScaleHeight(50), 0, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;

		JLabel pageTitle = new JLabel("Select the Number of Players");
		pageTitle.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(2400),
				GUIResolutionController.convertScaleHeight(300))); // set the size
		pageTitle.setMinimumSize(pageTitle.getPreferredSize());
		pageTitle.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(300)));
		pageTitle.setBackground(new Color(255, 165, 0, 120));
		pageTitle.setForeground(Color.WHITE);
		pageTitle.setOpaque(true);
		pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(pageTitle, c);

		c.gridy++;
		c.ipady = 0;
		c.weighty = 0.1;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(GUIResolutionController.convertScaleHeight(120), 0, 0, 0);

		String[] buttonText = new String[] { "Two Players", "Three Players", "Four Players", "Continue" };
		MouseListenerClicked ml = new MouseListenerClicked();

		Font buttonFont = new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(150));
		for (int i = 0; i < 4; i++) {
			// if (c.gridy == 2) { c.weighty = 1; }

			if (c.gridy == 3) {
				// c.insets = new Insets(60, 0, 0, 0);
				c.weighty = 10;
			}

			buttonList.add(new GUIMenuButton(buttonText[i]));
			buttonList.get(i).setFont(buttonFont);
			buttonList.get(i).setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(1000),
					GUIResolutionController.convertScaleHeight(225))); // set the size
			buttonList.get(i).setMinimumSize(buttonList.get(i).getPreferredSize());
			buttonList.get(i).addMouseListener(ml); // add the mouse listener
			// then resize the image of the button based on the size of the button itself
			Image newButtonImage = ((ImageIcon) buttonList.get(i).getIcon()).getImage().getScaledInstance(
					GUIResolutionController.convertScaleWidth(1000), GUIResolutionController.convertScaleHeight(225),
					Image.SCALE_SMOOTH);
			buttonList.get(i).setIcon(new ImageIcon(newButtonImage));
			this.add(buttonList.get(i), c); // add button to panel
			c.gridy++;
			c.insets = new Insets(0, 0, 0, 0);
		}

		buttonList.get(3).setVisible(false);
	}

	/**
	 * This method deselects other buttons that have not been selected in a button group
	 * @param target
	 */
	public void deselectOtherButtons(GUIMenuButton target) {
		// deselect every button but the target button
		for (GUIMenuButton mb : buttonList) {
			if (mb != target) {
				mb.setButtonSelected(false);
				mb.resetButtonImage();
			}
		}
	}

	/**
	 * This method creates a list of players equal to the number specified
	 * @param players - number of players the list will hold
	 */
	public void createNewPlayerList(int players) {
		GUIGameController.setPlayerList(new ArrayList<GUIPlayer>()); // create new array list
		for (int i = 1; i <= players; i++) {
			try { // then add the desired number of players
				GUIGameController.getPlayerList().add(new GUIPlayer("Player " + i, i));
			} catch (InvalidPlayerNameException e) {
				System.out.println("Error: Invalid name used when creating a player in GUIPlayerNumberPanel.java.");
				e.printStackTrace();
			}
		}
	}
}
