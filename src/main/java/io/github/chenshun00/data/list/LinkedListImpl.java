package io.github.chenshun00.data.list;

/**
 * @author luobo.cs@raycloud.com
 * @since 2021/9/6 9:16 上午
 */
public class LinkedListImpl<E> {

    private static class Node<E> {
        public Node<E> prev;
        public Node<E> next;
        private E data;
    }

    private int location;

    private final Node<E> head;
    private final Node<E> tail;

    public LinkedListImpl() {
        head = new Node<E>();
        tail = new Node<E>();
        head.next = tail;
        head.prev = tail;

        tail.next = head;
        tail.prev = head;
        location = 0;
    }

    public int size() {
        return location;
    }

    public void add(E data) {
        Node<E> temp = new Node<>();
        //1、获取尾节点的前一个节点
        final Node<E> prev = tail.prev;
        //2、修改尾部指向
        prev.next = temp;
        tail.prev = temp;
        //3、修改当前节点指向
        temp.next = tail;
        temp.prev = prev;
        temp.data = data;
        location = location + 1;
    }

    public void add(int index, E data) {
        //1、找到下标节点
        Node<E> node = getNode(index);
        if (node == null) {
            add(data);
            return;
        }
        //找到前一个节点
        node = node.prev;
        //2、修改指向
        Node<E> temp = new Node<>();
        temp.data = data;
        //
        temp.prev = node;
        temp.next = node.next;
        //2.1
        node.next = temp;
        location = location + 1;
    }

    public boolean contains(E data) {
        Node<E> next = head.next;
        while (!next.equals(tail)) {
            final E nodeData = next.data;
            if (data.equals(nodeData)) {
                return true;
            }
            next = next.next;
        }
        return false;
    }

    public boolean remove(E data) {
        final Node<E> node = getNode(data);
        if (node == null) {
            return false;
        }

        //前一个节点的下一个修改为当前节点的下一个
        node.prev.next = node.next;
        //下一个节点的前一个修改为当前节点的前一个
        node.next.prev = node.prev;
        location = location - 1;
        return true;
    }

    public E get(int index) {
        final Node<E> node = getNode(index);
        return node == null ? null : node.data;
    }


    /**
     * 可以用二分法优化
     *
     * @param index 下标索引
     * @return node或者null
     */
    private Node<E> getNode(int index) {
        Node<E> next = head.next;
        for (int i = 0; i < location; i++) {
            if (i == index) {
                return next;
            } else {
                next = next.next;
            }
        }
        return null;
    }

    private Node<E> getNode(E data) {
        Node<E> next = head.next;
        while (!next.equals(tail)) {
            final E nodeData = next.data;
            if (data.equals(nodeData)) {
                return next;
            }
            next = next.next;
        }
        return null;
    }
}
