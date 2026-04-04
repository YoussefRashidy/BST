package io.github.youssefrashidy;

public class BST <K extends Comparable<? super K>,V> extends AbstractBST<K ,V, BST.BSTNode<K,V>>{

    protected static class BSTNode<K extends Comparable<? super K>,V> extends Node<K,V,BSTNode<K,V>> {
        public BSTNode(K key, V val) { super(key, val); }
        public BSTNode() {}
    }
    @Override
    protected BSTNode<K,V> createNode(K key, V value) {
        return new BSTNode<>(key, value);
    }

    @Override
    protected BSTNode<K,V> createNIL() {
        return new BSTNode<>();
    }
}
