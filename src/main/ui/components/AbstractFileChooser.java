package ui.components;

import ui.HealthIO;
import ui.enums.IOOperation;
import ui.sound.AlertSound;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.io.File;

// Represents an generic FileChooser and handles action listener generation for all subtypes.
public abstract class AbstractFileChooser extends JFileChooser {

    public final IOOperation operation;     // Represents what this FileChooser does.

    // MODIFIES: this
    // EFFECTS: defines an abstract constructor which initializes the JFileChooser and
    //  sets the FileChooser operation and JSON file extension filter.
    public AbstractFileChooser(IOOperation operation) {
        super();

        this.operation = operation;

        setAcceptAllFileFilterUsed(false);
        setFileFilter(
                new FileNameExtensionFilter("JSON files", "json")
        );
    }

    // EFFECTS: Configures the specific FileChooser.
    protected abstract void initComponent();

    // EFFECTS: Consumes a reference to the root JFrame (HealthIO).
    //  Creates and returns a custom action listener based upon
    //  this FileChooser and the root JFrame.
    public ActionListener generateActionListener(HealthIO ui) {
        return new FileChooserActionHandler(this, ui);
    }

    @Override
    // EFFECTS: Checks if the user selected a file that already exists.
    //  If it does, prompt the user if they want to overwrite it (with an alert sound)
    //  and handle response. Else, carry on as normal.
    public void approveSelection() {
        File f = getSelectedFile();
        if (f.exists() && (operation == IOOperation.SAVE || operation == IOOperation.EXPORT)) {
            new AlertSound().play();

            int result = JOptionPane.showConfirmDialog(this, "This file exists, overwrite?",
                    "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
            switch (result) {
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;
                case JOptionPane.NO_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        super.approveSelection();
    }
}
