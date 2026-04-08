package io.github.youssefrashidy.treeshell.tokens;

public record TypeToken(TreeType value) implements Token {
	@Override
	public String toString() {
		return "TYPE(" + value + ")";
	}
}

