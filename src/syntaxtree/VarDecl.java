package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class VarDecl extends MyASTNode{
  public Type t;
  public Identifier i;

  public VarDecl(Type at, Identifier ai) {
    super(0,0);
    t=at; i=ai;
  }

  public VarDecl(Type at, Identifier ai, int line, int col) {
    super(line, col);
    t=at; i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}