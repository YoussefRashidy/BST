package io.github.youssefrashidy.treeshell;

import java.util.Scanner;

public class TreeShellMain {
    public static void main(String[] args) {
        TreeShell shell = new TreeShell();
        Scanner scanner = new Scanner(System.in);

        System.out.println("TreeShell test runner");
        System.out.println("Type 'exit' to quit.");
        System.out.println("Examples:");
        System.out.println("  BST t = new BST()");
        System.out.println("  t.insert(10, \"v10\")");
        System.out.println("  t.contains(10)");
        System.out.println("  t.inorder()");

        while (true) {
            System.out.print("treeshell> ");
            if (!scanner.hasNextLine()) {
                break;
            }

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                shell.shell(line);
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        System.out.println("Bye.");
    }
}

