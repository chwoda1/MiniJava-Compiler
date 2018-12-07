package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;

public abstract class Statement extends MyASTNode{

  public Statement(int line, int col)
  {
      super(line, col);
  }

  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);
  public abstract void accept(ThreeAddressCodeVisitor v);
}