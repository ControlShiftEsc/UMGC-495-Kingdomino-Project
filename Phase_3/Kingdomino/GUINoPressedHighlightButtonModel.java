/**
 * @Class: CMSC495
 * @Date: 23 SEP 2023
 * @Creator: Alan Anderson
 * @Editor: William Feighner
 * @Members: Alan Anderson, Michael Wood Jr., William Feighner, Ibadet Mijit, Jenna Seipel, Joseph Lewis
 * @File: GUIMenuButton.java
 * @Description: This button model prevents a JButton from being highlighted on button press
 */

import javax.swing.DefaultButtonModel;

// solution for preventing button highlight on mouse press from:
// https://stackoverflow.com/questions/22543633/stopping-jbutton-highlighting-on-press

public class GUINoPressedHighlightButtonModel extends DefaultButtonModel {

	private static final long serialVersionUID = 99L;

	@Override
    public boolean isPressed() {
        return false;
    }

    @Override
    public boolean isRollover() {
        return false;
    }
    
    @Override
    public void setRollover(boolean b) {
        //NOOP
    }

}
