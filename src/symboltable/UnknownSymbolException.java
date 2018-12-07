package symboltable;

public class UnknownSymbolException extends Exception 
{
    public UnknownSymbolException(String id)
    {
        super("unknown symbol " + id);
    }
}