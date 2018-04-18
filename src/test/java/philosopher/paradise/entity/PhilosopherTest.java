package philosopher.paradise.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

public class PhilosopherTest {

    private static final String TEST_NAME = "Brian";
    private static final Long TEST_ID = new Random().nextLong();
    private static final String TEST_DESCRIPTION = "The Life of Brian";

    private Set<Quote> testQuotes = new HashSet<>();
    private Set<Category> categories = new HashSet<>();

    private Philosopher brian = new Philosopher(TEST_NAME, categories, TEST_DESCRIPTION, testQuotes);

    @Before
    public void setUp(){
        brian.setId(TEST_ID);
    }

    @Test
    public void verifyConstructor() {
        assertNotNull(brian.getId());
        assertNotNull(brian.getName());
        assertNotNull(brian.getDescription());
        assertNotNull(brian.getCategories());
        assertNotNull(brian.getQuotes());
}

    @Test
    public void testEqualsHashCode() {
        Philosopher newPhilosopher = new Philosopher(TEST_NAME, categories, TEST_DESCRIPTION, testQuotes);
        assertNotEquals(newPhilosopher, brian);
        assertNotEquals("The auto generated id's should differ!", newPhilosopher.hashCode(), brian.hashCode());
    }

    @Test
    public void testToString() {
        Philosopher philosopher = brian;
        assertEquals("The printed strings should match!", philosopher.toString(), brian.toString());
    }
}