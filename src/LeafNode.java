import org.antlr.v4.runtime.ParserRuleContext;

public class LeafNode extends AST {
    boolean load;
    String varname;
    public LeafNode(int vartype, int regnum, boolean load, String varname){
        this.result_reg = regnum;
        this.vartype = vartype;
        this.load = load;
        this.varname = varname;
    }
    @Override
    public String generate_code() {
        String temp_code ="";
        if(load == true){
            temp_code+=String.format("move %s r%d \n", varname, result_reg);
        }

        return temp_code;
    }
}
