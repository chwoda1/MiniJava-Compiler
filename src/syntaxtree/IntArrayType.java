package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class IntArrayType extends Type {

  public IntArrayType()
  {
    super("int[]", 0,0);
  }

  public IntArrayType(int line, int col)
  {
    super("int[]", line, col);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }

}