package io.github.chenshun00.data.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author chenshun00@gmail.com
 * @since 2021/8/22 11:33 上午
 */
public class CeilTest {

    @Test
    public void testAnyMatch() {
        int[] values = {1, 2, 3, 4};
        final boolean b = Arrays.stream(values).anyMatch(value -> value == 2);
        Assert.assertTrue(b);
    }
}
