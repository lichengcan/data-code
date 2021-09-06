package io.github.chenshun00.data.list;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author luobo.cs@raycloud.com
 * @since 2021/9/6 9:21 上午
 */
public class LinkedListImplTest {

    @Test
    public void testLinkedListImpl() {
        LinkedListImpl<String> linkedList = new LinkedListImpl<>();
        linkedList.add("first");
        linkedList.add("second");
        Assert.assertEquals("first", linkedList.get(0));
        Assert.assertEquals("second", linkedList.get(1));
        Assert.assertEquals(2, linkedList.size());
        Assert.assertTrue(linkedList.contains("first"));
        Assert.assertTrue(linkedList.contains("second"));
        Assert.assertTrue(linkedList.remove("first"));
        Assert.assertFalse(linkedList.contains("first"));
        Assert.assertEquals(1, linkedList.size());
        for (int i = 0; i < 10000; i++) {
            linkedList.add("first" + i);
        }
        Assert.assertEquals(10001, linkedList.size());
    }
}