package io.github.chenshun00.data.list.code;

/**
 * 演示前序遍历和后序遍历.. 如果是三节点(存在左右孩子节点的情况),还有中序遍历
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/9 2:19 下午
 */
public class Print {

    public static void main(String[] args) {
        PrintNode listNode = new PrintNode(1);
        PrintNode p = listNode;
        for (int i = 2; i <= 10; i++) {
            p.next = new PrintNode(i);
            p = p.next;
        }
        Print.printList(listNode);
        System.out.println();
        Print.printLastList(listNode);
    }

    //给定一个链表，递归打印出来他们的顺序
    public static void printList(PrintNode printNode) {
        if (printNode == null) return;
        System.out.print(printNode.val + "\t");
        printList(printNode.next);
    }

    //给定一个链表，递归反转打印出来他们的顺序
    public static void printLastList(PrintNode printNode) {
        if (printNode == null) return;
        printLastList(printNode.next);
        System.out.print(printNode.val + "\t");
    }
}
