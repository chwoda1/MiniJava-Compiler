package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;
import symboltable.SymbolInfo;

public class This extends Exp {

  public This(){ super(0,0); }

  public This(int line, int col) {
    super(line, col);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }

  public SymbolInfo accept(ThreeAddressCodeVisitor v) {
    return v.visit(this);
  }
}