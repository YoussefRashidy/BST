package io.github.youssefrashidy.treeshell.nodes;

import io.github.youssefrashidy.Trees.AbstractBST;
import io.github.youssefrashidy.treeshell.TreeShell;
import io.github.youssefrashidy.treeshell.tokens.*;

public record CommandNode(IdentifierToken identifier, CommandToken commandToken,
                          java.util.List<Token> args) implements Node {
    public void execute() {
        switch (commandToken.type()) {
            case INSERT -> {
                AbstractBST<Integer, Object, ?> tree = TreeShell.environmentVars.get(identifier.identifier());
                if (tree == null) {
                    System.out.println("Tree not found: " + identifier.identifier());
                } else {

                    boolean insert ;
                    switch (args.get(1)) {
                        case NumberToken num -> insert = tree.insert(((NumberToken) args.get(0)).value(),num.value());
                        case StringToken stringToken -> insert = tree.insert(((NumberToken) args.get(0)).value(),stringToken.value());
                        default -> throw new IllegalArgumentException("Unsupported argument type: " + args.get(1).getClass().getSimpleName());
                    }
                    if (insert)
                        System.out.println("Inserted " + args.get(0) + " into tree " + identifier.identifier());
                    else
                        System.out.println("Value " + args.get(0) + " already exists in tree " + identifier.identifier());
                }
            }
            case DELETE -> {
                AbstractBST<Integer, Object, ?> tree = TreeShell.environmentVars.get(identifier.identifier());
                if (tree == null) {
                    System.out.println("Tree not found: " + identifier.identifier());
                } else {
                    boolean delete = tree.delete(((NumberToken) args.get(0)).value());
                    if (delete)
                        System.out.println("Deleted " + args.get(0) + " from tree " + identifier.identifier());
                    else
                        System.out.println("Value " + args.get(0) + " not found in tree " + identifier.identifier());
                }

            }
            case INORDER -> {
                AbstractBST<Integer, Object, ?> tree = TreeShell.environmentVars.get(identifier.identifier());
                if (tree == null) {
                    System.out.println("Tree not found: " + identifier.identifier());
                } else {
                    System.out.println("Inorder traversal of tree " + identifier.identifier() + ":");
                    var inorder = tree.inOrder();
                    System.out.println(inorder);
                }
            }
            case PRINT -> {
                AbstractBST<Integer, Object, ?> tree = TreeShell.environmentVars.get(identifier.identifier());
                if (tree == null) {
                    System.out.println("Tree not found: " + identifier.identifier());
                } else {
                    System.out.println("Tree " + identifier.identifier() + ":");
                    tree.print();
                }
            }
            case CONTAINS -> {
                AbstractBST<Integer, Object, ?> tree = TreeShell.environmentVars.get(identifier.identifier());
                if (tree == null) {
                    System.out.println("Tree not found: " + identifier.identifier());
                } else {
                    boolean contains = tree.contains(((NumberToken) args.get(0)).value());
                    if (contains)
                        System.out.println("Tree " + identifier.identifier() + " contains " + args.get(0));
                    else
                        System.out.println("Tree " + identifier.identifier() + " does not contain " + args.get(0));
                }
            }
            case HEIGHT -> {
                AbstractBST<Integer, Object, ?> tree = TreeShell.environmentVars.get(identifier.identifier());
                if (tree == null) {
                    System.out.println("Tree not found: " + identifier.identifier());
                } else {
                    int height = tree.getHeight();
                    System.out.println("Height of tree " + identifier.identifier() + ": " + height);
                }
            }
            case SIZE -> {
                AbstractBST<Integer, Object, ?> tree = TreeShell.environmentVars.get(identifier.identifier());
                if (tree == null) {
                    System.out.println("Tree not found: " + identifier.identifier());
                } else {
                    int size = tree.getSize();
                    System.out.println("Size of tree " + identifier.identifier() + ": " + size);
                }
            }
            case CLEAR -> {
                AbstractBST<Integer, Object, ?> tree = TreeShell.environmentVars.get(identifier.identifier());
                if (tree == null) {
                    System.out.println("Tree not found: " + identifier.identifier());
                } else {
                    tree.clear();
                    System.out.println("Cleared tree " + identifier.identifier());
                }
            }
        }
    }
}

