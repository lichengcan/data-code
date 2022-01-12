package io.github.chenshun00.data.tree.binary;

/**
 * https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 * <p>
 * 从前序与中序遍历序列构造二叉树
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/10 2:05 下午
 */
public class FirstBuildTree {

    /**
     * 还是对二叉树对遍历理解不深刻
     * 中序遍历: 跟节点在中间，左边的全是左子树. 递归下去. 左子树中间的也是跟节点，然后循环往复. 右边的全是右子树.
     * 前序遍历: 跟节点在头部
     * 后序遍历: 跟节点在尾部
     * <p>
     * 根据中序+前序==>构造二叉树
     * 根据中序+后序==>构造二叉树
     * 都是因为根据前序和后序可以确定跟节点的数据
     */
    public static void main(String[] args) {
        FirstBuildTree firstBuildTree = new FirstBuildTree();
        int[] pre = {1, 2, 3};
        int[] in = {3, 2, 1};
        final TreeNode treeNode = firstBuildTree.buildTree(pre, in);
        System.out.println(1);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return doBuildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode doBuildTree(int[] pre, int preStart, int preEnd, int[] in, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }
        final int root = pre[preStart];
        final int middle = middle(in, root, inStart, inEnd);
        int leftSize = middle - inStart;

        TreeNode left = doBuildTree(pre, preStart + 1, preStart + leftSize, in, inStart, middle - 1);
        TreeNode right = doBuildTree(pre, preStart + leftSize + 1, preEnd, in, middle + 1, inEnd);

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
