package irgen;

import symboltable.SymbolInfo;

public class IRCondJump extends Quadruple{

  private String label;

  public IRCondJump(SymbolInfo arg1, String label)
  {
    super("goto", arg1, null, null);
    this.label = label;
  }

  public String toString()
  {
    String rslt = "iffalse";
    rslt += " := " + arg1.getName() + " " + op + " " + label;
    return rslt;
  }

}