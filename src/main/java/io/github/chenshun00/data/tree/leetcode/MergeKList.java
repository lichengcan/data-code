package io.github.chenshun00.data.tree.leetcode;

import java.util.PriorityQueue;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/8 4:50 下午
 */
public class MergeKList {

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
        MergeKList mergeKList = new MergeKList();
        final ListNode listNode = mergeKList.mergeKLists(new ListNode[]{first, second});
        listNode.traverse();
    }

    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(100, (o1, o2) -> o1 - o2);
        for (ListNode list : lists) {
            while (list != null) {
                priorityQueue.add(list.val);
                list = list.next;
            }
        }
        ListNode listNode = new ListNode();
        ListNode p = listNode;
        while (!priorityQueue.isEmpty()) {
            final Integer poll = priorityQueue.poll();
            p.next = new ListNode(poll, null);
            p = p.next;
        }
        return listNode.next;
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
