package io.github.chenshun00.data.tree.binary;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/9 5:06 下午
 */
public class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};