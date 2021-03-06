import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.*;


public class Code_Generator extends SymbolTableListener {

    private class Expressions{
        Stack<LeafNode> leaves = new Stack<>();
        Stack<MulEXP> factor_prefix = new Stack<>();
        Stack<AddEXP> exp_prefix = new Stack<>();
        Stack<Result_Node> factors = new Stack<>();
        Stack<String> in_labels = new Stack<>();
        String var_assigned = null;
        Stack<String> out_labels = new Stack<>();
        Stack<String> cmp_statements = new Stack<>();
        Stack<String> else_blocks = new Stack<>();

        public Expressions(){

        }
        public void printStacks(){
            System.out.println("FACT_PRE\n"+factor_prefix);
            System.out.println("EXP_PRE\n"+exp_prefix);
            System.out.println("FACTORS\n"+factors);
            System.out.println("LEAVES\n"+leaves);

        }
    }



    Stack<Expressions> exprs = new Stack<>();
    private Expressions current_exp = new Expressions();
    private String generated_code = "";
    int assigns = 0;
    int label_iterator = 0;
    int register_iterator = 0;
    Root_Node ass_root;
    LinkedHashMap<String, Integer> reg_nums = new LinkedHashMap<>();

    public Code_Generator(Vocabulary v){
        super(v);
    }


    public String compare_stmt(int r1, int r2, String vtype, String op, String target){
        String tempcode = String.format("cmp%s r%d r%d\n", vtype, r1, r2);
        switch(op){
            case "<=": tempcode += String.format("jle %s", target);
                        break;
            case "==": tempcode += String.format("jeq %s", target);
                    break;
            case "!=": tempcode += String.format("jne %s", target);
                break;
            case ">=": tempcode += String.format("jge %s", target);
                break;
            case "<": tempcode += String.format("jlt %s", target);
                break;
            case ">": tempcode += String.format("jgt %s", target);
                break;

            default: tempcode+="";
        }
        return tempcode;
    }

    public String compare_stmt_inv(int r1, int r2, String vtype, String op, String target){
        String tempcode = String.format("cmp%s r%d r%d\n", vtype, r1, r2);
        switch(op){
            case "<=": tempcode += String.format("jgt %s", target);
                break;
            case "==": tempcode += String.format("jne %s", target);
                break;
            case "!=": tempcode += String.format("jeq %s", target);
                break;
            case ">=": tempcode += String.format("jlt %s", target);
                break;
            case "<": tempcode += String.format("jge %s", target);
                break;
            case ">": tempcode += String.format("jle %s", target);
                break;

            default: tempcode+="";
        }
        return tempcode;
    }




        @Override public void enterWrite_stmt(MicroParser.Write_stmtContext ctx){
        String temp = ctx.getChild(2).getText();
        String[] temp_arr =temp.split(",");

        for(String s :temp_arr){

            String vartype;
            int rnum;

            if (declared_vars.containsKey(s) && reg_nums.containsKey(s)){

                rnum = reg_nums.get(s);
                vartype = declared_vars.get(s).get(0);
                if (vartype.equals("INT")){vartype="i";}
                else if(vartype.equals("FLOAT")){vartype="r";}
                this.generated_code+=String.format("sys write%s r%d\n", vartype, rnum);
            }
            else{
                vartype="s";
                this.generated_code+=String.format("sys write%s %s\n", vartype, s);

            }


        }
    }

    @Override public void enterRead_stmt(MicroParser.Read_stmtContext ctx){
        String temp = ctx.getChild(2).getText();
        String[] temp_arr =temp.split(",");
        for(String s :temp_arr){

            String vartype;
            int rnum;
            vartype = declared_vars.get(s).get(0);

            if (declared_vars.containsKey(s)){
                if(reg_nums.containsKey(s)){rnum = reg_nums.get(s);}
                else{
                    register_iterator+=1;
                    rnum = register_iterator;
                    reg_nums.put(s, rnum);
                }
                vartype = declared_vars.get(s).get(0);
                if (vartype.equals("INT")){vartype="i";}
                else if(vartype.equals("FLOAT")){vartype="r";}
                else{vartype="s";}
                this.generated_code+=String.format("sys read%s %s\n", vartype, s);
                this.generated_code+=String.format("move %s r%d\n", s, rnum);}

        }

        }



        @Override public void enterAssign_expr(MicroParser.Assign_exprContext ctx) {

        int register;
        if(reg_nums.containsKey(ctx.getChild(0).getText())){
            register = reg_nums.get(ctx.getChild(0).getText());
        }
        else{

            register_iterator+=1;
            register = register_iterator;

        }
        ass_root = new Root_Node(true);
        ass_root.setResult_reg(register);

    }
    @Override public void exitAssign_expr(MicroParser.Assign_exprContext ctx) {
        current_exp.var_assigned = ctx.getChild(0).getText();
        ass_root.set_assignment_var(current_exp.var_assigned);
        if(!(current_exp.factors.isEmpty())){ass_root.setTerm_2(current_exp.factors.pop());}
        this.generated_code += ass_root.generate_code();


        ass_root = new Root_Node(true);
        current_exp.leaves = new Stack<>();
        current_exp.factor_prefix = new Stack<>();
        current_exp.exp_prefix = new Stack<>();
        current_exp.factors = new Stack<>();
        current_exp.var_assigned = "";


    }

    @Override public void enterIf_stmt(MicroParser.If_stmtContext ctx) {
        String blockname = String.format("BLOCK %d", next_block);
        next_block += 1;
        current_scope = blockname;
        scopes.push(blockname);
        symb_tab.put(current_scope, new LinkedHashMap<>());

        String target = String.format("label%d\n",label_iterator);
        String out_label = String.format("out%d\n",label_iterator);
        current_exp.in_labels.push(target);
        current_exp.out_labels.push(out_label);
        label_iterator+=1;
    }

    @Override public void exitIf_stmt(MicroParser.If_stmtContext ctx) {
        String target = current_exp.in_labels.pop();

        String vartype = declared_vars.get(ctx.getChild(2).getChild(0).getText()).get(0);
        if (vartype.equals("INT")){vartype ="i";}
        else{vartype ="r";}
        String op = ctx.getChild(2).getChild(1).getText();
        int r1;
        int r2;
        if(!reg_nums.containsKey(ctx.getChild(2).getChild(0).getText())){
            String temp = ctx.getChild(2).getChild(0).getText();
            register_iterator+=1;
            reg_nums.put(temp, register_iterator);
            r1 = register_iterator;
            reg_nums.put(temp, register_iterator);
            this.generated_code+= String.format("move %s r%d\n", temp, register_iterator);
        }
        else{
            r1 = reg_nums.get(ctx.getChild(2).getChild(0).getText());
        }
        if(reg_nums.containsKey(ctx.getChild(2).getChild(2).getText())){
            r2 = reg_nums.get(ctx.getChild(2).getChild(2).getText());
        }
        else if(ctx.getChild(2).getChild(2).getChildCount()>1) {
            r2 = ass_root.get_result_register();
        }
        else{
            register_iterator+=1;
            r2 = register_iterator;
            String temp =ctx.getChild(2).getChild(2).getText();
            reg_nums.put(temp, register_iterator);
            this.generated_code+= String.format("move %s r%d\n", temp, register_iterator);

        }
        this.generated_code+= compare_stmt_inv(r1, r2, vartype, op, current_exp.out_labels.peek());
        this.generated_code += "label "+target;
        this.generated_code+= String.format("label %s", current_exp.out_labels.pop());


    }



    @Override public void enterWhile_stmt(MicroParser.While_stmtContext ctx) {

        enter_conditional();

    }

    @Override public void enterElse_part(MicroParser.Else_partContext ctx) {
        String blockname = String.format("BLOCK %d", next_block);
        next_block += 1;
        current_scope = blockname;
        scopes.push(current_scope);
        symb_tab.put(current_scope, new LinkedHashMap<>());
        enter_conditional();



    }

    @Override public void exitElse_part(MicroParser.Else_partContext ctx) {
        scopes.pop();
        current_scope = scopes.peek();
        this.generated_code+= String.format("label %s", current_exp.out_labels.pop());


    }




    @Override public void exitWhile_stmt(MicroParser.While_stmtContext ctx) {
        int r1 = reg_nums.get(ctx.getChild(2).getChild(0).getText());

        int r2;
        String vartype = declared_vars.get(ctx.getChild(2).getChild(0).getText()).get(0);
        String op = ctx.getChild(2).getChild(1).getText();

        if (vartype.equals("INT")){vartype ="i";}
        else{vartype ="r";}
        if(reg_nums.containsKey(ctx.getChild(2).getChild(2).getText())){
            r2 = reg_nums.get(ctx.getChild(2).getChild(2).getText());
        }
        else{
            register_iterator+=1;
            r2 = register_iterator;
            reg_nums.put(ctx.getChild(2).getChild(2).getText(), register_iterator);
            this.generated_code+= String.format("move %s r%d\n",ctx.getChild(2).getChild(2).getText(), register_iterator );
        }
        this.generated_code+= compare_stmt_inv(r1, r2, vartype, op, current_exp.out_labels.peek());
        this.generated_code+= String.format("jmp %s", current_exp.in_labels.get(current_exp.in_labels.size()-1));
        current_exp.cmp_statements.push(compare_stmt(r1, r2, vartype, op, current_exp.in_labels.pop()));



        String cmp_stmt = current_exp.cmp_statements.pop();
        this.generated_code+= cmp_stmt;
        this.generated_code+="label "+current_exp.out_labels.pop();
        this.generated_code+= String.format("");
        scopes.pop();
        current_scope = scopes.peek();

    }

    private void enter_conditional() {
        String blockname = String.format("BLOCK %d", next_block);
        next_block += 1;
        current_scope = blockname;
        scopes.push(blockname);

        symb_tab.put(current_scope, new LinkedHashMap<>());
        String target = String.format("label%d\n",label_iterator);
        String out_label = String.format("out%d\n",label_iterator);
        current_exp.in_labels.push(target);
        current_exp.out_labels.push(out_label);
        this.generated_code += "label "+target;
        label_iterator+=1;
    }

        @Override public void enterExpr(MicroParser.ExprContext ctx) {

        }


        @Override public void exitExpr(MicroParser.ExprContext ctx) {
        if(current_exp.exp_prefix.isEmpty()){
            if(!current_exp.factors.isEmpty()){

                Result_Node res = current_exp.factors.pop();
                ass_root.setTerm_2(res);
            }
            else{
                System.out.println("XXXXXXXXXX");
            }
        }
        else{

            AddEXP temp =current_exp.exp_prefix.pop();
            Result_Node expr_res;

            if(!(current_exp.factors.isEmpty())) {

                Result_Node res = current_exp.factors.pop();
                if (temp.get_term_1()==null){
                    temp.setTerm_1(res);
                    current_exp.exp_prefix.push(temp);
                }
                else{
                    temp.setTerm_2(res);

                    this.generated_code += temp.generate_code();
                }


            }
            else if(!(current_exp.leaves.isEmpty())){
                LeafNode res = current_exp.leaves.pop();
                temp.setTerm_2(res);
                this.generated_code += temp.generate_code();
            }

            expr_res = new Result_Node(temp.get_result_register(), temp.get_vartype());
            current_exp.factors.push(expr_res);



        }

    }
    @Override public void exitCmpnd_expr(MicroParser.Cmpnd_exprContext ctx) {

        Result_Node res = current_exp.factors.pop();
        current_exp = exprs.pop();
        current_exp.factors.push(res);

    }
    @Override public void enterCmpnd_expr(MicroParser.Cmpnd_exprContext ctx) {

        exprs.push(current_exp);
        current_exp = new Expressions();
    }

    @Override public void enterFactor(MicroParser.FactorContext ctx) {

    }


    @Override public void exitFactor(MicroParser.FactorContext ctx) {

        if(current_exp.factor_prefix.isEmpty()){

            if(!(current_exp.leaves.isEmpty())){
                LeafNode temp = current_exp.leaves.pop();
                Result_Node res = new Result_Node(temp.get_result_register(), temp.get_vartype());
                current_exp.factors.push(res);
            }

        }
        else{

            MulEXP temp = current_exp.factor_prefix.pop();
            if(temp.get_term_1()!=null){
                if(!(current_exp.factors.isEmpty())){

                    temp.setTerm_2(current_exp.factors.pop());
                }
                else {

                    LeafNode leaf = current_exp.leaves.pop();
                    temp.setTerm_2(leaf);
                }
                generated_code+= temp.generate_code();
                Result_Node res = new Result_Node(temp.get_result_register(), temp.get_vartype());
                current_exp.factors.push(res);
            }

            else{
                if(!(current_exp.factors.isEmpty())){

                    temp.setTerm_1(current_exp.factors.pop());
                }
                else {

                    LeafNode leaf = current_exp.leaves.pop();
                    temp.setTerm_1(leaf);
                }
                current_exp.factor_prefix.push(temp);
            }




        }

    }
    @Override public void exitPrimary(MicroParser.PrimaryContext ctx) {

        String temp = ctx.getText();
        int vartype = ctx.getAltNumber();
        boolean ld = true;
        int reg_number;
        if(reg_nums.containsKey(temp)){
            reg_number = reg_nums.get(temp);
            ld = false;
        }
        else{
            register_iterator+=1;
            reg_number = register_iterator;
            reg_nums.put(temp, reg_number);

        }
        if(declared_vars.containsKey(temp)){
            if(declared_vars.get(temp).get(0).equals("INT")){
                vartype = 2;
            }
            else{vartype=3;}
        }
        LeafNode leaf = new LeafNode(vartype, reg_number, ld, temp);
        this.generated_code+= leaf.generate_code();
        current_exp.leaves.push(leaf);
    }


    @Override public void enterExpr_prefix(MicroParser.Expr_prefixContext ctx) {

        AddEXP temp;
        if(ctx.children.size()>1){

            temp = new AddEXP(ctx.getChild(2).getText());
            current_exp.exp_prefix.push(temp);
        }



    }

    @Override public void exitExpr_prefix(MicroParser.Expr_prefixContext ctx){

        AddEXP temp;
        if(ctx.children.size()>1){

            temp = current_exp.exp_prefix.pop();


            if (temp.get_term_1() == null && temp.get_term_2()== null){

                if(!current_exp.factors.isEmpty()){
                    temp.setTerm_1(current_exp.factors.pop());

                    current_exp.exp_prefix.push(temp);
                }
            }
            else if(temp.get_term_2()==null){
                if(!(current_exp.leaves.isEmpty())){
                    temp.setTerm_2(current_exp.factors.pop());
                    this.generated_code += temp.generate_code();
                    Result_Node res = new Result_Node(temp.get_result_register(), temp.get_vartype());
                    temp.setTerm_1(res);
                    current_exp.exp_prefix.push(temp);
                }
            }
        }
    }

    @Override public void enterFactor_prefix(MicroParser.Factor_prefixContext ctx){

        MulEXP temp;
        if(ctx.children.size()>1){

            temp = new MulEXP(ctx.getChild(2).getText());
            current_exp.factor_prefix.push(temp);
        }

    }
    @Override public void exitFactor_prefix(MicroParser.Factor_prefixContext ctx) {

        MulEXP temp;
        if (ctx.children.size() > 1) {
            temp = current_exp.factor_prefix.pop();

            if (temp.get_term_1() == null && temp.get_term_2() == null) {
                if(!current_exp.leaves.isEmpty()) {

                    temp.setTerm_1(current_exp.leaves.pop());
                }
                else{

                    temp.setTerm_1(current_exp.factors.pop());

                }
                current_exp.factor_prefix.push(temp);
            } else if (temp.get_term_2() == null) {

                if (!(current_exp.leaves.isEmpty())) {
                    temp.setTerm_2(current_exp.leaves.pop());
                    this.generated_code += temp.generate_code();
                    Result_Node res = new Result_Node(temp.get_result_register(), temp.get_vartype());
                    current_exp.factors.push(res);
                    if (!(current_exp.factor_prefix.isEmpty())) {

                        temp.setTerm_1(res);
                        current_exp.factor_prefix.push(temp);
                    } else {
                        current_exp.factors.push(res);
                    }
                }
            }


        }
    }
    public void print_code(){
        this.generated_code= get_declarations()+this.generated_code+"end\n";
        System.out.println(this.generated_code);
    }


    public String get_declarations(){
        String temp ="";
        for(String vars: this.declared_vars.keySet()){
            ArrayList<String> temp_array = declared_vars.get(vars);
            if (temp_array.size()>1){
                temp += String.format("var %s %s\n", vars, temp_array.get(1));
            }
            else{
                temp += String.format("var %s \n", vars);
            }
        }
        return temp;
    }

}




