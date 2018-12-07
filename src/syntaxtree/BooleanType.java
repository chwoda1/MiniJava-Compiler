package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;


public class BooleanType extends Type {

  public BooleanType()
  {
    super("boolean");
  }

  public BooleanType(int line, int col)
  {
    super("boolean", line, col);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}