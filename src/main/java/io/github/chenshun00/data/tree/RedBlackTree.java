package io.github.chenshun00.data.tree;

import io.github.chenshun00.data.Node;
import io.github.chenshun00.data.print.TreePrinter;

/**
 * @author chenshun00@gmail.com
 * @since 2021/8/16 11:28 下午
 */
public class RedBlackTree {

    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(20);
        redBlackTree.insert(10);
        redBlackTree.insert(30);
        redBlackTree.insert(1);
        redBlackTree.insert(2);
        redBlackTree.insert(3);
        redBlackTree.insert(4);
        redBlackTree.insert(5);
        redBlackTree.insert(6);
        redBlackTree.insert(7);

        System.out.println("删除4:" + redBlackTree.deleteNode(4));
        redBlackTree.traverse();
        System.out.println("删除6:" + redBlackTree.deleteNode(6));
        redBlackTree.traverse();
    }

    private Node root;

    //===========================删除===========================
    public boolean deleteNode(int data) {
        //找节点，找不到直接返回
        final Node node = findNode(data);
        if (node == null) {
            return false;
        }
        //如果是根节点
        if (node == root) {
            //如果根节点是叶子节点
            if (isLeaf(node)) {
                root = null;
                return true;
            } else {
                //根节点可能只有一个子节点，也可能有2个子节点
                replace(node);
            }
            return true;
        }

        //被移除的是叶子节点
        if (isLeaf(node)) {
            //如果叶子节点是红色，直接移除
            if (!node.black) {
                changeNodeDir(node);
                return true;
            }
            //如果叶子节点是黑色
            deleteBlackLeaf(node);
        } else {
            //节点可能只有一个子节点，也可能有2个子节点
            replace(node);
        }
        return true;
    }

    //修改红色叶子节点的指向
    private void changeNodeDir(Node node) {
        //找到叶子节点的parent节点，并且说明当前节点是左(left)子树还是右子树(right)
        final Node parent = node.parent;
        if (parent.leftChild == node) {
            parent.leftChild = null;
        } else {
            parent.rightChild = null;
        }
    }

    /**
     * 待删除节点为黑色叶子节点
     *
     * @param node 节点N,N为3
     */
    private void deleteBlackLeaf(Node node) {
        //查询当前节点为parent的左节点还是右节点
        //parent节点为2
        final Node parent = node.parent;
        //node3是parent2的右子
        boolean isRight = node != parent.leftChild;
        //1、获取兄弟节点
        //获取兄弟节点，如果当前节点是左子，那么兄弟节点就是右子，反之亦然
        //sibling为1
        Node sibling = isRight ? parent.leftChild : parent.rightChild;
        //2、兄弟节点为黑色
        if (sibling.black) {
            //2.1、兄子节点全黑
            if (childAllBlack(sibling)) {
                //2.1.1、父亲为红色
                if (!parent.black) {
                    changeNodeDir(node);
                    //交换P和S的颜色,这里因为上边的判断所以可以直接判定
                    parent.black = true;
                    sibling.black = false;
                } else {
                    //2.1.2、父亲为黑色
                    //兄弟节点S涂红
                    changeNodeDir(node);
                    sibling.black = false;
                    //将P作为新的N，进行递归处理
                    deleteBlackLeaf(parent);
                }
            } else {
                //2.2、兄子节点不全黑
                //isRight(true)当前节点在右，兄在左
                if (isRight) {
                    //2.2.1 兄在左，兄左子红
                    final Node sl = sibling.leftChild;
                    boolean red = sl != null && !sl.black;
                    if (red) {
                        //以当前节点的parent节点右旋，右旋完成后交互P和S的颜色，SL涂黑 平衡结束
                        rrNotColor(parent, parent == root);
                        final boolean parentColor = parent.black;
                        parent.black = sibling.black;
                        sibling.black = parentColor;
                        sibling.leftChild.black = true;
                        //为了兼容处理删除含2个子节点的数据，这里是调整删除nil节点
                        if (node.nil) {
                            changeNodeDir(node);
                        }
                    } else {
                        //2.2.2 兄弟节点在左,兄弟节点左子树黑
                        final Node SR = sibling.rightChild;
                        final Node SL = sibling.leftChild;
                        //以兄弟节点S左旋
                        llNotColor(sibling, false);
                        //交换S和SR的颜色
                        final boolean srColor = SR.black;
                        SR.black = sibling.black;
                        sibling.black = srColor;

                        deleteBlackLeaf(node);
                    }
                } else {
                    //2.2.2、兄在右(既成事实)
                    final Node sr = sibling.rightChild;
                    boolean red = sr != null && !sr.black;
                    if (red) {
                        //以P左旋，交换P和S的颜色，SR涂黑，平衡结束
                        llNotColor(parent, parent == root);
                        final boolean parentColor = parent.black;
                        parent.black = sibling.black;
                        sibling.black = parentColor;
                        sibling.rightChild.black = true;
                        if (node.nil) {
                            changeNodeDir(node);
                        }
                    } else {
                        final Node SL = sibling.leftChild;
                        //以S右旋
                        rrNotColor(sibling, false);
                        //交换S和SL颜色
                        final boolean slColor = sibling.black;
                        sibling.black = SL.black;
                        SL.black = slColor;

                        deleteBlackLeaf(node);
                    }
                }
            }
        } else {
            //3、兄弟节点为红色
            //3.1、兄弟节点在左
            //isRight(true)当前节点在右，兄在左
            if (isRight) {
                llNotColor(parent, parent == root);
            } else {
                //3.2、兄弟节点在右
                rrNotColor(parent, parent == root);
            }
            final boolean parentColor = parent.black;
            parent.black = sibling.black;
            sibling.black = parentColor;
            deleteBlackLeaf(parent);
        }
    }


    private boolean childAllBlack(Node node) {
        if (node.leftChild == null && node.rightChild == null) {
            return true;
        }
        boolean leftChildColorIsBlack = node.leftChild == null || node.leftChild.black;
        boolean rightChildColorIsBlack = node.rightChild == null || node.rightChild.black;
        return leftChildColorIsBlack && rightChildColorIsBlack;
    }

    private void replace(Node node) {
        //如果只有一个节点
        if (hasOneNode(node)) {
            handleOnlyOneChildNode(node);
        } else {
            //找到左子树中最大的节点 或者 是右子树中最小的节点
            //(这里用的是左子树的最大节点)
            final Node next = findNext(node);
            final Node parent = next.parent;
            //修改被替换节点的指向,完成左子树的最大节点的迁移工作
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
            //节点的数据修改为next节点的数据
            node.data = next.data;
            //到这里为止next节点就消失了，相当于删除了next节点
            //重新对next节点的parent节点进行平衡操作
            //把这里解决了，红黑树的删除问题就解决了，在解决之前，我觉得可以先溜一下理论问题。
            if (parent.leftChild == null) {
                parent.leftChild = new Node(true, parent, null, null, null);
                parent.leftChild.nil = true;
                deleteBlackLeaf(parent.leftChild);
            } else if (parent.rightChild == null) {
                parent.rightChild = new Node(true, parent, null, null, null);
                parent.rightChild.nil = true;
                deleteBlackLeaf(parent.rightChild);
            }

        }
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

    //==============================删================================

    /**
     * 处理只有一个孩子节点的情况
     *
     * <pre>
     *    <code>
     *         20(black)
     *        / \
     *    (B)10 30(B)
     *      /
     *     5(red)
     *    </code>
     * </pre>
     *
     * @param node 节点 ,这里是节点10，只有一个孩子节点5
     */
    private void handleOnlyOneChildNode(Node node) {
        //找到节点的父亲节点 20
        final Node parent = node.parent;
        //判断10是20的左子还是右子，这里是左子
        boolean isRight = parent.rightChild == node;
        Node child = node.leftChild == null ? node.rightChild : node.leftChild;
        if (isRight) {
            //将20的左子指向5
            parent.rightChild = child;
        } else {
            //否则，将右子指向child节点
            parent.leftChild = child;
        }
        //孩子节点5的parent指向20
        child.parent = parent;
        //孩子节点涂黑
        child.black = true;
    }

    private boolean hasOneNode(Node node) {
        if (node.leftChild == null && node.rightChild == null) {
            return false;
        }
        return node.leftChild == null || node.rightChild == null;
    }

    private boolean isLeaf(Node node) {
        return node == null || node.leftChild == null && node.rightChild == null;
    }

    public Node findNode(int data) {
        return root == null ? null : doFindNode(data);
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

    //===========================插入===========================

    /**
     * 插入节点数据
     *
     * @param data 数据
     * @return {@link Node}
     */
    public Node insert(int data) {
        //N为根节点 N涂黑
        if (root == null) {
            root = new Node(true, null, null, null, data);
            return root;
        }
        return doInsert(data, root);
    }

    private Node doInsert(int data, Node parent) {
        //如果数据比当前节点小，则插入作为左子树
        if (data < parent.data) {
            if (parent.leftChild == null) {
                parent.leftChild = new Node(false, parent, null, null, data);
                //调色
                coloringNode(parent.leftChild);
                return parent.leftChild;
            } else {
                //存在左子的情况下，继续找左子
                return doInsert(data, parent.leftChild);
            }
        } else {
            //否则插入作为右子树
            if (parent.rightChild == null) {
                parent.rightChild = new Node(false, parent, null, null, data);
                //调色
                coloringNode(parent.rightChild);
                return parent.rightChild;
            } else {
                //存在右子的情况下，继续找右子
                return doInsert(data, parent.rightChild);
            }
        }
    }

    /**
     * 节点N
     */
    private void coloringNode(Node node) {
        if (node == null) {
            return;
        }
        //1、节点N的parent节点(root)
        Node parent = node.parent;
        if (parent == null) {
            node.black = true;
            return;
        }
        //2、父黑 无需其他操作
        if (parent.black) {
            return;
        }
        //3、叔叔节点 父亲红叔叔红的情况
        Node uncle = getUncle(parent);
        //
        if (!node.black && !uncleIsBlack(uncle)) {
            parent.black = true;
            uncle.black = true;
            if (parent.parent != null) {
                parent.parent.black = false;
                coloringNode(parent.parent);
            }
            return;
        }
        Node p = parent;
        do {
            if (parent.black) {
                //4、父亲红 叔叔黑
                //4.1、父亲和N在同一边
                //4.1.1、父亲和N都在左边
                final Node gp = parent;
                if (gp.leftChild == p && p.leftChild == node) {
                    rr(gp, parent == root);
                } else if (gp.rightChild == p && p.rightChild == node) {
                    //4.1.2、父亲和N都在右边
                    ll(gp, parent == root);
                } else if (gp.leftChild == p && p.rightChild == node) {
                    //4.2.1、父亲左，N在右
                    lr(gp.leftChild, parent == root);
                } else if (gp.rightChild == p && p.leftChild == node) {
                    //4.2.2、父亲右，N在左
                    rl(gp.rightChild, parent == root);
                } else {
                    throw new IllegalStateException("不应该跑到这里");
                }
                break;
            }
            parent = parent.parent;
        } while (parent != null);
    }

    //先右旋 再左旋
    private void rl(Node node, boolean b) {
        final Node parent = node.parent;
        final Node leftChild = node.leftChild;

        node.parent = leftChild;
        node.leftChild = leftChild.rightChild;
        if (node.leftChild != null) {
            node.leftChild.parent = node;
        }
        leftChild.parent = parent;
        leftChild.rightChild = node;
        parent.rightChild = leftChild;

        ll(parent, b);
    }

    //先左旋 再右旋
    private void lr(Node node, boolean b) {
        final Node parent = node.parent;
        final Node rightChild = node.rightChild;

        node.parent = rightChild;
        node.rightChild = rightChild.leftChild;
        if (node.rightChild != null) {
            node.rightChild.parent = node;
        }
        rightChild.parent = parent;
        rightChild.leftChild = node;
        parent.leftChild = rightChild;

        rr(parent, b);
    }

    private boolean uncleIsBlack(Node node) {
        if (node == null) {
            return true;
        }
        return node.black;
    }

    private Node getUncle(Node node) {
        return node.parent == null ? null : node.parent.leftChild == node ? node.parent.rightChild : node.parent.leftChild;
    }


    //===========================旋转开始===========================
    //右旋不着色
    private void rrNotColor(Node node, boolean isRoot) {
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
            root.black = true;
        }
    }

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
            root.black = true;
        }
        //旋转好了，开始设置颜色
        //旋转节点的parent设置为黑色节点
        //孩子节点设置为红色节点
        node.parent.black = true;
        node.parent.leftChild.black = false;
        node.black = false;
    }

    //left==>左 ll==> left left 左旋 不着色
    public void llNotColor(Node node, boolean isRoot) {
        //获取当前节点的parent节点，用来修改parent的leftChild使用，因为一开始的leftChild旋转作为rightChild了
        //parent == 5
        final Node parent = node.parent;
        //判断当前节点是parent的左节点还是右节点，下文需要使用
        //如果当前节点是parent的左节点，旋转产生的新"根节点" 挂到parent的左子上
        //如果当前节点是parent的右节点，旋转产生的新"根节点" 挂到parent的右子上
        boolean isRightChild = false;
        if (parent != null) {
            if (parent.rightChild == node) {
                //3是2节点的右子树的根节点
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
            root.black = true;
        }
    }

    /**
     * 左旋转(右子树不平衡) if是用来兼容各种节点有数据的情况
     * 假设当前的根节点为5(黑色)，右子树的参考节点数据为6(红色)，新插入节点为3(红色)的情况，
     * 由于插入红色节点3变为不平衡，需要旋转成根为5(黑色)，左子为6(红)，右子为7(红)的情况
     *
     * <code>
     * 5             5
     * \            / \
     * 6  ====>    6   7
     * \
     * 7
     * </code>
     *
     * @param node 2 失去平衡的节点
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
                //3是2节点的右子树的根节点
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
            root.black = true;
        }
        //重新着色
        node.parent.black = true;
        node.parent.rightChild.black = false;
        node.black = false;
    }

    //===========================旋转结束===========================

    //===========================遍历开始===========================
    public void traverse() {
        TreePrinter.printNode(root);
        System.out.println("==================================================");
    }
    //===========================遍历结束===========================
}
