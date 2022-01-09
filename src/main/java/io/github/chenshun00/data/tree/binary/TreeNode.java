package io.github.chenshun00.data.tree.binary;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/9 4:54 下午
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}