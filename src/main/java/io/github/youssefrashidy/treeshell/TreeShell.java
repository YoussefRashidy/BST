package io.github.youssefrashidy.treeshell;

import io.github.youssefrashidy.Trees.AbstractBST;
import io.github.youssefrashidy.treeshell.nodes.CommandNode;
import io.github.youssefrashidy.treeshell.nodes.NewNode;

import java.util.HashMap;
import java.util.Scanner;

public class TreeShell {
    public static HashMap<String, AbstractBST<Integer, Object, ?>> environmentVars = new HashMap<>();
    private final Tokenizer tokenizer = new Tokenizer();
    private final Parser parser = new Parser();

    public void shell(String command) {
        var tokens = tokenizer.tokenize(command);
        var nodes = parser.parse(tokens);
        for (var node : nodes) {
            switch (node) {
                case NewNode newNode -> newNode.execute();
                case CommandNode commandNode -> commandNode.execute();
                default -> throw new IllegalArgumentException("Unsupported node: " + node.getClass().getSimpleName());
            }
        }
    }

    public void shell() {
        printWelcome();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("treeshell> ");
            String command = scanner.nextLine().trim();
            if (command.isEmpty()) {
                continue;
            }
            if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")) {
                break;
            }
            var tokens = tokenizer.tokenize(command);
            var nodes = parser.parse(tokens);
            for (var node : nodes) {
                switch (node) {
                    case NewNode newNode -> newNode.execute();
                    case CommandNode commandNode -> commandNode.execute();
                    default ->
                            throw new IllegalArgumentException("Unsupported node: " + node.getClass().getSimpleName());
                }
            }
        }
    }

    private void printWelcome() {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║         🌳 TreeShell - BST & RedBlack Tree 🌳      ║");
        System.out.println("║                   Command Console                  ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Available Commands:");
        System.out.println("  • Create a tree:     BST t = new BST()");
        System.out.println("  • Create RB tree:    RB t = new RB()");
        System.out.println("  • Insert value:      t.insert(key, value)");
        System.out.println("  • Delete value:      t.delete(key)");
        System.out.println("  • Check contains:    t.contains(key)");
        System.out.println("  • Print tree:        t.print()");
        System.out.println("  • In-order traversal: t.inorder()");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  treeshell> BST mytree = new BST()");
        System.out.println("  treeshell> mytree.insert(10, \"value\")");
        System.out.println("  treeshell> mytree.contains(10)");
        System.out.println("  treeshell> mytree.inorder()");
        System.out.println();
        System.out.println("Type 'exit' or 'quit' to leave.");
        System.out.println("─".repeat(52));
        System.out.println();
    }
}
