package philosopher.paradise.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import philosopher.paradise.entity.Category;
import philosopher.paradise.entity.Philosopher;
import philosopher.paradise.repository.PhilosopherRepository;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class PhilosopherServiceImplTest {

    private static final long ID = new Random().nextLong();
    private static final String NAME = "brian";
    private PhilosopherService service;

    @Mock
    private PhilosopherRepository repo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new PhilosopherServiceImpl(repo);
    }

    @Test
    public void getPhilosophers() {
        Set<Philosopher> set = new HashSet<>(Arrays.asList(new Philosopher(), new Philosopher(), new Philosopher()));

        when(repo.findAllByOrderByNameAsc()).thenReturn(set);

        List<Philosopher> allSorted = service.getPhilosophers();

        assertEquals(3, allSorted.size());
    }

    @Test
    public void findById() {
        Philosopher p = new Philosopher();
        Optional<Philosopher> o = Optional.of(p);
        p.setId(ID);
        p.setName(NAME);

        when(repo.findById(ID)).thenReturn(o);

        Philosopher brian = service.findById(ID);

        assertEquals(NAME, brian.getName());
    }

    @Test
    public void getPhilosopherByCategory() {
        Set<Philosopher> set = new HashSet<>(Arrays.asList(new Philosopher(), new Philosopher(), new Philosopher()));

        when(repo.findByCategories(Category.ETHICS)).thenReturn(set);

        Set<Philosopher> byCategory = service.getPhilosopherByCategory(Category.ETHICS.name());

        assertEquals(3, byCategory.size());

    }
}