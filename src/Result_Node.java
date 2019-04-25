public class Result_Node extends AST {

    public Result_Node(int register_num, int vartype){
        this.result_reg = register_num;
        this.vartype = vartype;
    }

    @Override
    public String generate_code() {
        return null;
    }
}
