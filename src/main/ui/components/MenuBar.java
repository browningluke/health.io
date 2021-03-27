package ui.components;

import ui.HealthIO;
import ui.sound.AlertSound;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a menu bar where users can select lesser used options of the program.
public class MenuBar extends JMenuBar {

    private final HealthIO healthIO;  // Reference back to JFrame subclass, enables drawing of other panels

    // MODIFIES: this
    // EFFECTS: Creates a new MenuBar, initializes the parent class (JMenuBar) then itself.
    public MenuBar(HealthIO ui) {
        super();
        healthIO = ui;
        initComponent();
    }

    // MODIFIES: this
    // EFFECTS: Adds the generated file menu to the MenuBar.
    private void initComponent() {
        add(generateFileMenu());
    }

    // EFFECTS: Helper method. Creates a new JMenu, adds Save, Load, Export, Reset & Exit buttons;
    //  with their respective action listener. Returns the created JMenu object.
    private JMenu generateFileMenu() {
        JMenu jmenu = new JMenu();
        jmenu.setText("File");

        jmenu.add(generateButton("Save",
                new SaveFileChooser().generateActionListener(healthIO)));

        jmenu.add(generateButton("Load",
                new LoadFileChooser().generateActionListener(healthIO)));

        jmenu.add(generateButton("Export",
                new ExportFileChooser().generateActionListener(healthIO)));

        jmenu.add(new JSeparator());

        jmenu.add(generateButton("Reset",
                new ResetButtonActionListener()));

        jmenu.add(new JSeparator());

        jmenu.add(generateButton("Exit",
                new CloseButtonActionListener()));

        return jmenu;
    }

    // EFFECTS: Helper method. Creates a new JMenuItem, sets its text
    //  and attaches an action listener. Returns the created JMenuItem.
    private JMenuItem generateButton(String text, ActionListener al) {
        JMenuItem menuitem = new JMenuItem();

        menuitem.setText(text);
        menuitem.addActionListener(al);

        return menuitem;
    }

    // Represents a button click handler which closes the program when clicked.
    private class CloseButtonActionListener implements ActionListener {

        @Override
        // EFFECTS: Prompts the user to confirm that they want to delete (with an alert sound).
        // If yes is selected, exists the program.
        public void actionPerformed(ActionEvent e) {
            new AlertSound().play();

            String message = "All unsaved data will be lost.\n"
                    + "Are you sure you exit?";
            int result = JOptionPane.showConfirmDialog(healthIO, message,
                    "Exit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    // Represents a button click handler which resets the Timeline.
    private class ResetButtonActionListener implements ActionListener {

        @Override
        // EFFECTS: Prompts the user to confirm that they want to delete (with an alert sound).
        //  If yes is selected, reset timeline.
        public void actionPerformed(ActionEvent e) {
            new AlertSound().play();

            String message = "This will delete all data.\n"
                    + "Are you sure you want to do this?";
            int result = JOptionPane.showConfirmDialog(healthIO, message,
                    "Reset All Data", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                healthIO.resetTimeline();
            }
        }
    }
}
