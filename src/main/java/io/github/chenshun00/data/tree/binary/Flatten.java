package io.github.chenshun00.data.tree.binary;

/**
 * https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/
 * <p>
 * 二叉树展开为链表
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/9 11:48 下午
 */
public class Flatten {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        {
            root.left = new TreeNode(2, new TreeNode(3), new TreeNode(4));
            root.right = new TreeNode(5, null, new TreeNode(6));
        }
        Flatten flatten = new Flatten();
        flatten.flatten(root);
        System.out.println(1);
    }

    /**
     * 二叉树展开为链表
     * <p>
     * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
     * <p>
     * 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
     * <p>
     * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
     * <p>
     * 先根节点，在左子树，在右子树
     *
     * @param root 根
     */
    public void flatten(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return;
        }
        TreeNode tt = new TreeNode();
        doSet(root, tt);
        root.left = null;
        root.right = tt.right.right;
    }

    private void doSet(TreeNode root, TreeNode temp) {
        if (root == null || temp == null) return;
        temp.right = new TreeNode(root.val);
        doSet(root.left, temp.right);
        doSet(root.right, treeNode(temp));
    }

    private TreeNode treeNode(TreeNode temp) {
        while (true) {
            if (temp.right == null) {
                return temp;
            }
            temp = temp.right;
        }
    }
}
