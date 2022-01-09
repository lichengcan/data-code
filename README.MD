## 部分数据结构的Java实现

### 树🌲

* 二叉树 io.github.chenshun00.data.tree.BinaryTree
    * [二叉树的双节点实现](https://www.yuque.com/chenshun00/sbny2o/xb92cq)
    * [二叉树的三节点实现](https://www.yuque.com/chenshun00/sbny2o/rxwtzp)
* 平衡二叉树 io.github.chenshun00.data.Node.AVLTree
    * [平衡二叉树的实现](https://www.yuque.com/chenshun00/sbny2o/rxwtzp)
* 红黑树 io.github.chenshun00.data.Node.RedBlackTree
    * [红黑树的实现](https://www.yuque.com/chenshun00/sbny2o/dey8sg)
* B树 io.github.chenshun00.data.tree.BTree
    * [B树的实现](https://www.yuque.com/chenshun00/sbny2o/ty9iys)
* B+树 io.github.chenshun00.data.tree.BPlusTree
    * [B+树的实现](https://www.yuque.com/chenshun00/sbny2o/md7guz)

### 哈希表

* 固定容量的hash实现 io.github.chenshun00.data.hash.SimpleHashImpl
* resize的hash实现 io.github.chenshun00.data.hash.ReHashImpl

### 链表

* 合并 `2` 个有序列表 ✅
* 合并 `K` 个有序列表 ✅
    * 优先级队列(堆)
* 寻找链表的倒数第 `k` 个节点 ✅
* 寻找链表的中点 ✅
* 判断单链表是否包含环 ✅
* 判断单链表是否包含环并找出环起点 🍵(差,没有想到那个意思)
* 判断两个单链表是否相交并找出交点 ✅

### 递归

> 不太懂, 尤其是链表反转部分的递归

* 递归反转一个链表 [反转链表 easy](https://leetcode-cn.com/problems/reverse-linked-list/)
* 反转链表中的前M位 (特殊的反转前N,M位),N为0,反转前N位
* 反转链表中前N,M位 [反转链表 II middle](https://leetcode-cn.com/problems/reverse-linked-list-ii/)

### 启动要求

* JDK 11，存在部分 `var` 变量，很少，如果没有JDK11，可以全局查看替换一下