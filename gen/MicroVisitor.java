// Generated from C:/Users/Giova/IdeaProjects/468_compiler/src\Micro.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MicroParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MicroVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MicroParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MicroParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#program_kwd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram_kwd(MicroParser.Program_kwdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(MicroParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#pgm_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPgm_body(MicroParser.Pgm_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(MicroParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#string_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_decl(MicroParser.String_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#string_kwd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_kwd(MicroParser.String_kwdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_decl(MicroParser.Var_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#var_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_type(MicroParser.Var_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#any_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny_type(MicroParser.Any_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#id_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_list(MicroParser.Id_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#id_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_tail(MicroParser.Id_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#param_decl_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_decl_list(MicroParser.Param_decl_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#param_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_decl(MicroParser.Param_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#param_decl_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_decl_tail(MicroParser.Param_decl_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#func_declarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_declarations(MicroParser.Func_declarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#func_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_decl(MicroParser.Func_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#begin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBegin(MicroParser.BeginContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#end}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnd(MicroParser.EndContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(MicroParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#func_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_body(MicroParser.Func_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#stmt_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt_list(MicroParser.Stmt_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(MicroParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#base_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase_stmt(MicroParser.Base_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#assign_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_stmt(MicroParser.Assign_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#assign_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_expr(MicroParser.Assign_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#read_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead_stmt(MicroParser.Read_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#write_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrite_stmt(MicroParser.Write_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#return_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(MicroParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#read}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(MicroParser.ReadContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#write}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrite(MicroParser.WriteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#return_kwd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_kwd(MicroParser.Return_kwdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MicroParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#expr_prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_prefix(MicroParser.Expr_prefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(MicroParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#factor_prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor_prefix(MicroParser.Factor_prefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#postfix_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix_expr(MicroParser.Postfix_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#call_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall_expr(MicroParser.Call_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list(MicroParser.Expr_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#expr_list_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list_tail(MicroParser.Expr_list_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MicroParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#addop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddop(MicroParser.AddopContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#mulop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulop(MicroParser.MulopContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#if_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(MicroParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#else_part}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_part(MicroParser.Else_partContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond(MicroParser.CondContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#if_kwd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_kwd(MicroParser.If_kwdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#else_kwd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_kwd(MicroParser.Else_kwdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#endif}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndif(MicroParser.EndifContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#compop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompop(MicroParser.CompopContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#assign_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_op(MicroParser.Assign_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#while_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stmt(MicroParser.While_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#comma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComma(MicroParser.CommaContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#while_kwd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_kwd(MicroParser.While_kwdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#endwhile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndwhile(MicroParser.EndwhileContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#continue_kwd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_kwd(MicroParser.Continue_kwdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#break_kwd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_kwd(MicroParser.Break_kwdContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#obr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObr(MicroParser.ObrContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#cbr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCbr(MicroParser.CbrContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#semicolon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSemicolon(MicroParser.SemicolonContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroParser#empty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmpty(MicroParser.EmptyContext ctx);
}