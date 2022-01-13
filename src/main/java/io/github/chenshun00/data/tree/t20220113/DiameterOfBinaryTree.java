package io.github.chenshun00.data.tree.t20220113;

/**
 * https://leetcode-cn.com/problems/diameter-of-binary-tree/
 * <p>
 * 543. 二叉树的直径
 * <p>
 * 二叉树的直径=左子树的高度+右子树的高度,但是需要计算全部的节点. 那么就需要中间变量保持最大高度是多少。base case就是节点为空的时候树的高度为0
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/13 9:31 上午
 */
public class DiameterOfBinaryTree {

    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        final int all = maxDepth(root.left) + maxDepth(root.right);

        max = Math.max(this.diameterOfBinaryTree(root.left), this.diameterOfBinaryTree(root.right));

        return Math.max(all, max);
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

}
