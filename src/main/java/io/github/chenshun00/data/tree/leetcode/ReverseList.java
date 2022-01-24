package io.github.chenshun00.data.tree.leetcode;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * <p>
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * <p>
 * 采用了最笨的办法实现的，需要仔细控制最后的分支条件，得画图理解
 * <p>
 * 绝的是，还可以利用Java的stack来实现
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/8 11:17 下午
 */
public class ReverseList {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode p = listNode;
        for (int i = 2; i <= 6; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }
        ReverseList reverseList = new ReverseList();
//        {
//            final ListNode xx = reverseList.reverseList(listNode);
//            xx.traverse();
//        }
//        {
//            final ListNode xx = reverseList.quick(listNode);
//            xx.traverse();
//        }
        {
            final ListNode xx = reverseList.reverseList(listNode);
            xx.traverse();
        }
    }

    public ListNode reverseList(ListNode root) {
        if (root == null) {
            return root;
        }
        ListNode tt = root;
        ListNode prev = null;
        while (tt != null) {
            final ListNode next = tt.next;
            tt.next = prev;
            prev = tt;
            tt = next;
        }

        return prev;
    }

    /**
     * 递归实现
     * head=5.head.next=6
     */
    public ListNode recursion(ListNode head) {
        if (head.next == null) return head;
        //1-->2-->3-->4-->5-->6-->NULL
        //6-->5-->4-->3-->2-->1-->NULL
        //以前是1-->2
        //t==1
        //head=5. head.next=6
        //last=6
        final ListNode last = recursion(head.next);
        //5.next=(6) 6.next=6;
        head.next.next = head;
        head.next = null;
        return last;
    }


    /**
     * 巧妙利用Java栈实现
     */
    public ListNode quick(ListNode head) {
        Stack<ListNode> listNodes = new Stack<>();
        ListNode t = head;
        while (t != null) {
            listNodes.push(new ListNode(t.val));
            t = t.next;
        }
        ListNode listNode = new ListNode();
        ListNode tt = listNode;

        while (!listNodes.isEmpty()) {
            tt.next = listNodes.pop();
            tt = tt.next;
        }
        return listNode.next;
    }


//    /**
//     * 每次取出链表最后一个节点放到新链表上. 有些傻，颇有硬编码的意思
//     */
//    public ListNode reverseList(ListNode head) {
//        if (head == null) return null;
//        if (head.next == null) return head;
//        ListNode p = new ListNode();
//        doSet(p, head);
//        return p.next;
//    }
//
//    private void doSet(ListNode p, ListNode head) {
//        if (head == null) {
//            return;
//        }
//        if (head.next == null) {
//            p.next = head;
//            return;
//        }
//        ListNode q = head;
//        while (q.next != null) {
//            q = q.next;
//            if (q.next == null) {
//                p.next = q;
//            }
//        }
//        ListNode q1 = head;
//        ListNode tt = new ListNode();
//        ListNode f = tt;
//        while (q1.next != null) {
//            if (q1.next.next == null) {
//                q1.next = null;
//                f.next = q1;
//                break;
//            }
//            f.next = q1;
//            f = f.next;
//            q1 = q1.next;
//        }
//        head = tt.next;
//        doSet(p.next, head);
//    }

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

        public void traverse() {
            System.out.println(val);
            if (next == null) {
                return;
            }
            ListNode temp = next;
            while (temp != null) {
                System.out.println(temp.val);
                temp = temp.next;
            }
        }

    }

}
