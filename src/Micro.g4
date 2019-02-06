
grammar Micro;
KEYWORD:('PROGRAM'|'BEGIN'|'END'|'FUNCTION'|'READ'|'WRITE'|'IF'|'ELSE'|'ENDIF'|'WHILE'|'ENDWHILE'|'CONTINUE'|'BREAK'|'RETURN'|'INT'|'VOID'|'STRING'|'FLOAT');

//INTLITERALS
INTLITERAL: [0-9]+;

//FLOATLITERALS
FLOATLITERAL:[0-9]*'.'[0-9]+;

//STRINGLITERALS
STRINGLITERAL:'"'(.)*?'"';

//COMMENTS
COMMENT:'--'(.)*?'\n' ->skip;

//KEYWORDS

//OPERATORS
OPERATOR:(':='|'+'|'-'|'*'|'/'|'='|'!='|'<'|'>'|'('|')'|';'|','|'<='|'>=');

//IDENTIFIERS
IDENTIFIER: [a-zA-Z]+[A-Za-z0-9]*;

//WHITESPACE------------------------------------------------
WS:[ \t\r\n]+ ->skip;

program: (KEYWORD|INTLITERAL|FLOATLITERAL|STRINGLITERAL|COMMENT|OPERATOR|IDENTIFIER)+;