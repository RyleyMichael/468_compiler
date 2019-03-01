import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.*;

//Terminal Listener class ovrerides the base lsitener class
//This class object is used when traversing the parse tree
// We have ovreridden the "On visiting treminal" so that the class
// maintains a list of the non-termnial tokens it's seen and their values.
public class TerminalListener extends MicroBaseListener {
    private Queue<String> tokens = new LinkedList<String>();
    private Queue<String> values = new LinkedList<String>();
    private Vocabulary v;
    private boolean error = false;
    String state;

    public TerminalListener(Vocabulary v){
        this.v = v;
    }
    @Override
    public void visitTerminal(TerminalNode node){

        values.add(node.getText());
        //tokens.add( " " + v.getSymbolicName(node.getSymbol().getType()));
        tokens.add(v.getSymbolicName(node.getSymbol().getType()));


    }

    @Override
    public void visitErrorNode(ErrorNode node){
        this.error = true;

    }


    // This Method writes the terminals the listener has visited to an output
    //
    public void output_terminals(String filename) throws IOException {
        
        for(String vals:values){
            String toks = tokens.remove();
            System.out.println("Token Type: " + toks + "\nValue: " + vals);
        }
        
        /*
        int length = filename.length()-5;
        String output_flie = (filename.substring(0,length))+"out";
        FileWriter fileWriter = new FileWriter(output_flie);
        for(String vals:values){
            String toks = tokens.remove();
            fileWriter.write("Token Type: " + toks + "\nValue: " + vals + "\n");
        }
        fileWriter.close();
        */
    }

    public void output_end_state(String filename, boolean err) throws IOException{
        int length = filename.length()-5;
        String output_flie = (filename.substring(0,length))+"out";
        FileWriter fileWriter = new FileWriter(output_flie);
        if (this.error==false && err==false){
            state = "Accepted";
        }
        else {
            state = "Not accepted";
        }
        fileWriter.write(state);
        fileWriter.close();
    }

}
