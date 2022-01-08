package io.github.chenshun00.data.queue;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chenshun00@gmail.com
 * @since 2021/9/6 9:21 上午
 */
public class LinkedListQueueTest {

    @Test
    public void testLinkedListQueue() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.push("111");
        queue.push("222");
        queue.push("333");
        queue.push("444");
        Assert.assertEquals(4, queue.size());
        Assert.assertEquals("111", queue.poll());
        Assert.assertEquals("222", queue.poll());
        Assert.assertEquals("333", queue.poll());
        Assert.assertEquals("444", queue.poll());
        Assert.assertNull(queue.poll());
        Assert.assertEquals(0, queue.size());
    }
}