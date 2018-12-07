package irgen;
import java.util.ArrayList;
import syntaxtree.*;
import symboltable.*;
import visitor.*;

public class ThreeAddressCodeVisitor {

  private ArrayList<Quadruple> listOfQuads;
  private SymbolTable symTable;
  private int registerCount;
  private int ifLabel;
  private int loopLabel;
  private int endLoopLabel;


  public ThreeAddressCodeVisitor(SymbolTable symbols)
  {
    listOfQuads = new ArrayList<Quadruple>();
    registerCount = 0;
    symTable = symbols;
    ifLabel = 0;
    loopLabel = 0;
    endLoopLabel = 0;
  }

  public SymbolInfo visit(Program n)
  {
    n.m.accept(this);
    for ( int i = 0; i < n.cl.size(); i++ ) {
      n.cl.elementAt(i).accept(this);
    }
    return null;
  }

  public SymbolInfo visit(MainClass n)
  {
    listOfQuads.add(new IRLabel("main"));
    symTable.enterClass(n.i1.s);
    n.s.accept(this);
    symTable.exitClass();
    return null;
  }

  public SymbolInfo visit(ClassDeclSimple n)
  {
    symTable.enterClass(n.i.s);
    symTable.addVariable(new VariableSymbol("this", new IdentifierType(n.i.s)));

    for(int i = 0; i < n.ml.size(); i++){
      n.ml.elementAt(i).accept(this);
    }
      
    symTable.exitClass(); 
    return null;
  }

  public SymbolInfo visit(ClassDeclExtends n)
  {
    symTable.enterClass(n.i.s);
    symTable.addVariable(new VariableSymbol("this", new IdentifierType(n.i.s)));

    for(int i = 0; i < n.ml.size(); i++){
      n.ml.elementAt(i).accept(this);
    }
    
    symTable.exitClass();
    return null;
  }

  public SymbolInfo visit(MethodDecl n)
  {
    ifLabel = 0;
    loopLabel = 0;
    endLoopLabel = 0;
    symTable.enterMethod(n.i.s); 
    listOfQuads.add(new IRLabel(getMethodTag()));

    for(int i = 0; i < n.sl.size(); i++)
    {
      n.sl.elementAt(i).accept(this);
    }
    listOfQuads.add(new IRReturn(n.e.accept(this)));
    symTable.exitMethod();
    return null;
  }

  public SymbolInfo visit(Block n){
    for(int i = 0; i < n.sl.size(); i++)
    {
      n.sl.elementAt(i).accept(this);
    }
    return null;
  }

  public SymbolInfo visit(If n)
  {
    String elseLabel = createLabel("else");
    String endIfLabel = createLabel("endif");
    listOfQuads.add(new IRCondJump(n.e.accept(this), elseLabel));
    n.s1.accept(this);
    listOfQuads.add(new IRUncondJump(endIfLabel));
    listOfQuads.add(new IRLabel(elseLabel));
    n.s2.accept(this);
    listOfQuads.add(new IRLabel(endIfLabel));
    return null;
  }

  public SymbolInfo visit(While n)
  {
    String loopLabel = createLabel("loop");
    String endLoopLabel = createLabel("endloop");
    listOfQuads.add(new IRLabel(loopLabel));
    listOfQuads.add(new IRCondJump(n.e.accept(this), endLoopLabel));
    n.s.accept(this);
    listOfQuads.add(new IRUncondJump(loopLabel));
    listOfQuads.add(new IRLabel(endLoopLabel));
    return null;
  }

  public SymbolInfo visit(Print n)
  {
    listOfQuads.add(new IRParam(n.e.accept(this)));
    listOfQuads.add(new IRCall(new MethodSymbol("System.out.println", null), 1, null));
    return null;
  }

  public SymbolInfo visit(ArrayAssign n)
  {
    listOfQuads.add(new IRArrayAssign(n.i.accept(this), n.e1.accept(this), n.e2.accept(this)));
    return null;
  }

  public SymbolInfo visit(Assign n)
  {
    SymbolInfo result = n.i.accept(this);
    listOfQuads.add(new IRCopy(n.e.accept(this), result));
    return result;

  }

  public SymbolInfo visit(LessThan n)
  {
    SymbolInfo result = createNewRegister(new BooleanType());
    listOfQuads.add(new IRAssignment("<", n.e1.accept(this), n.e2.accept(this), result));
    return result;
  }

  public SymbolInfo visit(Times n)
  {
    SymbolInfo result = createNewRegister(new IntegerType());
    listOfQuads.add(new IRAssignment("*", n.e1.accept(this), n.e2.accept(this), result));
    return result;
  }

  public SymbolInfo visit(Plus n)
  {
    SymbolInfo result = createNewRegister(new IntegerType());
    listOfQuads.add(new IRAssignment("+", n.e1.accept(this), n.e2.accept(this), result));
    return result;
  }

  public SymbolInfo visit(Minus n)
  {
    SymbolInfo result = createNewRegister(new IntegerType());
    listOfQuads.add(new IRAssignment("-", n.e1.accept(this), n.e2.accept(this), result));
    return result;
  }

  public SymbolInfo visit(And n)
  {
    SymbolInfo result = createNewRegister(new BooleanType());
    listOfQuads.add(new IRAssignment("&&", n.e1.accept(this), n.e2.accept(this), result));
    return result;
  }

  public SymbolInfo visit(ArrayLookup n)
  {
    SymbolInfo result = createNewRegister(new IntegerType());
    listOfQuads.add(new IRArrayLookup(n.e1.accept(this), n.e2.accept(this), result));
    return result;
  }

  public SymbolInfo visit(ArrayLength n)
  {
    SymbolInfo result = createNewRegister(new IntegerType());
    listOfQuads.add(new IRArrayLength(n.e.accept(this), result));
    return result;
  }

  // e = expression
  // i = identifier
  // el = expression list
  public SymbolInfo visit(Call n)
  {
    ArrayList<IRParam> arguments = new ArrayList<IRParam>();

    // get the type of the expression 
    VariableSymbol thisClass = (VariableSymbol) n.e.accept(this);
    arguments.add(new IRParam(thisClass));

    for(int i = 0; i < n.el.size(); i++)
    {
      arguments.add(new IRParam(n.el.elementAt(i).accept(this)));
    }

    for(IRParam ir : arguments)
    {
      listOfQuads.add(ir);
    }

    IdentifierType thisClassType = (IdentifierType) (thisClass.getType());

    ClassSymbol className = symTable.getClass(thisClassType.s);
    MethodSymbol methodName = className.getMethod(n.i.s); 

    SymbolInfo result = createNewRegister(methodName.getReturnType());
    listOfQuads.add(new IRCall(methodName, n.el.size() + 1, result));
    return result;
  }



  public SymbolInfo visit(True n)
  {
      return new BlankSymbol("true", new BooleanType());
  }

  public SymbolInfo visit(False n)
  {
    return new BlankSymbol("false", new BooleanType());
  }

  public SymbolInfo visit(IntegerLiteral n)
  {
    return new BlankSymbol(""+n.i, new IntegerType());
  }

  public SymbolInfo visit(Identifier n)
  {
    return symTable.getSymbol(n.s);
  }

  public SymbolInfo visit(IdentifierExp n)
  {
    return symTable.getSymbol(n.s);
  }

  public SymbolInfo visit(This n)
  {
    return symTable.getSymbol("this");
  }

  public SymbolInfo visit(NewArray n) {
  
	  SymbolInfo result = createNewRegister(null);
	  listOfQuads.add(new IRNewArray(n.e.accept(this) , result)); 
	  return result; 
  }

  public SymbolInfo visit(NewObject n)
  {
    SymbolInfo result = createNewRegister(new IdentifierType(n.i.s));
    listOfQuads.add(new IRNewObject(n.i.accept(this), result));
    return result;
  }

  public SymbolInfo visit(Not n)
  {
    SymbolInfo result = createNewRegister(new BooleanType());
    listOfQuads.add(new IRUnaryAssignment("!", n.e.accept(this), result));
    return result;
  }

  public ArrayList<Quadruple> getIRList()
  {
    return listOfQuads;
  }

  private VariableSymbol createNewRegister(Type type)
  {
    VariableSymbol reg = new VariableSymbol("$t" + registerCount++, type); 
    symTable.addVariable(reg); 
    return reg;
  }

  public String getMethodTag()
  {
    MethodSymbol sym = symTable.getCurrentMethod();
    String methodLabel = symTable.getCurrentClass().getName();
    methodLabel += "_" + sym.getName();

    for(Type t : sym.getFormalTypes())
    {
      methodLabel += "_" + t.toString();
    }

    return methodLabel;
  }

  public String createLabel(String typeOfLabel){
    String rslt = getMethodTag();
    switch(typeOfLabel){
      case "else":{
        rslt += "_ELSE _" + ifLabel;
        break;
      }
      case "endif":{
        rslt += "_ENDIF_" + ifLabel++;
        break;
      }
      case "loop":{
        rslt += "_LOOP_" + loopLabel++;
        break;
      }
      case "endloop":{
        rslt += "_ENDLOOP_" + endLoopLabel++;
        break;
      }
      default:
        break;
    }
    return rslt;
  }

  public void print3AddCode()
  {
    System.out.println("Lines: " + listOfQuads.size());
    for(Quadruple ir : listOfQuads)
    {
      System.out.println(ir.toString());
    }
  }

}
