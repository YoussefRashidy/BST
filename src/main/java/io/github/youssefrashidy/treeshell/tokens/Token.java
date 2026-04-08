package io.github.youssefrashidy.treeshell.tokens;

public sealed interface Token permits CloseParToken, CommaToken, CommandToken, CreateToken, DotToken, EqualsToken, IdentifierToken, NewToken, NumberToken, OpenParToken, StringToken, TypeToken {
}
