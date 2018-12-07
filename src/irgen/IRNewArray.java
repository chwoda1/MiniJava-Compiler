package irgen;

import symboltable.SymbolInfo;

public class IRNewArray extends Quadruple{

  public IRNewArray(SymbolInfo arg2, SymbolInfo result)
  {
    super("new", null, arg2, result);
  }

  public String toString()
  {
    String rslt = result.getName();
    rslt += " := " + op + " " + "int" + ", " + arg2.getName();
    return rslt;
  }

}