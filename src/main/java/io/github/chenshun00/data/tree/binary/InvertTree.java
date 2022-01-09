package io.github.chenshun00.data.tree.binary;

/**
 * https://twitter.com/mxcl/status/608682016205344768
 * <p>
 * https://leetcode-cn.com/problems/invert-binary-tree/
 * <p>
 * 翻转二叉树: 前序遍历的时候先把当前节点的左右子树翻转，然后递归翻转他们的孩子.
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/9 4:54 下午
 */
public class InvertTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        {
            root.left = new TreeNode(2, new TreeNode(1), new TreeNode(3));
            root.right = new TreeNode(7, new TreeNode(6), new TreeNode(9));
        }
        InvertTree invertTree = new InvertTree();
        final TreeNode treeNode = invertTree.invertTree(root);
        System.out.println(1);
    }

    /**
     * 翻转二叉树
     * <p>
     * 交换节点的左子树和右子树
     *
     * <p>
     * https://leetcode-cn.com/problems/invert-binary-tree/
     *
     * @param root 根
     * @return {@link TreeNode}
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        doInvert(root);
        return root;
    }

    private void doInvert(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }
        if (root.left == null) {
            root.left = root.right;
            root.right = null;
        } else if (root.right == null) {
            root.right = root.left;
            root.left = null;
        } else {
            final TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
        }
        doInvert(root.left);
        doInvert(root.right);
    }

}
