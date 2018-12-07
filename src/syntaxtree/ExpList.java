package syntaxtree;

import java.util.Vector;

public class ExpList extends MyASTNode{
   private Vector list;

   public ExpList() {
     super(0,0);
      list = new Vector();
   }

   public ExpList(int line, int col) {
      super(line, col);
      list = new Vector();
   }

   public void addElement(Exp n) {
      list.addElement(n);
   }

   public void addFront(Exp n) {
      list.add(0, n);
   }

   public Exp elementAt(int i)  {
      return (Exp)list.elementAt(i);
   }

   public int size() {
      return list.size();
   }
}