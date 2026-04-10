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
        position = 0;
        switch (tokens.get(position)) {
            case TypeToken typeToken -> parseCreateTree(tokens);
            case IdentifierToken identifierToken -> parseCommand(tokens);
            default -> {
                //reset position to 0 for the error message to be accurate
                throw new IllegalArgumentException("Unexpected token %s".formatted(tokens.get(position)));
            }
        }
        return nodes;
    }

    private void parseCreateTree(List<Token> tokens) {
        TypeToken type = (TypeToken) consume(TypeToken.class, tokens, "Expected tree type");
        IdentifierToken identifierToken = (IdentifierToken) consume(IdentifierToken.class, tokens, "Expected tree name");
        EqualsToken equalsToken = (EqualsToken) consume(EqualsToken.class, tokens, "Expected '='");
        NewToken newToken = (NewToken) consume(NewToken.class, tokens, "Expected 'new'");
        TypeToken createToken = (TypeToken) consume(TypeToken.class, tokens, "Expected 'RB' or 'BST'");
        OpenParToken openParToken = (OpenParToken) consume(OpenParToken.class, tokens, "Expected '('");
        CloseParToken closeParToken = (CloseParToken) consume(CloseParToken.class, tokens, "Expected ')'");
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
        IdentifierToken identifierToken = (IdentifierToken) consume(IdentifierToken.class, tokens, "Expected tree name");
        DotToken dotToken = (DotToken) consume(DotToken.class, tokens, "Expected '.'");
        CommandToken commandToken = (CommandToken) consume(CommandToken.class, tokens, "Expected command");
        OpenParToken openParToken = (OpenParToken) consume(OpenParToken.class, tokens, "Expected '('");
        // Parse the arguments of the command, which can be either a number or a string, and can be separated by commas
        List<Token> args = new ArrayList<>();
        int argsCount = 0;
        while (position < tokens.size() && !(tokens.get(position) instanceof CloseParToken)) {
            if (tokens.get(position) instanceof NumberToken || tokens.get(position) instanceof StringToken) {
                args.add(consume(Token.class, tokens, "Expected argument"));
                argsCount++;
            } else if (tokens.get(position) instanceof CommaToken) {
                consume(CommaToken.class, tokens, "Expected ','");
            } else {
                throw new IllegalArgumentException("Unexpected token %s at position %d".formatted(tokens.get(position), position));
            }
        }

        if (!matchArgs(argsCount, commandToken)) {
            throw new IllegalArgumentException("Invalid number of arguments for command %s at position %d".formatted(commandToken.type(), position));
        }
        // First validate that the first arg is a number since it is required for commands that take a key.
        if (argsCount > 0 && !(args.getFirst() instanceof NumberToken) && commandToken.type() != CommandType.INSERT_ALL)
            throw new IllegalArgumentException("Expected first argument to be a number for command %s at position %d".formatted(commandToken.type(), position));
        else if (commandToken.type() == CommandType.INSERT_ALL) {
            // Check even indices
            for (int i = 0; i < args.size(); i += 2) {
                if (!(args.get(i) instanceof NumberToken)) {
                    throw new IllegalArgumentException("Expected argument at index %d to be a number for command %s at position %d".formatted(i, commandToken.type(), position));
                }
            }
        }
        CloseParToken closeParToken = (CloseParToken) consume(CloseParToken.class, tokens, "Expected ')'");
//        if (argsCount > 0 && (commandToken.type() == CommandType.PRINT || commandToken.type() == CommandType.INORDER))
//            throw new IllegalArgumentException("Command %s does not take any arguments at position %d".formatted(commandToken.type(), position));
//        if (argsCount == 1 && !(commandToken.type() == CommandType.DELETE || commandToken.type() == CommandType.CONTAINS))
//            throw new IllegalArgumentException("Command %s takes exactly 1 arguments at position %d".formatted(commandToken.type(), position));
//        if (argsCount == 2 && commandToken.type() != CommandType.INSERT)
//            throw new IllegalArgumentException("Command %s takes exactly 2 arguments at position %d".formatted(commandToken.type(), position));
        // Validate that there are no extra tokens
        if (position != tokens.size()) {
            throw new IllegalArgumentException("Unexpected token %s at position %d".formatted(tokens.get(position), position));
        }
        CommandNode node = new CommandNode(identifierToken, commandToken, args);
        nodes.add(node);
    }

    private Token consume(Class<? extends Token> cla, List<Token> tokens, String message) {
        if (position >= tokens.size()) {
            throw new IllegalArgumentException("Unexpected end of input, expected " + message);
        }
        if (cla.isInstance(tokens.get(position))) {
            return tokens.get(position++);
        }
        throw new IllegalArgumentException(message + " at position " + position);
    }

    private boolean matchArgs(int argc, CommandToken token) {
        return switch (token.type()) {
            case PRINT, INORDER, HEIGHT, SIZE, CLEAR -> argc == 0;
            case DELETE, CONTAINS -> argc == 1;
            case INSERT -> argc == 2;
            case INSERT_ALL -> argc % 2 == 0; // insert all can take any even number of arguments since it takes pairs of key and value
        };
    }

}
