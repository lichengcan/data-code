package io.github.chenshun00.data.sort;

import java.util.Arrays;

/**
 * 两两比较. 排列出一个数组
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/24 11:10 下午
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {100, 9, 190, 361, 2, 7, 5};
        BubbleSort.sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int ai = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int aj = nums[j];
                if (ai > aj) {
                    swap(nums, i, j);
                }
            }
        }
    }

    private static void swap(int[] nums, int left, int right) {
        final int num = nums[left];
        nums[left] = nums[right];
        nums[right] = num;
    }

}
