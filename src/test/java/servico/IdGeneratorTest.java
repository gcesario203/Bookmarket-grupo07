package servico;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class IdGeneratorTest {

    @Before
    public void resetSingleton() {
        IdGenerator.resetInstance();
    }

    @Test
    public void testSingletonInstance() {
        IdGenerator instance1 = IdGenerator.getInstance();
        IdGenerator instance2 = IdGenerator.getInstance();

        assertNotNull(instance1);
        assertEquals(instance1, instance2);
    }

    @Test
    public void testGetNextReviewId() {
        IdGenerator idGenerator = IdGenerator.getInstance();

        int firstId = idGenerator.getNextReviewId();
        int secondId = idGenerator.getNextReviewId();
        int thirdId = idGenerator.getNextReviewId();

        assertEquals(2, firstId);
        assertEquals(3, secondId);
        assertEquals(4, thirdId);
    }

    @Test
    public void testThreadSafety() throws InterruptedException {
        IdGenerator idGenerator = IdGenerator.getInstance();

        final int threadCount = 100;
        Thread[] threads = new Thread[threadCount];
        int[] ids = new int[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            threads[i] = new Thread(() -> ids[index] = idGenerator.getNextReviewId());
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(threadCount, Arrays.stream(ids).distinct().count());
    }
}
