import java.io.IOException;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;

    private static final int UNVISITED = 0;
    private static final int VISITING = 1;
    private static final int VISITED = 2;


    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for (int i = 0; i < x; i = i + 1) {
            for (int j = 0; j < y; j = j + 1) {
                table[i][j] = new SCell(Ex2Utils.EMPTY_CELL);
            }
        }
        eval();
    }

    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        // Add your code here

        Cell c = get(x, y);
        if (c != null) {
            ans = c.toString();
        }

        /////////////////////
        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        if (!isIn(x, y)) {
            return null;
        }
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        Cell ans = null;
        if (cords != null && cords.length() >= 2 && cords.length() <= 3) {
            char FChar = cords.charAt(0);
            int x = Character.toUpperCase(FChar) - 'A'; // ממיר את האות לעמודה (A - 0, B - 1, ..., Z - 25)
            String rowStr = cords.substring(1);
            try {
                int y = Integer.parseInt(rowStr); // ממיר את המספר לשורה
                ans = get(x, y); // מחזיר את התא במיקום (x, y)
            } catch (NumberFormatException e) {
                // אם המספר לא תקין, נחזיר null
            }
        }
        return ans;
    }

    public static boolean isCellReference(String text) {
        if (text == null || text.length() < 2) {
            return false;
        }
        char firstChar = Character.toUpperCase(text.charAt(0));
        if (firstChar < 'A' || firstChar > 'Z') {
            return false;
        }
        String numberPart = text.substring(1);
        try {
            int row = Integer.parseInt(numberPart);
            return row >= 0 && row <= 99; // שורה חייבת להיות בין 0 ל-99
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    @Override
    public int width() {
        return table.length;
    }

    @Override
    public int height() {
        return table[0].length;
    }

    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
    }

    @Override
    public void eval() {
        int[][] dd = depth();
        // Add your code here

        // ///////////////////
    }

    @Override
    public boolean isIn(int xx, int yy) {
            return xx >= 0 && yy >= 0 && xx < this.width() && yy < this.height();
    }

    @Override
    public int[][] depth() {
        int w = width();  // רוחב הגיליון
        int h = height(); // גובה הגיליון
        int[][] ans = new int[w][h]; // מערך התוצאות

        // אתחול כל התאים ב-ans ל- -1
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                ans[x][y] = -1;
            }
        }

        int depth = 0; // רמת העומק הנוכחית
        int count = 0; // מספר התאים שחושבו עד כה
        int max = w * h; // מספר התאים הכולל בגיליון
        boolean flagC = true; // דגל שמציין אם לפחות תא אחד חושב בסבב הנוכחי

        while (count < max && flagC) {
            flagC = false; // מאפסים את הדגל בתחילת כל סבב

            // עוברים על כל התאים בגיליון
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    // אם התא עדיין לא חושב ויכול להיות מחושב כעת
                    if (ans[x][y] == -1 && canBeComputedNow(x, y, ans)) {
                        ans[x][y] = depth; // מעדכנים את העומק של התא
                        count++; // מעדכנים את מספר התאים שחושבו
                        flagC = true; // מסמנים שתא אחד לפחות חושב בסבב הנוכחי
                    }
                }
            }

            depth++; // מעלים את רמת העומק לסבב הבא
        }

        return ans; // מחזירים את מערך העומקים
    }

    /**
     * בודק אם התא במיקום (x, y) יכול להיות מחושב כעת.
     * x קואורדינטת X של התא.
     * y קואורדינטת Y של התא.
     * depths מערך העומקים של כל התאים.
     * return true אם התא יכול להיות מחושב כעת, אחרת false.
     */
    public boolean canBeComputedNow(int x, int y, int[][] depths) {
        Cell cell = get(x, y);
        if (cell == null) {
            return false; // אם התא לא קיים, לא ניתן לחשב אותו
        }

        // אם התא הוא מספר או טקסט, הוא תמיד ניתן לחישוב
        if (cell.getType() == Ex2Utils.NUMBER || cell.getType() == Ex2Utils.TEXT) {
            return true;
        }

        // אם התא הוא נוסחה
        if (cell.getType() == Ex2Utils.FORM) {
            String formula = cell.getData().substring(1); // הסר את הסימן "="
            int i = 0;
            while (i < formula.length()) {
                char c = formula.charAt(i);
                if (Character.isLetter(c)) {
                    // הפניה לתא (למשל, A1, B2)
                    String cellRef = "";
                    while (i < formula.length() && (Character.isLetter(formula.charAt(i)) || Character.isDigit(formula.charAt(i)))) {
                        cellRef += formula.charAt(i); // שרשור תווים למחרוזת
                        i++;
                    }
                    if (isCellReference(cellRef)) { // בדוק אם ההפניה תקינה
                        CellEntry refEntry = new CellEntry(cellRef.charAt(0) - 'A', Integer.parseInt(cellRef.substring(1)));
                        if (isIn(refEntry.getX(), refEntry.getY())) {
                            // אם התא התלוי עדיין לא חושב, התא הנוכחי לא יכול להיות מחושב
                            if (depths[refEntry.getX()][refEntry.getY()] == -1) {
                                return false;
                            }
                        } else {
                            return false; // אם ההפניה לא תקינה, לא ניתן לחשב את התא
                        }
                    } else {
                        return false; // אם ההפניה לא תקינה, לא ניתן לחשב את התא
                    }
                } else if (Character.isDigit(c) || c == '.') {
                    // מספר (למשל, 5, 3.14)
                    while (i < formula.length() && (Character.isDigit(formula.charAt(i)) || formula.charAt(i) == '.')) {
                        i++;
                    }
                } else if (isOperator(c)) {
                    // אופרטור (למשל, +, -, *, /)
                    i++;
                } else if (c == '(' || c == ')') {
                    // סוגריים (לא משפיעים על העומק)
                    i++;
                } else {
                    // תו לא תקין בנוסחה
                    return false;
                }
            }
            // אם כל התאים התלויים כבר חושבו, התא הנוכחי יכול להיות מחושב
            return true;
        }

        // אם התא הוא שגיאה או לא מוגדר, לא ניתן לחשב אותו
        return false;
    }

    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public String eval(int x, int y) {
        String ans = null;
        if (get(x, y) != null) {
            ans = get(x, y).toString();
        }
        return ans;
    }
}