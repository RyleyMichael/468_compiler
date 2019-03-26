import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.util.Scanner;

public class Driver3 {

    public static void main(String[]args){

        try {
            //for (String s : args) {
                String filename = "test9.micro";
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
                SymbolTableListener listener = new SymbolTableListener(v);
                //Traverse the tree
                walker.walk(listener, tree);
                listener.print_symbtab();
            //}
        }
        catch(Exception e){
            System.out.println("\n An error occured\n" + e.getMessage() + "\n" +e.fillInStackTrace());
        }
    }
}
