package io.github.chenshun00.data.other.t20220122;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/22 11:59 上午
 */
public class AddTwoNumbers {

    /**
     * https://leetcode-cn.com/problems/container-with-most-water/
     * <p>
     * 双for可以处理完，但是超时了.O(n^2)
     * 这里一个典型的优化，就是将两个for循环优化成一个for循环，具体是使用双指针的方式，利用不同的case，去改变不同的length. 以后遇到双for循环的
     * 都应该回想起是不是可以使用双指针来进行优化for循环，降低成一个for循环
     */
    public int maxArea(int[] height) {
        //求最大的长方形 = 长*高
        int max = 0;
        int length = height.length - 1;
        for (int i = 0; i < length; ) {
            //长 = length-i
            //宽 = min(最低侧为宽)
            int chang = (length - i);
            int kuan = Math.min(height[length], height[i]);
            max = Math.max(max, chang * kuan);
            if (height[length] > height[i]) {
                i++;
            } else {
                length--;
            }
        }
        return max;
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String zz = strs[0];
        int min = zz.length();
        for (String str : strs) {
            int temp = str.length();
            if (min > temp) {
                zz = str;
                min = temp;
            }
        }
        final int length = zz.length();
        for (int i = length; i > 0; i--) {
            final String substring = zz.substring(0, i);
            boolean ok = true;
            for (String str : strs) {
                ok = str.startsWith(substring);
                if (!ok) {
                    break;
                }
            }
            if (ok) {
                return substring;
            }
        }
        return "";
    }

    public int searchInsert(int[] nums, int target) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
            if (target > nums[i]) {
                index++;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        ListNode first = new ListNode();
        {
            int[] xx = {5};
            ListNode p = first;
            for (int i : xx) {
                p.next = new ListNode(i);
                p = p.next;
            }
        }
        ListNode second = new ListNode();
        {
            int[] xx = {5};
            ListNode p = second;
            for (int i : xx) {
                p.next = new ListNode(i);
                p = p.next;
            }
        }

        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        final ListNode listNode = addTwoNumbers.addTwoNumbers(first.next, second.next);
        System.out.println(1);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pp = new ListNode();
        ListNode tt = pp;
        ListNode p1 = l1, p2 = l2;
        boolean need = false;
        while (p1 != null && p2 != null) {
            final int p1Val = p1.val;
            final int p2Val = p2.val;
            int count = p1Val + p2Val;
            if (need) {
                count = count + 1;
            }
            if (count < 10) {
                tt.next = new ListNode(count);
                need = false;
            } else {
                need = true;
                count = count - 10;
                tt.next = new ListNode(count);
            }
            p1 = p1.next;
            p2 = p2.next;
            tt = tt.next;
        }
        if (p1 != null) {
            while (p1 != null) {
                int count = p1.val;
                if (need) {
                    count = count + 1;
                }
                if (count < 10) {
                    tt.next = new ListNode(count);
                    need = false;
                } else {
                    need = true;
                    count = count - 10;
                    tt.next = new ListNode(count);
                }
                p1 = p1.next;
                tt = tt.next;
            }
            if (need) {
                tt.next = new ListNode(1);
                need = false;
            }
        }
        if (p2 != null) {
            while (p2 != null) {
                int count = p2.val;
                if (need) {
                    count = count + 1;
                }
                if (count < 10) {
                    tt.next = new ListNode(count);
                    need = false;
                } else {
                    need = true;
                    count = count - 10;
                    tt.next = new ListNode(count);
                }
                p2 = p2.next;
                tt = tt.next;
            }
            if (need) {
                tt.next = new ListNode(1);
                need = false;
            }
        }
        if (need) {
            tt.next = new ListNode(1);
        }
        return pp.next;
    }
}
