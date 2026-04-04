package io.github.youssefrashidy;

public class RBBST<K extends Comparable<? super K>, V> extends AbstractBST<K, V, RBBST.RBNode<K, V>> {


    @Override
    public boolean insert(K key, V val) {
        RBNode<K, V> z = insertRaw(key, val);
        if (z == NIL) return false;
        // add fixup
        fixupRB(z);
        return true;
    }

    private void fixupRB(RBNode<K, V> z) {
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

    @Override
    public boolean delete(K key) {
        var z = containsNode(key);
        if (z == NIL) return false;

        RBNode<K, V> y = z;
        RBNode<K, V> x;

        Color originalColor = y.color;
        if (z.left == NIL) {
            // x points to the node that takes y position
            // which is y/z right child since both represent
            // the same node in this case
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            // y here represent the node that moves up the tree
            // hence x is its right child in either case
            y = getMin(z.right);
            originalColor = y.color;
            if (y.parent == z) {
                x = y.right;
                x.parent = y;
            } else {
                x = y.right;
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (originalColor == Color.BLACK) fixupDelete(x);
        return true;
    }

    void fixupDelete(RBNode<K, V> x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                var w = x.parent.right;
                // case 1
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                // case 2
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                }
                else {
                    // case 3
                    if (w.right.color == Color.BLACK) {
                        w.left.color = Color.BLACK;
                        w.color = Color.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    // case 4
                    w.color = x.parent.color ;
                    x.parent.color = Color.BLACK ;
                    w.right.color = Color.BLACK ;
                    leftRotate(x.parent);
                    x = root ;
                }
            }
            else {
                var w = x.parent.left;
                // case 1
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                // case 2
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                }
                else {
                    // case 3
                    if (w.left.color == Color.BLACK) {
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    // case 4
                    w.color = x.parent.color ;
                    x.parent.color = Color.BLACK ;
                    w.left.color = Color.BLACK ;
                    rightRotate(x.parent);
                    x = root ;
                }
            }
        }
        x.color = Color.BLACK ;
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
        return new RBNode<>();
    }
}
