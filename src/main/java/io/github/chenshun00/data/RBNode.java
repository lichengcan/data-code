package io.github.chenshun00.data;

/**
 * 红黑树
 *
 * @author chenshun00@gmail.com
 * @since 2021/8/21 7:53 下午
 */
public class RBNode extends Node {

    public boolean black;

    public Node leftChild;
    public Node rightChild;
    public Node parent;
    public Integer data;
    public boolean nil = false;

    public RBNode(boolean black, Node parent, Node leftChild, Node rightChild, Integer data) {
        super(leftChild, rightChild, parent, data);
        this.black = black;
    }

    @Override
    public String getMsg() {
        String data = this.data == null ? "N" : this.data.toString();
        return this.black ? data : colorStr(data);
    }

    private static final String RESET = "\033[0m";

    private static String colorStr(String msg) {
        return "\033[" + 31 + "m" + msg + RESET;
    }

}
