package io.github.youssefrashidy.treeshell.tokens;

public record CommandToken(CommandType type) implements Token {
	@Override
	public String toString() {
		return "COMMAND(" + type + ")";
	}
}

