package io.github.chenshun00.data.hash;

import java.util.Objects;

/**
 * @author chenshun00@gmail.com
 * @since 2021/9/4 4:52 下午
 */
public interface Hash<K, V> {


    /**
     * 将key和value放置到hash表中
     *
     * <ol>
     *     <li>如果hash表中不存在，放置成功并返回null</li>
     *     <li>如果hash表中存在，放置成功并返回旧的值</li>
     * </ol>
     *
     * @param key   key
     * @param value value
     * @return {@link V}
     */
    V put(K key, V value);

    /**
     * 根据key获取对应的value
     *
     * @param key key
     * @return {@link V}
     */
    V get(K key);

    V remove(K key);

    int size();

    default void check(K k, V v) {
        Objects.requireNonNull(k, "key can't be null");
        Objects.requireNonNull(v, "value can't be null");
    }

    default int hash(K key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

}
