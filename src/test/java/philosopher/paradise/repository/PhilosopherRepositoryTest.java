package philosopher.paradise.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import philosopher.paradise.entity.Category;
import philosopher.paradise.entity.Philosopher;
import philosopher.paradise.entity.Quote;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PhilosopherRepositoryTest {

    @Autowired
    private PhilosopherRepository repo;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findAllByOrderByNameAsc() {
        Set<Philosopher> all = repo.findAllByOrderByNameAsc();
        assertEquals(12, all.size());
    }

    @Test
    public void findByCategories() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void deleteById() {
        
    }
}