
public class SCell implements Cell {
    private String line;
    private int type;
    private int order;

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
            return row >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isNumber(String text) {
        boolean ans = false;
        if (text == null || text.isEmpty()) {
            return ans;
        }
        if (text.charAt(0) != '.' && text.charAt(0) != '-' && !Character.isDigit(text.charAt(0))) //check if the first char is valid
            return ans;
        boolean point = false;
        for (int i = 1; i < text.length(); i++)
        {
            if (text.charAt(i) == '.'){
                if(point)
                {
                    ans = false;
                    return ans;
                }
                point = true;
            }
            else{
                if(!Character.isDigit(text.charAt(i)))
                {
                    ans = false;
                    return ans;
                }
            }
        }
        ans = true;
        return ans;
    }

    public static boolean isText(String text) {
        boolean ans = false;
        if (!isForm(text) && !isNumber(text)) {
            ans = true;
        }
        return ans;
    }

    public static boolean brackets(String form)      //check if the brackets of the formula are valid
    {
        boolean ans = true;
        if (!form.contains("(") && !form.contains(")")) //check if there is no brackets
        {
            return ans;
        }
        ans = false;
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < form.length(); i++) {
            if (form.charAt(i) == ')') {
                sum2++;
                if (sum2 > sum1) {         //if there is ) before (
                    return ans;
                }
            }
            if (form.charAt(i) == '(')
                sum1++;
        }
        if (sum2 == sum1) {
            ans = true;
        }
        return ans;
    }

    public static boolean isForm(String text) {
        if (text == null || text.isEmpty() || text.charAt(0) != '=' || text.length() < 2) {
            return false;
        }
        String nform = text.substring(1).replace(" ", "");

        if (isNumber(nform)) {
            return true;
        }
        if (!brackets(nform)) {
            return false;
        }
        if (isCellReference(nform)) {
            return true;
        }
        int opIndex = findOfMainOp(nform);
        if (opIndex == -1) {
            return false;
        }
        String leftSide = nform.substring(0, opIndex);
        String rightSide = nform.substring(opIndex + 1);
        return !leftSide.isEmpty() && !rightSide.isEmpty() && isForm("=" + leftSide) && isForm("=" + rightSide);
    }

    public static int findOfMainOp(String form) {
        // String nform = form.substring(1);
        String nform = form;
        int sum = 0;
        int mainOpIndex = -1;
        int mainOp = 1000;

        for (int i = 0; i < nform.length(); i++) {
            if (nform.charAt(i) == '(') {
                sum++;
            }
            else
            if (nform.charAt(i) == ')')
            {
                sum--;
            }
            else
            if (sum == 0)
            {
                if (nform.charAt(i) == '*' || nform.charAt(i) == '/')
                {
                    if (2 <= mainOp)
                    {
                        mainOp = 2;
                        mainOpIndex = i;
                    }
                }
                else
                if (nform.charAt(i) == '+' || nform.charAt(i) == '-')
                {
                    if (1 <= mainOp)
                    {
                        mainOp = 1;
                        mainOpIndex = i;
                    }
                }
            }
        }

        return mainOpIndex;
    }

    public static double computeForm(String form) {
        form = form.replace(" ", "");
        String nform = form;
        if(nform.charAt(0)=='='){
            nform = nform.substring(1);
        }
        if (isNumber(nform)) {
            return Double.parseDouble(nform);
        }

        if(nform.startsWith("(") && nform.endsWith(")")){
            if(brackets(nform.substring(1, nform.length() - 1))){
                return computeForm(nform.substring(1, nform.length() - 1));
            }
        }
        if(!brackets(nform)){
            throw new NumberFormatException();
        }
        if(nform.endsWith("*") || nform.endsWith("/") || nform.endsWith("-") || nform.endsWith("+")){
            throw new NumberFormatException();
        }
        int op = findOfMainOp(nform);
        if(op == -1)
        {
//            if(isCellReference(nform.substring(1)))
//            {
//                CellEntry c = new CellEntry(nform.substring(1).charAt(0), Integer.parseInt(nform.substring(2)));
//                if(c.isValid())
//                {
//                    return computeForm()
//                }
//            }
            throw new NumberFormatException();
        }

        String leftSide = nform.substring(0, op);
        String rightSide = nform.substring(op + 1);
        double left = computeForm("=" + leftSide);
        double right = computeForm("=" + rightSide);

        if (nform.charAt(op) == '+') {
            return left + right;
        } else if (nform.charAt(op) == '-') {
            return left - right;
        } else if (nform.charAt(op) == '*') {
            return left * right;
        } else if (nform.charAt(op) == '/')
        {
            if (right == 0) {
                throw new NumberFormatException();
            }
            return left / right;
        }
        else {
            throw new NumberFormatException();
        }
    }

    public SCell(String s) {
        setData(s);
    }

    public void setOrder(String s){

    }
    @Override
    public int getOrder() {
        if (type == Ex2Utils.TEXT || type == Ex2Utils.NUMBER) {
            return 0; // Base case: text or number cells have order 0
        }
        return this.order;
    }

    @Override
    public void setOrder(int t) {
        this.order = t;
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
public void setData(String s) {
        line = s;
        if (isNumber(s)) {
            type = Ex2Utils.NUMBER;
        } else if (isText(s)) {
            type = Ex2Utils.TEXT;
        } else if (isForm(s)) {
            type = Ex2Utils.FORM; //
        } else {
            type = Ex2Utils.ERR_FORM_FORMAT;
        }
    }
    @Override
    public int getType() {
        return type;
    }
    @Override
    public String getData() {
        return line;
    }

    @Override
    public void setType(int t) {
        if (t == Ex2Utils.NUMBER || t == Ex2Utils.TEXT ||
                t == Ex2Utils.FORM || t == Ex2Utils.ERR_FORM_FORMAT ||
                t == Ex2Utils.ERR_CYCLE_FORM) {
            type = t;
            // Reset order to 0 if changing to non-formula type
            if (t != Ex2Utils.FORM) {
                order = 0;
            }
        }
    }
}
