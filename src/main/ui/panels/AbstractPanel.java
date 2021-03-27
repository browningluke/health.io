package ui.panels;

import ui.HealthIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Represents a generic panel containing default colors, fonts and helper methods.
public abstract class AbstractPanel extends JPanel {

    public static final Color HIGHLIGHT_COLOR = new Color(195, 143, 255);
    public static final Color MAIN_CARD_COLOR = new Color(248, 248, 249);
    public static final Color TOP_BAR_COLOR = new Color(240,240, 240);

    public static final Font BOLD_FONT = new Font("Tahoma", Font.BOLD, 11);
    public static final Font BIG_FONT = new Font("Tahoma", Font.BOLD, 18);
    public static final Font DEFAULT_FONT = new Font("Tahoma", Font.PLAIN, 11);

    protected HealthIO healthIO;    // Reference back to JFrame subclass, enables drawing of other panels

    // MODIFIES: this
    // EFFECTS: defines an abstract constructor which initializes the JPanel,
    //  background color, layout and JFrame reference.
    public AbstractPanel(Color background, LayoutManager lm, HealthIO healthIO) {
        super();
        this.healthIO = healthIO;
        setBackground(background);
        setLayout(lm);
    }

    // EFFECTS: Helper method. Sets the default options for all buttons;
    //  allows setting of text & action listener. Returns the created button.
    public JButton createButton(String text, ActionListener al) {
        JButton button = new JButton();
        button.setBackground(HIGHLIGHT_COLOR);
        button.setFont(BOLD_FONT);
        button.setText(text);
        button.addActionListener(al);

        return button;
    }

    // EFFECTS: Helper method. Abstracts the JLabel creation code to a single function.
    //  Allows setting of text, font & color. Returns the created label.
    public JLabel createLabel(String text, Font ft, Color foregroundColor) {
        JLabel jlabel = new JLabel();
        jlabel.setFont(ft);
        jlabel.setForeground(foregroundColor);
        jlabel.setText(text);

        return jlabel;
    }

    // REQUIRES: alignment is one of SwingConstants
    // EFFECTS: Overloaded helper method. Creates a label and sets its horizontal alignment.
    //  Returns the created label.
    public JLabel createLabel(String text, Font ft, Color foregroundColor, int alignment) {
        JLabel jlabel = createLabel(text, ft, foregroundColor);
        jlabel.setHorizontalAlignment(alignment);

        return jlabel;
    }

    // EFFECTS: Helper method. Abstracts the JSlider creation code to a single function.
    //  Allows setting of current value, max value, min value, major & minor tick spacing.
    //  Returns the created slider.
    public JSlider createSlider(int value, int max, int min, int majorTickSpacing, int minorTickSpacing) {
        JSlider slider = new JSlider();

        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setMinorTickSpacing(minorTickSpacing);
        slider.setMaximum(max);
        slider.setMinimum(min);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setValue(value);

        return slider;
    }

    /*
        Create GridBagConstraint with different parameters
     */

    // EFFECTS: Helper method. Abstracts the GridBagConstraints creation code to a single function.
    //  Allows setting of x & y position. Used for specifying location on a GridBagLayout.
    //  Returns the created GridBagConstraint.
    public GridBagConstraints createGridBagConstraints(int x, int y) {
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;

        return gridBagConstraints;
    }

    // EFFECTS: Overloaded helper method. Creates a GridBagConstraint &
    //  allows specification of grid width. Returns the created GridBagConstraint.
    public GridBagConstraints createGridBagConstraints(int x, int y, int gridwidth) {
        GridBagConstraints gridBagConstraints = createGridBagConstraints(x, y);
        gridBagConstraints.gridwidth = gridwidth;
        return gridBagConstraints;
    }

    // EFFECTS: Overloaded helper method. Creates a GridBagConstraint &
    //  allows specification of grid width and y padding. Returns the created GridBagConstraint.
    public GridBagConstraints createGridBagConstraints(int x, int y, int gridwidth, int ipady) {
        GridBagConstraints gridBagConstraints = createGridBagConstraints(x, y, gridwidth);
        gridBagConstraints.ipady = ipady;
        return gridBagConstraints;
    }

    // EFFECTS: runs the code to declare, create and add components to the JPanel.
    protected abstract void initPanel();
}
