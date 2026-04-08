package io.github.youssefrashidy.treeshell.tokens;

public record NewToken() implements Token {
	@Override
	public String toString() {
		return "KEYWORD(new)";
	}
}

