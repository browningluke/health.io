package model.io;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoaderTest {

    @Test
    void testLoad() {
        Loader l = new Loader();
        assertNull(l.load());
    }

}
