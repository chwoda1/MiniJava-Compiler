package symboltable;
import syntaxtree.Type;
import java.util.*;

public class MethodSymbol extends SymbolInfo 
{
    private Map<String, VariableSymbol> formalParams = new LinkedHashMap<String, VariableSymbol>();
    private Map<String, VariableSymbol> localVars = new HashMap<String, VariableSymbol>();

    private Type returnType;

    public MethodSymbol(String name, Type returnType)
    {
        super(name);
        this.returnType = returnType;
    }

    public VariableSymbol addFormalVar(VariableSymbol formal)
    {
        String varName = formal.getName();
        VariableSymbol var = formalParams.put(varName, formal);

        if (var == null){
        	var = localVars.get(varName);
        }

        return var;
    }

    public VariableSymbol addLocalVar(VariableSymbol symbol)
    {
        String varName = symbol.getName();
        VariableSymbol var = localVars.put(varName, symbol);
        
        if (var == null){
        	var = formalParams.get(varName);
        }
        
        return var;
    }

    public VariableSymbol getVariable(String id)
    {
        if (formalParams.containsKey(id))
            return formalParams.get(id);
        return localVars.get(id);
    }

    public boolean doesVariableExist(String id)
    {
        return getVariable(id) != null;
    }


    public String toString()
    {
        StringBuilder result = new StringBuilder();

        result.append(getName());
        if (formalParams.isEmpty())
        {
            result.append("()");
        }
        else
        {
            result.append('(');
            for (VariableSymbol formal : formalParams.values())
            {
                result.append(formal.toString());
                result.append(',');
            }

            result.setCharAt(result.length() - 1, ')');
        }
        
        result.append(" : ");
        if (returnType != null){
        	result.append(returnType.getName());
        }
            
        result.append(" {\n");

        for (VariableSymbol local : localVars.values()){
        	result.append(local.toString());  
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
        return "method";
    }

    public Type getReturnType()
    {
        return returnType;
    }

    public List<Type> getFormalTypes()
    {
        List<Type> result = new ArrayList<Type>();

        for (String key : formalParams.keySet()){
        	result.add(formalParams.get(key).getType());
        }
            
        return result;
    }
}
