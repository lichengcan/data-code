package io.github.chenshun00.data.tree.bst;

import java.util.ArrayList;
import java.util.List;

/**
 * 如何把root的约束传递下去,因为每一次都把当前节点的parent节点传递下去了，所以自然就可以进行约束了
 *
 * @author chenshun00@gmail.com
 * @since 2022/1/15 11:49 上午
 */
public class ValidBST {

    public static void main(String[] args) {
        ValidBST validBST = new ValidBST();
        TreeNode treeNode = new TreeNode(5);
        treeNode.left = new TreeNode(4);
        treeNode.right = new TreeNode(6);
        {
            treeNode.right.right = new TreeNode(7);
            treeNode.right.left = new TreeNode(3);
        }
        System.out.println(validBST.isGoodValidBST(treeNode));
    }

    public boolean isGoodValidBST(TreeNode root) {
        return doValidateBST(root, null, null);
    }

    private boolean doValidateBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return true;
        }
        if (min != null) {
            if (root.val <= min.val) {
                return false;
            }
        }
        if (max != null) {
            if (root.val >= max.val) {
                return false;
            }
        }
        //left的节点也有他的子节点(right),所以min和max还是得继续向下进行传递
        return doValidateBST(root.left, min, root) && doValidateBST(root.right, root, max);
    }


    public boolean isValidBST(TreeNode root) {
        final List<Integer> xx = xx(root);
        boolean temp = true;
        for (int i = 0; i < xx.size() - 1; i++) {
            temp = temp && xx.get(i) < xx.get(i + 1);
        }
        return temp;
    }

    private List<Integer> xx(TreeNode treeNode) {
        if (treeNode == null) {
            return new ArrayList<>();
        }
        List<Integer> con = new ArrayList<>();
        con.addAll(xx(treeNode.left));
        con.add(treeNode.val);
        con.addAll(xx(treeNode.right));
        return con;
    }

}
