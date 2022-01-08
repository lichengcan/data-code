package io.github.chenshun00.data.queue;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chenshun00@gmail.com
 * @since 2021/9/6 9:21 上午
 */
public class ArrayListQueueTest {

    @Test
    public void testArrayListQueue() {
        ArrayListQueue<String> queue = new ArrayListQueue<>(3);
        queue.push("11");
        queue.push("22");
        queue.push("33");
        Assert.assertEquals(3, queue.size());
        Assert.assertEquals("11", queue.poll());
        Assert.assertEquals(2, queue.size());
        Assert.assertEquals("22", queue.poll());
        Assert.assertEquals("33", queue.poll());
        Assert.assertNull(queue.poll());
        queue.push("44");
        queue.push("55");
        queue.push("66");
        Assert.assertEquals(3, queue.size());
        Assert.assertEquals("44", queue.poll());
        Assert.assertEquals("55", queue.poll());
        Assert.assertEquals("66", queue.poll());
        Assert.assertEquals(0, queue.size());
    }

    @Test
    public void testWait() throws InterruptedException {
        ArrayListQueue<String> queue = new ArrayListQueue<>(3);
        new Thread(() -> {
            try {
                Thread.sleep(5000L);
                queue.push("cc");
            } catch (Exception ignore) {
            }
        }).start();
        final String take = queue.take();
        Assert.assertNotNull(take);
        Assert.assertEquals("cc", take);
    }
}