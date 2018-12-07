package symboltable;
import syntaxtree.Type;

public class VariableSymbol extends SymbolInfo
{
    private Type type;

    public VariableSymbol(String name, Type t)
    {
        super(name);
        type = t;
    }

    public boolean LValue()
    {
        return true;
    }

    public boolean RValue()
    {
        return true;
    }

    public String getSymbolType()
    {
        return "variable";
    }

    public Type getType()
    {
        return type;
    }

    public String toString()
    {
        return getName() + " : " + type + "\n";
    }
}
