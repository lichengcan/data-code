package io.github.chenshun00.data.list;

import java.util.Arrays;

/**
 * <ul>
 *     <li>1、一个跳表应该有几个层组成，第一层包含所有的元素</li>
 *     <li>2、每一层都是有序链表</li>
 *     <li>3、如果元素x出现在第i层，则所有比i小的层都包含x</li>
 *     <li>4、第i层的元素通过一个down指针指向下一层拥有相同值的元素</li>
 * </ul>
 *
 * @author chenshun00@gmail.com
 * @since 2022/2/12 3:37 下午
 */
public class SkipList<E> {

    private static final double p = 0.25D;

    int MAX_LEVEL = 32;

    /**
     * 跳表实际层数
     */
    int level;

    /**
     * 跳表头节点
     */
    SkipListNode<E> header;

    public static void main(String[] args) {
        SkipList<String> list = new SkipList<>();
        list.add(3, "耳朵听声音");
        list.add(7, "镰刀来割草");
        list.add(6, "口哨嘟嘟响");
        list.add(4, "红旗迎风飘");
        list.add(2, "小鸭水上漂");
        list.add(9, "勺子能吃饭");
        list.add(1, "铅笔细又长");
        list.add(5, "秤钩来买菜");
        list.add(8, "麻花扭一扭");
        list.printList();
        System.out.println("1==" + list.get(1));
        list.remove(1);
        System.out.println("====");
        System.out.println("1==" + list.get(1));
        list.printList();
    }

    /**
     * 创建节点
     *
     * @param level 节点所在层数
     * @param key   节点key
     * @param value 节点value
     * @return {@link SkipListNode<E>}
     */
    private SkipListNode<E> createNode(int level, int key, E value) {
        return new SkipListNode<E>(key, value, level);
    }


    /**
     * 初始化跳跃表
     * 1、初始化level为1级
     * 2、填充header的的next指针为null
     */
    public SkipList() {
        this.level = 0;
        this.header = createNode(MAX_LEVEL, Integer.MIN_VALUE, null);
        for (int i = 0; i < MAX_LEVEL; i++) {
            this.header.forwards[i] = null;
        }
    }


    /**
     * 添加节点到跳跃表
     *
     * @param key   ke3y
     * @param value value
     */
    public void add(int key, E value) {
        final int randomLevel = getRandomLevel();
        final SkipListNode<E> node = createNode(randomLevel, key, value);

        //从高层开始向底层遍历，为每一层补充节点信息，操作方式同链表
        for (int i = level - 1; i >= 0; i--) {
            SkipListNode<E> closable = header;
            //找到这一层的下一个节点
            while (closable.forwards[i] != null) {
                if (key == closable.forwards[i].key) {
                    closable.forwards[i].value = value;
                    return;
                } else if (key > closable.forwards[i].key) {
                    closable = closable.forwards[i];
                } else {
                    break;
                }
            }
            if (randomLevel > i) {
                if (closable.forwards[i] == null) {
                    closable.forwards[i] = node;
                } else {
                    final SkipListNode<E> next = closable.forwards[i];
                    closable.forwards[i] = node;
                    node.forwards[i] = next;
                }
            }
        }
        if (randomLevel > level) { //如果随机出来的层数比当前的层数还大，那么超过currentLevel的head 直接指向newNode
            for (int i = level; i < randomLevel; i++) {
                header.forwards[i] = node;
            }
            this.level = randomLevel;
        }
    }

    public E get(int key) {
        SkipListNode<E> closable = header;
        for (int i = level - 1; i >= 0; i--) {
            //找到这一层的下一个节点
            while (closable.forwards[i] != null) {
                if (key == closable.forwards[i].key) {
                    return closable.forwards[i].value;
                } else if (key > closable.forwards[i].key) {
                    closable = closable.forwards[i];
                } else {
                    break;
                }
            }
            if (closable.forwards[i] != null && closable.forwards[i].key == key) {
                return closable.forwards[i].value;
            }
        }
        return null;
    }

    public void remove(int key) {
        SkipListNode<E> closable = header;

        for (int i = level - 1; i >= 0; i--) {
            //找到这一层的下一个节点
            while (closable.forwards[i] != null) {
                if (key > closable.forwards[i].key) {
                    closable = closable.forwards[i];
                } else {
                    break;
                }
            }
            if (closable.forwards[i] != null && closable.forwards[i].key == key) {
                final SkipListNode<E> current = closable.forwards[i];
                final SkipListNode<E> next = current.forwards[i];
                closable.forwards[i] = next;
            }
        }
    }

    public void printList() {
        for (int i = level - 1; i >= 0; i--) {
            SkipListNode<E> curNode = this.header.forwards[i];
            System.out.print("HEAD->");
            while (curNode != null) {
                String line = String.format("(%s,%s)->", curNode.key, curNode.value);
                System.out.print(line);
                curNode = curNode.forwards[i];
            }
            System.out.println("NIL");
        }
    }

    private int getRandomLevel() {
        int lvl = 1;
        //Math.random() 返回一个介于 [0,1) 之间的数字
        while (lvl < MAX_LEVEL && Math.random() < p) {
            lvl++;
        }
        return lvl;
    }

    private static class SkipListNode<E> {
        /**
         * key 信息
         * <p>
         * 这个是什么？index 吗？
         */
        int key;
        /**
         * 存放的元素
         */
        E value;
        /**
         * 向前的指针
         * <p>
         * 跳表是多层的，这个向前的指针，最多和层数一样。
         *
         * @since 0.0.4
         */
        SkipListNode<E>[] forwards;

        @SuppressWarnings("all")
        public SkipListNode(int key, E value, int level) {
            this.key = key;
            this.value = value;
            this.forwards = new SkipListNode[level];
        }

        @Override
        public String toString() {
            return "SkipListNode{" +
                    "key=" + key +
                    ", value=" + value +
                    ", forwards=" + Arrays.toString(forwards) +
                    '}';
        }
    }
}
