package io.github.youssefrashidy.treeshell;

import io.github.youssefrashidy.treeshell.nodes.CommandNode;
import io.github.youssefrashidy.treeshell.nodes.NewNode;
import io.github.youssefrashidy.treeshell.nodes.Node;
import io.github.youssefrashidy.treeshell.tokens.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    int position = 0;
    List<Node> nodes;

    List<Node> parse(List<Token> tokens) {
        nodes = new ArrayList<>();
        switch (tokens.get(position)) {
            case TypeToken typeToken -> parseCreateTree(tokens);
            case IdentifierToken identifierToken -> parseCommand(tokens);
            default -> throw new IllegalArgumentException("Unexpected token %s".formatted(tokens.get(position)));
        }
        ;
        // reset position and list of nodes for the next parse call
        position = 0;
        return nodes;
    }

    private void parseCreateTree(List<Token> tokens) {
        TypeToken type = (TypeToken) consume(TypeToken.class, tokens.get(position), "Expected tree type");
        IdentifierToken identifierToken = (IdentifierToken) consume(IdentifierToken.class, tokens.get(position), "Expected tree name");
        EqualsToken equalsToken = (EqualsToken) consume(EqualsToken.class, tokens.get(position), "Expected '='");
        NewToken newToken = (NewToken) consume(NewToken.class, tokens.get(position), "Expected 'new'");
        TypeToken createToken = (TypeToken) consume(TypeToken.class, tokens.get(position), "Expected 'RB' or 'BST'");
        OpenParToken openParToken = (OpenParToken) consume(OpenParToken.class, tokens.get(position), "Expected '('");
        CloseParToken closeParToken = (CloseParToken) consume(CloseParToken.class, tokens.get(position), "Expected ')'");
        // Validate that there are no extra tokens
        if (position != tokens.size()) {
            throw new IllegalArgumentException("Unexpected token %s at position %d".formatted(tokens.get(position), position));
        }
        // Valid create tree statement, create the node and add it to the list of nodes
        // Check whether the type matches the type in the new statement
        if (type.value() != createToken.value()) {
            throw new IllegalArgumentException("Tree type mismatch: expected %s but got %s at position %d".formatted(type.value(), createToken.value(), position));
        }
        NewNode node = new NewNode(type, identifierToken);
        nodes.add(node);
    }

    private void parseCommand(List<Token> tokens) {
        IdentifierToken identifierToken = (IdentifierToken) consume(IdentifierToken.class, tokens.get(position), "Expected tree name");
        DotToken dotToken = (DotToken) consume(DotToken.class, tokens.get(position), "Expected '.'");
        CommandToken commandToken = (CommandToken) consume(CommandToken.class, tokens.get(position), "Expected command");
        OpenParToken openParToken = (OpenParToken) consume(OpenParToken.class, tokens.get(position), "Expected '('");
        // Parse the arguments of the command, which can be either a number or a string, and can be separated by commas
        List<Token> args = new ArrayList<>();
        int argsCount = 0; // at most 2 arguments for the commands we have, so we can use this to limit the number of arguments
        while (position < tokens.size() && !(tokens.get(position) instanceof CloseParToken)) {
            if (tokens.get(position) instanceof NumberToken || tokens.get(position) instanceof StringToken) {
                args.add(tokens.get(position));
                position++;
                argsCount++;
            } else if (tokens.get(position) instanceof CommaToken) {
                position++;
            } else {
                throw new IllegalArgumentException("Unexpected token %s at position %d".formatted(tokens.get(position), position));
            }
        }
        if (argsCount > 2) {
            throw new IllegalArgumentException("Too many arguments for command %s at position %d".formatted(commandToken.type(), position));
        }
        // First validate that the first arg is a number since it is required for all commands except print and inorder
        if (argsCount > 0 && !(args.getFirst() instanceof NumberToken))
            throw new IllegalArgumentException("Expected first argument to be a number for command %s at position %d".formatted(commandToken.type(), position));
        else if (argsCount > 0 && (commandToken.type() == CommandType.PRINT || commandToken.type() == CommandType.INORDER))
            throw new IllegalArgumentException("Command %s does not take any arguments at position %d".formatted(commandToken.type(), position));
        CloseParToken closeParToken = (CloseParToken) consume(CloseParToken.class, tokens.get(position), "Expected ')'");
        // Validate that there are no extra tokens
        if (position != tokens.size()) {
            throw new IllegalArgumentException("Unexpected token %s at position %d".formatted(tokens.get(position), position));
        }
        CommandNode node = new CommandNode(identifierToken, commandToken, args);
        nodes.add(node);
    }

    private Token consume(Class<? extends Token> cla, Token token, String message) {
        if (cla.isInstance(token)) {
            position++;
            return token;
        }
        throw new RuntimeException(message + " at position " + position);
    }

}
