package io.github.chenshun00.data;

/**
 * @author luobo.cs@raycloud.com
 * @since 2021/9/2 9:24 上午
 */
public class Tuple<K, V> {

    public K first;

    public V second;

    public Tuple(K first, V second) {
        this.first = first;
        this.second = second;
    }
}
