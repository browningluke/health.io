package ui.components;

import ui.HealthIO;
import ui.enums.IOOperation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// Represents a generic click handler which handles showing the FileChooser and handling responses.
public class FileChooserActionHandler implements ActionListener {

    AbstractFileChooser fileChooser;            // The FileChooser object
    HealthIO healthIO;                          // The root JFrame object

    // MODIFIES: this
    // EFFECTS: creates a new click listener with a specified FileChooser and root JFrame.
    public FileChooserActionHandler(AbstractFileChooser fileChooser, HealthIO ui) {
        this.fileChooser = fileChooser;
        this.healthIO = ui;
    }

    @Override
    // EFFECTS: Shows the FileChooser the ActionHandler instance was bound to.
    //  If the user chose the APPROVE option:
    //   Calls a helper function to handle the user's selection
    public void actionPerformed(ActionEvent e) {
        int result = fileChooser.showDialog(healthIO,
                fileChooser.operation.shortName);

        if (result == JFileChooser.APPROVE_OPTION) {
            handleSelection();
        }
    }

    // MODIFIES: healthIO
    // EFFECTS: Helper function. Handles exporting and saving/loading then redraws UI.
    //  If exporting: handle the export then short circuit.
    //  If saving/loading: handle the save/load
    private void handleSelection() {
        File selectedFile = fileChooser.getSelectedFile();
        String path = selectedFile.getAbsolutePath();

        if (fileChooser.operation == IOOperation.EXPORT) {
            handleExport(path);
            return;
        }

        handleSaveAndLoad(path);
        healthIO.drawPanels();
    }

    // MODIFIES: healthIO
    // EFFECTS: Helper function. Ensures path ends with '.json'
    //  and calls necessary Timeline IO function.
    private void handleSaveAndLoad(String path) {
        if (!path.endsWith(".json")) {
            path += ".json";
        }

        if (fileChooser.operation == IOOperation.LOAD) {
            healthIO.loadTimeline(path);
        } else {
            healthIO.saveTimeline(path);
        }
    }

    // MODIFIES: healthIO
    // EFFECTS: Helper function. Ensures path ends with '.csv'
    //  and calls export Timeline IO function.
    private void handleExport(String path) {
        if (!path.endsWith(".csv")) {
            path += ".csv";
        }
        healthIO.exportTimelineAsCSV(path);
    }
}