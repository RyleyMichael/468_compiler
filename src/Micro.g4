grammar Micro;

//Program

program
	: program_kwd id begin pgm_body end
	;
	
program_kwd
	: 'PROGRAM'
	;
	
id
	: IDENTIFIER
	;
	
pgm_body
	: decl func_declarations
	;
	
decl
	: string_decl decl 
	| var_decl decl 
	| empty
	;

string_decl
	: string_kwd id assign_op STRINGLITERAL
	;
	
string_kwd
	: 'STRING'
	;


//Variable declaration
	
var_decl
	:  var_type id_list
	;
	
var_type
	: 'FLOAT' 
	| 'INT'
	;

any_type
	: var_type 
	| 'VOID'
	;

id_list
	: id id_tail
	;
	
id_tail
	: comma id id_tail
	| empty
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
	: function any_type id obr id_list cbr begin func_body end
	;
	
begin
	: 'BEGIN'
	;
	
end
	: 'END'
	;
	
function
	: 'FUNCTION'
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
	: assign_expr semicolon
	;
	
assign_expr
	: id assign_op expr
	;
	
read_stmt
	: read obr id_list cbr semicolon
	;

write_stmt
	: write obr id_list cbr semicolon
	;
	
return_stmt
	: return_kwd expr semicolon
	;
	
read
	: 'READ'
	;

write
	: 'WRITE'
	;

return_kwd
	: 'RETURN'
	;
	
//Expressions

expr
	: expr_prefix factor
	;
	
expr_prefix
	: expr_prefix factor addop
	| empty
	;

factor
	: factor_prefix postfix_expr
	;
	
factor_prefix
	: factor_prefix postfix_expr mulop
	| empty
	;
	
postfix_expr
	: primary
	| call_expr
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
	: 'IF'
	;
	
else_kwd
	: 'ELSE'
	;

endif
	: 'ENDIF'
	;
	
compop
	: '<'
	| '>'
	| '='
	| '!='
	| '<='
	| '>='
	;
	
assign_op
	: ':='
	;
	
//While statements

while_stmt
	: while_kwd obr cond cbr decl stmt_list endwhile
	;
comma
	: ','
	;
	
while_kwd
	: 'WHILE'
	;
	
endwhile
	: 'ENDWHILE'
	;
	
continue_kwd
	: 'CONTINUE'
	;
	
break_kwd
	: 'BREAK'
	;

obr
	: '('
	;
	
cbr
	: ')'
	;
	
semicolon
	: ';'
	;

empty
    :
    ;
	
//Literals

WS
    :   (' ' | '\t' | '\r'| '\n') -> skip
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
	: ('"' .* '"')
	;
	
COMMENT
	: ('--' .* '\n')
	;