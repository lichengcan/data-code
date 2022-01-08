package io.github.chenshun00.data.queue;

/**
 * <a href="https://www.yuque.com/chenshun00/sbny2o/tm8xas">基于链表的队列实现</a>
 *
 * @author chenshun00@gmail.com
 * @since 2021/7/25 10:47 上午
 */
public class LinkedListQueue<E> {


    private int size = 0;
    private Node<E> head;
    private Node<E> tail;

    public LinkedListQueue() {
        head = new Node<>();
        tail = new Node<>();
        head.next = tail;
        head.prev = tail;

        tail.next = head;
        tail.prev = head;
    }

    public int size() {
        return size;
    }

    public void push(E e) {
        final Node<E> temp = new Node<>();

        //1、获取尾节点的前一个节点
        final Node<E> prev = tail.prev;
        //2、修改尾部指向
        prev.next = temp;
        tail.prev = temp;
        //3、修改当前节点指向
        temp.next = tail;
        temp.prev = prev;
        temp.data = e;
        size++;
    }

    public E poll() {
        final Node<E> next = head.next;
        if (next.equals(tail)) {
            return null;
        }
        final E data = next.data;
        head = next;
        size--;
        return data;
    }

    private static class Node<E> {
        public Node<E> prev;
        public Node<E> next;
        private E data;
    }
}