package irgen;

import symboltable.SymbolInfo;

public class IRReturn extends Quadruple{

  public IRReturn(SymbolInfo arg1)
  {
    super("return", arg1, null, null);
  }

  public String toString()
  {
    String rslt = op + " " + arg1.getName();
    return rslt;
  }

}