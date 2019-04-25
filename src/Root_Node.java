import org.antlr.v4.runtime.ParserRuleContext;

public class Root_Node extends AST {
    private String ass_var = null;
    public Root_Node(boolean root){
        this.root = root;
    }


    @Override
    public String generate_code() {
        String temp_code = "";
        int r2 = this.get_term_2().get_result_register();
        temp_code+=String.format("move r%d %s\n", r2, ass_var);
        return temp_code;

    }

    public void set_assignment_var(String ass_var) {
        this.ass_var = ass_var;
    }
}
