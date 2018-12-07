package symboltable;
import java.util.*;

public class SymbolTable implements ISymbolTable
{
    private Map<String, ClassSymbol> classes = new HashMap<String, ClassSymbol>();

    private ClassSymbol currClass = null;
    private MethodSymbol currMethod = null;

    public SymbolInfo getSymbol(String id)
    {
        if (currMethod != null)
        {
            VariableSymbol symbol = currMethod.getVariable(id);
            if (symbol != null){
                return symbol;
            }
        }

        if (currClass != null)
        {
            ClassSymbol currentClass = currClass;
            while (currentClass != null)
            {
                SymbolInfo symbol = currClass.getSymbol(id);
                if (symbol != null){
                    return symbol;
                }
                currentClass = classes.get(currentClass.getParentName());
            }
        }
        return getClass(id);
    }

    public void enterClass(String id)
    {
        if (currClass != null || currMethod != null){
            throw new IllegalStateException("Already in a class scope.  Cannot enter another.");
        }
            
        ClassSymbol classInfo = classes.get(id);
        if (classInfo == null){
            throw new NoScopeFound(id);
        }
            
        currClass = classInfo;
    }

    public void enterMethod(String id)
    {
        if (currClass == null){
            throw new IllegalStateException("Need to be inside of a class scope to enter a method scope.");
        }

        MethodSymbol method = currClass.getMethod(id);
        if (method == null){
            throw new NoScopeFound(id);
        }
        currMethod = method;
    }

    public void exitClass()
    {
        if (currClass == null){
            throw new IllegalStateException("Not in a class scope.");
        }
        currClass = null;
    }

    public void exitMethod()
    {
        if (currMethod == null){
            throw new IllegalStateException("Not in a method scope.");
        }
        currMethod = null;
    }

    public boolean doesClassExist(String id)
    {
        return getClass(id) != null;
    }

    public ClassSymbol getClass(String id)
    {
        return classes.get(id);
    }

    public ClassSymbol getCurrentClass()
    {
        return currClass;
    }

    public MethodSymbol getCurrentMethod()
    {
        return currMethod;
    }

    public ClassSymbol addClass(ClassSymbol symbol)
    {
        return classes.put(symbol.getName(), symbol);
    }

    public MethodSymbol addMethod(MethodSymbol symbol)
    {
        if (currClass == null){
            throw new IllegalStateException("Must be inside class scope to add a method scope.");
        } 
        return currClass.addMethod(symbol);
    }

    public VariableSymbol addVariable(VariableSymbol symbol)
    {
        if (currMethod != null){
            return currMethod.addLocalVar(symbol);
        }

        if (currClass != null){
            return currClass.addVariable(symbol);
        }
        throw new IllegalStateException("Must be inside a class or method scope to add variables.");
    }

    public void resolveInheritance()
    {
      for(ClassSymbol classSym : classes.values())
      {
        if(classSym.getParentName() != null){
            classSym.setParentClass(getClass(classSym.getParentName()));
        }
      }
    }

    public boolean doesSymbolExist(String id)
    {
        return getSymbol(id) != null;
    }

    public String toString()
    {
        StringBuilder result = new StringBuilder();

        for (ClassSymbol currentClass : classes.values()){
            result.append(currentClass.toString());
        }
        return result.toString();
    }
}
