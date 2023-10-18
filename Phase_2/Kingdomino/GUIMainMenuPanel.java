/**
 * Class: CMSC495 
 * Date: 2 SEP 2023 
 * Creator: Michael Wood Jr. & Alan Anderson 
 * Editor/Revisor: William Feighner 
 * Team Members: Alan Anderson, William Feighner, Michael Wood Jr., Ibadet Mijit, Jenna
 * Seipel, Joseph Lewis 
 * File: GUIMainMenuPanel.java Description: This class is the main menu panel
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUIMainMenuPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private ArrayList<GUIMenuButton> buttonList = new ArrayList<GUIMenuButton>();

  public GUIMainMenuPanel() {
    // mouse listener for buttons
    TitleMenuListenerClicked ml = new TitleMenuListenerClicked();

    // set this page up as a GridBagLayout
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

    // this sets up the transparent title at the top of the page
    JLabel pageTitle = new JLabel("Option Menu");
    pageTitle.setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(900),
        GUIResolutionController.convertScaleHeight(225))); // set the size
    pageTitle.setMinimumSize(pageTitle.getPreferredSize());
    pageTitle.setFont(
        new Font("Augusta", Font.PLAIN, (GUIResolutionController.convertScaleWidth(180))));
    pageTitle.setBackground(new Color(0, 0, 0, 120));
    pageTitle.setForeground(Color.WHITE);
    pageTitle.setOpaque(true);
    //pageTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
    pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(pageTitle, c);

    // this sets up each of the buttons on the page
    c.gridy++;
    c.ipady = 0;
    c.weighty = 0.1;
    c.weightx = 0;
    c.insets = new Insets(GUIResolutionController.convertScaleHeight(120), 0, 0, 0);
    // go through and set up each of the menu buttons
    String[] buttonText = new String[]{"Play Game", "How to Play", "Exit Game"};

    for (int i = 0; i < 3; i++) {
      if (c.gridy == 3) {
        c.insets = new Insets(GUIResolutionController.convertScaleHeight(60), 0, 0, 0);
        c.weighty = 1;
      }

      buttonList.add(new GUIMenuButton(buttonText[i]));
      buttonList.get(i)
          .setPreferredSize(new Dimension(GUIResolutionController.convertScaleWidth(1000),
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

    // add a listener to resize components if window changes size
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(final ComponentEvent e) {
        // calculations for size based on 1728 x 972 resolution (90% 1980 x 1080)
        super.componentResized(e);
        int width = getWidth();
        int height = getHeight();

        pageTitle.setPreferredSize(
            new Dimension((int) (0.3472222 * width), (int) (0.07716049 * height)));
        pageTitle.setMinimumSize(
            new Dimension((int) (0.3472222 * width), (int) (0.07716049 * height)));
        pageTitle.setFont(new Font("Augusta", Font.PLAIN, (int) (width * 0.05208333)));

        for (JButton b : buttonList) {
          b.setSize(new Dimension((int) (width * 0.260416666), (int) (height * 0.1157407)));
          b.setFont(new Font("Augusta", Font.PLAIN, (int) (width * 0.04)));
          Image newButtonImage = ((ImageIcon) b.getIcon()).getImage().getScaledInstance(
              b.getWidth(), b.getHeight(), Image.SCALE_SMOOTH);
          b.setIcon(new ImageIcon(newButtonImage));
        }

      }
    });
  }

  // we override the paint method here to draw the background image for this panel
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Image newImage = GUIImageController.getBackGroundImage(3).getScaledInstance(
        this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
    g.drawImage(newImage, 0, 0, null);
  }

  private class TitleMenuListenerClicked extends MouseAdapter {

    @Override

    // functionality for each of the buttons
    public void mousePressed(MouseEvent e) {
      // display the player number selection panel if the Play button is pressed
      if (e.getSource() == buttonList.get(0)) {
        GUIComponentController.getCardLayout()
            .show(GUIComponentController.getParentPanel(), "playerCard");
        GUIComponentController.setCurrentCardNumber(3);
      } else if (e.getSource() == buttonList.get(1)) {
        // display the how to play panel if the how to play button is pressed
        System.out.println("Under Construction");
        GUIComponentController.setCurrentCardNumber(2);
        // exit the game if exit button is selected
      } else if (e.getSource() == buttonList.get(2)) {
        System.exit(0);
      }
    }
  }
}
