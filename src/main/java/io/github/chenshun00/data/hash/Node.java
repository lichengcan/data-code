package io.github.chenshun00.data.hash;

/**
 * @author chenshun00@gmail.com
 * @since 2021/9/4 4:51 下午
 */
public class Node<K, V> {

    public int hash;

    public K k;

    public V v;

    public Node<K,V> next;

    public Node(K k, V v, Node<K, V> next) {
        this.k = k;
        this.v = v;
        this.next = next;
    }

    public Node(int hash, K k, V v, Node<K, V> next) {
        this.hash = hash;
        this.k = k;
        this.v = v;
        this.next = next;
    }
}
