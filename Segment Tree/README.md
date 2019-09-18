线段树(Segment Tree)又叫区间树(Interval Tree)，它实际上是一颗二叉树，树种的每一个节点表示一个区间[a, b]，左儿子的区间是[a, (a+b)/2]，右儿子的区间是[(a+b)/2+1, b]。

线段树常用于区间统计/查询相关的问题：比如某些数据可以按区间进行划分，按区间动态进行修改，而且还需要按区间多次进行查询，那么使用线段树可以达到较快查询速度。动态的求/更新区间和、区间最值就适用于用线段树来求解。

由于线段树的深度不会超过logL，所以查询的时间复杂度也是O(logL)。
————————————————
版权声明：本文为CSDN博主「月光下的夜曲」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/luoshengkim/article/details/72568519

left [a, (a+b) / 2)             (floor (a+b) / 2)
right [(a+b) / 2, b]           (ceil (a+b) / 2)

[2, 6]
[2,4]   [5,6]

[2,7]
[2,4]  [5,7]]



/**
 Definition of SegmentTreeNode:
 public class SegmentTreeNode {
     public int start, end;
     public SegmentTreeNode left, right;
     public SegmentTreeNode(int start, int end) {
         this.start = start, this.end = end;
         this.left = this.right = null;
     }
 }

————————————————
版权声明：本文为CSDN博主「月光下的夜曲」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/luoshengkim/article/details/72568519


SEGMENT TREE BUILD

public class Solution {

/ *
* @param start, end: Denote an segment / interval
* @return: The root of Segment Tree
* /
public SegmentTreeNode build(int start, int end) {
    if (start <= end) {
    SegmentTreeNode node = new SegmentTreeNode(start, end);
    if (start == end) {
        return node;
    }
        node.left = build(start, (start + end) / 2);
        node.right = build( (start + end) / 2 + 1, end);
        return node;
    }
    return null;
}

————————————————
版权声明：本文为CSDN博主「月光下的夜曲」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/luoshengkim/article/details/72568519

SEGMENT TREE BUILD2

/**
 * Definition of SegmentTreeNode:
 * public class SegmentTreeNode {
 *     public int start, end, max;
 *     public SegmentTreeNode left, right;
 *     public SegmentTreeNode(int start, int end, int max) {
 *         this.start = start;
 *         this.end = end;
 *         this.max = max
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     *@param A: a list of integer
     *@return: The root of Segment Tree
     */
    public SegmentTreeNode buildHelper(int[] A, int start, int end) {
        if (start <= end) {
            SegmentTreeNode node = new SegmentTreeNode(start, end, A[start]);
            if (start == end) {
                return node;
            }
            node.left = buildHelper(A, start, (start + end) / 2);
            node.right = buildHelper(A, (start + end) / 2 + 1, end);
            node.max = Math.max(node.left.max, node.right.max);
            return node;
        }
        return null;
    }
    public SegmentTreeNode build(int[] A) {
        return buildHelper(A, 0, A.length - 1);
    }
}
————————————————
版权声明：本文为CSDN博主「月光下的夜曲」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/luoshengkim/article/details/72568519
