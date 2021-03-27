package ui.components;

import ui.enums.IOOperation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

// Represents a FileChooser window to select where to export Timeline as CSV.
public class ExportFileChooser extends AbstractFileChooser {

    // MODIFIES: this
    // EFFECTS: create a FileChooser with custom parameters through
    //  the AbstractFileChooser class. Override the FileFilter to csv files.
    public ExportFileChooser() {
        super(IOOperation.EXPORT);
        setFileFilter(
                new FileNameExtensionFilter("CSV files", "csv")
        );

        initComponent();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets the FileChooser as a save window and sets the title of the window.
    protected void initComponent() {
        setDialogTitle("Export timeline file");
        setDialogType(JFileChooser.SAVE_DIALOG);
    }
}
