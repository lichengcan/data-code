package io.github.chenshun00.data.sort;

import java.util.Arrays;

/**
 * @author chenshun00@gmail.com
 * @since 2022/4/4 4:19 PM
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] array = {100, 9, 190, 361, 2, 7, 5};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    private static void sort(int[] arr) {
        buildHeap(arr);

        //2.调整堆结构+交换堆顶元素与末尾元素
        for (int j = arr.length - 1; j > 0; j--) {
            //将堆顶元素与末尾元素进行交换
            if (arr[0] > arr[j]) {
                swap(arr, 0, j);
            }
            //重新对堆进行调整
            heapify(arr, j, 0);
        }
    }

    private static void buildHeap(int[] tree) {
        int lastNode = tree.length - 1;
        int parent = (lastNode - 1) / 2;
        for (int i = parent; i >= 0; i--) {
            heapify(tree, tree.length, i);
        }
    }

    private static void heapify(int[] tree, int length, int i) {
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        int max = i;
        if (left < length && tree[max] < tree[left]) {
            max = left;
        }

        if (right < length && tree[max] < tree[right]) {
            max = right;
        }
        if (max != i) {
            swap(tree, max, i);
        }
    }

    private static void swap(int[] tree, int i, int j) {
        int temp = tree[i];
        tree[i] = tree[j];
        tree[j] = temp;
    }

}
