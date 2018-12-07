package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;

public class While extends Statement {
  public Exp e;
  public Statement s;

  public While(Exp ae, Statement as) {
    super(0,0);
    e=ae; s=as;
  }

  public While(Exp ae, Statement as, int line, int col) {
    super(line, col);
    e=ae; s=as;
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