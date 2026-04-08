package io.github.youssefrashidy.treeshell.tokens;

public record NumberToken(int value) implements Token {
	@Override
	public String toString() {
		return "NUMBER(" + value + ")";
	}
}

