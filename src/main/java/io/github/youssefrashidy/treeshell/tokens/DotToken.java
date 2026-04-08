package io.github.youssefrashidy.treeshell.tokens;

public record DotToken() implements Token {
	@Override
	public String toString() {
		return "SYMBOL('.')";
	}
}

