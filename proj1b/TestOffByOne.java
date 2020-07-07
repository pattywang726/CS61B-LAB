import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.

    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
    @Test
    public void TestEqualChars() {
        assertTrue(offByOne.equalChars('a','b'));
        assertTrue(offByOne.equalChars('k','l'));
        assertFalse(offByOne.equalChars('a','z'));
        assertFalse(offByOne.equalChars('a','A'));
        assertTrue(offByOne.equalChars('%','&'));
    }
}
