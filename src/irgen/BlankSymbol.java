package irgen;

import symboltable.*;
import syntaxtree.*;

public class BlankSymbol extends VariableSymbol
{

  public BlankSymbol (String name, Type type)
  {
    super(name, type);
  }
  public String getSymbolType(){
  	return "constant";
  }

  public String getValue()
  {
    return getName();
  }

}