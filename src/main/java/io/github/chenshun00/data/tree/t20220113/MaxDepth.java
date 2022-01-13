package io.github.chenshun00.data.tree.t20220113;

/**
 * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 * <p>
 * 104. 二叉树的最大深度: 将一件事分解交给下边的去做. 但是要找到base case
 * <p>
 * 找到树的最大深度=1+max(左子树的最大深度, 右子树的最大深度), 递归继续向下找子子节点, 最终结束整个过程. base case就是当节点为空的时候，深度就是0，结束递归
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/12 9:51 下午
 */
public class MaxDepth {

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

}
