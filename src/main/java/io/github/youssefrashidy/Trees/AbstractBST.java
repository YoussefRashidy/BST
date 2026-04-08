package io.github.youssefrashidy.Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractBST<K extends Comparable<? super K>, V, N extends AbstractBST.Node<K, V, N>> {

    protected final N NIL = createNIL();
    protected N root = NIL;
    protected int size;
    private static final String RESET   = "\u001B[0m";
    private static final String FG      = "\u001B[36m";
    private static final String DIM     = "\u001B[2m";
    private static final String BOLD    = "\u001B[1m";

    protected static class Node<K extends Comparable<? super K>, V, N extends Node<K, V, N>> {
        K key;
        V val;
        N left;
        N right;
        N parent;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public Node() {
        }
    }

    public int getSize() {
        return this.size;
    }

    public boolean insert(K key, V val) {
        return insertRaw(key, val) != NIL;
    }

    protected N insertRaw(K key, V val) {
        N y = NIL;
        N x = this.root;
        while (x != this.NIL) {
            y = x;
            int cmp = x.key.compareTo(key);
            if (cmp == 0) return NIL;
            else if (cmp > 0) x = x.left;
            else x = x.right;
        }
        N z = createNode(key, val);
        z.parent = y;
        if (y == this.NIL) root = z;
        else if (y.key.compareTo(z.key) > 0) y.left = z;
        else y.right = z;
        z.left = z.right = NIL;
        size++;
        return z;
    }

    public boolean delete(K key) {
        N z = containsNode(key);
        if (z == NIL) return false;
        if (z.left == NIL) transplant(z, z.right);
        else if (z.right == NIL) transplant(z, z.left);
        else {
            N y = getMin(z.right);
            if (y.parent != z) {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
        }
        size--;
        return true;
    }

    protected N getMin(N x) {
        while (x.left != NIL) x = x.left;
        return x;
    }

    protected N containsNode(K key) {
        N y = NIL;
        N x = root;
        while (x != NIL) {
            y = x;
            int cmp = x.key.compareTo(key);
            if (cmp == 0) return x;
            else if (cmp > 0) x = x.left;
            else x = x.right;
        }
        return NIL;
    }

    protected void transplant(N u, N v) {
        if (u.parent == NIL) root = v;
        else if (u == u.parent.left) u.parent.left = v;
        else u.parent.right = v;
        v.parent = u.parent;
    }

    public boolean contains(K key) {
        N y = NIL;
        N x = root;
        while (x != NIL) {
            y = x;
            int cmp = x.key.compareTo(key);
            if (cmp == 0) return true;
            else if (cmp > 0) x = x.left;
            else x = x.right;
        }
        return false;
    }

    public List<Map.Entry<K, V>> inOrder() {
        List<Map.Entry<K, V>> list = new ArrayList<>();
        inOrder(list, root);
        return list;
    }

    public void clear() {
        root = NIL ;
        size = 0 ;
    }

    private void inOrder(List<Map.Entry<K, V>> list, N root) {
        if (root == NIL) return;
        inOrder(list, root.left);
        list.add(Map.entry(root.key, root.val));
        inOrder(list, root.right);
    }

    public int getHeight() {
        if (root == NIL) return 0;
        return getHeight(root);
    }

    private int getHeight(N root) {
        if (root == NIL) return -1;
        return 1 + Math.max(
                getHeight(root.left)
                , getHeight(root.right));
    }

    public void print(String prefix, N node, boolean isLast, boolean isRoot) {
        // delegates to overridable hook
        printNode(prefix, node, isLast, isRoot);
    }

    public void print() {
        if (root == NIL) {
            System.out.println("(empty tree)");
            return;
        }
        print("", root, true, true);
    }


    protected void printNode(String prefix, N node, boolean isLast, boolean isRoot) {
        boolean hasLeft  = node.left  != NIL;
        boolean hasRight = node.right != NIL;

        String label = FG + "○ " + BOLD + node.key + RESET
                + FG + " → " + node.val + RESET;

        System.out.println(DIM + prefix + RESET + label);

        if (!hasLeft && !hasRight) return;

        String childPrefix = isRoot
                ? "    "
                : prefix.endsWith("├── ") ? prefix.replace("├── ", "│   ")
                : prefix.replace("└── ", "    ");

        if (hasLeft && hasRight) {
            print(childPrefix + "├── ", node.left,  false, false);
            print(childPrefix + "└── ", node.right, true,  false);
        } else if (hasLeft) {
            print(childPrefix + "└── ", node.left,  true,  false);
        } else {
            print(childPrefix + "└── ", node.right, true,  false);
        }
    }

    protected abstract N createNode(K key, V value);

    protected abstract N createNIL();

}
