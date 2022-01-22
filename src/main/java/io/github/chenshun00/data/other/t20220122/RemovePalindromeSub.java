package io.github.chenshun00.data.other.t20220122;

/**
 * 删除回文子序列
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/22 3:16 下午
 */
public class RemovePalindromeSub {

    /**
     * 给你一个字符串s，它仅由字母'a' 和 'b'组成。每一次删除操作都可以从 s 中删除一个回文 子序列。
     * <p>
     * 返回删除给定字符串中所有字符（字符串为空）的最小删除次数。
     * <p>
     * 「子序列」定义：如果一个字符串可以通过删除原字符串某些字符而不改变原字符顺序得到，那么这个字符串就是原字符串的一个子序列。
     * <p>
     * 「回文」定义：如果一个字符串向后和向前读是一致的，那么这个字符串就是一个回文。
     * <p>
     * <p>
     * 输入：s = "ababa"
     * 输出：1
     * 解释：字符串本身就是回文序列，只需要删除一次。
     * </p>
     * <p>
     * 输入：s = "abb"
     * 输出：2
     * 解释："abb" -> "bb" -> "".
     * 先删除回文子序列 "a"，然后再删除 "bb"。
     * </p>
     * <p>
     * 输入：s = "baabb"
     * 输出：2
     * 解释："baabb" -> "b" -> "".
     * 先删除回文子序列 "baab"，然后再删除 "b"。
     * </p>
     * <p>
     * 评论: 傻逼题目, 这个题目没有说只能删除连续的字符. 所以题目就变成了判断是否是回文字符串
     * <p>
     * 如果题目难一点: 必须是删除连续的回文字符串呢
     */
    public int removePalindromeSub(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (isPalindrome(s)) {
            return 1;
        }
        return 2;
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
