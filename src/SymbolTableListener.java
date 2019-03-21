import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.*;

/*
*
* The SymbolTableListener class uses nested linkedhashmaps to store symbol tables
* The keys in the outer linkedhashmap are the scopes of the program and the values are linkedhashmaps, with each
* key in the inner linkedhashmaps representing variable names and the values in the hashmaps being array lists
* containing the type and value for each unique variable
*
*
* This class uses a stack to keep track of the current scope.
* Whenever the listener enters a rule which would change the scope (such as entering a while statement),
* the new scope is updated and pushed onto the stack. Likewise, whenever the listener leaves a while statement,
* the last scope is popped from the stack and the current scope is updated to the element on the top of the stack
*
*
* Chose linkedhashmaps because this data structure preserves the order in which they keys were initially added to the hashmap
* This allows for iteration over the symbol tables in the order that the scopes were defined in the source program
* */
public class SymbolTableListener extends MicroBaseListener {
    private LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> symb_tab = new LinkedHashMap<>();
    private Stack<String> scopes;
    String current_scope = "Global";
    private int next_block = 1;
    private Vocabulary v;
    String state;

    public SymbolTableListener(Vocabulary v){
        this.v = v;
        this.scopes = new Stack<>();
        scopes.push(current_scope);
    }

    public void print_symbtab(){
        for(String scope : symb_tab.keySet()){
            System.out.println(String.format("Symbol table %s", scope));
            LinkedHashMap table = symb_tab.get(scope);
            for( Object var_name : table.keySet()){
                ArrayList<String> temp =(ArrayList<String>) table.get(var_name);
                if(temp.size()>1){
                    System.out.println(String.format("\nname  %s type %s value %s",var_name, temp.get(0), temp.get(1) ));
                }
                else{
                    System.out.println(String.format("\nname  %s type %s",var_name, temp.get(0) ));
                }


                }
            }

    }




    /*
     *
     * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
     *                       Actions when variables are declared
     * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
     *
     * Store variables in the appropriate symbol tables
     */



    @Override public void exitVar_decl(MicroParser.Var_declContext ctx){
        LinkedHashMap declarations = symb_tab.get(current_scope);
        int chx  =ctx.getChildCount();
        String vtype = ctx.getChild(0).getText();
        String id_list = ctx.getChild(1).getText();
        String [] ids = id_list.split(",");
        for(String s : ids){
            ArrayList<String> temp = new ArrayList<>();
            temp.add(vtype);
            declarations.put(s, temp);
        }
        symb_tab.put(current_scope, declarations);

    }

    @Override public void exitParam_decl(MicroParser.Param_declContext ctx) {
        LinkedHashMap declarations = symb_tab.get(current_scope);
        String vtype = ctx.getChild(0).getText();
        String id = ctx.getChild(1).getText();
        ArrayList<String> temp = new ArrayList<>();
        temp.add(vtype);
        declarations.put(id, temp);
        symb_tab.put(current_scope, declarations);
    }


    @Override public void exitString_decl(MicroParser.String_declContext ctx) {
        LinkedHashMap declarations = symb_tab.get(current_scope);
        String vtype = ctx.getChild(0).getText();
        String id = ctx.getChild(1).getText();
        String value_of = ctx.getChild(3).getText();
        ArrayList<String> temp = new ArrayList<>();
        temp.add(vtype);
        temp.add(value_of);
        declarations.put(id, temp);
        symb_tab.put(current_scope, declarations);


    }


    /*
    *
    * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    *                       Actions when the scope is changed
    * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    *
    * Update the current scope
    * Make an entry into the LinkedHashMap symb_tab for the current_scope if none exists
    */

    @Override
    public void enterProgram(MicroParser.ProgramContext ctx) {
        symb_tab.put(current_scope, new LinkedHashMap<>());
    }
    @Override public void enterFunc_decl(MicroParser.Func_declContext ctx) {
        String blockname = ctx.getChild(2).getText();
        current_scope = blockname;
        scopes.push(blockname);
        symb_tab.put(current_scope, new LinkedHashMap<>());
    }
    @Override public void exitFunc_decl(MicroParser.Func_declContext ctx) {
        scopes.pop();
        current_scope = scopes.peek();
    }

    @Override public void enterWhile_stmt(MicroParser.While_stmtContext ctx) {
        String blockname = String.format("BLOCK %d", next_block);
        next_block += 1;
        current_scope = blockname;
        scopes.push(blockname);
        symb_tab.put(current_scope, new LinkedHashMap<>());
    }

    @Override public void exitWhile_stmt(MicroParser.While_stmtContext ctx) {
        scopes.pop();
        current_scope = scopes.peek();
    }


    @Override public void enterIf_stmt(MicroParser.If_stmtContext ctx) {
        String blockname = String.format("BLOCK %d", next_block);
        next_block += 1;
        current_scope = blockname;
        scopes.push(current_scope);
        symb_tab.put(current_scope, new LinkedHashMap<>());
    }

    @Override public void exitIf_stmt(MicroParser.If_stmtContext ctx) {
        scopes.pop();
        current_scope = scopes.peek();
    }

    @Override public void enterElse_part(MicroParser.Else_partContext ctx) {
        String blockname = String.format("BLOCK %d", next_block);
        next_block += 1;
        current_scope = blockname;
        scopes.push(current_scope);
        symb_tab.put(current_scope, new LinkedHashMap<>());
    }

    @Override public void exitElse_part(MicroParser.Else_partContext ctx) {
        scopes.pop();
        current_scope = scopes.peek();
    }


}
