package io.github.chenshun00.data.list;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chenshun00@gmail.com
 * @since 2021/9/6 9:21 上午
 */
public class ArrayListImplTest {

    @Test
    public void testArrayList() {
        ArrayListImpl<String> arrayList = new ArrayListImpl<>(16);
        arrayList.add("first");
        arrayList.add("second");
        Assert.assertEquals("first", arrayList.get(0));
        Assert.assertEquals("second", arrayList.get(1));
        Assert.assertEquals(2, arrayList.size());
        Assert.assertTrue(arrayList.contains("first"));
        Assert.assertTrue(arrayList.contains("second"));
        Assert.assertTrue(arrayList.remove("first"));
        Assert.assertFalse(arrayList.contains("first"));
        Assert.assertEquals(1, arrayList.size());
    }
}