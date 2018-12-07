package irgen;

import symboltable.SymbolInfo;

public class IRArrayLength extends Quadruple{

  public IRArrayLength(SymbolInfo arg1, SymbolInfo result)
  {
    super("length", arg1, null, result);
  }

  public String toString()
  {
    String rslt = result.getName();
    rslt += " := " + op + " " + arg1.getName();
    return rslt;
  }

}