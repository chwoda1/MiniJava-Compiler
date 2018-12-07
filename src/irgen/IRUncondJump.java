package irgen;

import symboltable.SymbolInfo;

public class IRUncondJump extends Quadruple{

  private String label;

  public IRUncondJump(String label)
  {
    super("goto", null, null, null);
    this.label = label;
  }

  public String toString()
  {
    String rslt = op + " " + label;
    return rslt;
  }

}