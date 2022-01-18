package io.github.chenshun00.data.tree.bst;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/15 1:03 下午
 */
public class Tree {

    public static void main(String[] args) {
        Tree tree = new Tree();
        TreeNode treeNode = new TreeNode(4);
        treeNode = tree.insertIntoBST(treeNode, 2);
        treeNode = tree.insertIntoBST(treeNode, 7);
        treeNode = tree.insertIntoBST(treeNode, 1);
        treeNode = tree.insertIntoBST(treeNode, 3);
        treeNode = tree.insertIntoBST(treeNode, 5);
        System.out.println(treeNode);
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return root;
        }
        //找到当前节点
        final TreeNode node = doFindNode(root, key);
        //找不到节点的情况
        if (node == null) {
            return root;
        }
        //如果节点是根节点
        if (node == root) {
            //如果根节点是叶子节点
            if (isLeaf(root)) {
                root = null;
            } else {
                replace(root);
            }
            return true;
        }
        //被移除的是不是叶子节点
        if (isLeaf(node)) {
            //找到叶子节点的parent节点，并且说明当前节点是左(left)子树还是右子树(right)
            final NodeContext nodeContext = findParent(node);
            final Node parentNode = nodeContext.node;
            if (nodeContext.left) {
                parentNode.leftChild = null;
            } else {
                parentNode.rightChild = null;
            }
        } else {
            replace(node);
        }
        return true;
    }

    private TreeNode doFindNode(TreeNode root, Integer data) {
        if (root == null) {
            return null;
        }
        return data >= root.val ? doFindNode(root.right, data) : doFindNode(root.left, data);
    }


    public boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        } else {
            handleData(root, val);
            return root;
        }
    }

    private void handleData(TreeNode root, int val) {
        if (val >= root.val) {
            handleRight(root, root.right, val);
        } else {
            handleLeft(root, root.left, val);
        }
    }

    private void handleLeft(TreeNode root, TreeNode left, int val) {
        if (left == null) {
            left = new TreeNode(val);
            root.left = left;
        } else {
            handleData(left, val);
        }
    }

    private void handleRight(TreeNode root, TreeNode right, int val) {
        if (right == null) {
            right = new TreeNode(val);
            root.right = right;
        } else {
            handleData(right, val);
        }
    }

    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        } else if (root.val > val) {
            return searchBST(root.left, val);
        } else {
            return searchBST(root.right, val);
        }
    }

}
