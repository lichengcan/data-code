package io.github.chenshun00.data.other.t20220122;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/22 11:59 上午
 */
public class AddTwoNumbers {

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
