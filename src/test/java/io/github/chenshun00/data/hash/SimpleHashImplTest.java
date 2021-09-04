package io.github.chenshun00.data.hash;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chenshun00@gmail.com
 * @since 2021/9/4 5:15 下午
 */
public class SimpleHashImplTest {

    //可以感受到hash冲突对于put性能的严重影响
    //如果容量是8，put10万次和容量是1008，put10w次的速度不是一个量级的，主要还是链表对于性能的影响
    private final Hash<String, String> fast = new SimpleHashImpl<>(1008);
    private final Hash<String, String> slow = new SimpleHashImpl<>(8);

    @Test
    public void fast() {
        String put = fast.put("first", "first");
        Assert.assertNull(put);
        Assert.assertEquals(1, fast.size());
        put = fast.put("first", "reFirst");
        Assert.assertNotNull(put);
        Assert.assertEquals(1, fast.size());
        for (int i = 0; i < 100000; i++) {
            fast.put("first" + i, "first" + i);
        }
        Assert.assertEquals(100001, fast.size());
        Assert.assertEquals("first88", fast.get("first88"));

        final String first88 = fast.remove("first88");
        Assert.assertEquals("first88", first88);
        Assert.assertEquals(100000, fast.size());
    }

    @Test
    public void slow() {
        String put = slow.put("first", "first");
        Assert.assertNull(put);
        Assert.assertEquals(1, slow.size());
        put = slow.put("first", "reFirst");
        Assert.assertNotNull(put);
        Assert.assertEquals(1, slow.size());
        for (int i = 0; i < 100000; i++) {
            slow.put("first" + i, "first" + i);
        }
        Assert.assertEquals(100001, slow.size());
        Assert.assertEquals("first88", slow.get("first88"));

        final String first88 = slow.remove("first88");
        Assert.assertEquals("first88", first88);
        Assert.assertEquals(100000, slow.size());
    }
}