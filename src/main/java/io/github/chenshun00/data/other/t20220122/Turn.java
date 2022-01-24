package io.github.chenshun00.data.other.t20220122;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/24 9:44 上午
 */
public class Turn {

    /**
     * 罗马数字包含以下七种字符：I，V，X，L，C，D和M。
     * <p>
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写II，即为两个并列的 1。12 写做XII，即为XII。 27 写做XXVII, 即为XX+V+II。
     * <p>
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。
     * 同样地，数字 9 表示为IX。这个特殊的规则只适用于以下六种情况：
     * <p>
     * I可以放在V(5) 和X(10) 的左边，来表示 4 和 9。
     * X可以放在L(50)和C(100)的左边，来表示 40 和90。
     * C可以放在D(500) 和M(1000) 的左边，来表示400 和900。
     * 给你一个整数，将其转为罗马数字。
     * <p>
     */
    public static void main(String[] args) {
        Turn turn = new Turn();
//        System.out.println(turn.parser(3));
//        System.out.println(turn.parser(4));
//        System.out.println(turn.parser(9));
        System.out.println(turn.parser(58));
//        System.out.println(turn.parser(1994));
    }

    /**
     * 思路: 数字取于
     * 递归
     */
    public String intToRoman(int num) {
        return parser(num);
    }

    private String parser(int num) {
        if (num == 0) {
            return "";
        }
        if (num == 1) {
            return "I";
        }
        if (num >= 1000) {
            int n = num % 1000;
            int nm = num / 1000;
            return build(nm, "M") + parser(n);
        } else if (num >= 900) {
            int n = num % 900;
            int nm = num / 900;
            return build(nm, "CM") + parser(n);
        } else if (num >= 500) {
            int n = num % 500;
            int nm = num / 500;
            return build(nm, "D") + parser(n);
        } else if (num >= 400) {
            int n = num % 400;
            int nm = num / 400;
            return build(nm, "CD") + parser(n);
        } else if (num >= 100) {
            int n = num % 100;
            int nm = num / 100;
            return build(nm, "C") + parser(n);
        } else if (num >= 90) {
            int n = num % 90;
            int nm = num / 90;
            return build(nm, "XC") + parser(n);
        } else if (num >= 50) {
            int n = num % 50;
            int nm = num / 50;
            return build(nm, "L") + parser(n);
        } else if (num >= 40) {
            int n = num % 40;
            int nm = num / 40;
            return build(nm, "XL") + parser(n);
        } else if (num >= 10) {
            int n = num % 10;
            int nm = num / 10;
            return build(nm, "X") + parser(n);
        } else if (num >= 9) {
            int n = num % 9;
            int nm = num / 9;
            return build(nm, "IX") + parser(n);
        } else if (num >= 5) {
            int n = num % 5;
            int nm = num / 5;
            return build(nm, "V") + parser(n);
        } else if (num >= 4) {
            int n = num % 4;
            int nm = num / 4;
            return build(nm, "IV") + parser(n);
        } else if (num > 1) {
            return build(num, "I");
        }
        return "";
    }


    private String build(int n, String m) {
        return String.valueOf(m).repeat(Math.max(0, n));
    }

}
