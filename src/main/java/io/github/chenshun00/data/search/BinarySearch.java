package io.github.chenshun00.data.search;

/**
 * 二分
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/24 11:51 下午
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] xx = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100};
        System.out.println(binary(xx, 100));
    }


    public static int binary(int[] nums, int target) {
        int end = nums.length - 1;
        int start = 0;
        while (end >= start) {
            int middle = (start + end) / 2;
            if (nums[middle] == target) {
                return middle;
            }
            if (nums[middle] > target) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }
        return -1;
    }
}
