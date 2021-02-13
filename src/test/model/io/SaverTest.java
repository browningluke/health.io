package model.io;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SaverTest {

    @Test
    void testSave() {
        Saver s = new Saver();
        assertNull(s.save());
    }

}
