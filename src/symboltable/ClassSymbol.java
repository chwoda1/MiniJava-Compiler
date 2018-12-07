package symboltable;
import java.util.*;

public class ClassSymbol extends SymbolInfo
{
    private Map<String, MethodSymbol> methods = new HashMap<String, MethodSymbol>();
    private Map<String, VariableSymbol> vars = new HashMap<String, VariableSymbol>();
    private ClassSymbol parentClass;
    private String parentName;
    

    public ClassSymbol(String name)
    {
        this(name, null);
    }

    public ClassSymbol(String name, String parent)
    {
        super(name);
        parentName = parent;
    }

    public MethodSymbol getMethod(String id)
    {

        if((parentClass != null) && !(methods.containsKey(id)) ){

		return parentClass.getMethod(id);
        }
	
        return methods.get(id);
    }

    public MethodSymbol addMethod(MethodSymbol symbol)
    {
        MethodSymbol replacedSym = methods.get(symbol.getName());
        methods.put(symbol.getName(), symbol);
        return replacedSym;
    }

    public VariableSymbol getVariable(String id)
    {
        if(!(vars.containsKey(id)) && (parentClass != null)){
        	return parentClass.getVariable(id);
        }
          
        return vars.get(id);
    }

    public SymbolInfo getSymbol(String id)
    {
        SymbolInfo symbol = getMethod(id);
        if(symbol != null){
            return symbol;
        }else{
            return getVariable(id);
        }
    }

    public VariableSymbol addVariable(VariableSymbol symbol)
    {
        VariableSymbol old = vars.get(symbol.getName());
        vars.put(symbol.getName(), symbol);
        return old;
    }


    public String getParentName()
    {
        return parentName;
    }

    public void setParentClass(ClassSymbol classSym)
    {
      parentClass = classSym;
    }

    public ClassSymbol getParentClass()
    {
      return parentClass;
    }

    public String toString()
    {
        StringBuilder result = new StringBuilder();

        result.append(getName());
        result.append(" : ");
        result.append(getParentName());
        result.append(" {\n");

        for (VariableSymbol variable : vars.values()){
        	result.append(variable.toString());
        }
            
        result.append("\n");

        for (MethodSymbol method : methods.values()){
        	result.append(method.toString());
        }
            
        result.append("}\n");
        return result.toString();
    }

    public boolean LValue()
    {
        return false;
    }

    public boolean RValue()
    {
        return false;
    }

    public String getSymbolType()
    {
        return "class";
    }
}
