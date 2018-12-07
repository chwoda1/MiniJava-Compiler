package irgen;

import symboltable.SymbolInfo;

public class IRArrayAssign extends Quadruple{

  public IRArrayAssign(SymbolInfo arg1, SymbolInfo arg2, SymbolInfo result)
  {
    super(null, arg1, arg2, result);
  }

  public String toString()
  {
    String rslt = arg1.getName();
    rslt += "[" + arg2.getName() + "]" + " := " + result.getName();
    return rslt;
  }

}
