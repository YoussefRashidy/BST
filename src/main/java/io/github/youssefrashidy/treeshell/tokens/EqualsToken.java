package io.github.youssefrashidy.treeshell.tokens;

public record EqualsToken() implements Token {
	@Override
	public String toString() {
		return "SYMBOL('=')";
	}
}

