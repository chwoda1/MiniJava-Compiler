import java.io.*;
import java.util.*;
import irgen.*;
import semanticanalysis.*;
import symboltable.*;
import syntaxtree.*;
import visitor.*;

public class Project3bDriver
{
    public static void main(String[] args) throws Exception
    {

	Program program = null; 

        if (args.length != 1)
        {
            System.err.println("usage: java MiniJavaCompiler <input-file>");
            System.exit(1);
        }

	try {

	  miniParser parser = new miniParser(new miniLexer(new FileReader(args[0])));

          program = (Program)parser.parse().value;

	} catch(Exception e) { System.out.println("Parse Failed... Exiting"); System.exit(0); }

        CreateSymbolTableVisitor symTableBuilder = new CreateSymbolTableVisitor();
        symTableBuilder.visit(program);

        ISymbolTable symbolTable = symTableBuilder.getSymbolTable();

        for (String error : symTableBuilder.getErrors())
            System.out.println(error);

        NameResolution nameAnalysis = new NameResolution(symbolTable);
        nameAnalysis.visit(program);
        for (String error : nameAnalysis.getErrors())
            System.out.println(error);

        TypeCheckVisitor typeChecker = new TypeCheckVisitor(symbolTable);
        typeChecker.visit(program);
        for (String error : typeChecker.getErrors())
            System.out.println(error);

        if(!(typeChecker.getErrors().isEmpty() && nameAnalysis.getErrors().isEmpty() && symTableBuilder.getErrors().isEmpty()))
        {
            System.out.println("Errors found.  Can't continue.");
            return;
        }

        ThreeAddressCodeVisitor ir = new ThreeAddressCodeVisitor((SymbolTable)symbolTable);
        ir.visit(program);
        ir.print3AddCode();
    }
}
