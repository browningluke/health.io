package ui.components;

import ui.enums.IOOperation;

import javax.swing.*;

// Represents a FileChooser window to select saved timelines (json files).
public class LoadFileChooser extends AbstractFileChooser {

    // MODIFIES: this
    // EFFECTS: create a FileChooser with custom parameters through
    //  the AbstractFileChooser class.
    public LoadFileChooser() {
        super(IOOperation.LOAD);
        initComponent();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets the FileChooser as a load window and sets the title of the window.
    protected void initComponent() {
        setDialogTitle("Load timeline file");
        setDialogType(JFileChooser.OPEN_DIALOG);
    }

}
