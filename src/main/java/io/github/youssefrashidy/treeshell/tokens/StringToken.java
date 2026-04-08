package io.github.youssefrashidy.treeshell.tokens;

public record StringToken(String value) implements Token {
	@Override
	public String toString() {
		return "STRING(\"" + value + "\")";
	}
}

