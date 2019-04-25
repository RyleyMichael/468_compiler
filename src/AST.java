import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.*;


public abstract class AST {
    protected boolean root = false;
    protected boolean leaf = false;
    protected int vartype;
    protected String code;
    protected int result_reg;
    protected AST term_1 = null;
    protected AST term_2 = null;


    public String getCode() {
        return code;
    }



    public boolean is_root() {
        return root;
    }

    public boolean is_leaf() {
        return leaf;
    }

    public boolean is_unary() {
        if (this.term_1 != null && this.term_2 != null) {
            return true;
        } else {
            return false;
        }
    }

    public abstract String generate_code();


    public void setTerm_1(AST t1){
        this.term_1 = t1;
    }
    public void setTerm_2(AST t2){
        this.term_2 = t2;
    }
    public AST get_term_1() {
        return term_1;
    }
    public AST get_term_2() {
        return term_2;
    }
    public int get_result_register(){return this.result_reg;}
    public int get_vartype(){return this.vartype;}
    public void setResult_reg(int result_register){this.result_reg = result_register;}

}

