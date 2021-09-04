package io.github.chenshun00.data.hash;

import java.util.Objects;

/**
 * 简单hash实现，一个固定数组+链表实现，不进行 <strong>rehash</strong>
 *
 * @author chenshun00@gmail.com
 * @since 2021/9/4 4:49 下午
 */
public class SimpleHashImpl<K, V> implements Hash<K, V> {

    public Node<K, V>[] tables;

    private int size;

    @SuppressWarnings("unchecked")
    public SimpleHashImpl(int capacity) {
        this.tables = new Node[capacity];
    }

    @Override
    public V put(K key, V value) {
        check(key, value);
        final int hash = hash(key);

        final int index = hash & (tables.length - 1);
        Node<K, V> head = tables[index];
        if (head == null) {
            final Node<K, V> kvNode = new Node<>(key, value, null);
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
            head.next = new Node<>(key, value, null);
        }
        incr();
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

    private void incr() {
        size++;
    }

    private void decr() {
        size--;
    }
}
