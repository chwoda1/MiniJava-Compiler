package irgen;

import symboltable.SymbolInfo;

public class IRUnaryAssignment extends Quadruple{

  public IRUnaryAssignment(String op, SymbolInfo arg1, SymbolInfo result)
  {
    super(op, arg1, null, result);
  }

  public String toString()
  {
    String rslt = result.getName();
    rslt += " := " + op + " " + arg1.getName();
    return rslt;
  }

}
