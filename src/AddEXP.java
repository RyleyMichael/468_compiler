public class AddEXP extends AST {
    private String operator;
    public AddEXP(String operator){
        this.operator = operator;
    }
    @Override
    public String generate_code() {

        this.vartype = this.term_1.vartype;
        String op_type = "";
        if(operator.equals("-")){op_type = "sub";}
        else{op_type="add";}
        if(this.vartype==2){op_type+="i";}
        else{op_type+="r";}
        int r1 = this.get_term_1().get_result_register();
        int r2 = this.get_term_2().get_result_register();
        this.result_reg = r1;
        String temp_code = String.format("%s r%d r%d\n", op_type, r2, r1);
        return temp_code;
    }
}
