import org.junit.jupiter.api.Test;


public class Cell {


    public static boolean isNumber(String text) {
        boolean ans = false;
        if (text.charAt(0) != '.' && !Character.isDigit(text.charAt(0))) //check if the first char is valid
            return ans;

        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != '.' && !Character.isDigit(text.charAt(i)))
                return ans;
        }
        ans = true;
        return ans;
    }

    public static boolean isText(String text)
    {
        boolean ans = true;


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
                if (sum2 > sum1){         //if there is ) before (
                    return ans;}
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
        boolean ans = false;
        if (text.charAt(0) != '=')
            return ans;
        if (!brackets(text))
            return ans;


        return ans;
    }

 /*   public static Double computeForm(String form) {
        double ans = 0;
        if (isForm(form)) {
            for (int i = 1; i < form.length(); i++) {
                if (Character.isDigit(form.charAt(i)) && i != form.length() - 1) {
                    if (form.charAt(i + 1) != '(' && form.charAt(i + 1) != ')' &&)
                }
            }
        }
        return ans;
    }

  */
}
