
public class Cell
{
    public static boolean isNumber(String text){
        boolean ans = false;
        if(text.charAt(0)!='.' && text.charAt(0)!= '=' && !Character.isDigit(text.charAt(0))) //check if the first char is valid
            return ans;

        for(int i = 1; i<text.length(); i++)
        {
            if(text.charAt(i)!= '.' && !Character.isDigit(text.charAt(i)))
                return ans;
        }
        ans = true;
        return ans;
    }

    public static boolean isText(String)
    {

    }

    public static boolean isForm(String text)
    {

    }

    public static Double computeForm(String form){
       double ans = 0;
        if(isForm(form))
        {
            for(int i = 1; i<form.length(); i++)
            {
                if (Character.isDigit(form.charAt(i)) && i!=form.length()-1)
                {
                    if (form.charAt(i+1)!= '(' && form.charAt(i+1)!= ')' &&)
                }
            }
        }
        return ans;
    }
}
