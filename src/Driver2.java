import org.antlr.runtime.tree.ParseTree;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.*;
import java.io.*;

import java.lang.String;

class Driver2{
    public static void main(String[]args){
        String[] filenames = {"test1.micro", "test2.micro", "test3.micro", "test4.micro", "test5.micro", "test6.micro", "test7.micro", "test8.micro", "test9.micro", "test10.micro", "test12.micro", "test15.micro", "test16.micro", "test17.micro", "test18.micro", "test19.micro", "test20.micro", "test21.micro"};
        try {
            for (String s : filenames) {
                String filename = s;
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
                Error_Listener el = new Error_Listener();
                parser.addErrorListener(el);
                RuleContext tree = parser.program();
                // Create a walker object to traverse the arse tree
                ParseTreeWalker walker = new ParseTreeWalker();
                //Create a treminal listener to collect the non-terminals in the input file
                TerminalListener listener = new TerminalListener(v);
                //Traverse the tree
                walker.walk(listener, tree);

                boolean err = el.has_error();
                //Create output file for terminals
                listener.output_end_state(filename, err);
            }
        }
        catch(Exception e){
            System.out.println("\n An error occured\n" + e.getMessage() + "\n" +e.fillInStackTrace());
        }
    }
}




