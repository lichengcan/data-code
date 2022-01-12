package io.github.chenshun00.data.tree.t20220113;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/12 9:50 下午
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

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