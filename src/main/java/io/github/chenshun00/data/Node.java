package io.github.chenshun00.data;

/**
 * 节点(二叉搜索树)
 *
 * @author chenshun00@gmail.com
 * @since 2021/08/21
 */
public class Node {

    private String type = "Binary";

    public Node(Node leftChild, Node rightChild, Node parent, Integer data) {
        type = "Binary";
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.parent = parent;
        this.data = data;
    }

    /**
     * AVL 树使用
     */
    public Node(Node leftChild, Node rightChild, Node parent, Integer data, Integer height) {
        type = "AVL";
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.parent = parent;
        this.data = data;
        this.height = height;
    }

    /**
     * 红黑树 树使用
     */
    public Node(boolean black, Node parent, Node leftChild, Node rightChild, Integer data) {
        type = "RB";
        this.black = black;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.data = data;
    }

    public Node leftChild;
    public Node rightChild;
    public Node parent;

    public Integer data;

    //AVL树使用
    public Integer height;

    //红黑树使用
    public boolean black;
    //红黑树使用
    public boolean nil = false;

    public String getMsg() {
        if (type.equals("AVL")) {
            return data + "(" + colorStr(String.valueOf(height)) + ")";
        }
        if (type.equals("RB")) {
            String data = this.data == null ? "N" : this.data.toString();
            return this.black ? data : colorStr(data);
        }
        return String.valueOf(data);
    }

    private static final String RESET = "\033[0m";

    private static String colorStr(String msg) {
        return "\033[" + 31 + "m" + msg + RESET;
    }

}
