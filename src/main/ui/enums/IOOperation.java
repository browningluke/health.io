package ui.enums;

// Represents an FileChooser IO operation the user can take and its string representation.
public enum IOOperation {
    SAVE("Save"),
    LOAD("Load"),
    EXPORT("Export");

    public final String shortName;

    // MODIFIES: this
    // EFFECTS: creates an IOOperation, and assigns it its string representation.
    IOOperation(String label) {
        this.shortName = label;
    }
}
