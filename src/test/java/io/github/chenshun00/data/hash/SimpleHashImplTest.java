package io.github.chenshun00.data.hash;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author luobo.cs@raycloud.com
 * @since 2021/9/4 5:15 下午
 */
public class SimpleHashImplTest {

    //可以感受到hash冲突对于put性能的严重影响
    //如果容量是8，put10万次和容量是1008，put10w次的速度不是一个量级的，主要还是链表对于性能的影响
    private final Hash<String, String> simple = new SimpleHashImpl<>(1008);

    @Test
    public void put() {
        String put = simple.put("first", "first");
        Assert.assertNull(put);
        Assert.assertEquals(1, simple.size());
        put = simple.put("first", "reFirst");
        Assert.assertNotNull(put);
        Assert.assertEquals(1, simple.size());
        for (int i = 0; i < 100000; i++) {
            simple.put("first" + i, "first" + i);
        }
        Assert.assertEquals(100001, simple.size());
        Assert.assertEquals("first88", simple.get("first88"));

        final String first88 = simple.remove("first88");
        Assert.assertEquals("first88", first88);
        Assert.assertEquals(100000, simple.size());
    }
}