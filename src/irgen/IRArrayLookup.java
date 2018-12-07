package irgen;

import symboltable.SymbolInfo;

public class IRArrayLookup extends Quadruple{

  public IRArrayLookup(SymbolInfo arg1, SymbolInfo arg2, SymbolInfo result)
  {
    super(null, arg1, arg2, result);
  }

  public String toString()
  {
    String rslt = result.getName();
    rslt += " := " + arg1.getName() + "[" + arg2.getName() + "]";
    return rslt;
  }

}