package irgen;

import symboltable.SymbolInfo;

public class IRCopy extends Quadruple{

  public IRCopy(SymbolInfo arg1, SymbolInfo result)
  {
    super(null, arg1, null, result);
  }

  public String toString()
  {
    String rslt = result.getName();
    rslt += " := " + arg1.getName();
    return rslt;
  }

}