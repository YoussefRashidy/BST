package io.github.youssefrashidy.Trees;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RBBST<K extends Comparable<? super K>, V> extends AbstractBST<K, V, RBBST.RBNode<K, V>> {
    private static final boolean VALIDATE = false;
    private static final Logger logger = LoggerFactory.getLogger(RBBST.class) ;
    private static final String RESET   = "\u001B[0m";
    private static final String RED_FG  = "\u001B[91m";
    private static final String GRAY_FG = "\u001B[90m";
    private static final String DIM     = "\u001B[2m";
    private static final String BOLD    = "\u001B[1m";

    @Override
    public boolean insert(K key, V val) {
        logger.info("Insert key {}",key);
        RBNode<K, V> z = insertRaw(key, val);
        if (z == NIL) {
            logger.warn("Duplicate Key {} ignored" , key);
            return false;
        }
        // add fixup
        fixupRB(z);
        if (VALIDATE) new Validator<K,V>().validate(this);
        logger.info("Insertion Completed tree size is {}",size);
        return true;
    }

    private void fixupRB(RBNode<K, V> z) {
        while (z.parent.color == Color.RED) {
            var x = z.parent;
            if (x.parent.left == x) {
                var y = x.parent.right;
                if (y.color == Color.RED) {
                    logger.debug("Insert fixup: Case 1 - recolor parent={} uncle={} grandparent={}", x.key, y.key, x.parent.key);
                    y.color = x.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    z = x.parent;
                } else {
                    if (z == x.right) {
                        logger.debug("Insert fixup: Case 2 - left rotate on parent={}", x.key);
                        leftRotate(x);
                        z = x;
                        x = z.parent;
                    }
                    logger.debug("Insert fixup: Case 3 - right rotate on grandparent={} recolor", x.parent.key);
                    x.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rightRotate(x.parent);
                }
            } else {
                var y = x.parent.left;
                if (y.color == Color.RED) {
                    logger.debug("Insert fixup: Case 1 [mirror] - recolor parent={} uncle={} grandparent={}", x.key, y.key, x.parent.key);
                    y.color = x.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    z = x.parent;
                } else {
                    if (z == x.left) {
                        logger.debug("Insert fixup: Case 2 [mirror] - right rotate on parent={}", x.key);
                        rightRotate(x);
                        z = x;
                        x = z.parent;
                    }
                    logger.debug("Insert fixup: Case 3 [mirror] - left rotate on grandparent={} recolor", x.parent.key);
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
        logger.info("Delete key={}", key);
        var z = containsNode(key);
        if (z == NIL) {
            logger.warn("Delete key={} not found", key);
            return false;
        }

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
        if (VALIDATE) new Validator<K,V>().validate(this);
        logger.info("Delete completed, tree size={}", size);
        size--;
        return true;
    }

    void fixupDelete(RBNode<K, V> x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                var w = x.parent.right;
                if (w.color == Color.RED) {
                    logger.debug("Delete fixup: Case 1 - recolor sibling={} left rotate parent={}", w.key, x.parent.key);
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    logger.debug("Delete fixup: Case 2 - push black up, recolor sibling={} move up from={}", w.key, x.key);
                    w.color = Color.RED;
                    x = x.parent;
                } else {
                    if (w.right.color == Color.BLACK) {
                        logger.debug("Delete fixup: Case 3 - recolor sibling={} right rotate", w.key);
                        w.left.color = Color.BLACK;
                        w.color = Color.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    logger.debug("Delete fixup: Case 4 - recolor sibling={} left rotate parent={}", w.key, x.parent.key);
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                var w = x.parent.left;
                if (w.color == Color.RED) {
                    logger.debug("Delete fixup: Case 1 [mirror] - recolor sibling={} right rotate parent={}", w.key, x.parent.key);
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    logger.debug("Delete fixup: Case 2 [mirror] - push black up, recolor sibling={} move up from={}", w.key, x.key);
                    w.color = Color.RED;
                    x = x.parent;
                } else {
                    if (w.left.color == Color.BLACK) {
                        logger.debug("Delete fixup: Case 3 [mirror] - recolor sibling={} left rotate", w.key);
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    logger.debug("Delete fixup: Case 4 [mirror] - recolor sibling={} right rotate parent={}", w.key, x.parent.key);
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    private void leftRotate(RBNode<K, V> x) {
        logger.debug("Left rotate: pivot={}", x.key);
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
        logger.debug("Right rotate: pivot={}", x.key);
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

//    public void print(String prefix, RBNode<K, V> node) {
//        printNode(prefix, node, true, false);
//    }

    @Override
    protected void printNode(String prefix, RBNode<K, V> node, boolean isLast, boolean isRoot) {
        boolean hasLeft  = node.left  != NIL;
        boolean hasRight = node.right != NIL;

        String dot     = node.color == Color.RED ? "●" : "○";
        String colorFg = node.color == Color.RED ? RED_FG : GRAY_FG;
        String label   = colorFg + dot + " " + BOLD + node.key + RESET
                + colorFg + " → " + node.val + RESET;

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

    private int computeBlackHeight() {
        int h = 0;
        RBNode<K, V> x = root;
        while (x != NIL) {
            if (x.color == Color.BLACK) h++;
            x = x.left;
        }
        return h;
    }

    private static class Validator<K extends Comparable<? super K>, V> {
        /*
         * Property 1 (nodes are either red or black) is tautology  because of enum color
         * There is no need to check property 3 (NIL nodes are black) but it is being checked anyway
         * */

        public void validate(RBBST<K, V> tree) {
            if (!isRootBlack(tree)) {
                logger.error("RB violation: root is not black");
                throw new RBViolationException("Root is not black");
            }
            if (!isBlackHeightEqual(tree)) {
                logger.error("RB violation: black-height inconsistency detected");
                throw new RBViolationException("Black-height inconsistency detected");
            }
            if (!isNoConsecutiveReds(tree)) {
                logger.error("RB violation: consecutive red nodes detected");
                throw new RBViolationException("Red-red violation detected");
            }
            logger.debug("Validation passed");
        }

        public boolean isRootBlack(RBBST<K, V> tree) {
            return tree.root.color == Color.BLACK;
        }

        public boolean isLeavesBlack(RBBST<K, V> tree) {
            var x = tree.root;
            return isLeavesBlack(x, tree);
        }

        private boolean isLeavesBlack(RBNode<K, V> node, RBBST<K, V> tree) {
            if (node == tree.NIL && node.color == Color.BLACK) return true;
            return isLeavesBlack(node.left, tree) && isLeavesBlack(node.right, tree);
        }

        public boolean isBlackHeightEqual(RBBST<K, V> tree) {
            return getBlackHeight(tree.root, tree) != -1;
        }


        private int getBlackHeight(RBNode<K, V> node, RBBST<K, V> tree) {
            if (node == tree.NIL) return 0;
            int height = node.color == Color.BLACK ? 1 : 0;
            int leftHeight = getBlackHeight(node.left, tree);
            int rightHeight = getBlackHeight(node.right, tree);
            if (leftHeight != rightHeight) return -1;
            else if (leftHeight == -1) return -1;
            else return leftHeight + height;
        }

        public boolean isNoConsecutiveReds(RBBST<K, V> tree) {
            return isNoConsecutiveReds(tree.root, tree);
        }

        private boolean isNoConsecutiveReds(RBNode<K, V> node, RBBST<K, V> tree) {
            if (node == tree.NIL) return true;
            else if (node.color == Color.RED) {
                if (node.left.color == Color.RED || node.right.color == Color.RED) return false;
            }
            return isNoConsecutiveReds(node.left, tree) && isNoConsecutiveReds(node.right, tree);
        }

    }

    private static class RBViolationException extends RuntimeException {
        public RBViolationException(String message) {
            super(message);
        }
    }
}
