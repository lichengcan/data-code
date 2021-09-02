package io.github.chenshun00.data.test;

/**
 * @author chenshun00@gmail.com
 * @since 2021/8/22 11:33 上午
 */
public class CeilTest {

    public static void main(String[] args) {
        System.out.println();
        final int ceil = (int) Math.ceil(5 / 2.0) ;
        System.out.println(ceil);
        System.out.println(Math.ceil(5 / 2)-1);

        System.out.println((int)Math.ceil(2.3));
    }

}
