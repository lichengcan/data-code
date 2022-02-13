package io.github.chenshun00.data.other.t20220213;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenshun00@gmail.com
 * @since 2022/2/13 7:18 下午
 */
public class TwoSum {

    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        int[] xx = {2, 7, 11, 15};
        final int[] ints = twoSum.twoSumChange(xx, 22);
        System.out.println(1);
    }

    /**
     * 这里是返回下标, 如果是要返回对于的数字，还可以使用双指针来做
     * <p>
     * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * <p>
     * 你可以按任意顺序返回答案。
     */
    public int[] twoSum(int[] nums, int target) {
        int[] xx = new int[2];

        Map<Integer, Integer> ff = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (ff.containsKey(nums[i])) {
                xx[0] = i;
                xx[1] = ff.get(nums[i]);
            } else {
                ff.put(target - nums[i], i);
            }
        }
        return xx;
    }

    public int[] twoSumChange(int[] nums, int target) {
        int[] xx = new int[2];

        Arrays.sort(nums);

        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            final int first = nums[start];
            final int second = nums[end];
            int count = first + second;
            if (count == target) {
                xx[0] = first;
                xx[1] = second;
                break;
            }
            if (count > target) {
                end--;
            } else {
                start++;
            }
        }
        return xx;
    }

}
