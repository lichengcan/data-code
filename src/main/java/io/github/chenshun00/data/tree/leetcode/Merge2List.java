package io.github.chenshun00.data.tree.leetcode;

/**
 * @author luobo.cs@raycloud.com
 * @since 2022/1/8 3:45 下午
 */
public class Merge2List {

    public static void main(String[] args) {
        ListNode first = new ListNode(1);
        {
            final ListNode listNode = new ListNode(2);
            first.next = listNode;
            listNode.next = new ListNode(4);
        }
        ListNode second = new ListNode(1);
        {
            final ListNode listNode = new ListNode(3);
            final ListNode listNode2 = new ListNode(4);
            second.next = listNode;
            listNode.next = listNode2;
            listNode2.next = new ListNode(10086);
        }
        Merge2List merge2List = new Merge2List();
        final ListNode listNode = merge2List.mergeTwoLists(first, second);
        listNode.traverse();
    }

    public ListNode mergeTwoLists(ListNode first, ListNode second) {
        if (first == null && second == null) {
            return null;
        }
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        ListNode listNode = new ListNode(-1);
        ListNode p = listNode;
        do {
            //比较小值 获取对象
            int firstVal = first.val;
            int secondVal = second.val;

            boolean _f = firstVal <= secondVal;
            if (_f) {
                p.next = first;
                first = first.next;
            } else {
                p.next = second;
                second = second.next;
            }
            p = p.next;
        } while (first != null && second != null);
        if (first != null) {
            p.next = first;
        }
        if (second != null) {
            p.next = second;
        }
        return listNode.next;
    }

    public static class ListNode {
        public int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
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
