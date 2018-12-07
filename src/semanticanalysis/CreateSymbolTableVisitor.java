package semanticanalysis;
import visitor.Visitor;
import symboltable.*;
import syntaxtree.*;

import java.util.*;

public class CreateSymbolTableVisitor extends ErrorBuilder implements Visitor
{
    private SymbolTable symTable = new SymbolTable();

    public void visit(Program n)
    {
        n.m.accept(this);

        for (int i = 0; i < n.cl.size(); ++i)
            n.cl.elementAt(i).accept(this);

        symTable.resolveInheritance();
    }

    public void visit(MainClass n)
    {
        multiplyIdCheck(symTable.addClass(new ClassSymbol(n.i1.s)), n.getLine(), n.getColumn());
        symTable.enterClass(n.i1.s);
        multiplyIdCheck(symTable.addMethod(new MethodSymbol("main", null)), n.getLine(), n.getColumn());
        symTable.exitClass();
    }

    public void visit(ClassDeclSimple n)
    {
        multiplyIdCheck(symTable.addClass(new ClassSymbol(n.i.s)), n.getLine(), n.getColumn());
        symTable.enterClass(n.i.s);

        if (n.vl != null)
        {
            for (int i = 0; i < n.vl.size(); ++i)
                n.vl.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.ml.size(); ++i)
            n.ml.elementAt(i).accept(this);

        symTable.exitClass();
    }
    public void visit(ClassDeclExtends n)
    {
        multiplyIdCheck(symTable.addClass(new ClassSymbol(n.i.s, n.j.s)), n.getLine(), n.getColumn());
        symTable.enterClass(n.i.s);

        if (n.vl != null)
        {
            for (int i = 0; i < n.vl.size(); ++i)
                n.vl.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.ml.size(); ++i)
            n.ml.elementAt(i).accept(this);

        symTable.exitClass();
    }
    public void visit(VarDecl n)
    {
        multiplyIdCheck(symTable.addVariable(new VariableSymbol(n.i.s, n.t)), n.getLine(), n.getColumn());
    }
    public void visit(MethodDecl n)
    {
        multiplyIdCheck(symTable.addMethod(new MethodSymbol(n.i.s, n.t)), n.getLine(), n.getColumn());
        symTable.enterMethod(n.i.s);

        if (n.fl != null)
        {
            for (int i = 0; i < n.fl.size(); ++i)
                n.fl.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.vl.size(); ++i)
            n.vl.elementAt(i).accept(this);

        symTable.exitMethod();
    }
    public void visit(Formal n)
    {
        multiplyIdCheck(symTable.getCurrentMethod().addFormalVar(new VariableSymbol(n.i.s, n.t)), n.getLine(), n.getColumn());
    }

    private void multiplyIdCheck(SymbolInfo oldSymbol, int line, int col)
    {
        if (oldSymbol != null){
             addError("Multiply defined identifier " + oldSymbol.getName(), line, col);
        }
    }

    public ISymbolTable getSymbolTable()
    {
        return symTable;
    }

    public void visit(IntArrayType n) {}
    public void visit(BooleanType n) {}
    public void visit(IntegerType n) {}
    public void visit(IdentifierType n) {}
    public void visit(Block n) {}
    public void visit(If n) {}
    public void visit(While n) {}
    public void visit(Print n) {}
    public void visit(Assign n) {}
    public void visit(ArrayAssign n) {}
    public void visit(And n) {}
    public void visit(LessThan n) {}
    public void visit(Plus n) {}
    public void visit(Minus n) {}
    public void visit(Times n) {}
    public void visit(ArrayLookup n) {}
    public void visit(ArrayLength n) {}
    public void visit(Call n) {}
    public void visit(IntegerLiteral n) {}
    public void visit(True n) {}
    public void visit(False n) {}
    public void visit(IdentifierExp n) {}
    public void visit(This n) {}
    public void visit(NewArray n) {}
    public void visit(NewObject n) {}
    public void visit(Not n) {}
    public void visit(Identifier n) {}
}