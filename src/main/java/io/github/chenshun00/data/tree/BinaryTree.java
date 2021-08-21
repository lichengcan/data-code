package io.github.chenshun00.data.tree;

import io.github.chenshun00.data.Node;
import io.github.chenshun00.data.print.TreePrinter;

/**
 * @author chenshun00@gmail.com
 * @since 2021/8/13 1:24 下午
 */
public class BinaryTree {

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.insertNode(1);
        binaryTree.insertNode(2);
        binaryTree.insertNode(3);
        binaryTree.insertNode(4);
        binaryTree.insertNode(5);
        binaryTree.insertNode(6);
        binaryTree.insertNode(7);
        binaryTree.insertNode(8);
        binaryTree.insertNode(9);
        binaryTree.insertNode(999);
        binaryTree.traverse();
        System.out.println("删除节点:===>" + binaryTree.deleteNode(3));
        binaryTree.traverse();
        System.out.println("删除节点:===>" + binaryTree.deleteNode(2));
        binaryTree.traverse();
    }

    public Node root;

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
        TreePrinter.printNode(root);
        System.out.println("==================================================");
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

}
