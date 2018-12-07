package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;

public class Assign extends Statement {
  public Identifier i;
  public Exp e;

  public Assign(Identifier ai, Exp ae) {
    super(0,0);
    i=ai; e=ae;
  }

  public Assign(Identifier ai, Exp ae, int line, int col) {
    super(line, col);
    i=ai; e=ae;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }

  public void accept(ThreeAddressCodeVisitor v) {
    v.visit(this);
  }
}