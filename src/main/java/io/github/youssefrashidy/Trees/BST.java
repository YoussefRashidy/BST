package io.github.youssefrashidy.Trees;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class BST<K extends Comparable<? super K>, V> extends AbstractBST<K, V, BST.BSTNode<K, V>> {

    private static final Logger logger = LoggerFactory.getLogger(BST.class);
    private static final boolean VALIDATE = false;

    protected static class BSTNode<K extends Comparable<? super K>, V> extends Node<K, V, BSTNode<K, V>> {
        public BSTNode(K key, V val) {
            super(key, val);
        }

        public BSTNode() {
        }
    }

    @Override
    protected BSTNode<K, V> createNode(K key, V value) {
        return new BSTNode<>(key, value);
    }

    @Override
    protected BSTNode<K, V> createNIL() {
        return new BSTNode<>();
    }

    @Override
    public boolean insert(K key, V val) {
        boolean result = super.insert(key, val);
        if (result) {
            logger.debug("Inserted key {} with value {}", key, val);
        } else {
            logger.warn("Insertion failed for key {}: duplicate key", key);
        }
        if (VALIDATE) {
            new Validator<K, V>().validate(this);
        }
        return result;
    }

    @Override
    public boolean delete(K key) {
        boolean result = super.delete(key);
        if (result) {
            logger.debug("Deleted key {}", key);
            new Validator<K, V>().validate(this);
        } else {
            logger.warn("Deletion failed for key {}: key not found", key);
        }
        if (VALIDATE) {
            new Validator<K, V>().validate(this);
        }
        return result;
    }

    private static class Validator<K extends Comparable<? super K>, V> {
        public boolean validate(BST<K, V> tree) {
            List<Map.Entry<K, V>> traversal = tree.inOrder();
            for (int i = 1; i < traversal.size(); i++) {
                if (traversal.get(i - 1).getKey().compareTo(traversal.get(i).getKey()) > 0) {
                    logger.error("BST violation: in-order traversal is not sorted at index {} with keys {} and {}", i, traversal.get(i - 1).getKey(), traversal.get(i).getKey());
                    throw new BST.BSTViolationException("BST violation: in-order traversal is not sorted at index " + i);
                }
            }
            logger.debug("Validation passed");
            return true;
        }
    }

    private static class BSTViolationException extends RuntimeException {
        public BSTViolationException(String message) {
            super(message);
        }
    }

}
