package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;
import symboltable.SymbolInfo;

public abstract class Exp extends MyASTNode{

  public Exp(int line, int col) {
    super(line, col);
  }

  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);
  public abstract SymbolInfo accept(ThreeAddressCodeVisitor v);
}