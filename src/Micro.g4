grammar Micro;

//Program

program
	: program_kwd id '\n' begin pgm_body WS end
	;
	
program_kwd
	: 'PROGRAM' singlespace*
	;
	
id
	: IDENTIFIER singlespace*
	;
	
pgm_body
	: decl empty ('FUNCTION')* func_declarations
	;
	
decl
	: string_decl singlespace* decl
	| var_decl singlespace* decl
	| empty
	;

string_decl
	: string_kwd id assign_op STRINGLITERAL semicolon
	;
	
string_kwd
	: 'STRING' singlespace*
	;


//Variable declaration
	
var_decl
	:  var_type id_list singlespace* semicolon
	;
	
var_type
	: 'FLOAT' singlespace*
	| 'INT' singlespace*
	;

any_type
	: var_type singlespace*
	| 'VOID' singlespace*
	;

id_list
	: id singlespace* id_tail
	| singlespace*
	;
	
id_tail
	: comma singlespace* id id_tail
	| (empty | WS* empty | empty WS*)
	;
	
//Function parameter list

param_decl_list
	: param_decl param_decl_tail
	| empty
	;
	
param_decl
	: var_type id
	;
	
param_decl_tail
	: comma param_decl param_decl_tail
	| empty
	;
	
//Function declarations

func_declarations
	: func_decl func_declarations
	| empty
	;
	
func_decl
	: function singlespace* any_type id obr id_list cbr begin func_body end
	;
	
begin
	: 'BEGIN' (WS | empty)
	;
	
end
	: 'END' (WS | empty)
	;
	
function
	: 'FUNCTION' singlespace*
	;
	
func_body
	: decl stmt_list
	| empty
	;

//Statement List

stmt_list
	: stmt stmt_list
	| empty
	;
	
stmt
	: base_stmt
	| if_stmt
	| while_stmt
	;
	
base_stmt
	: assign_stmt
	| read_stmt
	| write_stmt
	| return_stmt
	;
	
//Basic Statements

assign_stmt
	: assign_expr semicolon empty
	;
	
assign_expr
	: id assign_op expr
	;
	
read_stmt
	: read singlespace* obr id_list cbr  semicolon empty
	;

singlespace
    : ' '
    ;

write_stmt
	: write obr id_list cbr semicolon empty
	;
	
return_stmt
	: return_kwd expr semicolon empty
	;
	
read
	: 'READ' singlespace*
	;

write
	: 'WRITE' singlespace*
	;

return_kwd
	: 'RETURN' singlespace*
	;
	
//Expressions

expr
	: expr_prefix singlespace* factor singlespace*
	;
	
expr_prefix
	: expr_prefix factor addop
	| empty
	;

factor
	: factor_prefix singlespace* postfix_expr
	;
	
factor_prefix
	: factor_prefix singlespace* postfix_expr singlespace* mulop
	| empty
	;
	
postfix_expr
	: primary singlespace*
	| call_expr singlespace*
	;
	
call_expr
	: id obr expr_list cbr
	;
	
expr_list
	: expr expr_list_tail
	| empty
	;
	
expr_list_tail
	: comma expr expr_list_tail
	| empty
	;
	
primary
	: obr expr cbr
	| id_list
	| INTLITERAL
	| FLOATLITERAL
	;
	
addop
	: '+'
	| '-'
	;
	
mulop
	: '*'
	| '/'
	;
	
//Complex statements

if_stmt
	: if_kwd obr cond cbr decl stmt_list else_part endif
	;
	
else_part
	: else_kwd decl stmt_list
	| empty
	;
	
cond
	: expr compop expr
	;
	
if_kwd
	: 'IF' singlespace*
	;
	
else_kwd
	: 'ELSE' singlespace*
	;

endif
	: 'ENDIF' singlespace*
	;
	
compop
	: singlespace* '<' singlespace*
	| singlespace* '>' singlespace*
	| singlespace* '=' singlespace*
	| singlespace* '!=' singlespace*
	| singlespace* '<=' singlespace*
	| singlespace* '>=' singlespace*
	;
	
assign_op
	: singlespace* ':=' singlespace*
	;
	
//While statements

while_stmt
	: while_kwd obr cond cbr decl stmt_list endwhile
	;
comma
	: ','
	;
	
while_kwd
	: 'WHILE' singlespace*
	;
	
endwhile
	: 'ENDWHILE' singlespace*
	;
	
continue_kwd
	: 'CONTINUE' singlespace*
	;
	
break_kwd
	: 'BREAK' singlespace*
	;

obr
	: '(' singlespace*
	;
	
cbr
	:  singlespace* ')'
	;
	
semicolon
	: ';' (WS | COMMENT)*
	;

empty
    : (COMMENT | WS)*
    |
    ;
	
//Literals

WS
    :   (' ' | '\t' | '\r'| '\n')* -> skip
    ;

KEYWORD
	: 'PROGRAM'
	| 'BEGIN'
	| 'END'
	| 'FUNCTION'
	| 'READ'
	| 'WRITE'
	| 'IF'
	| 'ELSE'
	| 'ENDIF'
	| 'WHILE'
	| 'ENDWHILE'
	| 'CONTINUE'
	| 'BREAK'
	| 'RETURN'
	| 'STRING'
	| 'VOID'
	| 'INT'
	| 'FLOAT'
	;
	
	

OPERATOR
	: ':='
	| '+'
	| '-'
	| '*'
	| '/'
	| '='
	| '!='
	| '<'
	| '>'
	| '('
	| ')'
	| ';'
	| ','
	| '<='
	| '>='
	;
	
IDENTIFIER
	:[a-zA-Z]+[a-zA-Z0-9]*
	;

INTLITERAL
	:[0-9]+
	;
	
FLOATLITERAL
	: (('0'..'9')+'.'('0'..'9')+)
	;
	
STRINGLITERAL
	: '"' ~('"')* '"'
	;
	
COMMENT
	: ('--' ~('\n')* '\n')
	;