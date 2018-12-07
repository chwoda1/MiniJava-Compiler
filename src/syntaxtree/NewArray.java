package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;
import symboltable.SymbolInfo;

public class NewArray extends Exp {
  public Exp e;

  public NewArray(Exp ae) {
    super(0,0);
    e=ae;
  }

  public NewArray(Exp ae, int line, int col) {
    super(line, col);
    e=ae;
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
