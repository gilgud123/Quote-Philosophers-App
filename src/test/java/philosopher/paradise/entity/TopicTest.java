package philosopher.paradise.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

public class TopicTest {

    private static final String TEST_TEXT = "fun";
    private static final Long TEST_ID = new Random().nextLong();

    private Set<Quote> testQuotes = new HashSet<>();
    private Topic topic = new Topic(TEST_TEXT, testQuotes);

    @Before
    public void setUp(){
        topic.setId(TEST_ID);
    }

    @Test
    public void verifyConstructor() {
        assertNotNull(topic.getId());
        assertNotNull(topic.getText());
        assertNotNull(topic.getQuotes());
    }

    @Test
    public void verifyGettersSetters() {
        final String TEST_SETTERS = "not fun";

        topic.setId(new Random().nextLong());
        assertNotEquals(topic.getId(), TEST_ID);

        topic.setText(TEST_SETTERS);
        assertNotEquals(topic.getText(), TEST_TEXT);

        assertEquals(topic.getQuotes(), testQuotes);
    }

    @Test
    public void testEqualsHashCode() {
        Topic newTopic = new Topic(TEST_TEXT, testQuotes);
        assertNotEquals(newTopic, topic);
        assertNotEquals("The auto generated id's shoudl differ!", newTopic.hashCode(), topic.hashCode());
    }

    @Test
    public void testToString() {
        Topic newTopic = topic;
        assertEquals("The printed strings should match!", topic.toString(), newTopic.toString());
    }
}