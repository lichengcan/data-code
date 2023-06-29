package io.github.chenshun00.data.tree;

import io.github.chenshun00.data.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <a href="https://stackoverflow.com/questions/5890960/print-btree-by-level">print-btree-by-level</a>
 *
 * @author chenshun00@gmail.com
 * @since 2021/8/22 7:51 上午
 */
public class BTree {

    public static void main(String[] args) {
        BTree bTree = new BTree(5);
        bTree.insert(1000);
        bTree.insert(1100);
        bTree.insert(1200);
        bTree.insert(1300);
        bTree.insert(1400);
        bTree.insert(1500);
        bTree.insert(1600);
        bTree.insert(1700);
        bTree.insert(1800);
        bTree.insert(1900);
        bTree.insert(2000);
        bTree.insert(2100);
        bTree.insert(2200);
        bTree.insert(2300);
        bTree.insert(2400);
        bTree.insert(2500);
        bTree.insert(2600);
        bTree.insert(2700);
        bTree.insert(2800);
        bTree.insert(2900);
        bTree.insert(3000);
        bTree.insert(3100);
        bTree.traverse();

//        bTree.delete(2700);
//        bTree.traverse();
//
        bTree.delete(3000);
        bTree.delete(3100);
        bTree.delete(2900);
        bTree.delete(2800);
        bTree.delete(2700);
        bTree.traverse();
        bTree.delete(2600);
        bTree.traverse();
        bTree.delete(2500);
        bTree.delete(2400);
        bTree.traverse();
        bTree.delete(2300);
        bTree.traverse();
    }

    //数据和孩子
    // m/2<=数据<=m-1
    // m/2<=孩子<=m-1
    private final int m;

    private Node root;

    public BTree(int m) {
        this.m = m;
    }

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

    public boolean delete(int data) {
        //找节点
        final Node node = findDeleteNode(root, data);
        if (node == null) {
            return false;
        }
        boolean exist = Arrays.stream(node.key).anyMatch(x -> x == data);
        if (!exist) {
            System.out.println("node不存在");
            return false;
        }
        //处理叶子节点
        if (node.isLeaf()) {
            handleExternalNode(node, data);
        } else {
            //处理非叶子节点
            handleInternalNode(node, data);
        }
        return true;
    }

    /**
     * 处理外部节点(叶子节点)
     *
     * @param node 节点
     * @param data 数据
     */
    private void handleExternalNode(Node node, int data) {
        //自己是富裕节点
        if (isWealthyNode(node)) {
            node.delete(data);
        } else {
            //自己是贫穷节点
            node.delete(data);
            final Node parent = node.parent;
            //找到兄弟节点
            final Tuple<Node, Boolean> tuple = findBrother(node);
            Node brother = tuple.first;
            final Boolean right = tuple.second;
            assert brother != null;
            final int parentMax = parent.getMax();
            //兄弟节点是富裕节点
            if (isWealthyNode(brother)) {
                //parent 最大key下移
                node.setData(parentMax);
                final int brotherMax = right ? brother.getMin() : brother.getMax();
                brother.delete(brotherMax);
                //兄弟节点最大key上移
                parent.delete(data);
                parent.setData(brotherMax);
            } else {
                //兄弟节点也是贫穷节点
                //先问parent一个节点 然后合并贫穷节点，修改指向，parent节点成为新的节点N
                Node newNode = new Node();
                final int[] key = brother.key;
                for (int i : key) {
                    newNode.setData(i);
                }
                for (int i : node.key) {
                    newNode.setData(i);
                }
                newNode.setData(parentMax);
                //节点合并完成了，修改指向
                final Node[] children = parent.children;

                int parentChildIndex = -1;
                for (int i = 0; i < children.length; i++) {
                    if (children[i] == node) {
                        parentChildIndex = i;
                        break;
                    }
                }
                parent.children[parentChildIndex - 1] = newNode;
                newNode.parent = parent;
                Node[] newChildren = new Node[parentChildIndex];
                System.arraycopy(parent.children, 0, newChildren, 0, parentChildIndex);
                if (parentChildIndex < children.length - 1) {
                    System.arraycopy(parent.children, parentChildIndex + 1, newChildren, parentChildIndex, children.length - parentChildIndex - 1);
                }
                parent.children = newChildren;
                parent.trim();
                //向parent借了节点，现在要处理parent的情况，如果parent也有这种情况则需要额外进行处理
                replace(parent, parentMax);
            }
        }
    }

    private void replace(Node node, int data) {
        //自己是富裕节点
        if (isWealthyNode(node)) {
            node.delete(data);
        } else {
            node.delete(data);
            final Node parent = node.parent;
            //找到兄弟节点
            final Tuple<Node, Boolean> tuple = findBrother(node);
            Node brother = tuple.first;
            final Boolean right = tuple.second;
            assert brother != null;
            final int parentMax = parent.getMax();
            //兄弟节点是富裕节点
            if (isWealthyNode(brother)) {
                //parent 最大key下移
                node.setData(parentMax);
                final int brotherMax = right ? brother.getMin() : brother.getMax();
                brother.delete(brotherMax);
                //兄弟节点最大key上移
                parent.setData(brotherMax);
            } else {
                //兄弟节点也是贫穷节点
                //先问parent一个节点 然后合并贫穷节点，修改指向，parent节点成为新的节点N
                Node newNode = new Node();
                final int[] key = brother.key;
                for (int i : key) {
                    newNode.setData(i);
                }
                for (int i : node.key) {
                    newNode.setData(i);
                }
                newNode.setData(parentMax);
                //节点合并完成了，修改指向
                final Node[] children = parent.children;

                int parentChildIndex = -1;
                for (int i = 0; i < children.length; i++) {
                    if (children[i] == node) {
                        parentChildIndex = i;
                        break;
                    }
                }

                final Node[] brotherChildren = brother.children;

                //把孩子节点的指向导向到新节点
                int i = 0;
                for (; i < brotherChildren.length; i++) {
                    newNode.setChild(i, brotherChildren[i]);
                }
                final Node[] nodeChildren = node.children;
                for (int i1 = 0; i1 < nodeChildren.length; i1++) {
                    newNode.setChild(i + i1, nodeChildren[i1]);
                }

                for (Node child : newNode.children) {
                    child.parent = newNode;
                }

                if (parent.parent == null) {
                    root = newNode;
                    root.trim();
                    return;
                }
                parent.children[parentChildIndex - 1] = newNode;
                //新的孩子节点
                Node[] newChildren = new Node[parentChildIndex];
                System.arraycopy(parent.children, 0, newChildren, 0, parentChildIndex);
                if (parentChildIndex < children.length - 1) {
                    System.arraycopy(parent.children, parentChildIndex + 1, newChildren, parentChildIndex, children.length - parentChildIndex - 1);
                }
                parent.children = newChildren;
                parent.trim();
                //向parent借了节点，现在要处理parent的情况，如果parent也有这种情况则需要额外进行处理
                replace(parent, parentMax);
            }
        }
    }

    /**
     * 处理内部节点(非叶子节点)
     *
     * @param node 节点
     * @param data 数据
     */
    private void handleInternalNode(Node node, int data) {
        int temp = -1;
        for (int i = 0; i < node.key.length; i++) {
            if (node.key[i] == data) {
                temp = i;
                break;
            }
        }
        node.delete(data);
        final Node maxNode = doFindLeafNode(node, temp);
        final int max = maxNode.getMax();
        node.setData(max);
        handleExternalNode(maxNode, max);
    }

    private Node doFindLeafNode(Node node, int index) {
        Node child = node.children[index];
        if (child.isLeaf()) {
            return child;
        }
        while (!child.isLeaf()) {
            child = child.children[child.numberOfNodes];
        }
        return child;
    }

    /**
     * 找兄弟节点
     *
     * @param node 节点
     * @return 兄弟节点信息，兄弟是左还是右
     */
    private Tuple<Node, Boolean> findBrother(Node node) {
        final Node parent = node.parent;
        int index = -1;
        for (int i = 0; i < parent.children.length; i++) {
            if (parent.children[i] == node) {
                index = i;
                break;
            }
        }
        //取右兄弟
        if (index == 0) {
            return new Tuple<>(parent.children[index + 1], true);
        } else {
            final Node leftBro = parent.children[index - 1];
            if (index + 1 >= parent.children.length) {
                return new Tuple<>(leftBro, false);
            }
            final Node rightBro = parent.children[index + 1];
            if (isWealthyNode(leftBro)) {
                return new Tuple<>(leftBro, false);
            }
            if (isWealthyNode(rightBro)) {
                return new Tuple<>(rightBro, true);
            }
            return new Tuple<>(leftBro, false);
        }
    }


    private boolean isWealthyNode(Node node) {
        return node.numberOfNodes > Math.ceil(m / 2.0) - 1;
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

    //这里的递归也可以用 while(next != null){ next = next.child();}的形式来解决
    private Node findDeleteNode(Node node, int data) {
        if (node == null) return null;
        //如果root是叶子节点

        final int max = node.getMax();

        final int min = node.getMin();
        if (data >= min && data <= max) {
            return node;
        }
        //继续寻找叶子节点
        //从root节点中寻找下继节点
        final Node nextChild = nextChild(node, data);
        if (nextChild == null) {
            return null;
        }
        return findDeleteNode(nextChild, data);
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
        public int numberOfNodes;
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

        /**
         * The constructor of the node class
         * The node can have at most 3 keys. We have 4 references for each node, and assign the node to be isLeaf.
         */
        Node() {
            numberOfNodes = 0;
        }

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
