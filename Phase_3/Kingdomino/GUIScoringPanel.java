
/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Michael Wood & Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIPlayerNumberPanel.java
 * Description: Displays the score panel
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class GUIScoringPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public void initialize() {

		this.removeAll();

		ArrayList<GUIPlayer> scorePlayers = new ArrayList<GUIPlayer>(GUIGameController.getPlayerList());

		if (GUIGameController.getPlayerCount() == 2) {
			scorePlayers.remove(3);
			scorePlayers.remove(2);
		}

		// save player colors before the list gets shuffled

		for (GUIPlayer p : scorePlayers) {
			p.getGUIGameBoard().resetBoardImagesUnderlay();
			p.getGUIGameBoard().resetPlayLayerImage();
			GUIImageController.colorBoardImagesOverlay(p.getGUIGameBoard());
			p.getGUIGameBoard().repaint();
		}

		// sort scores by order of most points > largest tile group area > most crowns
		scorePlayers.sort(Comparator.comparing(x -> ((GUIPlayer) x).getGUIGameBoard().getGameBoard().getCurrentScore())
				.reversed().thenComparing(x -> ((GUIPlayer) x).getGUIGameBoard().getGameBoard().getMaxSpaceGroupSize())
				.reversed().thenComparing(x -> ((GUIPlayer) x).getGUIGameBoard().getGameBoard().getCurrentCrowns())
				.reversed());
		
		// set up grid bag layout
		this.setLayout(new GridLayout(0, 2));
		this.setBackground(Color.BLACK);

		for (int i = 0; i < scorePlayers.size(); i++) {
			int playerNumber = scorePlayers.get(i).getPlayerNumber()-1;
			
			GUIGameBoard gameboard = scorePlayers.get(i).getGUIGameBoard();	

			JPanel playerPanel = new JPanel();
			playerPanel.setLayout(new GridBagLayout());
			//playerPanel.setBackground(GUIGameController.getPlayerColors(i));
			GridBagConstraints c = new GridBagConstraints();
			
			JLabel label = new JLabel(scorePlayers.get(i).getPlayerName() + " ("
					+ scorePlayers.get(i).getGUIGameBoard().getGameBoard().getCurrentScore() + " points)");
			label.setFont(new Font("Arial", Font.PLAIN, GUIResolutionController.convertScaleWidth(50)));
			label.setForeground(Color.BLACK);
			label.setOpaque(true);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBackground(GUIGameController.getPlayerColors(playerNumber));
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			playerPanel.add(label, c);

			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.fill = GridBagConstraints.BOTH;
			JPanel statsPanel = new JPanel();
			statsPanel.setBackground(GUIGameController.getPlayerColors(playerNumber));
			statsPanel.setLayout(new GridLayout(0, 2));
			
			playerPanel.add(statsPanel, c);
			
			//statsPanel.align
			statsPanel.add(gameboard);
			
			gameboard.repaint();
			gameboard.setVisible(true);
			gameboard.resetPlayLayerImage();
				
			String column[] = { "#", "Type", "Crowns", "Spaces", "Pts" };

			ArrayList<ArrayList<GameBoard.Space>> scoredSpaces = gameboard.getGameBoard().getGameBoardScoreGroups();

			String data[][] = new String[scoredSpaces.size()-2][5];

			int group = 0;
			for (ArrayList<GameBoard.Space> array : scoredSpaces) {
				int crowns = 0;
				int spaces = 0;
				int totalScore = 0;
				String type = "";
				boolean skipped = false;
				for (GameBoard.Space space : array) {
					if (space.getSType() == LandType.EMPTY || space.getSType() == LandType.CASTLE) {
						skipped = true;
						break;
					}
					type = space.getSType().toString();
					crowns += space.getNumCrowns();
					spaces += 1;
					// System.out.println(String.valueOf(crowns));
				}

				if (!skipped) {
					totalScore += crowns * spaces;
					data[group] = new String[] { String.valueOf(group), type, String.valueOf(crowns),
							String.valueOf(spaces), String.valueOf(totalScore) };
					group++;
				}

				// for (String x : row) {
				// System.out.println(x);
				// }
			}
			
			JTable scoreTable = new JTable(data, column) {	
				private static final long serialVersionUID = 2005547622435152379L;

				public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
					Component c = super.prepareRenderer(renderer, row, column);

					c.setForeground(Color.BLACK);
					Color color = GUIImageController.getColoredScoreGroups().get(row);
					int r = color.getRed();
					int b = color.getBlue();
					int g = color.getGreen();
					int a = color.getAlpha();
					c.setBackground(new Color(r, g, b, a));
					
					// ensures text is white on darker colored rows for readability
					if (r + g + b <= 400) {
						c.setForeground(Color.WHITE);
					}
					return c;
				}
			};
			
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);

			for (int z = 0; z < scoreTable.getColumnCount(); z++) {
				scoreTable.getColumnModel().getColumn(z).setCellRenderer( centerRenderer );
			}

			scoreTable.getColumnModel().getColumn(0).setPreferredWidth(20);
			scoreTable.setBackground(GUIGameController.getPlayerColors(playerNumber));
			scoreTable.setFillsViewportHeight(false);
			
			JScrollPane scrollPanel = new JScrollPane(scoreTable);

			scrollPanel.getViewport().setBackground(Color.BLACK);
			scrollPanel.getViewport().setBorder(null);
			
			scrollPanel.setAlignmentX(JScrollPane.CENTER_ALIGNMENT);
			scrollPanel.setAlignmentY(JScrollPane.CENTER_ALIGNMENT);
	        
			scrollPanel.setBorder(BorderFactory.createEmptyBorder());
			
			JPanel scrollAndButtonPanel = new JPanel();
			scrollAndButtonPanel.setLayout(new GridBagLayout());
			scrollAndButtonPanel.setBackground(GUIGameController.getPlayerColors(playerNumber));
			GridBagConstraints x = new GridBagConstraints();
			statsPanel.add(scrollAndButtonPanel);
		
			x.gridy = 0;
			x.weightx = 1;
			x.weighty = 1;
			x.anchor = GridBagConstraints.NORTH;
			x.fill = GridBagConstraints.BOTH;
			scrollAndButtonPanel.add(scrollPanel, x);
			
			x.weightx = 0;
			x.weighty = 0;
			x.gridy += 1;
			x.fill = GridBagConstraints.NONE;
			JCheckBox toggleTable = new JCheckBox("Score Table");
			toggleTable.setBackground(GUIGameController.getPlayerColors(playerNumber));
			toggleTable.setSelected(true);
			
			ActionListener scoreTableAL = new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	if (e.getSource() == toggleTable) {
			    		if (toggleTable.isSelected()) {
			    			scrollPanel.setVisible(true);
			    		} else {
			    			scrollPanel.setVisible(false);
			    		}
			    	}
			    }
			};
			
			toggleTable.addActionListener(scoreTableAL);
			
			scrollAndButtonPanel.add(toggleTable, x);
			
			x.gridy += 1;
			x.weighty = 0;
			//x.anchor = GridBagConstraints.NORTH;
			x.fill = GridBagConstraints.NONE;
			JCheckBox toggleOverlay = new JCheckBox("Score Color Overlay");
			toggleOverlay.setBackground(GUIGameController.getPlayerColors(playerNumber));
			toggleOverlay.setSelected(true);

			ActionListener toggleOverlayAL = new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			       // JCheckBox checkbox = (JCheckBox)e.getSource();
			        // do something with the checkbox
			    	if (e.getSource() == toggleOverlay) {
			    		if (toggleOverlay.isSelected()) {
			    			GUIImageController.colorBoardImagesOverlay(gameboard);
			    		} else {
			    			GUIImageController.resetColorBoardImagesOverlay(gameboard);
			    		}
			    	}
			    }
			};
						
			toggleOverlay.addActionListener(toggleOverlayAL);
			
			scrollAndButtonPanel.add(toggleOverlay, x);
			
			this.add(playerPanel);
			
			if (scorePlayers.size() == 2) {
				//scrollPanel.setPreferredSize(new Dimension(50, 50));
				//scrollPanel.setMaximumSize(scrollPanel.getPreferredSize());

				
				//this.add(scrollPanel, c);
				
				JLabel blankLabel = new JLabel();
				blankLabel.setOpaque(true);
				blankLabel.setHorizontalAlignment(SwingConstants.CENTER);
				blankLabel.setBackground(Color.BLACK);
				//this.add(blankLabel, c);
				
			} else {
				//
			}
		}
	}
}
