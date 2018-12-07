package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;

public class MethodDecl extends MyASTNode{
  public Type t;
  public Identifier i;
  public FormalList fl;
  public VarDeclList vl;
  public StatementList sl;
  public Exp e;

  public MethodDecl(Type at, Identifier ai, FormalList afl, VarDeclList avl,
                    StatementList asl, Exp ae) {
    super(0,0);
    t=at; i=ai; fl=afl; vl=avl; sl=asl; e=ae;
  }

  public MethodDecl(Type at, Identifier ai, FormalList afl, VarDeclList avl,
                    StatementList asl, Exp ae, int line, int col)
  {
    super(line, col);
    t=at; i=ai; fl=afl; vl=avl; sl=asl; e=ae;
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