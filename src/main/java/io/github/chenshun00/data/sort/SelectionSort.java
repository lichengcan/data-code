package io.github.chenshun00.data.sort;

import java.util.Arrays;

/**
 * 每次都选出一个最小的和起始位置的数进行互换
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/24 11:15 下午
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] array = {100, 9, 190, 361, 2, 7, 5};
        sortArray(array);
        System.out.println(Arrays.toString(array));
    }

    private static void sortArray(int[] array) {
        int min;
        for (int i = 0; i < array.length; i++) {
            min = i;
            for (int j = i; j < array.length; j++) {
                //找到最小的数字的下标
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            swap(array, i, min);
        }
    }

    private static void swap(int[] nums, int left, int right) {
        final int num = nums[left];
        nums[left] = nums[right];
        nums[right] = num;
    }

}
