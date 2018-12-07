package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;

public class Block extends Statement {
  public StatementList sl;

  public Block(StatementList asl) {
    super(0,0);
    sl=asl;
  }

  public Block(StatementList asl, int line, int col) {
    super(line, col);
    sl=asl;
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