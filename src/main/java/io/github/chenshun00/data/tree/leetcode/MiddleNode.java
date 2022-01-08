package io.github.chenshun00.data.tree.leetcode;

/**
 * https://leetcode-cn.com/problems/middle-of-the-linked-list
 * <p>
 * 876. 链表的中间结点
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/8 5:37 下午
 */
public class MiddleNode {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode p = listNode;
        for (int i = 2; i <= 5; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }
        MiddleNode middleNode = new MiddleNode();
        System.out.println(middleNode.middleNode(listNode).val);
        System.out.println(middleNode.quick(listNode).val);
    }

    public ListNode quick(ListNode listNode) {
        ListNode p1 = listNode;
        ListNode p2 = listNode;
        assert p2 != null;
        while (p1 != null && p1.next != null) {
            p1 = p1.next.next;
            p2 = p2.next;
        }
        return p2;
    }

    public ListNode middleNode(ListNode head) {
        final int count = getCount(head);
        //偶数2只 [1,2,3,4,5,6] 值分别为 3 和 4
        //奇数1只 [1,2,3,4,5]==>值为3

        boolean a = count % 2 == 0;
        int value = count / 2;
        while (true) {
            if (value <= 0) {
                return head;
            }
            head = head.next;
            value--;
        }
    }

    private int getCount(ListNode head) {
        int all = 0;
        while (head != null) {
            all++;
            head = head.next;
        }
        return all;
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
