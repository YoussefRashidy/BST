package io.github.youssefrashidy.treeshell.tokens;

public record OpenParToken() implements Token {
	@Override
	public String toString() {
		return "SYMBOL('(')";
	}
}

