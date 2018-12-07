package semanticanalysis;
import visitor.Visitor;
import symboltable.*;
import syntaxtree.*;

import java.util.*;

public class NameResolution extends ErrorBuilder implements Visitor
{
    private ISymbolTable symTable;

    public NameResolution(ISymbolTable symbolTable)
    {
        symTable = symbolTable;
    }

    public void visit(Program n)
    {
        n.m.accept(this);
        for (int i = 0; i < n.cl.size(); ++i)
            n.cl.elementAt(i).accept(this);
    }
    public void visit(MainClass n)
    {
        if (!symTable.doesClassExist(n.i1.s))
            return;

        symTable.enterClass(n.i1.s);
        try
        {
            symTable.enterMethod("main");
            n.s.accept(this);
        }
        catch (NoScopeFound e)
        {
            symTable.exitClass();
            return;
        }

        symTable.exitMethod();
        symTable.exitClass();


    }
    public void visit(ClassDeclSimple n)
    {
        if (!symTable.doesClassExist(n.i.s))
            return;

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
        if (!symTable.doesClassExist(n.i.s))
            return;

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
        n.t.accept(this);
    }
    public void visit(MethodDecl n)
    {
        try 
        {
            symTable.enterMethod(n.i.s);
        }
        catch (NoScopeFound e)
        {
            return;
        }
        
        if (n.fl != null)
        {
            for (int i = 0; i < n.fl.size(); ++i)
                n.fl.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.vl.size(); ++i)
            n.vl.elementAt(i).accept(this);

        for (int i = 0; i < n.sl.size(); ++i)
            n.sl.elementAt(i).accept(this);

        n.e.accept(this);

        symTable.exitMethod();
    }
    public void visit(Formal n) 
    { 
        n.t.accept(this);
    }
    public void visit(IdentifierType n)
    {
        if (!symTable.doesClassExist(n.s))
            addError("Use of undefined identifier " + n.s, n.getLine(), n.getColumn());
    }
    public void visit(Block n)
    {
        for (int i = 0; i < n.sl.size(); ++i)
            n.sl.elementAt(i).accept(this);
    }
    public void visit(If n)
    {
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
    }
    public void visit(While n)
    {
        n.e.accept(this);
        n.s.accept(this);
    }
    public void visit(Print n)
    {
        n.e.accept(this);
    }
    public void visit(Assign n)
    {
        if (!symTable.doesSymbolExist(n.i.s))
            addError("Use of undefined identifier " + n.i.s, n.getLine(), n.getColumn());

        n.e.accept(this);
    }
    public void visit(ArrayAssign n)
    {
        if (!symTable.doesSymbolExist(n.i.s))
            addError("Use of undefined identifier " + n.i.s, n.getLine(), n.getColumn());

        n.e1.accept(this);
        n.e2.accept(this);
    }
    public void visit(And n)
    {
        n.e1.accept(this);
        n.e2.accept(this);
    }
    public void visit(LessThan n)
    {
        n.e1.accept(this);
        n.e2.accept(this);
    }
    public void visit(Plus n)
    {
        n.e1.accept(this);
        n.e2.accept(this);
    }
    public void visit(Minus n)
    {
        n.e1.accept(this);
        n.e2.accept(this);
    }
    public void visit(Times n)
    {
        n.e1.accept(this);
        n.e2.accept(this);
    }
    public void visit(ArrayLookup n)
    {
        n.e1.accept(this);
        n.e2.accept(this);
    }
    public void visit(ArrayLength n)
    {
        n.e.accept(this);
    }
    public void visit(Call n)
    {
        n.e.accept(this);
        for (int i = 0; i < n.el.size(); ++i)
            n.el.elementAt(i).accept(this);
    }

    public void visit(IdentifierExp n)
    {
        if (!symTable.doesSymbolExist(n.s))
            addError("Use of undefined identifier " + n.s, n.getLine(), n.getColumn());
    }
    public void visit(NewArray n)
    {
        n.e.accept(this);
    }
    public void visit(NewObject n)
    {
        if (!symTable.doesSymbolExist(n.i.s))
            addError("Use of undefined identifier " + n.i.s, n.getLine(), n.getColumn());
    }
    public void visit(Not n)
    {
        n.e.accept(this);
    }
    public void visit(Identifier n) {}
    public void visit(IntegerLiteral n) {}
    public void visit(True n) {}
    public void visit(False n) {}
    public void visit(IntArrayType n) {}
    public void visit(BooleanType n) {}
    public void visit(IntegerType n) {}
    public void visit(This n) {}
}
