package io.github.youssefrashidy.treeshell.tokens;

public record CommaToken() implements Token {
	@Override
	public String toString() {
		return "SYMBOL(',')";
	}
}

