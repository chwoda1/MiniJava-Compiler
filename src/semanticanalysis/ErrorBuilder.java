package semanticanalysis;

import java.util.*;

public abstract class ErrorBuilder
{
    private List<String> errors = new ArrayList<String>();

    public List<String> getErrors()
    {
        return errors;
    }

    protected void addError(String error, int line, int column)
    {
        String rslt = error + " at line " + line + ", character " + column;
        errors.add(rslt);
    }
}
