package io.github.chenshun00.data.tree.leetcode;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle-ii/
 * <p>
 * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/8 8:49 下午
 */
public class DetectCycle {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode p = listNode;
        for (int i = 2; i <= 5; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }
        p.next = listNode.next;
        DetectCycle cycleList = new DetectCycle();
        System.out.println(cycleList.detectCycle(listNode));
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode p1 = head, p2 = head;
        while (p1 != null && p1.next != null) {
            p1 = p1.next.next;
            p2 = p2.next;
            if (p1 == p2) {
                break;
            }
        }
        if (p1 == null || p1.next == null) {
            return null;
        }
        p2 = head;
        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
}
