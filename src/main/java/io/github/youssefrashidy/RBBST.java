package io.github.youssefrashidy;

public class RBBST<K extends Comparable<? super K>, V> extends AbstractBST<K, V, RBBST.RBNode<K, V>> {


    @Override
    public boolean insert(K key, V val) {
        RBNode<K, V> z = insertRaw(key, val);
        if (z == NIL) return false;
        // add fixup
        fixUpRB(z);
        return true;
    }

    private void fixUpRB(RBNode<K, V> z) {
        while (z.parent.color == Color.RED) {
            var x = z.parent;
            if (x.parent.left == x) {
                var y = x.parent.right;
                // Case 1 change colors
                if (y.color == Color.RED) {
                    y.color = x.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    z = x.parent;
                } else {
                    // Case 2 z is right child and x is left
                    if (z == x.right) {
                        // left rotate on x
                        leftRotate(x);
                        z = x;
                        x = z.parent;
                    }
                    // Case 3 z and x form a line change colors and rotate
                    x.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rightRotate(x.parent);
                }
            } else {
                var y = x.parent.left;
                if (y.color == Color.RED) {
                    y.color = x.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    z = x.parent;
                } else {
                    if (z == x.left) {
                        // left rotate on x
                        rightRotate(x);
                        z = x;
                        x = z.parent;
                    }
                    x.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    private void leftRotate(RBNode<K, V> x) {
        RBNode<K, V> y = x.right;
        if (y.left != NIL) y.left.parent = x;
        x.right = y.left;
        y.parent = x.parent;
        if (x.parent == NIL) root = y;
        else if (x.parent.left == x) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(RBNode<K, V> x) {
        RBNode<K, V> y = x.left;
        if (y.right != NIL) y.right.parent = x;
        x.left = y.right;
        y.parent = x.parent;
        if (x.parent == NIL) root = y;
        else if (x.parent.left == x) x.parent.left = y;
        else x.parent.right = y;
        y.right = x;
        x.parent = y;
    }

    protected static class RBNode<K extends Comparable<? super K>, V> extends Node<K, V, RBNode<K, V>> {
        Color color;

        public RBNode(K key, V value) {
            super(key, value);
            this.color = Color.RED;
        }

        public RBNode() {
            super();
            this.color = Color.BLACK;
        }
    }

    protected enum Color {
        RED, BLACK;
    }

    @Override
    protected RBNode<K, V> createNode(K key, V value) {
        var node = new RBNode<>(key, value);
        node.left = node.right = node.parent = NIL;
        return node;
    }

    @Override
    protected RBNode<K, V> createNIL() {
        RBNode<K, V> node = new RBNode<>();
        node.left = node.right = node.parent = NIL;
        return node;
    }
}
