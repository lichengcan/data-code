package io.github.chenshun00.data.other.t20220213;

/**
 * @author chenshun00@gmail.com
 * @since 2022/2/13 7:34 下午
 */
public class AddTwoNumbers {

    public static void main(String[] args) {
        ListNode first = new ListNode();
        {
            int[] xx = {2, 4, 3};
            ListNode p = first;
            for (int i : xx) {
                p.next = new ListNode(i);
                p = p.next;
            }
        }
        ListNode second = new ListNode();
        {
            int[] xx = {5, 6, 4};
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

        ListNode ff = new ListNode();
        ListNode temp = ff;
        int carry = 0;

        while (l1 != null || l2 != null) {
            int first = 0, second = 0;
            if (l1 != null) {
                first = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                second = l2.val;
                l2 = l2.next;
            }
            int count = first + second + carry;
            carry = count / 10;
            temp.next = new ListNode(count % 10);
            temp = temp.next;
        }
        if (carry > 0) {
            temp.next = new ListNode(1);
        }
        return ff.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
