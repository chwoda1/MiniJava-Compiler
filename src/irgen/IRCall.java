package irgen;

import symboltable.*;

public class IRCall extends Quadruple{

  private int paramLength;

  public IRCall(MethodSymbol arg1, int num, SymbolInfo result)
  {
    super("call", arg1, null, result);
    paramLength = num;
  }

  public String toString()
  {
      String rslt = "";
      if(result != null)
      {
        rslt = result.getName() + " := ";
      }      
      rslt += op + " " + arg1.getName() + ", " + Integer.toString(paramLength);
      return rslt;
  }

}
