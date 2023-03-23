import com.epam.jmp.task2.forLRU.DoublyLinkedList;
import com.epam.jmp.task2.impl.LRU;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing main LRU caching methods.
 */
public class LRUTest {

    final int testValue1 = 2;
    final int testValue2 = 0;
    final Optional<String> testValue3 = Optional.of("hello");

    @Test
    public void testLRUCaching() {
        LRU<Integer, String> lruCache = new LRU<>();
        lruCache.put(123, "hello");
        Optional<String> resultValue = lruCache.get(123);
        assertEquals(resultValue, testValue3);
    }

    @Test
    public void testLRUNodeList() {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();
        doublyLinkedList.add(123);
        doublyLinkedList.add(432);
        System.out.println();
        assertEquals(doublyLinkedList.size(), testValue1);
        doublyLinkedList.clear();
        assertEquals(doublyLinkedList.size(), testValue2);
    }
}
