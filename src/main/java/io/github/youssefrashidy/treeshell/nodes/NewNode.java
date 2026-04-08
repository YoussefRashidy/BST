package io.github.youssefrashidy.treeshell.nodes;

import io.github.youssefrashidy.Trees.BST;
import io.github.youssefrashidy.Trees.RBBST;
import io.github.youssefrashidy.treeshell.TreeShell;
import io.github.youssefrashidy.treeshell.tokens.IdentifierToken;
import io.github.youssefrashidy.treeshell.tokens.TypeToken;

public record NewNode(TypeToken type, IdentifierToken identifier) implements Node {
    public void execute() {
        if (TreeShell.environmentVars.containsKey(identifier.identifier())) {
            System.out.println("Tree already exists: " + identifier.identifier());
            return;
        }

        switch (type.value()) {
            case BST -> {
                BST<Integer, Object> bst = new BST<>();
                TreeShell.environmentVars.put(identifier.identifier(), bst);
            }
            case RB -> {
                RBBST<Integer, Object> rbTree = new RBBST<>();
                TreeShell.environmentVars.put(identifier.identifier(), rbTree);
            }
        }
        System.out.println("Created " + type.value() + " tree: " + identifier.identifier());
    }
}

