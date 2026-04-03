package io.github.youssefrashidy;

public class BST <K extends Comparable<? super K>,V> extends AbstractBST<K ,V,BST<K,V>.BSTNode>{

    protected class BSTNode extends Node {
        public BSTNode(K key, V val) {
            super(key, val);
        }

        public BSTNode() {
        }
    }
    @Override
    protected BSTNode createNode(K key, V value) {
        return new BSTNode(key, value);
    }

    @Override
    protected BSTNode createNIL() {
        return new BSTNode();
    }
}
