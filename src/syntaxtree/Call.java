package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;
import symboltable.SymbolInfo;

public class Call extends Exp {
  public Exp e;
  public Identifier i;
  public ExpList el;

  public Call(Exp ae, Identifier ai, ExpList ael) {
    super(0,0);
    e=ae; i=ai; el=ael;
  }

  public Call(Exp ae, Identifier ai, ExpList ael, int line, int col) {
    super(line, col);
    e=ae; i=ai; el=ael;
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