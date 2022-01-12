package io.github.chenshun00.data.tree.binary;

/**
 * https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
 * <p>
 * 从中序与后序遍历序列构造二叉树
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/11 10:02 下午
 */
public class SecondBuildTree {

    public static void main(String[] args) {
        SecondBuildTree secondBuildTree = new SecondBuildTree();
        int[] pre = {9, 3, 15, 20, 7};
        int[] in = {9, 15, 7, 20, 3};
        final TreeNode treeNode = secondBuildTree.buildTree(pre, in);
        System.out.println(1);
    }

    /**
     * 中:[9,3,15,20,7]
     * 后:[9,15,7,20,3]
     * <p>
     * 还是对二叉树对遍历理解不深刻
     * 中序遍历: 跟节点在中间，左边的全是左子树. 递归下去. 左子树中间的也是跟节点，然后循环往复. 右边的全是右子树.
     * 前序遍历: 跟节点在头部
     * 后序遍历: 跟节点在尾部
     * <p>
     * 根据中序+前序==>构造二叉树
     * 根据中序+后序==>构造二叉树
     * 都是因为根据前序和后序可以确定跟节点的数据
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return doBuild(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode doBuild(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        if (postStart > postEnd) {
            return null;
        }

        final int root = postorder[postEnd];

        final int middle = middle(inorder, root, inStart, inEnd);

        int leftSize = middle - inStart;


        final TreeNode left = doBuild(inorder, inStart, middle - 1, postorder, postStart, postStart + leftSize - 1);

        final TreeNode right = doBuild(inorder, middle + 1, inEnd, postorder, postStart + leftSize, postEnd - 1);

        TreeNode treeNode = new TreeNode();
        treeNode.val = root;
        treeNode.left = left;
        treeNode.right = right;
        return treeNode;
    }

    public int middle(int[] inorder, int target, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (inorder[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
