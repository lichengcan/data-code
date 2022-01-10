package io.github.chenshun00.data.tree.binary;

/**
 * https://leetcode-cn.com/problems/maximum-binary-tree/
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/10 12:42 下午
 */
public class ConstructMaximumBinaryTree {

    public static void main(String[] args) {
        ConstructMaximumBinaryTree constructMaximumBinaryTree = new ConstructMaximumBinaryTree();
        int[] xx = {3, 2, 1, 6, 0, 5};
        final TreeNode treeNode = constructMaximumBinaryTree.constructMaximumBinaryTree(xx);
        System.out.println(1);
    }

    /**
     * 给定一个不含重复元素的整数数组 nums 。一个以此数组直接递归构建的 最大二叉树 定义如下：
     * <p>
     * 最大构造二叉树
     * <p>
     *     <ul>
     *         <li>二叉树的根是数组 nums 中的最大元素。</li>
     *         <li>左子树是通过数组中 最大值左边部分 递归构造出的最大二叉树。</li>
     *         <li>右子树是通过数组中 最大值右边部分 递归构造出的最大二叉树。</li>
     *     </ul>
     * <p>
     * 返回有给定数组 nums 构建的 最大二叉树 。
     * <p>
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode();
        final int[] max = findMax(nums);
        if (max.length == 0) {
            return root;
        }
        root.val = max[0];
        final int index = max[1];
        int[] left = new int[index];
        int[] right = new int[nums.length - index - 1];

        System.arraycopy(nums, 0, left, 0, index);
        System.arraycopy(nums, index + 1, right, 0, nums.length - index - 1);
        root.left = constructMaximumBinaryTree(left);
        root.right = constructMaximumBinaryTree(right);
        return root;
    }

    private int[] findMax(int[] nums) {
        if (nums == null || nums.length == 0) return new int[]{};
        int[] temp = new int[2];
        int max = nums[0];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                continue;
            }
            final int num = nums[i];
            if (num > max) {
                index = i;
                max = num;
            }
        }
        temp[0] = max;
        temp[1] = index;
        return temp;
    }

}
