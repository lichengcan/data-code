package io.github.chenshun00.data.other.t20220122;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/comments/
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/22 3:36 下午
 */
public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        LengthOfLongestSubstring lengthOfLongestSubstring = new LengthOfLongestSubstring();
        System.out.println(lengthOfLongestSubstring.lengthOfLongestSubstring("dvdf"));
    }

    /**
     * a指针放0，b指针后移，出现重复，开始移动a指针到重复到地方.一直到结束
     */
    public int doublePoint(String s) {
        int start = 0;
        int end = 0;
        final char[] chars = s.toCharArray();

        return 0;
    }

    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        final char[] chars = s.toCharArray();
        int max = 0;
        Set<Character> characters = new HashSet<>();
        for (char aChar : chars) {
            final boolean add = characters.add(aChar);
            if (!add) {
                max = characters.size();
                break;
            }
        }
        if (characters.size() == chars.length) {
            return characters.size();
        }
        return Math.max(max, lengthOfLongestSubstring(s.substring(1)));
    }

}
