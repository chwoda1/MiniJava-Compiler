package irgen;

import symboltable.SymbolInfo;

public class IRLabel extends Quadruple{

  String label;

  public IRLabel(String label)
  {
    super(null, null, null, null);
    this.label = label;
  }

  public String toString()
  {
    String rslt = label + ":";
    return rslt;
  }

}