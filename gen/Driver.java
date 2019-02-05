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

    public void output_terminals(){
        File output = new File
        for(String vals:values){
            String toks = tokens.remove();

        }
    }

}

public static void main(String[] args){

        for(String s:args){
            File input = new File(s)
            Scanner s = new Scanner(input).useDelimiter("\\A");
            String result = s.hasNext()?s.next():"";
            MicroLexer lexer = new Microlexer(CharStreams.fromString(result));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MicroParser parser = new MicroParser(tokens);
            ParseTree tree = parser.compilationUnit();
            ParseTreeWalker walker = new ParseTreeWalker();
            TerminalListener listener = new TerminalListener();
            walker.walk(listener, tree);
            listener.output_terminals();
            input.close();
        }
    }



