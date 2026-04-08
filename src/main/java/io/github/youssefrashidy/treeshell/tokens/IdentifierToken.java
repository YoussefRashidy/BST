package io.github.youssefrashidy.treeshell.tokens;

public record IdentifierToken(String identifier) implements Token {
	@Override
	public String toString() {
		return "IDENTIFIER(" + identifier + ")";
	}
}

