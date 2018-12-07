package syntaxtree;

import java.util.Vector;

public class MethodDeclList extends MyASTNode{
   private Vector list;

   public MethodDeclList() {
     super(0,0);
      list = new Vector();
   }

   public MethodDeclList(int line, int col) {
      super(line, col);
      list = new Vector();
   }

   public void addElement(MethodDecl n) {
      list.addElement(n);
   }

   public void addFront(MethodDecl n) {
      list.add(0, n);
   }

   public MethodDecl elementAt(int i)  {
      return (MethodDecl)list.elementAt(i);
   }

   public int size() {
      return list.size();
   }
}