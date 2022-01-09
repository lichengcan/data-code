package io.github.chenshun00.data.tree.binary;

/**
 * 所谓前序位置，就是刚进入一个节点（元素）的时候，后序位置就是即将离开一个节点（元素）的时候。
 * <p>
 * 二叉树题目的递归解法可以分两类思路，第一类是遍历一遍二叉树得出答案，第二类是通过分解问题计算出答案
 * <p>
 * 是否可以通过遍历一遍二叉树得到答案？如果不能的话，是否可以定义一个递归函数，通过子问题（子树）的答案推导出原问题的答案？
 * 填充每个节点的下一个右侧节点指针
 * <p>
 * https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/9 5:06 下午
 */
public class ConnectTree {

    public static void main(String[] args) {
        Node root = new Node(1);
        {
            root.left = new Node(2, new Node(4), new Node(5), null);
            root.right = new Node(3, new Node(6), new Node(7), null);
        }
        ConnectTree connectTree = new ConnectTree();
        final Node connect = connectTree.connect(root);
    }

    /**
     * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     *     1
     *   /   \
     *  2     3
     * / \   / \
     * 4   5 6  7
     */
    public Node connect(Node root) {
        if (root == null || root.left == null) {
            return null;
        }
        root.left.next = root.right;
        root.right.next = root.next == null ? null : root.next.left;
        connect(root.left);
        connect(root.right);
        return root;
    }
}
