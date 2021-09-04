package io.github.chenshun00.data.hash;

import java.util.Objects;

/**
 * 相对复杂一点的hash实现，添加过程存在<em>rehash</em>的动作
 *
 * @author chenshun00@gmail.com
 * @since 2021/9/4 4:49 下午
 */
public class ReHashImpl<K, V> implements Hash<K, V> {

    public Node<K, V>[] tables;

    private int capacity;

    private int size;

    int threshold;

    /**
     * 扩容因子
     *
     * @see java.util.HashMap#DEFAULT_LOAD_FACTOR
     */
    private final float factor;

    @SuppressWarnings("unchecked")
    public ReHashImpl(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("invalid capacity");
        }
        this.capacity = capacity;
        this.tables = new Node[capacity];
        factor = 0.75f;
        threshold = (int) (this.capacity * factor);
    }

    @Override
    public V put(K key, V value) {
        check(key, value);
        final int hash = hash(key);

        final int index = hash & (tables.length - 1);
        Node<K, V> head = tables[index];
        if (head == null) {
            final Node<K, V> kvNode = new Node<>(hash, key, value, null);
            tables[index] = kvNode;
        } else {
            if (head.k.equals(key)) {
                final V v = head.v;
                head.v = value;
                return v;
            }
            while (head.next != null) {
                head = head.next;
                final K k = head.k;
                if (k.equals(key)) {
                    final V v = head.v;
                    head.v = value;
                    return v;
                }
            }
            head.next = new Node<>(hash, key, value, null);
        }
        incr();
        resize();
        return null;
    }

    @Override
    public V get(K key) {
        Objects.requireNonNull(key, "key can't be null");
        final int hash = hash(key);

        final int index = hash & (tables.length - 1);
        Node<K, V> head = tables[index];
        if (head == null) {
            return null;
        }
        if (head.k.equals(key)) {
            return head.v;
        }
        head = head.next;
        do {
            if (head.k.equals(key)) {
                return head.v;
            }
            head = head.next;
        } while (head.next != null);
        return null;
    }

    @Override
    public V remove(K key) {
        Objects.requireNonNull(key, "key can't be null");
        final int hash = hash(key);

        final int index = hash & (tables.length - 1);
        Node<K, V> head = tables[index];
        if (head == null) {
            return null;
        }
        if (head.k.equals(key)) {
            if (head.next == null) {
                tables[index] = null;
            } else {
                tables[index] = head.next;
            }
            decr();
            return head.v;
        }
        Node<K, V> leader = head;
        head = head.next;
        do {
            if (head.k.equals(key)) {
                leader.next = head.next;
                decr();
                return head.v;
            }
            leader = head;
            head = head.next;
        } while (head.next != null);
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    protected void resize() {
        if (shouldReHash()) {
            int oldCap = capacity;
            capacity = capacity << 1;
            final Node<K, V>[] newTables = new Node[capacity];
            for (int i = 0; i < tables.length; i++) {
                Node<K, V> headNode = tables[i];
                if (headNode != null) {
                    //确定在新table中的下标索引
                    Node<K, V> loHead = null, loTail = null;
                    Node<K, V> hiHead = null, hiTail = null;
                    Node<K, V> next;
                    do {
                        next = headNode.next;
                        if ((headNode.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = headNode;
                            else
                                loTail.next = headNode;
                            loTail = headNode;
                        } else {
                            if (hiTail == null)
                                hiHead = headNode;
                            else
                                hiTail.next = headNode;
                            hiTail = headNode;
                        }
                    } while ((headNode = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTables[i] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTables[i + oldCap] = hiHead;
                    }
                }
            }
            tables = newTables;
            threshold = (int) (this.capacity * factor);
        }
    }

    private boolean shouldReHash() {
        return size >= threshold && tables.length <= 1024;
    }

    private void incr() {
        size++;
    }

    private void decr() {
        size--;
    }
}
