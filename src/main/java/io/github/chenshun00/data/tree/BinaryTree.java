package io.github.chenshun00.data.tree;

import io.github.chenshun00.data.Node;
import io.github.chenshun00.data.print.TreePrinter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenshun00@gmail.com
 * @since 2021/8/13 1:24 下午
 */
public class BinaryTree {
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();

        Integer[] xx = {1, 3, 2};
        for (Integer integer : xx) {
            binaryTree.insertNode(integer);
        }
        TreePrinter.printNode(binaryTree.root);
        binaryTree.preorder(binaryTree.root);
    }


    //前序
    public List<Integer> preorderTraversal(Node root) {
        List<Integer> xx = new ArrayList<>();
        if (root == null) {
            return xx;
        }
        xx.add(root.data);
        xx.addAll(preorderTraversal(root.leftChild));
        xx.addAll(preorderTraversal(root.rightChild));
        return xx;
    }

    //https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
    //给定一个二叉树，找出其最大深度。
    //二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
    public int maxLevel(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(maxLevel(node.leftChild), maxLevel(node.rightChild));
    }

    int max = 0;

    /**
     * 二叉树的直径
     * https://leetcode-cn.com/problems/diameter-of-binary-tree/
     * <p>
     * 必须经过跟节点，这个方法就是正确的. 但是如果不是必须经过根节点，这个方法就是错误的. 出现了审题错误
     * <p>
     * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
     *
     * @param root 根
     * @return int
     */
    public void diameterOfBinaryTree(Node root) {
        //
    }

    public Node root;

    //前序遍历
    public void preorder(Node node) {
        if (node == null) return;
        System.out.print(node.data + "\t");
        preorder(node.leftChild);
        preorder(node.rightChild);
    }

    //后序遍历
    public void postorder(Node node) {
        if (node == null) return;
        postorder(node.leftChild);
        postorder(node.rightChild);
        System.out.print(node.data + "\t");
    }

    //中序遍历
    public void inorder(Node node) {
        if (node == null) return;
        inorder(node.leftChild);
        System.out.print(node.data + "\t");
        inorder(node.rightChild);
    }


    public boolean deleteNode(Integer data) {
        if (root == null) {
            return false;
        }
        //找到当前节点
        final Node node = doFindNode(data);
        //找不到节点的情况
        if (node == null) {
            return false;
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
        //被移除的是叶子节点
        if (isLeaf(node)) {
            //找到叶子节点的parent节点，并且说明当前节点是左(left)子树还是右子树(right)
            final Node parent = node.parent;
            if (parent.leftChild == node) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
        } else {
            replace(node);
        }
        return true;
    }

    public Node findNode(Integer data) {
        return root == null ? null : doFindNode(data);
    }

    public void insertNode(Integer data) {
        if (root == null) {
            root = new Node(null, null, null, data);
        } else {
            handleData(root, data);
        }
    }

    public void traverse() {
        doTraverse(root);
        System.out.println();
    }

    //==============================删==================================
    private void replace(Node node) {
        //找到左子树中最大的节点 或者 是右子树中最小的节点
        final Node next = findNext(node);
        final Node parent = next.parent;
        if (parent.leftChild == next) {
            parent.leftChild = null;
        } else if (parent.rightChild == next) {
            parent.rightChild = null;
        }
        node.data = next.data;
    }

    private Node findNext(Node node) {
        if (node.leftChild != null) {
            return doFindMaxNode(node.leftChild);
        }
        return doFindMinNode(node.rightChild);
    }

    private Node doFindMaxNode(Node node) {
        if (node.rightChild == null) {
            return node;
        } else {
            return doFindMaxNode(node.rightChild);
        }
    }

    private Node doFindMinNode(Node node) {
        if (node.leftChild == null) {
            return node;
        } else {
            return doFindMinNode(node.leftChild);
        }
    }

    public boolean isLeaf(Node node) {
        return node.leftChild == null && node.rightChild == null;
    }

    //==============================查==================================
    private Node doFindNode(Integer data) {
        return data >= root.data ? findRight(root, data) : findLeft(root, data);
    }

    /**
     * 找到右
     *
     * @param root 根
     * @param data 数据
     * @return {@link Node}
     */
    private Node findRight(Node root, Integer data) {
        if (root == null) {
            return null;
        }
        if (root.data.equals(data)) {
            return root;
        }
        if (data >= root.data) {
            //右子树
            return findRight(root.rightChild, data);
        } else {
            //左子树
            return findLeft(root.leftChild, data);
        }
    }

    private Node findLeft(Node root, Integer data) {
        if (root == null) {
            return null;
        }
        if (root.data.equals(data)) {
            return root;
        }
        if (data >= root.data) {
            //右子树
            return findRight(root.rightChild, data);
        } else {
            //左子树
            return findLeft(root.leftChild, data);
        }
    }

    //==============================遍历==================================
    private void doTraverse(Node node) {
        if (node == null) {
            return;
        }
        doTraverse(node.leftChild);
        System.out.print(node.data + "\t");
        doTraverse(node.rightChild);
    }

    //=================插入数据=====================

    private void handleData(Node root, Integer data) {
        if (data >= root.data) {
            handleRight(root, root.rightChild, data);
        } else {
            handleLeft(root, root.leftChild, data);
        }
    }

    //处理右子树
    private void handleRight(Node root, Node right, Integer data) {
        if (right == null) {
            right = new Node(null, null, root, data);
            root.rightChild = right;
        } else {
            handleData(right, data);
        }
    }

    //处理左子树
    private void handleLeft(Node root, Node left, Integer data) {
        if (left == null) {
            left = new Node(null, null, root, data);
            root.leftChild = left;
        } else {
            handleData(left, data);
        }
    }


//    public static class Node {
//
//        public Node(Node leftChild, Node rightChild, Node parent, Integer data) {
//            this.leftChild = leftChild;
//            this.rightChild = rightChild;
//            this.parent = parent;
//            this.data = data;
//        }
//
//        public Node leftChild;
//        public Node rightChild;
//        public Node parent;
//        public Integer data;
//    }

}
