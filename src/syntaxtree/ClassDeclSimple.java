package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;

public class ClassDeclSimple extends ClassDecl {
  public Identifier i;
  public VarDeclList vl;
  public MethodDeclList ml;

  public ClassDeclSimple(Identifier ai, VarDeclList avl, MethodDeclList aml) {
    super(0,0);
    i=ai; vl=avl; ml=aml;
  }

  public ClassDeclSimple(Identifier ai, VarDeclList avl, MethodDeclList aml, int line, int col) {
    super(line, col);
    i=ai; vl=avl; ml=aml;
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