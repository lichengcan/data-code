package io.github.chenshun00.data.tree.bst;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/15 11:48 上午
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

    public void traverse() {
        doTraverse(this);
        System.out.println();
    }

    private void doTraverse(TreeNode node) {
        if (node == null) {
            System.out.println("么数据");
            return;
        }
        if (node.left != null) {
            doTraverse(node.left);
        }
        System.out.print(node.val + "\t");
        if (node.right != null) {
            doTraverse(node.right);
        }
    }
}