package io.github.youssefrashidy.treeshell.tokens;

public record CreateToken() implements Token {
	@Override
	public String toString() {
		return "KEYWORD(create)";
	}
}
