package ui.components;

import ui.enums.IOOperation;

import javax.swing.*;

// Represents a FileChooser window to select where to save Timeline as json file.
public class SaveFileChooser extends AbstractFileChooser {

    // MODIFIES: this
    // EFFECTS: create a FileChooser with custom parameters through
    //  the AbstractFileChooser class.
    public SaveFileChooser() {
        super(IOOperation.SAVE);
        initComponent();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets the FileChooser as a save window and sets the title of the window.
    protected void initComponent() {
        setDialogTitle("Save timeline file");
        setDialogType(JFileChooser.SAVE_DIALOG);
    }
}
