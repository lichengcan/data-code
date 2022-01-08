package io.github.chenshun00.data.tree.leetcode;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle/
 * <p>
 * 给你一个链表的头节点 head ，判断链表中是否有环。
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/8 8:42 下午
 */
public class CycleList {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode p = listNode;
        for (int i = 2; i <= 5; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }
        p.next = listNode;
        CycleList cycleList = new CycleList();
        System.out.println(cycleList.hasCycle(listNode));
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode p1 = head, p2 = head;
        try {
            while (p1 != null) {
                p1 = p1.next.next;
                p2 = p2.next;
                if (p1 == p2) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

}
