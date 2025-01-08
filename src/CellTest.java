import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This JUnit class represents a very partial test class for Ex1.
 * Make sure you complete all the needed JUnits
 */
public class CellTest {

@Test
void Close()
{
    String s1 = "=((3+8)*7";
   boolean ans = SCell.brackets(s1);
   assertFalse(ans);
   assertTrue(SCell.brackets("=(3-9)*(A1-3)"));
   assertTrue(SCell.brackets("=((3-9)*(A1-3))"));
   assertTrue(SCell.brackets("=((3-9*A1-3))"));
   assertTrue(SCell.brackets("=(3)-(9*A1)-(3)"));
   assertTrue(SCell.brackets("=(5*(9)*5)"));
   assertFalse(SCell.brackets("=)5*(9)*5("));
}

    @Test
    void testIsNumber()
    {
        String s1 = "-.256";
        boolean ans = SCell.isNumber(s1);
        assertTrue(ans);
    }

    @Test
    void testCompueform()
    {
       // try {
            Double s1 = SCell.computeForm("=(1-1)+(5+3)/2");
            assertEquals(s1, 4.0);
//        } catch(Exception e){
//            System.out.println("Compute Form failed");
//            e.getMessage();
//        }

    }



}