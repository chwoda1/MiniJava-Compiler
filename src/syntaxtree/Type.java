package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public abstract class Type extends MyASTNode{
  private String name;

  public Type(String name)
  {
    super(0,0);
    this.name = name;
  }

  public Type(String name, int line, int col)
  {
    super(line, col);
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);

  public boolean equals(Object o)
  {
    if (o instanceof Type)
    {
      Type other = (Type)o;
      return name.equals(other.getName());
    }
    return false;
  }

  public String toString()
  {
    return name;
  }

}