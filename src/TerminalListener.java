import java.io.File;
import java.util.Queue;
import org.antlr.v4.runtime.tree.*;

//Terminal Listener class ovrerides the base lsitener class
//This class object is used when traversing the parse tree
// We have ovreridden the "On visiting treminal" so that the class
// maintains a list of the non-termnial tokens it's seen and their values.
public class TerminalListener extends MicroBaseListener {
    private Queue<String> tokens;
    private Queue<String> values;

    @Override
    public void visitTerminal(TerminalNode node){
        values.add(node.getText());
        tokens.add(node.getSymbol().getText());

    }
    // This Method writes the terminals the listener has visited to an output
    //
    public void output_terminals(String filename){
        //File output = new File(filename);
        for(String vals:values){
            String toks = tokens.remove();
            System.out.println("\n Tokens = " + toks + "\n Vals = " + vals);

        }
    }

}