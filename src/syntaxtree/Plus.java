package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;
import symboltable.SymbolInfo;

public class Plus extends Exp {
  public Exp e1,e2;

  public Plus(Exp ae1, Exp ae2) {
    super(0,0);
    e1=ae1; e2=ae2;
  }

  public Plus(Exp ae1, Exp ae2, int line, int col) {
    super(line, col);
    e1=ae1; e2=ae2;
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
