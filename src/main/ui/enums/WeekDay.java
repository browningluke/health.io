package ui.enums;

// Represents a weekday and its shortname
public enum WeekDay {
    SUNDAY("Sun"),
    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thu"),
    FRIDAY("Fri"),
    SATURDAY("Sat");

    public final String shortName;

    // MODIFIES: this
    // EFFECTS: creates a Weekday, and assigns it its shortname
    WeekDay(String label) {
        this.shortName = label;
    }
}
