package io.github.chenshun00.data.other.t20220213;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenshun00@gmail.com
 * @since 2022/2/13 7:59 下午
 */
public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        LengthOfLongestSubstring lengthOfLongestSubstring = new LengthOfLongestSubstring();
        System.out.println(lengthOfLongestSubstring.lengthOfLongestSubstring("abba"));
    }

    /**
     * 滑动窗口问题
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.length() <= 1) return s.length();
        Map<Character, Integer> index = new HashMap<>();
        //最长字串
        int max = 0;
        //滑动窗口偏移的位置
        int left = 0;
        final int length = s.length();
        for (int i = 0; i < length; i++) {
            final Character c = s.charAt(i);
            //如果二次包含该字符
            if (index.containsKey(c)) {
                final Integer first = index.get(c);
                //计算当前最大
                //abba [3] 滑动窗口现在要偏移到b这个位置.  需要比较一下left，放置left继续向右移动
                left = Math.max(first + 1, left);
            }
            index.put(c, i);
            //实际计数比下标要大1，所以+1
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

}
