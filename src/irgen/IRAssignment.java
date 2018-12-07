package irgen;

import symboltable.SymbolInfo;

public class IRAssignment extends Quadruple{

  public IRAssignment(String op, SymbolInfo arg1, SymbolInfo arg2, SymbolInfo result)
  {
    super(op, arg1, arg2, result);
  }

  public String toString()
  {
    String rslt = result.getName();
    rslt += " := " + arg1.getName() + " " + op + " " + arg2.getName();
    return rslt;
  }

}