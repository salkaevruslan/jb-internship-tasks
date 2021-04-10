import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MatcherTest {

    @Test
    public void testEvilRegexTimeout() {
        int maxTimeMillis = 100;
        Matcher matcher = new Matcher(maxTimeMillis);
        String regex = "(x|xx)+";
        String text = "x".repeat(10000);
        assertFalse(matcher.matches(text, regex));
    }

    @Test
    public void testEvilRegexNoTimeout() {
        int maxTimeMillis = 100;
        Matcher matcher = new Matcher(maxTimeMillis);
        String regex = "(x|xx)+";
        String text = "x".repeat(1);
        assertTrue(matcher.matches(text, regex));
    }

    @Test
    public void testLongTextTimeout() {
        int maxTimeMillis = 100;
        Matcher matcher = new Matcher(maxTimeMillis);
        String regex = "x+";
        String text = "x".repeat(100000000);
        assertFalse(matcher.matches(text, regex));
    }

    @Test
    public void testShortText() {
        int maxTimeMillis = 100;
        Matcher matcher = new Matcher(maxTimeMillis);
        String regex = "x+yz";
        String text = "x".repeat(10000) + "y" + "z";
        assertTrue(matcher.matches(text, regex));
        text = "x".repeat(10000000) + "y";
        assertFalse(matcher.matches(text, regex));
    }

    @Test
    public void testBadPattern() {
        int maxTimeMillis = 100;
        Matcher matcher = new Matcher(maxTimeMillis);
        String regex = "[";
        String text = "[";
        assertFalse(matcher.matches(text, regex));
    }
}

