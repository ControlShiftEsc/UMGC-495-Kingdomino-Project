/**
 * @Class: CMSC495
 * @Date: 2 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIErrorPopup.java
 * @Description: This class provides for error popups in the GUI
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GUIErrorPopupPanel extends JLabel {
	
	// attributes
	private static final long serialVersionUID = -6715478315919574601L;
	
	// html code helps to format the error string in the desired way
	private String preHTML = "<html><body><p style=\"padding: 15px 15px; text-align: center;\">";
	private String postHTML =  "<br><br>(Click to Remove)</p></body></html>";
	private String whiteSpace =  "<br><br>";
	
	// constructor
	public GUIErrorPopupPanel(String errorType, String errorMessage, Point location, int width, int height) {
		//TODO slight graphics glitch when error is initially shown; text appears to "jump" up slightly
		super(errorMessage); // call super
		// convert width and height based on user screen resolution
		int newWidth = GUIResolutionController.convertScaleWidth(width);
		int newHeight = GUIResolutionController.convertScaleHeight(height);
		// create a line border with a margin
		Border border = new LineBorder(Color.WHITE, 5);
        Border margin = new 
        		EmptyBorder(GUIResolutionController.convertScaleWidth(40),
        		GUIResolutionController.convertScaleWidth(40),
        		GUIResolutionController.convertScaleWidth(40),
        		GUIResolutionController.convertScaleWidth(40));
        setBorder(new CompoundBorder(border, margin));
		// configure remaining parameters
        setText(preHTML + errorType + whiteSpace + errorMessage + postHTML);
        setHorizontalAlignment(SwingConstants.CENTER);
		setOpaque(true);
		setBounds((int)location.getX()-newWidth, (int)location.getY()-newHeight, newWidth, newHeight);
		setForeground(Color.WHITE);
		setBackground(Color.RED);
        addMouseListener(new ErrorMouseListener());
        // add to the layered pane and set it to the top level
		GUIComponentController.getLayeredPane().add(this);
		GUIComponentController.getLayeredPane().setLayer(this, 10);
	}
	
	/**
	 * this method removes all error messages
	 */
	private void removePreviousErrorMessages() {
		for (Component c : (GUIComponentController.getLayeredPane().getComponents())) {
			if (c instanceof GUIErrorPopupPanel) {
				GUIComponentController.getLayeredPane().remove(c);
			}
		}
		GUIComponentController.getLayeredPane().revalidate();
		GUIComponentController.getLayeredPane().repaint();
	}
	
	private class ErrorMouseListener extends MouseAdapter {
		@Override
	    public void mouseClicked(MouseEvent e) {
			removePreviousErrorMessages();
		}
	}
}
