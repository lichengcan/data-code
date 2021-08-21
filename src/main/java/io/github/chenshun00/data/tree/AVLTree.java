package io.github.chenshun00.data.tree;

import io.github.chenshun00.data.Node;

/**
 * @author chenshun00@gmail.com
 * @since 2021/8/14 10:26 上午
 */
public class AVLTree {

    private Node root;

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        avlTree.insert(1);
        avlTree.insert(2);
        avlTree.insert(3);
//        avlTree.insert(4);
//        avlTree.insert(5);
//        avlTree.insert(6);
//        avlTree.insert(7);
//        avlTree.insert(8);
//        avlTree.insert(9);
//        avlTree.insert(999);
//        avlTree.traverse();
//        System.out.println(avlTree.isAVLTree());
//        final Node node = avlTree.findNode(9);
//        System.out.println(node);
//        avlTree.delete(999);
//        System.out.println("删除节点4===>" + avlTree.delete(4));
//        avlTree.traverse();
//        System.out.println("删除节点3===>" + avlTree.delete(3));
//        System.out.println("删除节点6===>" + avlTree.delete(6));
//        System.out.println("删除节点1===>" + avlTree.delete(1));
        avlTree.traverse();
    }

    public boolean isAVLTree() {
        if (root == null) {
            return false;
        }
        return avl(root, true);
    }

    public Node findNode(int data) {
        return root == null ? null : doFindNode(data);
    }

    //删除操作
    public boolean delete(int data) {
        //找节点，找不到直接返回
        final Node node = findNode(data);
        if (node == null) {
            return false;
        }
        //如果节点是root
        if (node == root) {
            //如果root是叶子
            if (isLeaf(node)) {
                root = null;
            } else {
                //如果root还有子树
                replace(node);
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
            //重新平衡
            reBalance(parent);
        } else {
            replace(node);
        }
        return true;
    }


    /**
     * 插入
     *
     * @param data 数据
     * @return {@link Node}
     */
    public Node insert(int data) {
        if (root == null) {
            root = new Node(null, null, null, data, 1);
            return root;
        }
        return doInsert(data, root);
    }

    public void traverse() {
        doTraverse(root);
        System.out.println();
    }

    //==============================删================================
    private boolean isLeaf(Node node) {
        return node == null || node.leftChild == null && node.rightChild == null;
    }

    /**
     * 从AVL树中删除，可以通过把要删除的节点向下旋转成一个叶子节点，接着直接移除这个叶子节点来完成。
     * 因为在旋转成叶子节点期间最多有log n个节点被旋转，而每次AVL旋转耗费固定的时间，所以删除处理在整体上耗费O(log n) 时间。
     */
    private void replace(Node node) {
        //找到左子树中最大的节点 或者 是右子树中最小的节点
        final Node next = findNext(node);
        final Node parent = next.parent;
        //修改被替换节点的指向
        if (parent.leftChild == next) {
            if (next.leftChild == null) {
                parent.leftChild = null;
            } else {
                parent.leftChild = next.leftChild;
                next.leftChild.parent = parent;
            }
        } else if (parent.rightChild == next) {
            if (next.rightChild == null) {
                parent.rightChild = null;
            } else {
                parent.rightChild = next.rightChild;
                next.rightChild.parent = parent;
            }
        }
        //节点修改数据
        node.data = next.data;
        reBalance(node);
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

    //==============================查==================================
    private Node doFindNode(Integer data) {
        return data >= root.data ? findRight(root, data) : findLeft(root, data);
    }

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

    //===========================是否AVL树===========================
    private boolean avl(Node node, boolean ok) {
        if (!ok) return false;
        if (node == null) {
            return true;
        }
        final boolean balance = isBalance(node);
        return avl(node.rightChild, balance) && avl(node.leftChild, balance);
    }

    //===========================插入===========================
    private Node doInsert(int data, Node parent) {
        //如果数据比当前节点小，则插入作为左子树
        if (data < parent.data) {
            if (parent.leftChild == null) {
                parent.leftChild = new Node(parent, null, null, data, 1);
                //修改高度 && 可能需要重新修正为平衡树
                changeNodeHeight(parent.leftChild);
                return parent.leftChild;
            } else {
                //存在左子的情况下，继续找左子
                return doInsert(data, parent.leftChild);
            }
        } else {
            //否则插入作为右子树
            if (parent.rightChild == null) {
                parent.rightChild = new Node(parent, null, null, data, 1);
                //修改高度 && 可能需要重新修正为平衡树
                changeNodeHeight(parent.rightChild);
                return parent.rightChild;
            } else {
                //存在右子的情况下，继续找右子
                return doInsert(data, parent.rightChild);
            }
        }
    }

    private void doReBalance(Node node) {
        if (isBalance(node)) {
            return;
        }
        node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
        if (leftNotBalance(node)) {
            //平衡因子，用来判断是旋转一次，还是旋转两次
            final int rotationFactor = rotationFactor(node.leftChild);
            if (rotationFactor < 0) {
                rr(node, node == root);
            } else {
                rl(node.leftChild, node == root);
            }
        } else {
            //右节点失去平衡 平衡因子，用来判断是旋转一次，还是旋转两次
            final int rotationFactor = rotationFactor(node.rightChild);
            if (rotationFactor > 0) {
                ll(node, node == root);
            } else {
                lr(node.rightChild, node == root);
            }
        }
    }

    /**
     * 重新平衡
     */
    private void reBalance(Node node) {
        //如果是root节点需要重新平衡
        if (node.parent == null) {
            doReBalance(node);
            return;
        }
        node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
        Node parent = node.parent;
        do {
            //递归修改parent节点的高度
            parent.height = Math.max(getHeight(parent.leftChild), getHeight(parent.rightChild)) + 1;
            //查看节点是否失去平衡
            if (!isBalance(parent)) {
                //左节点失去平衡
                if (leftNotBalance(parent)) {
                    //平衡因子，用来判断是旋转一次，还是旋转两次
                    final int rotationFactor = rotationFactor(parent.leftChild);
                    if (rotationFactor < 0) {
                        rr(parent, parent == root);
                    } else {
                        rl(parent.leftChild, parent == root);
                    }
                } else {
                    //右节点失去平衡 平衡因子，用来判断是旋转一次，还是旋转两次
                    final int rotationFactor = rotationFactor(parent.rightChild);
                    if (rotationFactor > 0) {
                        ll(parent, parent == root);
                    } else {
                        lr(parent.rightChild, parent == root);
                    }
                }
            }
            parent = parent.parent;
        } while (parent != null);
    }

    private void changeNodeHeight(Node node) {
        Node parent = node.parent;
        do {
            //递归修改parent节点的高度
            parent.height = Math.max(getHeight(parent.leftChild), getHeight(parent.rightChild)) + 1;
            //查看节点是否失去平衡
            if (!isBalance(parent)) {
                //左节点失去平衡(右旋)
                if (leftNotBalance(parent)) {
                    //平衡因子(左子失去平衡，需要进行右旋，使用当前节点的左子树的根节点来判断平衡因子)
                    //用来判断是旋转一次，还是旋转两次，如果小于0则进行一次右旋即可
                    //否则进行一次左旋，一次右旋，左旋的作用是将数据规整为右旋的规则作用。方便做右旋操作
                    final int rotationFactor = rotationFactor(parent.leftChild);
                    if (rotationFactor < 0) {
                        //右旋
                        rr(parent, parent == root);
                    } else {
                        //
                        rl(parent.leftChild, parent == root);
                    }
                } else {
                    //右节点失去平衡 平衡因子，用来判断是旋转一次，还是旋转两次 (左旋)
                    //同上变的注释
                    final int rotationFactor = rotationFactor(parent.rightChild);
                    if (rotationFactor > 0) {
                        ll(parent, parent == root);
                    } else {
                        lr(parent.rightChild, parent == root);
                    }
                }
            }
            parent = parent.parent;
        } while (parent != null);
    }

    //先右旋 再左旋
    private void rl(Node node, boolean b) {
        final Node parent = node.parent;

        parent.leftChild = node.rightChild;
        node.rightChild.parent = parent;

        node.parent = parent.leftChild;
        node.rightChild = parent.rightChild == null ? null :
                parent.rightChild.leftChild;
        parent.leftChild.leftChild = node;

        node.height = Math.max(getHeight(null), getHeight(node.rightChild)) + 1;
        node.parent.height = Math.max(getHeight(node.parent), getHeight(node.parent)) + 1;

        rr(parent, b);
    }

    //先左旋 再右旋
    private void lr(Node node, boolean b) {
        final Node parent = node.parent;

        parent.rightChild = node.leftChild;
        node.leftChild.parent = parent;

        node.parent = parent.rightChild;
        node.leftChild = parent.leftChild == null ? null : parent.rightChild.rightChild;
        parent.rightChild.rightChild = node;

        node.height = Math.max(getHeight(null), getHeight(node.rightChild)) + 1;
        node.parent.height = Math.max(getHeight(node.parent), getHeight(node.parent)) + 1;

        ll(parent, b);
    }

    /**
     * 旋转因子，用来判断是旋转一次，还是旋转2次
     *
     * @param node 节点
     * @return int
     */
    private int rotationFactor(Node node) {
        final int left = getHeight(node.leftChild);
        final int right = getHeight(node.rightChild);
        return right - left;
    }

    /**
     * 左子树不平衡
     *
     * @param node 节点
     * @return boolean
     */
    private boolean leftNotBalance(Node node) {
        return (getHeight(node.rightChild) - getHeight(node.leftChild)) < 0;
    }

    private boolean rightBalance(Node node) {
        return (getHeight(node.rightChild) - getHeight(node.leftChild)) > 0;
    }

    /**
     * 判断当前节点是否平衡，abs(左子和右子的节点高度)<=1即可
     *
     * @param node 节点
     * @return boolean
     */
    private boolean isBalance(Node node) {
        if (node == null) {
            return true;
        }
        final int lh = getHeight(node.leftChild);
        final int rh = getHeight(node.rightChild);
        return Math.abs(rh - lh) <= 1;
    }

    //===========================遍历===========================
    private void doTraverse(Node node) {
        if (node == null) {
            System.out.println("么数据");
            return;
        }
        if (node.leftChild != null) {
            doTraverse(node.leftChild);
        }
        System.out.print(node.data + "(" + node.height + ")" + "\t");
        if (node.rightChild != null) {
            doTraverse(node.rightChild);
        }
    }
    //===========================旋转===========================

    /**
     * 右旋(某节点的左子树不平衡)，注释同左旋
     * RightRight的缩写
     */
    private void rr(Node node, boolean isRoot) {
        final Node parent = node.parent;
        boolean isRightChild = false;
        if (parent != null) {
            if (parent.rightChild == node) {
                isRightChild = true;
            }
        }

        final Node leftChild = node.leftChild;
        //设置
        node.leftChild = leftChild.rightChild;
        if (leftChild.rightChild != null) {
            leftChild.rightChild.parent = node;
        }
        leftChild.parent = node.parent;
        if (parent != null) {
            if (isRightChild) {
                node.parent.rightChild = leftChild;
            } else {
                node.parent.leftChild = leftChild;
            }
        }

        leftChild.rightChild = node;
        node.parent = leftChild;

        if (isRoot) {
            root = leftChild;
        }
        node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
        leftChild.height = Math.max(getHeight(leftChild.leftChild), getHeight(leftChild.rightChild)) + 1;
    }


    /**
     * 左旋转(右子树不平衡) if是用来兼容各种节点有数据的情况
     * 假设当前的根节点为5，右子树的参考节点数据为 6 7，新插入节点为8的情况，
     * 6由于插入变为不平衡节点，需要旋转成根为7，左子为6，右子为8的情况
     *
     * <code>
     * 5              5                 5
     * / \            / \               /\
     * 3   6  ====>   3   6  ====>      3 7
     * /    \        /     \           / / \
     * 1      7      1       7         1 6   8
     * \
     * 8
     * </code>
     *
     * @param node 6 失去平衡的节点
     */
    private void ll(Node node, boolean isRoot) {
        //获取当前节点的parent节点，用来修改parent的leftChild使用，因为一开始的leftChild旋转作为rightChild了
        //parent == 5
        final Node parent = node.parent;
        //判断当前节点是parent的左节点还是右节点，下文需要使用
        //如果当前节点是parent的左节点，旋转产生的新"根节点" 挂到parent的左子上
        //如果当前节点是parent的右节点，旋转产生的新"根节点" 挂到parent的右子上
        boolean isRightChild = false;
        if (parent != null) {
            if (parent.rightChild == node) {
                //6是5节点的右子树的根节点
                isRightChild = true;
            }
        }

        //获取右节点7
        final Node rightChild = node.rightChild;
        //将节点6的右孩子设置为7的左孩子，需要明白旋转方式，这里是旋转的理论基础
        node.rightChild = rightChild.leftChild;
        //如果节点7的左孩子不为空，将左孩子的parent指向6，这里完成了旋转的第一轮交接
        if (rightChild.leftChild != null) {
            rightChild.leftChild.parent = node;
        }
        //节点7的parent应用修改为节点6的parent5
        //节点5就指向了节点7
        rightChild.parent = node.parent;
        //如果parent为空，需要旋转出来的节点挂到parent节点下
        //如果6节点是左子，挂5的左子，如果6是右子就挂5的右子
        if (parent != null) {
            if (isRightChild) {
                node.parent.rightChild = rightChild;
            } else {
                node.parent.leftChild = rightChild;
            }
        }
        //节点7的左子设置为节点6
        rightChild.leftChild = node;
        //节点的6的parent指向节点7
        node.parent = rightChild;

        //这里的root判断是，由于Java的值传递
        if (isRoot) {
            root = rightChild;
        }
        //重新设置节点高度
        node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
        rightChild.height = Math.max(getHeight(rightChild.leftChild), getHeight(rightChild.rightChild)) + 1;
    }

    /**
     * 获取节点高度
     *
     * @param node 节点
     * @return int
     */
    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }
}
