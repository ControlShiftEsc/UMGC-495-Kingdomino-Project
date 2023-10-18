/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIPlayerNumberPanel.java
 * Description: Panel for selecting player number
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
     				GUIComponentController.getCardLayout().show(
     						GUIComponentController.getParentPanel(), "playerNameCard");
     				GUIComponentController.setCurrentCardNumber(4);
     			}
     		}
         }
     }
 	    
    public GUIPlayerNumberPanel(){
    	// set this panel's layout to gridbag
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridy = 0;
        c.weighty = 0.1;
        c.weightx = 1;
        c.ipady = GUIResolutionController.convertScaleHeight(50);
        c.gridheight = 1;
        c.insets = new Insets(GUIResolutionController.convertScaleHeight(50), 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.NORTH;
        
        JLabel pageTitle = new JLabel("Select the Number of Players");
        pageTitle.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(2400), 
        		GUIResolutionController.convertScaleHeight(225))); // set the size
        pageTitle.setMinimumSize(pageTitle.getPreferredSize());
        pageTitle.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(180)));
        pageTitle.setBackground(new Color(0,0,0, 120));
        pageTitle.setForeground(Color.WHITE);
        pageTitle.setOpaque(true);
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(pageTitle, c);
        
        c.gridy++;
        c.ipady = 0;
        c.weighty = 0.1;
        c.weightx = 0;
        c.insets = new Insets(GUIResolutionController.convertScaleHeight(120), 0, 0, 0);
        
        String[] buttonText = new String[] {"Two Players", "Three Players", "Four Players", "Continue"};
        MouseListenerClicked ml = new MouseListenerClicked();
        
        for (int i = 0; i < 4; i++) {
        	//if (c.gridy == 2) { c.weighty = 1; }

        	if (c.gridy == 3) { 
        		//c.insets = new Insets(60, 0, 0, 0); 
        		c.weighty = 10;
        	}
        	
        	buttonList.add(new GUIMenuButton(buttonText[i]));
        	buttonList.get(i).setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(1000), 
        			GUIResolutionController.convertScaleHeight(225))); // set the size
        	buttonList.get(i).setMinimumSize(buttonList.get(i).getPreferredSize());
            buttonList.get(i).addMouseListener(ml); // add the mouse listener
            // then resize the image of the button based on the size of the button itself
    		Image newButtonImage = ((ImageIcon) buttonList.get(i).getIcon()).getImage().getScaledInstance(
    				GUIResolutionController.convertScaleWidth(1000), 
        			GUIResolutionController.convertScaleHeight(225), Image.SCALE_SMOOTH);
    		buttonList.get(i).setIcon(new ImageIcon(newButtonImage));
            this.add(buttonList.get(i), c); // add button to panel
            c.gridy++;
            c.insets = new Insets(0, 0, 0, 0);
        }
        
        buttonList.get(3).setVisible(false);
        
        this.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(final ComponentEvent e) {
		    	// calculations for size based on 1728 x 972 resolution (90% 1980 x 1080)
		        super.componentResized(e);
		    	int width = getWidth();
		    	int height = getHeight();
                
		    	pageTitle.setPreferredSize(new Dimension((int)(0.694444*width), (int)(0.07716049*height)));
		        pageTitle.setMinimumSize(new Dimension((int)(0.3472222*width), (int)(0.07716049*height)));
		        pageTitle.setFont(new Font("Augusta", Font.PLAIN, (int)(width*0.05208333)));
		        
		        for (JButton b : buttonList) {
		    		b.setSize(new Dimension((int)(width*0.260416666), (int)(height*0.1157407)));
	                b.setFont(new Font("Augusta", Font.PLAIN, (int)(width*0.04)));
	                Image newButtonImage = ((ImageIcon) b.getIcon()).getImage().getScaledInstance(
	                		b.getWidth(), b.getHeight(), Image.SCALE_SMOOTH);
	                b.setIcon(new ImageIcon(newButtonImage));
		    	}
                
		    }
		});
    }
    
    public void deselectOtherButtons(GUIMenuButton target) {
    	for (GUIMenuButton mb : buttonList) {
    		if (mb != target) {
    			mb.setButtonSelected(false);
    			mb.updateButtonImage();
    		}
    	}
    }
    
    public void createNewPlayerList(int players) {
    	GUIGameController.setPlayerList(new ArrayList<GUIPlayer>());
    	for (int i = 1; i <= players; i++) {
    		try {
				GUIGameController.getPlayerList().add(new GUIPlayer("Player " + i));
			} catch (InvalidPlayerNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
 // we override the paint method here to draw the background image for this panel
    @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    	Image newImage = GUIImageController.getBackGroundImage(4).getScaledInstance(
	    			this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
	        g.drawImage(newImage, 0, 0, null);
	}
}
