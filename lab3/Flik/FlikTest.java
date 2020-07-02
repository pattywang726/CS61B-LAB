import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    @Test
    public void TestFilk() {
        int a = 1;
        int b = 2;
        int c = 1;
        assertTrue("These two numbers are same", Flik.isSameNumber(a,c));
        assertFalse("These two numbers are not same", Flik.isSameNumber(a,b));
    }
}
