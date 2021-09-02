package io.github.chenshun00.data.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author chenshun00@gmail.com
 * @since 2021/9/2 9:41 下午
 */
public class BPlusTree {

    private Node root;


    public void insert(int data) {
        //如果root节点为空
        if (root == null) {
            root = new Node();
            root.setData(data);
            return;
        }
        //找到数据节点
        final Node node = findNode(root, data);
        assert node != null;
        final int count = node.setData(data);
        if (count <= m - 1) {
            return;
        }
        //节点分裂
        split(node, node == root);
    }

    private void split(Node node, boolean isRoot) {
        //1、选出中间节点 ， 这里分为偶数和奇数，奇数取中间值. 偶数取中间值-1
        int childIndex = (int) (m % 2 == 0 ? (Math.ceil(m >> 1) - 1) : Math.ceil(m >> 1));
        final int[] key = node.key;
        //上移节点的数据
        final int indexValue = key[childIndex];
        //root节点需要进行抽离
        if (isRoot) {
            Node newRoot = new Node();
            newRoot.setData(indexValue);
            root = newRoot;
            //当节点分裂成2个节点 拷贝左边数据到left节点 拷贝右边数据到right节点
            Node leftChild = new Node();
            leftChild.key = new int[childIndex];
            System.arraycopy(node.key, 0, leftChild.key, 0, childIndex);
            leftChild.trim();
            leftChild.parent = root;
            //右节点
            Node rightChild = new Node();
            rightChild.key = new int[key.length - childIndex - 1];
            System.arraycopy(node.key, childIndex + 1, rightChild.key, 0, key.length - childIndex - 1);
            rightChild.trim();
            rightChild.parent = root;

            //节点修正，一开始的root只有2个孩子,直接用0和1使用即可
            root.trim();
            root.setChild(0, leftChild);
            root.setChild(1, rightChild);

            final Node[] children = node.children;
            //如果当前节点的孩子不为空，需要对孩子的指向进行修改，分别把node的一部分指向修改到left，一部分修改到right
            if (children != null) {
                //进行拷贝
                leftChild.children = new Node[childIndex + 1];
                System.arraycopy(children, 0, leftChild.children, 0, childIndex + 1);
                //孩子节点指向parent节点
                for (Node child : leftChild.children) {
                    child.parent = leftChild;
                }
                rightChild.children = new Node[children.length - childIndex - 1];
                System.arraycopy(children, childIndex + 1, rightChild.children, 0, children.length - childIndex - 1);
                for (Node child : rightChild.children) {
                    child.parent = rightChild;
                }
            }
        } else {
            //             2000(A)
            //            /     \
            // [750 1000 500]B  [3000]C
            // 当前Node=B
            //获取当前叶子节点的parent节点 A
            final Node parent = node.parent;
            //            1000,2000(A)
            //            /     \
            // [750 1000 500]B  [3000]C
            parent.setData(indexValue);

            //当切节点分裂成2个节点
            Node leftChild = new Node();
            leftChild.key = new int[childIndex];
            System.arraycopy(node.key, 0, leftChild.key, 0, childIndex);
            leftChild.trim();
            leftChild.parent = parent;
            //右节点
            Node rightChild = new Node();
            rightChild.key = new int[key.length - childIndex - 1];
            System.arraycopy(node.key, childIndex + 1, rightChild.key, 0, key.length - childIndex - 1);
            rightChild.trim();
            rightChild.parent = parent;

            var children = parent.children;
            int parentChildIndex = -1;
            for (int i = 0; i < children.length; i++) {
                if (children[i] == node) {
                    parentChildIndex = i;
                    break;
                }
            }

            var tempChildren = new Node[children.length + 1];
            System.arraycopy(children, 0, tempChildren, 0, parentChildIndex);
            System.arraycopy(children, parentChildIndex + 1, tempChildren, parentChildIndex + 2, children.length - parentChildIndex - 1);
            parent.children = tempChildren;
            parent.children[parentChildIndex] = leftChild;
            parent.children[parentChildIndex + 1] = rightChild;
            parent.trim();

            final Node[] nodeChildren = node.children;
            if (nodeChildren != null) {
                //进行分裂
                leftChild.children = new Node[leftChild.numberOfNodes + 1];
                //进行拷贝
                System.arraycopy(nodeChildren, 0, leftChild.children, 0, leftChild.numberOfNodes + 1);
                for (Node child : leftChild.children) {
                    child.parent = leftChild;
                }
                rightChild.children = new Node[nodeChildren.length - leftChild.numberOfNodes - 1];
                System.arraycopy(nodeChildren, leftChild.numberOfNodes + 1, rightChild.children, 0, nodeChildren.length - leftChild.numberOfNodes - 1);
                for (Node child : rightChild.children) {
                    child.parent = rightChild;
                }
            }
            if (parent.key.length >= m) {
                //在二次迭代中，需要将当前树的孩子的子树修改为新生成的子树的孩子
                split(parent, parent == root);
            }
        }
    }

    //这里的递归也可以用 while(next != null){ next = next.child();}的形式来解决
    private Node findNode(Node node, int data) {
        if (node == null) return null;
        //如果root是叶子节点
        if (node.isLeaf()) {
            return node;
        }
        //继续寻找叶子节点
        //从root节点中寻找下继节点
        final Node nextChild = nextChild(node, data);
        if (nextChild == null) {
            return null;
        }
        return findNode(nextChild, data);
    }

    /**
     * 寻找当前满足data要求的孩子节点
     *
     * @param node 当前节点
     * @param data 数据
     */
    private Node nextChild(Node node, int data) {
        final int min = node.getMin();
        //比最小的还小
        if (data <= min) {
            return node.children[0];
        }
        final int max = node.getMax();
        //比最大的还大
        if (data >= max) {
            return node.children[node.numberOfNodes];
        }
        //选中
        for (int i = 1; i < node.key.length; i++) {
            final int value = node.key[i];
            if (data <= value) {
                return node.children[i];
            }
        }
        return null;
    }


    //数据和孩子
    // m/2<=数据<=m-1
    // m/2<=孩子<=m-1
    private final int m;

    public BPlusTree(int m) {
        this.m = m;
    }

    public void traverse() {
        if (root == null) return;
        var queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            try {
                final Object take = queue.take();
                if (take instanceof Node) {
                    Node parent = (Node) take;
                    doTraverse(parent);
                    System.out.println();
                    final Node[] children = parent.children;
                    if (children == null) {
                        continue;
                    }
                    queue.add(children);
                } else {
                    Node[] children = (Node[]) take;
                    List<Node> nodes = new ArrayList<>();
                    for (Node child : children) {
                        doTraverse(child);
                        if (child != null && child.children != null) {
                            nodes.addAll(Arrays.asList(child.children));
                        }
                    }
                    System.out.println();
                    if (!nodes.isEmpty()) {
                        queue.add(nodes.toArray(new Node[0]));
                    }
                }
            } catch (InterruptedException ignore) {

            }
        }
        System.out.println();
        System.out.println("======");
    }

    private void doTraverse(Node node) {
        if (node == null || node.key == null) return;
        final int[] key = node.key;
        final int level = maxLevel(node);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("     ".repeat(Math.max(0, level))).append("[");
        for (int j : key) {
            stringBuilder.append(j).append(",");
        }
        if (node.isLeaf() && node.next != null) {
            stringBuilder.append("---->");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length()).append("]");
        System.out.print(stringBuilder.toString());
    }

    private static <T extends Comparable<?>> int maxLevel(Node node) {
        if (node == null)
            return 0;
        Node temp = node.children == null ? null : node.children.length <= 0 ? null : node.children[0];
        return maxLevel(temp) + (temp == null ? 1 : temp.numberOfNodes);
    }


    private final class Node {
        public int numberOfNodes = 0;
        /**
         * the array that holds the keys value.
         */
        public int key[];

        /**
         * 父亲节点
         */
        public Node parent;
        /**
         * the array that holds the references of the keys in the node.
         */
        public Node children[];

        public Node next;

        public int setData(int data) {
            int[] newKey = new int[numberOfNodes + 1];
            if (key != null) {
                System.arraycopy(key, 0, newKey, 0, key.length);
            }
            newKey[numberOfNodes] = data;
            key = newKey;
            numberOfNodes++;
            //这里的排序是个问题，如何对key进行排序
            Arrays.sort(key);
            assert numberOfNodes < key.length;
            return numberOfNodes;
        }

        public void setChild(int index, Node child) {
            Node[] newChild = new Node[numberOfNodes + 1];
            if (children != null) {
                System.arraycopy(children, 0, newChild, 0, index);
                newChild[index] = child;
                System.arraycopy(children, index, newChild, index + 1, children.length - index - 1);
            } else {
                newChild[0] = child;
            }
            children = newChild;
        }

        public boolean isLeaf() {
            return children == null;
        }


        public void trim() {
            numberOfNodes = key == null ? 0 : key.length;
        }

        public int getMax() {
            return key[numberOfNodes - 1];
        }

        public int getMin() {
            return key[0];
        }

        public void delete(int data) {
            int[] newKey = new int[numberOfNodes - 1];
            for (int i = 0; i < key.length; i++) {
                if (key[i] == data) {
                    key[i] = -996;
                }
            }
            if (key.length == 1) {
                return;
            }
            Arrays.sort(key);
            System.arraycopy(key, 1, newKey, 0, numberOfNodes - 1);
            key = newKey;
            trim();
        }
    }

}
