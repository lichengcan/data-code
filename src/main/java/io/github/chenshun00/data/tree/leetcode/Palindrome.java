package io.github.chenshun00.data.tree.leetcode;

/**
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
 * <p>
 * https://leetcode-cn.com/problems/palindrome-linked-list/
 * <p>
 * 还可以利用快慢指针获取到中间节点来实现，秒呀
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/9 11:33 上午
 */
public class Palindrome {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode p = listNode;
        int[] dd = {1, 2, 1};
        for (int i : dd) {
            p.next = new ListNode(i);
            p = p.next;
        }
        System.out.println(new Palindrome().isPalindrome(listNode));
    }

    public boolean isPalindrome(ListNode head) {
        ListNode t = new ListNode(), tt = t, q = head;
        while (q != null) {
            tt.next = new ListNode(q.val);
            tt = tt.next;
            q = q.next;
        }
        ListNode recursion = this.recursion(t.next);
        while (recursion != null && head != null) {
            if (recursion.val != head.val) {
                return false;
            }
            recursion = recursion.next;
            head = head.next;
        }
        return true;
    }


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
