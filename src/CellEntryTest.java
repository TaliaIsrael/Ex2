import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CellEntryTest {

    @Test
    public void testIsValid() {
        CellEntry validEntry1 = new CellEntry(0, 0);
        assertTrue(validEntry1.isValid()); // בדוק מיקום תקין

        CellEntry validEntry2 = new CellEntry(25, 99);
        assertTrue(validEntry2.isValid()); // בדוק מיקום תקין

        CellEntry invalidEntry1 = new CellEntry(26, 0);
        assertFalse(invalidEntry1.isValid()); // בדוק מיקום לא תקין (עמודה גדולה מ-Z)

        CellEntry invalidEntry2 = new CellEntry(0, 100);
        assertFalse(invalidEntry2.isValid()); // בדוק מיקום לא תקין (שורה גדולה מ-99)
    }

    @Test
    public void testGetX() {
        CellEntry entry1 = new CellEntry(0, 0);
        assertEquals(0, entry1.getX()); // בדוק את הערך של x

        CellEntry entry2 = new CellEntry(25, 99);
        assertEquals(25, entry2.getX()); // בדוק את הערך של x
    }

    @Test
    public void testToString() {
        CellEntry entry1 = new CellEntry(0, 0);
        assertEquals("A0", entry1.toString()); // בדוק את הייצוג המחרוזתי

        CellEntry entry2 = new CellEntry(25, 99);
        assertEquals("Z99", entry2.toString()); // בדוק את הייצוג המחרוזתי

        CellEntry entry3 = new CellEntry(1, 50);
        assertEquals("B50", entry3.toString()); // בדוק את הייצוג המחרוזתי
    }
}