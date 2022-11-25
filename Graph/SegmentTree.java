class SegmentTree {
    static int tree[];

    public static void main(String agrs[]) {
        int[] arr = { 1, 3, 2, -2, 4, 5 };
        int n = arr.length;

        new SegmentTree(arr, n);
        System.out.println("Minimum: " + query(tree, 0, 0, n - 1, 0, 5));
        updateTree(tree, 0, 0, n - 1, 5, -50);
        System.out.println("Minimum After update: " + query(tree, 0, 0, n - 1, 4,
                4));

        // updateRange(tree, 0, 0, n - 1, 0, 0, -100);
        // System.out.println("Minimum After update: " + query(tree, 0, 0, n - 1, 0,
        // 5));

    }

    static int getMid(int start, int end) {
        return start + (end - start) / 2;
    }

    SegmentTree(int[] arr, int n) {
        // Height of the Segment Tree
        int x = (int) (Math.ceil(Math.log(n)) / Math.log(2));

        // Maximum size of segment tree
        // Max_size is also 4*N in case of 0 based indexing
        // int max_size = 2 * ((int) Math.pow(2, x) - 1);

        int max_size = 4 * n;
        tree = new int[max_size];

        constructSTree(arr, 0, n - 1, 0);
    }

    /*
     * si = index of current node in segment tree
     * ss & se --> Starting and ending indexes of the segment represented by current
     * node, i.e., st[si]
     */
    int constructSTree(int[] arr, int ss, int se, int si) {
        if (ss == se) {
            tree[si] = arr[ss];
            return arr[ss];
        }

        int mid = getMid(ss, se);
        tree[si] = Math.min(constructSTree(arr, ss, mid, si * 2 + 1), constructSTree(arr, mid + 1, se, si * 2 + 2));
        return tree[si];
    }

    // Return min element from the tree lying between startQuery and endQuery
    // sq 1, 4,
    // s 2, 3
    static int query(int[] tree, int index, int start, int end, int startQuery, int endQuery) {
        // No Overlap
        if (startQuery > endQuery || startQuery > end || endQuery < start)
            return Integer.MAX_VALUE;

        // Complete Overlap
        if (startQuery <= start && endQuery >= end)
            return tree[index];

        // Partial Overlap- call both sides;
        int mid = getMid(start, end);
        int leftMin = query(tree, 2 * index + 1, start, mid, startQuery, endQuery);
        int rightMin = query(tree, 2 * index + 2, mid + 1, end, startQuery, endQuery);
        return Math.min(leftMin, rightMin);
    }

    static void updateTree(int[] tree, int index, int start, int end, int pos, int value) {
        // No Overlap
        if (pos < start || pos > end)
            return;

        // Reached leaf node
        if (start == end) {
            tree[index] = value;
            return;
        }

        // Partial Overlap
        int mid = getMid(start, end);
        updateTree(tree, 2 * index + 1, start, mid, pos, value);
        updateTree(tree, 2 * index + 2, mid + 1, end, pos, value);
        tree[index] = Math.min(tree[2 * index + 1], tree[2 * index + 2]);
        return;
    }

    // Range update = increment every value in range rs, re by a value v)

    static void updateRange(int[] tree, int index, int start, int end, int rs, int re, int value) {
        // No Overlap
        if (rs > end || re < start)
            return;

        // Reached leaf node
        if (start == end) {
            tree[index] += value;
            return;
        }

        // Lying in the range
        int mid = getMid(start, end);
        updateRange(tree, 2 * index + 1, start, mid, rs, re, value);
        updateRange(tree, 2 * index + 2, mid + 1, end, rs, re, value);
        tree[index] = Math.min(tree[2 * index + 1], tree[2 * index + 2]);
        return;
    }

}