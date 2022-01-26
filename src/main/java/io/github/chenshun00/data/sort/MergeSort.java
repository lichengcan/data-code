package io.github.chenshun00.data.sort;

import java.util.Arrays;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/24 11:49 下午
 */
public class MergeSort {

    static int[] temp;

    public static void main(String[] args) {
        int[] array = {100, 9, 190, 361, 2, 7, 5, 1};
        temp = new int[array.length];
        mergeSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    private static void mergeSort(int[] src,
                                  int low,
                                  int high) {
        if (low >= high) return;
        int mid = (low + high) / 2;
        mergeSort(src, low, mid);
        mergeSort(src, mid + 1, high);

        merge(src, low, mid, mid + 1, high);
    }

    private static void merge(int[] src, int low, int mid, int highStart, int high) {

        //将大小区间赋值到临时数组
        for (int k = low; k <= high; k++) {
            temp[k] = src[k];
        }
        for (int k = low; k <= high; k++) {
            //进入归并的时候，数组1和数组2的是顺序递增的
            //数组1从【low-----mid】
            //数字2从【highStart-----high】
            //如果数组1大于数组2的数据. 那么当前下标，应该使用数组2的数据
            //这里的处理是为了解决如果前边一个数组全部小于下一个数组，low会一直加，直到大于mid,那么应该把下一个数组的数据高过去
            //如果前边一个数组全部大于>下一个数组，highStart会一直加，直到大于high,把前边的补充到后边
            if (low > mid) {
                src[k] = temp[highStart++];
            } else if (highStart > high) {
                src[k] = temp[low++];
            } else if (temp[low] > temp[highStart]) {
                src[k] = temp[highStart++];
            } else {
                src[k] = temp[low++];
            }
        }
    }


}
