import com.epam.jmp.task2.forLFU.FrequencyNode;
import com.epam.jmp.task2.forLFU.NodeList;
import com.epam.jmp.task2.impl.LFU;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing main LFU caching methods.
 */
public class LFUTest {

    final int testValue1 = 7;
    final int testValue2 = 0;
    final Optional<String> testValue3 = Optional.of("hello");

    @Test
    public void testLfuNodeList() {
        NodeList list = new NodeList();
        ArrayList<FrequencyNode> aList = new ArrayList<FrequencyNode>(Arrays.asList(new FrequencyNode(0),
                new FrequencyNode(1), new FrequencyNode(2), new FrequencyNode(3), new FrequencyNode(4),
                new FrequencyNode(5), new FrequencyNode(6)));
        list.append(aList.get(0));
        list.append(aList.get(1));
        list.prepend(aList.get(2));
        list.prepend(aList.get(3));
        list.insertAfter(aList.get(3), aList.get(4));
        list.insertAfter(aList.get(2), aList.get(5));
        list.insertAfter(aList.get(1), aList.get(6));
        assertEquals(testValue1, list.getLength());
        list.remove(aList.get(0));
        list.remove(aList.get(1));
        list.remove(aList.get(2));
        list.remove(aList.get(3));
        list.remove(aList.get(4));
        list.remove(aList.get(5));
        list.remove(aList.get(6));
        assertEquals(testValue2,list.getLength());
    }

    @Test
    public void testLfuCaching() {
        LFU<Integer, String> lfuCache = new LFU<>();
        lfuCache.put(123, "hello");
        assertEquals(lfuCache.get(123), testValue3);
    }
}
