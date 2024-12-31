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
   boolean ans = Cell.brackets(s1);
   assertFalse(ans);
}

    @Test
    void testIsNumber()
    {
        String s1 = "-.256";
        boolean ans = Cell.isNumber(s1);
        assertTrue(ans);
    }



}