import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Queue;


public class TerminalListener extends MicroBaseListener {
    private Queue<String> tokens = new Queue<>();
    private Queue<String> values = new Queue<>();

    @ovreride
    public void visitTerminal(TerminalNode node){
        values.add(node.getText());
        tokens.add(node.getSymbol().getText());

    }
    // This Method writes the treminals the listener havs visited to an output
    //
    public void output_terminals(){
        File output = new File
        for(String vals:values){
            String toks = tokens.remove();

        }
    }

}

public static void main(String[] args){

        for(String s:args){
            //Open input files
            File input = new File(s);
            //Convert the input file to string
            Scanner s = new Scanner(input).useDelimiter("\\A");
            String result = s.hasNext()?s.next():"";
            //Apply Lexer (Tokenizer) to input string
            MicroLexer lexer = new Microlexer(CharStreams.fromString(result));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            //Create a Parser
            MicroParser parser = new MicroParser(tokens);
            ParseTree tree = parser.compilationUnit();
            //Craete a walker object to traverse the arse tree
            ParseTreeWalker walker = new ParseTreeWalker();
            //Craete a treminal listner to collect the non-treminals in the niput file
            TerminalListener listener = new TerminalListener();
            //Traverse the tree
            walker.walk(listener, tree);
            //Craete output file for terminals
            listener.output_terminals();
            input.close();
        }
    }



