package semanticanalysis;
import symboltable.*;
import syntaxtree.*;
import visitor.TypeVisitor;

import java.util.*;

public class TypeCheckVisitor extends ErrorBuilder implements TypeVisitor 
{
    private ISymbolTable symTable;

    public TypeCheckVisitor(ISymbolTable symbolTable)
    {
        symTable = symbolTable;
    }

    public Type visit(Program n)
    {
        n.m.accept(this);

        for (int i = 0; i < n.cl.size(); ++i)
            n.cl.elementAt(i).accept(this);

        return null;
    }
    public Type visit(MainClass n)
    {
        if (!symTable.doesClassExist(n.i1.s))
            return null;

        symTable.enterClass(n.i1.s);
        try
        {
            symTable.enterMethod("main");
            n.s.accept(this);
        }
        catch (NoScopeFound e)
        {
            symTable.exitClass();
            return null;
        }

        symTable.exitMethod();
        symTable.exitClass();
    

        return null;
    }
    public Type visit(ClassDeclSimple n)
    {
        if (!symTable.doesClassExist(n.i.s))
            return null;

        symTable.enterClass(n.i.s);

        if (n.vl != null)
        {
            for (int i = 0; i < n.vl.size(); ++i)
                n.vl.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.ml.size(); ++i)
            n.ml.elementAt(i).accept(this);

        symTable.exitClass();

        return null;
    }
    public Type visit(ClassDeclExtends n)
    {
        if (!symTable.doesClassExist(n.i.s))
            return null;

        symTable.enterClass(n.i.s);

        if (n.vl != null)
        {
            for (int i = 0; i < n.vl.size(); ++i)
                n.vl.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.ml.size(); ++i)
            n.ml.elementAt(i).accept(this);

        symTable.exitClass();

        return null;
    }

    public Type visit(MethodDecl n)
    {
        try
        {
            symTable.enterMethod(n.i.s);
        }
        catch (NoScopeFound e)
        {
            return null;
        }
        for (int i = 0; i < n.sl.size(); ++i)
            n.sl.elementAt(i).accept(this);

        n.e.accept(this);

        symTable.exitMethod();
        return null;
    }

    public Type visit(While n)
    {
        if (!(n.e.accept(this) instanceof BooleanType))
            addError("Non-boolean expression used as the condition of while statement", n.getLine(), n.getColumn());

        n.s.accept(this);
        return null;
    }
    public Type visit(If n)
    {
        if (!(n.e.accept(this) instanceof BooleanType))
            addError("Non-boolean expression used as the condition of if statement", n.getLine(), n.getColumn());

        n.s1.accept(this);
        n.s2.accept(this);

        return null;
    }
    public Type visit(Block n)
    {
        for (int i = 0; i < n.sl.size(); ++i)
            n.sl.elementAt(i).accept(this);

        return null;
    }
    public Type visit(Print n)
    {
        if (!(n.e.accept(this) instanceof IntegerType))
            addError("Call of method System.out.println does not match its declared signature", n.getLine(), n.getColumn());

        return null;
    }
    public Type visit(Assign n)
    {
        SymbolInfo s = symTable.getSymbol(n.i.s);

        if (s != null && !s.LValue())
        {
            addError("Invalid l-value, " + s.getName() + " is a " + s.getSymbolType(), n.getLine(), n.getColumn());
        }

        Type expType = n.e.accept(this);

        if (s instanceof VariableSymbol)
        {
            VariableSymbol variable = (VariableSymbol)s;
            Type variableType = variable.getType();

            if (!typeMismatchCheck(variableType, expType))
                addError("Type mismatch during assignment", n.getLine(), n.getColumn());
        }
        badRVal(n.e);

        return null;
    }

    public Type visit(ArrayAssign n)
    {
        if (!(n.e2.accept(this) instanceof IntegerType))
            addError("Type mismatch during assignment", n.getLine(), n.getColumn());

        badRVal(n.e2);

        return null;
    }
    public Type visit(ArrayLength n)
    {
        if (!(n.e.accept(this) instanceof IntArrayType))
            addError("Length property only applies to arrays", n.getLine(), n.getColumn());

        return new IntegerType();
    }
    public Type visit(And n)
    {
        if (!(n.e1.accept(this) instanceof BooleanType) || !(n.e2.accept(this) instanceof BooleanType))
            addError("Attempt to use boolean operator '&&' on non-boolean operands", n.getLine(), n.getColumn());

        return new BooleanType();
    }
    public Type visit(LessThan n)
    {
        nonIntCheck(n.e1, n.e2, "<");
        return new BooleanType();        
    }
    public Type visit(Plus n)
    {
        nonIntCheck(n.e1, n.e2, "+");
        return new IntegerType();
    }
    public Type visit(Minus n)
    {
        nonIntCheck(n.e1, n.e2, "-");
        return new IntegerType();
    }
    public Type visit(Times n)
    {
        nonIntCheck(n.e1, n.e2, "*");
        return new IntegerType();
    }
    public Type visit(ArrayLookup n)
    {
        return new IntegerType();
    }

    public Type visit(Call n)
    {
        if (n.e.accept(this) != null)
        {            
            if (symTable.getClass(n.e.accept(this).getName()) != null)
            {
                MethodSymbol method = symTable.getClass(n.e.accept(this).getName()).getMethod(n.i.s);
                if (method == null)
                {
                    addError("Attempt to call a non-method", n.getLine(), n.getColumn());
                }
                else
                {
                    List<Type> temp = new ArrayList<Type>();

                    for (int i = 0; i < n.el.size(); ++i)
                        temp.add(n.el.elementAt(i).accept(this));

                    List<Type> typesOfExps = temp;
                    List<Type> formalTypes = method.getFormalTypes();

                    if (typesOfExps.size() != formalTypes.size())
                    {
                        addError("Call of method " + method.getName() + " does not match its declared number of arguments", n.getLine(), n.getColumn());
                    }                    
                    else
                    {
                        for (int i = 0; i < typesOfExps.size(); ++i)
                        {
                            if (!typeMismatchCheck(typesOfExps.get(i), formalTypes.get(i)))
                            {
                                addError("Call of method " + method.getName() + " does not match its declared signature", n.getLine(), n.getColumn());
                                break;
                            }
                        }                        
                    }
                    return method.getReturnType();
                }           
            }
        }
        return null;
    }
    public Type visit(IntegerLiteral n)
    {
        return new IntegerType();
    }
    public Type visit(True n)
    {
        return new BooleanType();
    }
    public Type visit(False n)
    {
        return new BooleanType();
    }
    public Type visit(IdentifierExp n)
    {
        SymbolInfo symbol = symTable.getSymbol(n.s);
        if (symbol instanceof VariableSymbol)
        {
            VariableSymbol varSymbol = (VariableSymbol)symbol;
            return varSymbol.getType();
        }
        return null;
    }
    public Type visit(This n)
    {
        if (symTable.getCurrentMethod().getName().equals("main"))
            addError("Illegal use of the keyword 'this' in static method", n.getLine(), n.getColumn());

        return new IdentifierType(symTable.getCurrentClass().getName());
    }
    public Type visit(NewArray n)
    {
        return new IntArrayType();
    }
    public Type visit(NewObject n)
    {
        return new IdentifierType(n.i.s);
    }
    public Type visit(Not n)
    {
        Type expType = n.e.accept(this);
        if (!(expType instanceof BooleanType))
            addError("Attempt to use boolean operator '!' on non-boolean operand", n.getLine(), n.getColumn());

        return new BooleanType();
    }
    public Type visit(Identifier n)
    {
        return null;
    }
    public Type visit(Formal n)
    {
        return null;
    }
    public Type visit(IntArrayType n)
    {
        return null;
    }
    public Type visit(BooleanType n)
    {
        return null;
    }
    public Type visit(IntegerType n)
    {
        return null;
    }
    public Type visit(IdentifierType n)
    {
        return null;
    }
    public Type visit(VarDecl n)
    {
        return null;
    }

    private void nonIntCheck(Exp lhs, Exp rhs, String op)
    {
        if (!(lhs.accept(this) instanceof IntegerType)){
            addError("Non-integer operand for operator " + op, lhs.getLine(), lhs.getColumn());
        }else if(!(rhs.accept(this) instanceof IntegerType)){
            addError("Non-integer operand for operator " + op, rhs.getLine(), rhs.getColumn());
        }
    }
    
    private void badRVal(Exp rhs)
    {
        if (rhs instanceof IdentifierExp)
        {
            SymbolInfo symbol = symTable.getSymbol(((IdentifierExp)rhs).s);
            if (symbol != null && !symbol.RValue())                        
                addError("Invalid r-value: " + symbol.getName() + " is a " + symbol.getSymbolType(), rhs.getLine(), rhs.getColumn());
        }
    }

    private boolean typeMismatchCheck(Type lhs, Type rhs)
    {
        if (lhs instanceof IdentifierType && rhs instanceof IdentifierType)
        {
            ClassSymbol lhsClass = symTable.getClass(lhs.getName());
            ClassSymbol rhsClass = symTable.getClass(rhs.getName());

            while (rhsClass != null && !lhsClass.getName().equals(rhsClass.getName()))
            {
                String parent = rhsClass.getParentName();
                if(parent == null){
                    rhsClass = null;
                }else{
                    rhsClass = symTable.getClass(parent);
                }
            }
            return rhsClass != null;
        }
        return lhs.equals(rhs);
    }
}
