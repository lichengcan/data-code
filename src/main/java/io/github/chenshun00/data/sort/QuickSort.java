package io.github.chenshun00.data.sort;

import java.util.Arrays;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/24 12:29 下午
 */
public class QuickSort {

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] array = {100, 9, 190, 361, 2, 7, 5};
        quickSort.sortArray(array);
        System.out.println(Arrays.toString(array));
    }

    public int[] sortArray(int[] nums) {
        doSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void doSort(int[] nums, int start, int end) {
        if (start > end) {
            return;
        }
        int middle = find(nums, start, end);
        doSort(nums, start, middle - 1);
        doSort(nums, middle + 1, end);
    }

    private int find(int[] nums, int left, int right) {
        final int bz = nums[left];
        for (int i = left; i <= right; i++) {
            int curStart = nums[i];
            if (curStart > bz) {
                for (int j = right; j >= i; j--) {
                    int end = nums[j];
                    if (end > bz) {
                        right--;
                    } else {
                        swap(nums, i, j);
                        right--;
                        break;
                    }
                }
            }
        }
        swap(nums, left, right);
        return right;
    }

    private void swap(int[] nums, int left, int right) {
        final int num = nums[left];
        nums[left] = nums[right];
        nums[right] = num;
    }

}
