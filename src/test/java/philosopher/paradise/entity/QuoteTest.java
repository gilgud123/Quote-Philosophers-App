package philosopher.paradise.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

public class QuoteTest {

    private static final String TEST_TEXT = "Philosophy rules!";
    private static final Long TEST_ID = new Random().nextLong();
    private final Philosopher philosopher = new Philosopher();

    private Set<Topic> testTopics = new HashSet<>();
    private Quote quote = new Quote(TEST_TEXT, testTopics,philosopher);

    @Before
    public void setUp(){
        quote.setId(TEST_ID);
    }

    @Test
    public void validateConstructor(){
        assertNotNull(quote.getId());
        assertNotNull(quote.getText());
        assertNotNull(quote.getTopics());
        assertNotNull(quote.getPhilosopher());
    }


    @Test
    public void testEqualsHashCode() {
        Quote newQuote = new Quote(TEST_TEXT, testTopics, philosopher);
        assertNotEquals(newQuote, quote);
        assertNotEquals("The auto generated id's should differ!", newQuote.hashCode(), quote.hashCode());
    }

    @Test
    public void testToString() {
        Quote newQuote = quote;
        assertEquals("The printed strings should match!", newQuote.toString(), newQuote.toString());
    }
}