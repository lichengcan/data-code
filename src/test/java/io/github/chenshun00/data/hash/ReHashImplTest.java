package io.github.chenshun00.data.hash;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chenshun00@gmail.com
 * @since 2021/9/4 8:35 下午
 */
public class ReHashImplTest {

    private final Hash<String, String> hash = new ReHashImpl<>(8);

    @Test
    public void put() {
        String key = "chenshun00";
        final String put = hash.put(key, "first");
        Assert.assertNull(put);
        final String result = hash.get(key);
        Assert.assertNotNull(result);
        Assert.assertEquals("first", result);
        Assert.assertEquals(1, hash.size());

        for (int i = 0; i < 10; i++) {
            Assert.assertNull(hash.put("chenshun00" + i, "chenshun00" + i));
        }
        Assert.assertEquals(11, hash.size());
    }

    @Test
    public void more() {
        String key = "chenshun00";
        final String put = hash.put(key, "first");
        Assert.assertNull(put);
        final String result = hash.get(key);
        Assert.assertNotNull(result);
        Assert.assertEquals("first", result);
        Assert.assertEquals(1, hash.size());

        for (int i = 0; i < 1000000; i++) {
            Assert.assertNull(hash.put("chenshun00" + i, "chenshun00" + i));
        }
        Assert.assertEquals(1000001, hash.size());
    }
}