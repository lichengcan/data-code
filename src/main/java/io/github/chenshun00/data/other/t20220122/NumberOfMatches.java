package io.github.chenshun00.data.other.t20220122;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/25 9:27 上午
 */
public class NumberOfMatches {

    public static void main(String[] args) {
        NumberOfMatches numberOfMatches = new NumberOfMatches();
        System.out.println(numberOfMatches.numberOfMatches(7));
        System.out.println(numberOfMatches.numberOfMatches(14));
        System.out.println(numberOfMatches.numberOfMatches(15));
    }

    public int numberOfMatches(int n) {
        int count = 0;
        while (n != 1) {
            if (isBool(n)) {
                n = n / 2;
                count += n;
            } else {
                n = n / 2;
                count += n;
                n = n + 1;
            }
        }
        return count;
    }

    private boolean isBool(int n) {
        return n % 2 == 0;
    }

}
