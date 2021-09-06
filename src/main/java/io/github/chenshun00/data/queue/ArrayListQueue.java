package io.github.chenshun00.data.queue;

/**
 * <a href="https://www.yuque.com/chenshun00/sbny2o/utvmmo">基于数组的队列实现</a>
 *
 * @author luobo.cs@raycloud.com
 * @since 2021/7/25 10:47 上午
 */
public class ArrayListQueue<E> {


    private final Object[] elements;

    private int tail;
    private int head;
    private int size = 0;

    private final int initialCapacity;

    public ArrayListQueue(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.elements = new Object[this.initialCapacity];
        tail = 0;
        head = 0;
    }

    public int size() {
        return size;
    }

    /**
     * 添加
     */
    public void push(E e) {
        if (e == null) {
            throw new NullPointerException("element can't be null");
        }
        if (size >= initialCapacity) {
            throw new ArrayIndexOutOfBoundsException("queue is full");
        }
        elements[tail] = e;
        tail++;
        size++;
        if (tail >= initialCapacity) {
            tail = 0;
        }
        if (size >= initialCapacity) {
            size = initialCapacity;
        }
    }

    @SuppressWarnings("unchecked")
    public E poll() {
        final Object element = elements[head];
        if (element == null) {
            return null;
        }
        elements[head] = null;
        head++;
        size--;
        if (head >= initialCapacity) {
            head = 0;
        }
        if (size <= 0) {
            size = 0;
        }
        return (E) element;
    }
}