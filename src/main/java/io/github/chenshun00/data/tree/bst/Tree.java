package io.github.chenshun00.data.tree.bst;

/**
 * @author chenshun00@gmail.com
 * @since 2022/1/15 1:03 下午
 */
public class Tree {

    public static void main(String[] args) {
        Tree tree = new Tree();
        TreeNode treeNode = new TreeNode(4);
        treeNode = tree.insertIntoBST(treeNode, 7);
        treeNode = tree.insertIntoBST(treeNode, 6);
        treeNode = tree.insertIntoBST(treeNode, 8);
        treeNode = tree.insertIntoBST(treeNode, 5);
        treeNode = tree.insertIntoBST(treeNode, 9);
        System.out.println(treeNode);

        treeNode = tree.deleteNode(treeNode, 7);
        System.out.println(1);
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
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
                return null;
            } else {
                replace(root, root);
            }
            return root;
        }
        //被移除的是不是叶子节点
        if (isLeaf(node)) {
            //找到叶子节点的parent节点，并且说明当前节点是左(left)子树还是右子树(right)
            final TreeNode parent = findParent(root, key);
            if (parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else {
            replace(root, node);
        }
        return root;
    }


    public void replace(TreeNode root, TreeNode node) {
        //找到左子树中最大的节点 或者 是右子树中最小的节点
        final TreeNode next = findNext(node);
        //找到这个节点的parent节点
        TreeNode nodeContext = findParent(root, next.val);
        if (nodeContext == null) {
            return;
        }
        if (nodeContext.left == next) {
            nodeContext.left = next.left;
        } else if (nodeContext.right == next) {
            nodeContext.right = next.right;
        }
        node.val = next.val;
    }

    public TreeNode findNext(TreeNode node) {
        if (node.left != null) {
            return doFindMaxNode(node.left);
        }
        return doFindMinNode(node.right);
    }


    private TreeNode doFindMaxNode(TreeNode node) {
        if (node.right == null) {
            return node;
        } else {
            return doFindMaxNode(node.right);
        }
    }


    private TreeNode doFindMinNode(TreeNode node) {
        if (node.left == null) {
            return node;
        } else {
            return doFindMinNode(node.left);
        }
    }

    public TreeNode findParent(TreeNode root, int data) {
        if (root == null) {
            return null;
        }
        TreeNode node = new TreeNode(data);
        return doFind(root, node);
    }


    private TreeNode doFind(TreeNode root, TreeNode node) {
        if (root == null) {
            return null;
        }
        return node.val >= root.val ? doFindParentRight(root, node) : doFindParentLeft(root, node);
    }

    /**
     * 左
     */
    private TreeNode doFindParentLeft(TreeNode root, TreeNode node) {
        if (root == null) {
            return null;
        }
        if (root.left != null) {
            if (root.left.val == node.val) {
                return root;
            }
        }
        if (root.right != null) {
            if (root.right.val == node.val) {
                return root;
            }
        }
        return doFind(root.left, node);
    }

    /**
     * 右
     */
    private TreeNode doFindParentRight(TreeNode root, TreeNode node) {
        if (root == null) {
            return null;
        }
        if (root.left != null) {
            if (root.left.val == node.val) {
                return root;
            }
        }
        if (root.right != null) {
            if (root.right.val == node.val) {
                return root;
            }
        }
        return doFind(root.right, node);
    }


    private TreeNode doFindNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val == key) {
            return root;
        }
        return key >= root.val ? doFindNode(root.right, key) : doFindNode(root.left, key);
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
