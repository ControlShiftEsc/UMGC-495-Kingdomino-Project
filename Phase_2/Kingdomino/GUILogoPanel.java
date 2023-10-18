/**
 * Class: CMSC495
 * Date: 2 SEP 2023
 * Creator: Michael Wood Jr.
 * Editor/Revisor: Alan Anderson & William Feighner
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet
 * Mijit, Jenna Seipel, Joseph Lewis
 * File: GUILogoPanel.java
 * Description: This class houses the label that will display above the gameplay
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUILogoPanel extends JPanel {
	private static final long serialVersionUID = 1L;

    public GUILogoPanel() {
    	// adds 3 labels that will represented the logo banner displayed above each panel
    	JLabel logoLabel= new JLabel("Kingdomino");
    	logoLabel.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(100)));
    	logoLabel.setForeground(Color.WHITE);
    	logoLabel.setHorizontalAlignment(JLabel.CENTER);
    	logoLabel.setVerticalAlignment(JLabel.CENTER);
    	
    	JLabel logoLabel2 = new JLabel(" presented by");
    	logoLabel2.setFont(new Font("Augusta", Font.PLAIN, GUIResolutionController.convertScaleWidth(50)));
    	logoLabel2.setForeground(Color.WHITE);
    	logoLabel2.setHorizontalAlignment(JLabel.CENTER);
    	logoLabel2.setVerticalAlignment(JLabel.CENTER);
    	
    	JLabel logoLabel3 = new JLabel();
    	logoLabel3.setForeground(Color.WHITE);
    	logoLabel3.setHorizontalAlignment(JLabel.CENTER);
    	logoLabel3.setVerticalAlignment(JLabel.CENTER);

    	BufferedImage image;
		try {
			image = ImageIO.read(GUILogoPanel.class.getResource("images/Logos/Logo7.png"));
			Image newImage = image.getScaledInstance(GUIResolutionController.convertScaleWidth(600), 
					GUIResolutionController.convertScaleHeight(100), Image.SCALE_SMOOTH);
			logoLabel3.setIcon(new ImageIcon(newImage));
		} catch (IOException e) {
			System.out.println("Logo image in logo panel failed to load. GUILogoPanel.java");
			e.printStackTrace();
		}
    	
    	setBackground(Color.BLACK);
    	setLayout(new FlowLayout());
    	add(logoLabel);
    	add(logoLabel2);
    	add(logoLabel3);
    }
}
