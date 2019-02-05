import org.antlr.runtime.tree.ParseTree;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.*;
import java.io.*;

import java.lang.String;

class Driver{
    public static void main(String[]args){
        try {
            for (String s : args) {
                //Open input files
                File input = new File(s);
                //Convert the input file to string
                Scanner scanner = new Scanner(input).useDelimiter("\\A");
                String result = scanner.hasNext() ? scanner.next() : "";
                //Apply Lexer (Tokenizer) to input string
                MicroLexer lexer = new MicroLexer(CharStreams.fromString(result));
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                //Create a Parser
                MicroParser parser = new MicroParser(tokens);
                RuleContext tree = parser.program();
                // Create a walker object to traverse the arse tree
                ParseTreeWalker walker = new ParseTreeWalker();
                //Create a treminal listener to collect the non-terminals in the niput file
                TerminalListener listener = new TerminalListener();
                //Traverse the tree
                walker.walk(listener, tree);
                //Create output file for terminals
                listener.output_terminals(s);
            }
        }
        catch(Exception e){
            System.out.println("\n Ab error occured");
        }
    }
}


