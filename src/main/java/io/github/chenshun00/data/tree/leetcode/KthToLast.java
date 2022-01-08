package io.github.chenshun00.data.tree.leetcode;

/**
 * https://leetcode-cn.com/problems/kth-node-from-end-of-list-lcci/
 * <p>
 * 实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/8 5:19 下午
 */
public class KthToLast {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode p = listNode;
        for (int i = 2; i <= 5; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }
        KthToLast kthToLast = new KthToLast();
        System.out.println(kthToLast.kthToLast(listNode, 2));
        System.out.println(kthToLast.quick(listNode, 2));
    }

    public int quick(ListNode head, int k) {
        ListNode p = head;

        for (int i = 0; i < k; i++) {
            p = p.next;
        }
        ListNode p2 = head;
        assert p2 != null;
        while (p != null) {
            p = p.next;
            p2 = p2.next;
        }
        return p2.val;
    }

    /**
     * 输入： 1->2->3->4->5 和 k = 2
     * 输出： 4
     */
    public int kthToLast(ListNode head, int k) {
        int all = getCount(head);
        int count = all - k;
        while (true) {
            if (count <= 0) {
                return head.val;
            }
            count--;
            head = head.next;
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

        ListNode(int x) {
            val = x;
        }
    }

}
