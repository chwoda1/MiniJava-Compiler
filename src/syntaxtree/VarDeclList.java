package syntaxtree;
import java.util.Vector;


public class VarDeclList extends MyASTNode{
   private Vector list;

   public VarDeclList() {
     super(0,0);
      list = new Vector();
   }

   public VarDeclList(int line, int col) {
      super(line, col);
      list = new Vector();
   }

   public void addElement(VarDecl n) {
      list.addElement(n);
   }

   public void addFront(VarDecl n) {
      list.add(0, n);
   }

   public VarDecl elementAt(int i)  {
      return (VarDecl)list.elementAt(i);
   }

   public int size() {
      return list.size();
   }

}