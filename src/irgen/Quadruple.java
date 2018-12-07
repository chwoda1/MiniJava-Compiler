package irgen;

import symboltable.*;
import syntaxtree.*;

// the generic storage format for IR
public class Quadruple {

  protected String op;
  protected SymbolInfo arg1;
  protected SymbolInfo arg2;
  protected SymbolInfo result;

  public Quadruple(String op, SymbolInfo arg1, SymbolInfo arg2, SymbolInfo result)
  {
    this.op = op;
    this.arg1 = arg1;
    this.arg2 = arg2;
    this.result = result;
  }

  public String toString(){
    return "";
  }

  public void setResult(SymbolInfo result)
  {
    this.result = result;
  }

  public SymbolInfo getArg1()
  {
    return arg1;
  }

  public SymbolInfo getArg2()
  {
    return arg2;
  }

  public SymbolInfo getResult()
  {
    return result;
  }

}
