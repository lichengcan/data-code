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
        return doSet(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    private TreeNode doSet(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }
        final TreeNode tt = new TreeNode();
        //根节点的数据
        final int rootNode = preorder[preStart];//rootNode=3
        //
        //9, 3, 15, 20, 7 //middle = 1
        final int middle = middle(inorder, rootNode, inStart, inEnd);

        //剩下没有计算完的数据
        int leftSize = middle - inStart;

        //
        // inStart, middle - 1 这部分肯定是没有歧义的
        // middle + 1, inEnd 这段肯定也是没有歧义的
        // preStart + 1 也可以理解的
        // left的preEnd+right的preStart是一体的，他们相加=参数的preEnd
        // 这里为什么是leftSize， middle-preStart不可以么，其实这里是要确定下表。而不是数量，如果我走拷贝应该也是可以的。
        final TreeNode left = doSet(preorder, inorder, preStart + 1, preStart + leftSize, inStart, middle - 1);
        final TreeNode right = doSet(preorder, inorder, preStart + leftSize + 1, preEnd, middle + 1, inEnd);

        tt.val = rootNode;
        tt.left = left;
        tt.right = right;
        return tt;
    }

    public int middle(int[] inorder, int target, int inStart, int inEnd) {
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == target) {
                return i;
            }
        }
        return -1;
    }

}
