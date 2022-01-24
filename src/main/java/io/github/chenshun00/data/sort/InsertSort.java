package io.github.chenshun00.data.sort;

import java.util.Arrays;

/**
 * https://zhuanlan.zhihu.com/p/42586566
 * <p>
 * 插入排序:
 * <p>
 * 把待排序的数组分成已排序和未排序两部分，初始的时候把第一个元素认为是已排好序的。
 * 从第二个元素开始，在已排好序的子数组中寻找到该元素合适的位置并插入该位置。
 * 重复上述过程直到最后一个元素被插入有序子数组中。
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/24 11:25 下午
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] array = {100, 9, 190, 361, 2, 7, 5};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    //也得两次循环
    private static void sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            //开始要进行排列的数据
            int start = array[i];
            int f = i;
            //0-index的数据都是排列好的
            for (int j = i - 1; j >= 0; j--) {
                if (array[j] > start) {
                    swap(array, f, j);
                    f--;
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
