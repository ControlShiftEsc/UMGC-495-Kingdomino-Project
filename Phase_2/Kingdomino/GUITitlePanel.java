/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Alan Anderson
 * Editor/Revisor: William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUIPlayer.java
 * Description: This class represents a player in the GUI version of the game
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUITitlePanel extends JPanel{
	
	// attributes
	private static final long serialVersionUID = 1L;

	Random rand = new Random(); // random number generator
	
	// list of animated dominos
	private ArrayList<GUIAnimatedTitleDomino> animatedDominos = new ArrayList<GUIAnimatedTitleDomino>();
	private int numAnimatedDominos = 10; // number of dominos to animate for title screen
	private static Image logo; // team logo image
	private GUIMenuButton startButton; // start button
	
	private String gameTitleString = "Kingdomino"; // Title text and label
	private JLabel titleLabel = new JLabel(gameTitleString, SwingConstants.CENTER);
	
	private String presentsString = "presents"; // presents text and label next to group logo
	private JLabel presentsLabel = new JLabel(presentsString, SwingConstants.CENTER);
	
	private JLabel logoLabel = new JLabel(); // label for team logo

	// mouse click adaptor for pressing start button
	private class MouseListenerClicked extends MouseAdapter {
    	@Override
    	
    	// after pressing start button, show top logo banner and back button, then go to next screen
        public void mousePressed(MouseEvent e) {
    		GUIComponentController.getBackButton().setVisible(true);
    		GUIComponentController.getLogoPanel().setVisible(true);
    		GUIComponentController.getCardLayout().show(GUIComponentController.getParentPanel(), "mainMenuCard");
    		GUIComponentController.setCurrentCardNumber(1);
        }
    }

	static {
		try { // load the team logo for display later
			logo = ImageIO.read(GUITitlePanel.class.getResource("/images/Logos/Logo7.png"));
		} catch (IOException e2) { 
			System.out.println("Error: Failed to load logo in title panel. GUITitlePanel.java");
			e2.printStackTrace();
		}
	}
	/**
	 * The initialize method sets up the components in this JPanel
	 */
	public GUITitlePanel() {		
		animatedDominos = new ArrayList<GUIAnimatedTitleDomino>();
		for (int i = 0; i < numAnimatedDominos; i++) {
			GUIAnimatedTitleDomino d = new GUIAnimatedTitleDomino(this, 100); // create dominos
			animatedDominos.add(d); // then add these to a list to animte them in the paint component
		}
		
		// this panel will be made into a grid to position everything in place
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// first, for the logo and "presents" text label, create a panel and add this to the
		// main panel that will contain everything; this is done so that we can manage each item
		// row by row in the main panel's grid layout
		
		// create the paenl and make it see-through
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());		
						
		logoLabel.setForeground(Color.WHITE);
		logoLabel.setAlignmentX(0.1f);
		logoLabel.setIcon(new ImageIcon(logo));

		logoLabel.setSize(new Dimension((int)(GUIResolutionController.convertScaleWidth(800)),
				GUIResolutionController.convertScaleHeight(100)));
		Image newLogoLabel = logo.getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 
								Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(newLogoLabel));
        
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weighty = 0;
		c.weightx = 0;
		c.insets = new Insets(GUIResolutionController.convertScaleHeight(165),
				GUIResolutionController.convertScaleWidth(450), 0, 0);
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(logoLabel, c);
		
		// then create the "presents" label
		presentsLabel = new JLabel("presents");
		presentsLabel.setForeground(Color.WHITE);
		presentsLabel.setFont(GUIFontController.getGameFont().deriveFont(
				Font.PLAIN, GUIResolutionController.convertScaleWidth(60)));
		presentsLabel.setVerticalAlignment(SwingConstants.BOTTOM);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.insets = new Insets(GUIResolutionController.convertScaleHeight(200), 0, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(presentsLabel, c);
				
		// after adding the above panel, we'll add the title panel
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weighty = 1;
		c.weightx = 1;
		c.ipady = GUIResolutionController.convertScaleWidth(200);
		c.insets = new Insets(0, GUIResolutionController.convertScaleWidth(500), 0, 
				GUIResolutionController.convertScaleWidth(500));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		titleLabel.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(450)));
		titleLabel.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(1000), 
				GUIResolutionController.convertScaleHeight(400)));
		titleLabel.setMinimumSize(titleLabel.getPreferredSize());
		titleLabel.setForeground(Color.WHITE);;
		titleLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		this.add(titleLabel, c);
		
		// finally, add the start button
		startButton = new GUIMenuButton("Click to Start");		
		startButton.addMouseListener(new MouseListenerClicked());
		startButton.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(150)));

		startButton.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(1000), 
				GUIResolutionController.convertScaleWidth(225)));
		startButton.setMinimumSize(startButton.getPreferredSize()); // stops this from flickering on resize
		Image newButtonImage = ((ImageIcon) startButton.getIcon()).getImage().getScaledInstance(
				GUIResolutionController.convertScaleWidth(1000), 
				GUIResolutionController.convertScaleWidth(225), Image.SCALE_SMOOTH);
		startButton.setIcon(new ImageIcon(newButtonImage));
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weighty = 0;
		c.weightx = 0;
		c.ipady = 0;
		c.insets = new Insets(0, 0, (int)(GUIResolutionController.convertScaleWidth(750)), 0);		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTH;
		this.add(startButton, c);
		
		this.addComponentListener(new ComponentAdapter() {
		    @Override
		    // fired when the screen resizes; resize all sub elements
		    public void componentResized(final ComponentEvent e) {
		        super.componentResized(e);
		        
		        
		    	// calculations for size based on 1152 x 648 resolution (90% 720p)
		    	int width = getWidth();
		    	int height = getHeight();
        		
		    	// update title label settings
        		titleLabel.setFont(new Font("Augusta", Font.PLAIN, (int)(width*0.130208333)));

        		
        		// presents label settings
        		presentsLabel.setFont(new Font("Augusta", Font.PLAIN, (int)(width*0.017361111)));
        		
        		// update logo settings
        		logoLabel.setSize(new Dimension((int)(width*0.23148), (int)(height*0.051440)));
        		Image newLogoLabel = logo.getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 
        								Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(newLogoLabel));
                
                // update start button settings
                startButton.setSize(new Dimension((int)(width*0.260416666), (int)(height*0.1157407)));
                startButton.setFont(new Font("Augusta", Font.PLAIN, (int)(width*0.04)));
                Image newButtonImage = ((ImageIcon) startButton.getIcon()).getImage().getScaledInstance(
               		startButton.getWidth(), startButton.getHeight(), Image.SCALE_SMOOTH);
                startButton.setIcon(new ImageIcon(newButtonImage));
                revalidate();
                repaint();
                
		    }
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // call super
		Graphics2D g2 = (Graphics2D) g.create(); // create Graphics2D object
			
		// we override the paintComponent here to draw the animated dominos
		// for each domino in the list of animated dominos
		for (GUIAnimatedTitleDomino d : animatedDominos) {

			if (!d.getInitialized()) {
				continue;
			}
			AffineTransform at2 = new AffineTransform();
			// move domino to location based on animation frame
			
			// at2.translate(d.getStartX(),d.getFrame()*10-d.getSImage().getHeight(this));
			double yTranslation = (((double)d.getFrame()/d.getFrameMax())*
					(this.getHeight()+d.getSImage().getHeight(this)) - d.getSImage().getHeight(this));
			at2.translate(d.getStartX(), yTranslation);

			// rotate domino based on current rotation degree
			at2.rotate(Math.toRadians(d.getDegrees()), d.getSImage().getWidth(this)/2,
					d.getSImage().getHeight(this)/2);
			// then draw domino image to panel
			g2.drawImage(d.getSImage(), at2, null);
		}
		g2.dispose();
	}
}
