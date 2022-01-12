package io.github.chenshun00.data.tree.binary;

/**
 * https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
 * <p>
 * 根据前序和后序遍历构造二叉树
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/12 8:42 下午
 */
public class ThirdBuildTree {

    public static void main(String[] args) {
        ThirdBuildTree thirdBuildTree = new ThirdBuildTree();

        int[] pre = {1, 2, 4, 5, 3, 6, 7};
        int[] post = {4, 5, 2, 6, 7, 3, 1};
        final TreeNode treeNode = thirdBuildTree.constructFromPrePost(pre, post);
        System.out.println(1);
    }

    /**
     * <p>
     * 前序遍历: 根左右
     * 后序遍历: 左右根
     * <p>
     * 1、确定根节点的值
     * 2、然后把前序遍历结果的第二个元素作为左子树的根节点的值。
     * 3、在后序遍历结果中寻找左子树根节点的值，从而确定了左子树的索引边界，进而确定右子树的索引边界，递归构造左右子树即可
     */
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {

        return doBuild(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
    }

    /**
     * int[] pre = {1, 2, 4, 5, 3, 6, 7};
     * int[] post = {4, 5, 2, 6, 7, 3, 1};
     */
    private TreeNode doBuild(int[] preorder, int preStart, int preEnd, int[] postorder, int postStart, int postEnd) {
        if (preStart > preEnd || preStart < 0) {
            return null;
        }
        if (preStart == preEnd) {
            return new TreeNode(preorder[preStart]);
        }

        // root 节点对应的值就是前序遍历数组的第一个元素
        int rootVal = preorder[preStart];
        // root.left 的值是前序遍历第二个元素
        // 通过前序和后序遍历构造二叉树的关键在于通过左子树的根节点
        // 确定 preorder 和 postorder 中左右子树的元素区间
        int leftRoot = preorder[preStart + 1];

        int middle = 0;

        for (int i = postStart; i < postEnd; i++) {
            if (leftRoot == postorder[i]) {
                middle = i;
                break;
            }
        }

        int leftSize = middle - postStart + 1;

        TreeNode left = doBuild(preorder, preStart + 1, preStart + leftSize, postorder, postStart, middle);
        TreeNode right = doBuild(preorder, preStart + 1 + leftSize, preEnd, postorder, middle + 1, postEnd);

        TreeNode treeNode = new TreeNode();
        treeNode.val = rootVal;
        treeNode.left = left;
        treeNode.right = right;

        return treeNode;
    }

}
