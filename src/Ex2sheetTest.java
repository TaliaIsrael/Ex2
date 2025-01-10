import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Ex2sheetTest {

    @Test
    void testCellReferences() {
        // הפניות תקניות
        assertTrue(Ex2Sheet.isCellReference("A1"));
        assertTrue(Ex2Sheet.isCellReference("B2"));
        assertTrue(Ex2Sheet.isCellReference("Z99"));
        assertTrue(Ex2Sheet.isCellReference("a1"));
        assertFalse(Ex2Sheet.isCellReference(null));
        assertFalse(Ex2Sheet.isCellReference("A 1"));
        assertFalse(Ex2Sheet.isCellReference("A100"));
    }

//    @Test
//    void testDepth() {
//                Ex2Sheet sheet = new Ex2Sheet(3, 3);
//                sheet.set(0, 0, "5");
//                sheet.set(0, 1, "Hello");
//                sheet.set(0, 2, "3.14");
//                sheet.set(1, 0, "=A1");
//                sheet.set(1, 1, "=B1");
//                sheet.set(2, 0, "=A1+B1");
//                sheet.set(2, 1, "=C1");     // C2 תלוי ב-C1
//                sheet.set(2, 2, "=C2");     // C3 תלוי ב-C2 (יצירת מעגליות)
//                int[][] depths = sheet.depth();
//                assertEquals(0, depths[0][0]); // A1 (מספר)
//                assertEquals(0, depths[0][1]); // B1 (טקסט)
//                assertEquals(0, depths[0][2]); // C1 (מספר)
//                assertEquals(1, depths[1][0]); // A2 (תלוי ב-A1)
//                assertEquals(1, depths[1][1]); // B2 (תלוי ב-B1)
//                assertEquals(1, depths[2][0]); // A3 (תלוי ב-A1 וב-B1)
//                assertEquals(-1, depths[2][1]); // B3 (תלוי ב-C1, שיוצר מעגליות)
//                assertEquals(-1, depths[2][2]); // C3 (תלוי ב-C2, שיוצר מעגליות)
//            }
//}

    @Test
    public void testDepth() {
        // יצירת גיליון בגודל 3x3
        Ex2Sheet sheet = new Ex2Sheet(3, 3);

        // אתחול כל התאים עם ערך ברירת מחדל
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                sheet.set(x, y, Ex2Utils.EMPTY_CELL); // אתחול עם מחרוזת ריקה
            }
        }

        // 1. תאים עם ערכים קבועים (מספרים או טקסט)
        sheet.set(0, 0, "5");       // מספר
        sheet.set(0, 1, "Hello");   // טקסט
        sheet.set(0, 2, "3.14");    // מספר

        // 2. תאים עם נוסחאות פשוטות
        sheet.set(1, 0, "=A1");     // נוסחה שתלויה ב-A1
        sheet.set(1, 1, "=B1");     // נוסחה שתלויה ב-B1

        // 3. תאים עם נוסחאות מורכבות
        sheet.set(2, 0, "=A1+B1");  // נוסחה שתלויה ב-A1 וב-B1

        // 4. מעגליות
        sheet.set(2, 1, "=C1");     // C2 תלוי ב-C1
        sheet.set(2, 2, "=C2");     // C3 תלוי ב-C2 (יצירת מעגליות)

        // חישוב העומק
        int[][] depths = sheet.depth();

        // בדיקות:
        // 1. תאים עם ערכים קבועים
        assertEquals(0, depths[0][0]); // A1 (מספר)
        assertEquals(0, depths[0][1]); // B1 (טקסט)
        assertEquals(0, depths[0][2]); // C1 (מספר)

        // 2. תאים עם נוסחאות פשוטות
        assertEquals(1, depths[1][0]); // A2 (תלוי ב-A1)
        assertEquals(1, depths[1][1]); // B2 (תלוי ב-B1)

        // 3. תאים עם נוסחאות מורכבות
        assertEquals(1, depths[2][0]); // A3 (תלוי ב-A1 וב-B1)

        // 4. מעגליות
        assertEquals(-1, depths[2][1]); // B3 (תלוי ב-C1, שיוצר מעגליות)
        assertEquals(-1, depths[2][2]); // C3 (תלוי ב-C2, שיוצר מעגליות)
    }

    @Test
    public void testFormulaWithUncomputedDependency() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");       // A1 = 5 (מספר)
        sheet.set(1, 0, "=A1");     // A2 = A1 (נוסחה)

        int[][] depths = new int[3][3];
        depths[0][0] = -1; // A1 עדיין לא חושב

        assertFalse(sheet.canBeComputedNow(1, 0, depths)); // A2 לא יכול להיות מחושב כי A1 לא חושב
    }

}

