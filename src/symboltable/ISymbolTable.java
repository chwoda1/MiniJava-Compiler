package symboltable;

public interface ISymbolTable{

    public void enterClass(String id);
    
    public void exitClass();

    public void enterMethod(String id);
    
    public void exitMethod();

    public SymbolInfo getSymbol(String id);

    public boolean doesSymbolExist(String id);

    public ClassSymbol getClass(String id);

    public boolean doesClassExist(String id);

    public ClassSymbol getCurrentClass();

    public MethodSymbol getCurrentMethod();
}
