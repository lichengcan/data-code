package io.github.chenshun00.data.other.t20220122;

import java.util.HashMap;
import java.util.Map;

/**
 * 回文四种解法
 * https://writings.sh/post/algorithm-longest-palindromic-substring
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/22 4:00 下午
 */
public class LongestPalindrome {


    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) return 0;
        int j = nums.length - 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                while (nums[j] == val && j > 0) {
                    j--;

                }
                if (i < j) {
                    nums[i] = nums[j];
                    nums[j] = val;
                }
            }
        }
        int length = nums.length;
        for (int num : nums) {
            if (num == val) {
                length--;
            }
        }
        return length;
    }


    Map<Integer, String> map = new HashMap<>();

    public String longestPalindrome(String s) {
        if (s.length() <= 1) {
            return s;
        }
        Integer integer = doLongestPalindrome(s);
        return map.get(integer);
    }

    public Integer doLongestPalindrome(String s) {
        if (s.length() <= 1) {
            map.put(1, s);
            return 1;
        }
        final int length = s.length();
        int max = 1;
        for (int i = 1; i <= length; i++) {
            final String substring = s.substring(0, i);
            if (isPalindrome(substring)) {
                map.putIfAbsent(substring.length(), substring);
                max = substring.length();
            }
        }
        return Math.max(max, doLongestPalindrome(s.substring(1)));
    }

    /**
     * 回文字符串
     */
    private boolean isPalindrome(String s) {
        if (s.length() <= 1) {
            return true;
        }
        final String reversal = reversal(s);
        return reversal.equals(s);
    }

    public int lengthOfLastWord(String s) {
        final String[] split = s.trim().split("\\s+");
        return split[split.length - 1].length();
    }

    /**
     * 反转字符串
     */
    private String reversal(String s) {
        if (s.length() == 1) {
            return s;
        }
        return s.substring(s.length() - 1) + reversal(s.substring(0, s.length() - 1));
    }

}
