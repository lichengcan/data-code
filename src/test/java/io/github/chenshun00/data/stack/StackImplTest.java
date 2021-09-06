package io.github.chenshun00.data.stack;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author luobo.cs@raycloud.com
 * @since 2021/9/6 9:21 上午
 */
public class StackImplTest {

    @Test
    public void testStackImpl() {
        StackImpl<String> testStack = new StackImpl<>();
        for (int i = 0; i < 16; i++) {
            testStack.push(i + "zz");
        }
        Assert.assertEquals(16, testStack.size());
        Assert.assertEquals("15zz", testStack.pop());
        Assert.assertEquals("14zz", testStack.pop());
        Assert.assertEquals("13zz", testStack.pop());
        Assert.assertEquals("12zz", testStack.pop());
        Assert.assertEquals(12, testStack.size());
    }
}