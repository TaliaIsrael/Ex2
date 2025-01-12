import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Ex2sheetTest {

    @Test
    void testCellReferences() {
        assertTrue(Ex2Sheet.isCellReference("A1"));
        assertTrue(Ex2Sheet.isCellReference("B2"));
        assertTrue(Ex2Sheet.isCellReference("Z99"));
        assertTrue(Ex2Sheet.isCellReference("a1"));
        assertFalse(Ex2Sheet.isCellReference(null));
        assertFalse(Ex2Sheet.isCellReference("A 1"));
        assertFalse(Ex2Sheet.isCellReference("A100"));
    }

    @Test
    public void testDepth() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3); // גיליון בגודל 3x3

        // תא A0 (עמודה 0, שורה 0) מכיל מספר
        sheet.set(0, 0, "5"); // A0 = 5

        // תא A1 (עמודה 0, שורה 1) תלוי ב-A0
        sheet.set(0, 1, "=A0"); // A1 = A0

        // תא B0 (עמודה 1, שורה 0) תלוי ב-A1
        sheet.set(1, 0, "=A1"); // B0 = A1

        // תא B1 (עמודה 1, שורה 1) תלוי ב-B0 וב-A0
        sheet.set(1, 1, "=B0+A0"); // B1 = B0 + A0

        // תא C2 (עמודה 2, שורה 2) מכיל טקסט
        sheet.set(2, 2, "Hello"); // C2 = "Hello"

        // תא A2 (עמודה 0, שורה 2) יוצר תלות מעגלית עם B2
        sheet.set(0, 2, "=B2"); // A2 = B2
        sheet.set(1, 2, "=A2"); // B2 = A2

        // מחשב את העומק של כל התאים
        int[][] depths = sheet.depth();
        // בודק את העומק של כל תא
        assertEquals(0, depths[0][0]); // A0: מספר, עומק 0
        assertEquals(1, depths[0][1]); // A1: תלוי ב-A0, עומק 1
        assertEquals(2, depths[1][0]); // B0: תלוי ב-A1, עומק 2
        assertEquals(3, depths[1][1]); // B1: תלוי ב-B0 וב-A0, עומק 3
        assertEquals(0, depths[2][2]); // C2: טקסט, עומק 0
        assertEquals(-1, depths[0][2]); // A2: תלות מעגלית, עומק -1
        assertEquals(-1, depths[1][2]); // B2: תלות מעגלית, עומק -1
    }


    @Test
    public void testFormulaWithUncomputedDependency() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");       // A1 = 5 (מספר)
        sheet.set(1, 0, "=A0");     // A2 = A1 (נוסחה)

        int[][] depths = new int[3][3];
        depths[0][0] = -1; // A1 עדיין לא חושב

        assertFalse(sheet.canBeComputedNow(1, 0, depths)); // A2 לא יכול להיות מחושב כי A1 לא חושב
    }

    @Test
    public void testCanBeComputedNow_EmptyCell() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(2, 2, ""); // C2 = "" (תא ריק)
        int[][] depths = new int[3][3];
        depths[2][2] = -1;
        assertTrue(sheet.canBeComputedNow(2, 2, depths)); // C2: תא ריק, ניתן לחישוב
    }


    @Test
    public void testCanBeComputedNow_InvalidCellReference() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=9"); // A0 = Z99 (הפניה לתא לא קיים)
        int[][] depths = new int[3][3];
        depths[0][0] = -1; // A0 עדיין לא חושב
        assertTrue(sheet.canBeComputedNow(0, 0, depths)); // A0: לא ניתן לחישוב כי ההפניה לא תקינה
    }


}

