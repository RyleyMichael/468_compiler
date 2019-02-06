import org.antlr.runtime.tree.ParseTree;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.*;
import java.io.*;

import java.lang.String;

class Driver{
    public static void main(String[]args){
        try {
            for (String s : args) {
                String filename = "./src/nested.micro";
                File input = new File(filename);
                //Convert the input file to string
                Scanner scanner = new Scanner(input).useDelimiter("\\A");
                String result = scanner.hasNext() ? scanner.next() : "";
                //Apply Lexer (Tokenizer) to input string
                MicroLexer lexer = new MicroLexer(CharStreams.fromString(result));
                Vocabulary v = lexer.getVocabulary();
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                //Create a Parser
                MicroParser parser = new MicroParser(tokens);
                RuleContext tree = parser.program();
            // Create a walker object to traverse the arse tree
                ParseTreeWalker walker = new ParseTreeWalker();
                //Create a treminal listener to collect the non-terminals in the input file
                TerminalListener listener = new TerminalListener(v);
                //Traverse the tree
                walker.walk(listener, tree);

            //Create output file for terminals
                listener.output_terminals(filename);
            }
        }
        catch(Exception e){
            System.out.println("\n Ab error occured\n" + e.getMessage() + "\n" +e.fillInStackTrace());
        }
    }
}


