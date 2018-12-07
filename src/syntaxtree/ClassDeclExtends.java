package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;
import irgen.ThreeAddressCodeVisitor;

public class ClassDeclExtends extends ClassDecl {
  public Identifier i;
  public Identifier j;
  public VarDeclList vl;
  public MethodDeclList ml;

  public ClassDeclExtends(Identifier ai, Identifier aj,
                  VarDeclList avl, MethodDeclList aml) {
    super(0,0);
    i=ai; j=aj; vl=avl; ml=aml;
  }

  public ClassDeclExtends(Identifier ai, Identifier aj,
                  VarDeclList avl, MethodDeclList aml, int line, int col)
  {
    super(line, col);
    i=ai; j=aj; vl=avl; ml=aml;
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
