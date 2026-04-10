package io.github.youssefrashidy.treeshell;

import io.github.youssefrashidy.treeshell.tokens.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tokenizer {

    Map<Character, Token> singleCharTokens = Map.of(
            '.', new DotToken(),
            '(', new OpenParToken(),
            ')', new CloseParToken(),
            ',', new CommaToken(),
            '=', new EqualsToken()
    );

    List<Token> tokenize(String context) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        boolean inString = false;
        for (char c : context.toCharArray()) {
            if (c == '"') inString = !inString;
            if (!inString) {
                if (!isDelimiterToken(c)) {
                    token.append(c);
                } else {
                    if (!token.isEmpty()) {
                        tokens.add(createToken(token.toString()));
                        token.setLength(0);
                    }
                    if (c != ' ' && c != '\t') tokens.add(singleCharTokens.get(c));
                }
            } else token.append(c);
        }
        // Flush trailing token when input does not end with a delimiter.
        if (!token.isEmpty()) {
            tokens.add(createToken(token.toString()));
        }
        return tokens;
    }

    boolean isDelimiterToken(char c) {
        return c == '.' ||
                c == '(' ||
                c == ')' ||
                c == ',' ||
                c == '=' ||
                c == ' ' ||
                c == '\t';
    }

    private Token createToken(String token) {
        return switch (token) {
            case "RB" -> new TypeToken(TreeType.RB);
            case "BST" -> new TypeToken(TreeType.BST);
            case "create" -> new CreateToken();
            case "insert" -> new CommandToken(CommandType.INSERT);
            case "delete" -> new CommandToken(CommandType.DELETE);
            case "contains" -> new CommandToken(CommandType.CONTAINS);
            case "print" -> new CommandToken(CommandType.PRINT);
            case "inorder" -> new CommandToken(CommandType.INORDER);
            case "height" -> new CommandToken(CommandType.HEIGHT);
            case "size" -> new CommandToken(CommandType.SIZE);
            case "clear" -> new CommandToken(CommandType.CLEAR);
            case "insertAll" -> new CommandToken(CommandType.INSERT_ALL);
            case "new" -> new NewToken() ;
            default -> {
                try {
                    Integer.parseInt(token);
                    yield new NumberToken(Integer.parseInt(token));
                } catch (RuntimeException _) {

                }
                if (token.startsWith("\"") && token.endsWith("\"")) {
                    yield new StringToken(token.substring(1, token.length() - 1));
                }
                yield new IdentifierToken(token);
            }
        };
    }
}
