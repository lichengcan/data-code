package io.github.chenshun00.data.other.t20220122;

/**
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 * <p>
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/23 12:55 下午
 */
public class Palindrome {

    public static void main(String[] args) {
        Palindrome palindrome = new Palindrome();
        System.out.println(palindrome.isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(palindrome.isPalindrome("raceacar"));

        // 48 -122
        System.out.println((int) ':');
    }

    public boolean isPalindrome(String s) {
        if (s.length() <= 1) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder(s.length());
        int length = s.length();
        for (int i = 0; i < length; i++) {
            final int c = s.charAt(i);
            if (c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122) {
                stringBuilder.append(s.charAt(i));
            }
        }
        s = stringBuilder.toString().toLowerCase();

        length = s.length();
        //true 偶数 false奇数
        boolean bool = length % 2 == 0;
        var middle = length / 2;
        String first, second;
        first = s.substring(0, middle);
        if (bool) {
            second = s.substring(middle);
        } else {
            second = s.substring(middle + 1);
        }
        int i = 0, j = first.length() - 1;
        while (i < first.length() && j >= 0) {
            final char f1 = first.charAt(j);
            final char s1 = second.charAt(i);

            if (f1 != s1) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

}
