package io.github.chenshun00.data.tree.leetcode;

import java.util.Objects;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 * <p>
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 * <p>
 * 这个问题有点扯淡，他并不是让你判断node节点的值是不是相等，而是判断内存和值想不想等. 不能直接用var来做.
 * <p>
 * 我的想法是用栈反转. 看评论区还有更好的方式来做. 利用尾部指针的方式..
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/8 9:59 下午
 */
public class GetIntersectionNode {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        Stack<ListNode> aa = new Stack<>();
        while (a != null) {
            aa.push(a);
            a = a.next;
        }
        Stack<ListNode> bb = new Stack<>();
        while (b != null) {
            bb.push(b);
            b = b.next;
        }
        ListNode ff = null;
        while (!aa.isEmpty() && !bb.isEmpty()) {
            final ListNode a1 = aa.pop();
            final ListNode b1 = bb.pop();
            if (a1 == b1) {
                ff = a1;
            }
        }
        return ff;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ListNode)) return false;
            ListNode listNode = (ListNode) o;
            return val == listNode.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }
}
