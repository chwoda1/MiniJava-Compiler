package irgen;

import symboltable.SymbolInfo;

public class IRParam extends Quadruple{

  public IRParam(SymbolInfo arg1)
  {
    super("param", arg1, null, null);
  }

  public String toString()
  {
    String rslt = op + " " + arg1.getName();
    return rslt;
  }

}
