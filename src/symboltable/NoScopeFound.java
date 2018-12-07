package symboltable;

public class NoScopeFound extends RuntimeException
{
    public NoScopeFound(String id)
    {
        super("Can't find scope " + id);
    }
}