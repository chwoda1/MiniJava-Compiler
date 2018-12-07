package irgen;

import symboltable.SymbolInfo;

public class IRNewObject extends Quadruple{

  public IRNewObject(SymbolInfo arg1, SymbolInfo result)
  {
    super("new", arg1, null, result);
  }

  public String toString()
  {
    String rslt = result.getName();
    rslt += " := " + op + " " + arg1.getName();
    return rslt;
  }

}
