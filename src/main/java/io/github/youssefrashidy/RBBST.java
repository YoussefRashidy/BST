package io.github.youssefrashidy;

public class RBBST<K extends Comparable<? super K>, V> extends AbstractBST<K, V,RBBST<K,V>.RBNode> {

    @Override
    protected RBNode createNode(K key, V value) {

        return new RBNode(key,value);
    }

    @Override
    protected RBNode createNIL() {
        return new RBNode();
    }

    protected class RBNode extends Node {
        Color color ;

        public RBNode(K key, V value) {
            super(key,value);
            this.color = Color.RED ;
        }
        public RBNode() {
            super();
            this.color = Color.RED ;
        }
    }

    protected enum Color {
        RED , BLACK ;
    }
}
