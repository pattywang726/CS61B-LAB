import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
//  Uncomment this class once you've created your Palindrome class.
    @Test
    public void Test1isPalindrome() {
        assertTrue("This word is palindrome", palindrome.isPalindrome("madam"));
        assertTrue("This word is palindrome", palindrome.isPalindrome("racecar"));
        assertFalse("This word is not palindrome", palindrome.isPalindrome("aaaab"));

    }
    @Test
    public void Test2isPalindrome() {
        assertFalse("This word is not palindrome", palindrome.isPalindrome("noun"));
        assertTrue("This word is palindrome", palindrome.isPalindrome("a"));
    }
    @Test
    public void Test3isPalindrome() {
        assertTrue("This word is palindrome", palindrome.isPalindrome(""));
    }
    @Test
    public void Test4isPalindrome() {
        assertTrue("This word is palindrome", palindrome.isPalindrome("", new OffByOne()));
        assertTrue("This word is palindrome", palindrome.isPalindrome("a", new OffByOne()));
        assertTrue("This word is palindrome", palindrome.isPalindrome("flake", new OffByOne()));
        assertTrue("This word is palindrome", palindrome.isPalindrome("tutu", new OffByOne()));
    }
}
