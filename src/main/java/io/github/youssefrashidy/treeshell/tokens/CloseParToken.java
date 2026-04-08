package io.github.youssefrashidy.treeshell.tokens;

public record CloseParToken() implements Token {
	@Override
	public String toString() {
		return "SYMBOL(')')";
	}
}

