package io.github.chenshun00.data.search;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/24 11:50 下午
 */
public class Order {

    public static int order(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

}
